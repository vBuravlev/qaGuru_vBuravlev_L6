package qa.guru.junit;

import org.junit.jupiter.api.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JunitCore {

    public static void main(String[] args) throws Exception {

        Class clazz = SimpleTest.class;

        for (Method methodBeforeAll : clazz.getDeclaredMethods()) {
            BeforeAll methodAnnotationBeforeAll = methodBeforeAll.getAnnotation(BeforeAll.class);
            if (methodAnnotationBeforeAll != null) {
                try {
                    methodBeforeAll.invoke(clazz.getConstructor().newInstance());
                } catch (InvocationTargetException e) {
                    if (e.getCause() instanceof AssertionError) {
                        System.out.println("BeforeAll failed: " + methodBeforeAll.getName());
                        System.out.println();
                        continue;
                    } else {
                        System.out.println("BeforeAll broken: " + methodBeforeAll.getName());
                        System.out.println();
                        continue;
                    }
                }
                System.out.println("BeforeAll passed: " + methodBeforeAll.getName());
                System.out.println();
            }
        }


        for (Method methodTest : clazz.getDeclaredMethods()) {
            Test methodAnnotationTest = methodTest.getAnnotation(Test.class);
            if (methodAnnotationTest != null) {
                extracted1(clazz);
                // run method with @Test
                try {
                    methodTest.invoke(clazz.getConstructor().newInstance());
                    extracted(clazz);
                } catch (InvocationTargetException e) {
                    if (e.getCause() instanceof AssertionError) {
                        System.out.println("Test failed: " + methodTest.getName());
                        System.out.println();
                        extracted(clazz);
                        continue;
                    } else {
                        System.out.println("Test broken: " + methodTest.getName());
                        System.out.println();
                        extracted(clazz);
                        continue;
                    }
                }
                System.out.println("Test passed: " + methodTest.getName());
                System.out.println();
            }
        }

        for (Method methodAfterAll : clazz.getDeclaredMethods()) {
            AfterAll methodAnnotationAfterAll = methodAfterAll.getAnnotation(AfterAll.class);
            if (methodAnnotationAfterAll != null) {
                try {
                    methodAfterAll.invoke(clazz.getConstructor().newInstance());
                } catch (InvocationTargetException e) {
                    if (e.getCause() instanceof AssertionError) {
                        System.out.println("AfterAll failed: " + methodAfterAll.getName());
                        System.out.println();
                        continue;
                    } else {
                        System.out.println("AfterAll broken: " + methodAfterAll.getName());
                        System.out.println();
                        continue;
                    }
                }
                System.out.println("AfterAll passed: " + methodAfterAll.getName());
                System.out.println();
            }
        }
    }



    private static void extracted1(Class clazz) throws IllegalAccessException, InstantiationException, NoSuchMethodException {
        for (Method methodBeforeEach : clazz.getDeclaredMethods()) {
            BeforeEach methodAnnotationBeforeEach = methodBeforeEach.getAnnotation(BeforeEach.class);
            if (methodAnnotationBeforeEach != null) {
                try {
                    methodBeforeEach.invoke(clazz.getConstructor().newInstance());
                    System.out.println("Test passed: " + methodBeforeEach.getName());
                    System.out.println();
                } catch (InvocationTargetException e) {
                    if (e.getCause() instanceof AssertionError) {
                        System.out.println("Test failed: " + methodBeforeEach.getName());
                        System.out.println();
                        continue;
                    } else {
                        System.out.println("Test broken: " + methodBeforeEach.getName());
                        System.out.println();
                        continue;
                    }
                }

            }
        }
    }

    private static void extracted(Class clazz) throws IllegalAccessException, InstantiationException, NoSuchMethodException {
        for (Method methodAfterEach : clazz.getDeclaredMethods()) {
            AfterEach methodAnnotationAfterEach = methodAfterEach.getAnnotation(AfterEach.class);
            if (methodAnnotationAfterEach != null) {
                try {
                    methodAfterEach.invoke(clazz.getConstructor().newInstance());
                    System.out.println("Test passed: " + methodAfterEach.getName());
                    System.out.println();
                } catch (InvocationTargetException e) {
                    if (e.getCause() instanceof AssertionError) {
                        System.out.println("Test failed: " + methodAfterEach.getName());
                        System.out.println();
                        continue;
                    } else {
                        System.out.println("Test broken: " + methodAfterEach.getName());
                        System.out.println();
                        continue;
                    }
                }

            }
        }
    }

}

