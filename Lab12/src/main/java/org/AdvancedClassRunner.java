package org;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.lang.annotation.Annotation;

public class AdvancedClassRunner {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please provide the absolute path to the folder containing .class files.");
            return;
        }

        String folderPath = String.join(" ", args).replace("\"", "");
        Path root = Paths.get(folderPath);

        if (!Files.exists(root) || !Files.isDirectory(root)) {
            System.out.println("Error: Provided path is not a valid directory.");
            return;
        }

        try {
            URL url = root.toUri().toURL();
            URLClassLoader classLoader = new URLClassLoader(new URL[]{url});

            List<Class<?>> annotationTypes = new ArrayList<>();
            List<Class<?>> publicClasses = new ArrayList<>();

            Files.walk(root)
                    .filter(p -> p.toString().endsWith(".class"))
                    .forEach(p -> {
                        try {
                            String relativePath = root.relativize(p).toString();
                            String className = relativePath.replace(".class", "").replace(File.separatorChar, '.');

                            Class<?> clazz = classLoader.loadClass(className);

                            if (clazz.isAnnotation()) {
                                annotationTypes.add(clazz);
                            } else if (Modifier.isPublic(clazz.getModifiers()) && !clazz.isInterface() && !clazz.isEnum()) {
                                publicClasses.add(clazz);
                            }
                        } catch (Exception | Error e) {
                            System.out.println("Skipped un-loadable class file: " + p.getFileName());
                        }
                    });

            System.out.println("=== 1. Found Annotation Types ===");
            annotationTypes.forEach(a -> System.out.println(" @" + a.getName()));

            System.out.println("\n=== 2. Public Class Prototypes ===");
            for (Class<?> clazz : publicClasses) {
                System.out.println("Class: " + clazz.getName());
                for (Method method : clazz.getDeclaredMethods()) {
                    System.out.println("  " + method.toString());
                }
            }

            System.out.println("\n=== 3. Invoking Annotated Methods ===");
            for (Class<?> clazz : publicClasses) {
                Object instance = null;
                for (Method method : clazz.getDeclaredMethods()) {

                    boolean hasDiscoveredAnnotation = false;
                    for (Annotation ann : method.getAnnotations()) {
                        if (annotationTypes.contains(ann.annotationType())) {
                            hasDiscoveredAnnotation = true;
                            break;
                        }
                    }

                    if (hasDiscoveredAnnotation) {
                        if (instance == null) {
                            try {
                                instance = clazz.getDeclaredConstructor().newInstance();
                            } catch (Exception e) {
                                System.out.println("  [Warning] Cannot instantiate " + clazz.getName() + " - missing no-arg constructor.");
                                break; // Break out of this class's methods if we can't create it
                            }
                        }

                        int paramCount = method.getParameterCount();
                        if (paramCount == 0) {
                            System.out.println("  -> Executing " + method.getName() + "() on " + clazz.getSimpleName());
                            method.invoke(instance);
                        } else if (paramCount == 1 && (method.getParameterTypes()[0] == int.class || method.getParameterTypes()[0] == Integer.class)) {
                            int mockValue = 42;
                            System.out.println("  -> Executing " + method.getName() + "(int) on " + clazz.getSimpleName() + " with mock value: " + mockValue);
                            method.invoke(instance, mockValue);
                        }
                    }
                }
            }

            classLoader.close();

        } catch (Exception e) {
            System.out.println("A critical error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}