package org;

public class DummyTask {

    @MyRunnable
    public void executeSimpleTask() {
        System.out.println("   [DummyTask] Success: executeSimpleTask() was invoked (0 arguments).");
    }

    @MyRunnable
    public void processData(int data) {
        System.out.println("   [DummyTask] Success: processData(int) was invoked with mock value: " + data);
    }

    @MyRunnable
    public void unsupportedMethod(String text) {
        System.out.println("   [DummyTask] FAILURE: This should not print because it takes a String.");
    }

    public void ignoredMethod() {
        System.out.println("   [DummyTask] FAILURE: This should not print because it lacks @MyRunnable.");
    }
}