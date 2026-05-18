package org;

import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please provide the fully qualified class name as an argument.");
            return;
        }
        String className = args[0];

        try {
            Class<?> targetClass = Class.forName(className);

            System.out.println("Successfully loaded class: " + targetClass.getName());
            Method runMethod = targetClass.getDeclaredMethod("run");
            Object instance = targetClass.getDeclaredConstructor().newInstance();
            System.out.println("Method 'run' located.");
            runMethod.invoke(instance);

        } catch (ClassNotFoundException e) {
            System.out.println("Error: Could not find the class in the classpath. Ensure the package name is correct.");
        } catch (NoSuchMethodException e) {
            System.out.println("Error: The loaded class does not contain a no-argument 'run' method.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred during reflection or execution: " + e.getMessage());
        }
    }
}
