package qa.guru.junit;

import org.junit.jupiter.api.BeforeAll;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;

public class StreamTest {

    public static void main(String[] args) throws Exception {
        Class clazz = SimpleTest.class;



        final Object invoke = Arrays.stream(clazz.getDeclaredMethods()).filter(method -> method.getAnnotation(BeforeAll.class) != null).peek(method ->{
           try {
            method.invoke(clazz.getConstructor().newInstance());
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof AssertionError) {
                System.out.println("BeforeAll failed: " + method.getName());
                System.out.println();
            } else {
                System.out.println("BeforeAll broken: " + method.getName());
                System.out.println();
            }
        } catch (IllegalAccessException e) {
               e.printStackTrace();
           } catch (InstantiationException e) {
               e.printStackTrace();
           } catch (NoSuchMethodException e) {
               e.printStackTrace();
           }
            System.out.println("BeforeAll passed: " + method.getName());
        System.out.println();
        });


    }
}
