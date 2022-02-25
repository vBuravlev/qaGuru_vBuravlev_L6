package qa.guru;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Класс c тестами")
public class SimpleTest {

    int a, b;

    @BeforeAll
    @DisplayName("BeforeAll тест")
    static void simpleBeforeAll () {
        System.out.println("BeforeAll выполнился");
    }

    @BeforeEach
    @DisplayName("BeforeEach тест")
    void simpleBeforeEach () {
        a = 5;
        b = 2;
        System.out.println("BeforeEach выполнился"+" a = "+a+" b = "+b);
    }


    @Test
    @DisplayName("Зеленый тест")
    void simpleGreenTest() {
        assertTrue(a > b);
    }

    @Test
    @DisplayName("Красный тест")
    void simpleRedTest() {
        assertTrue(b < a);
    }

    @Test
    @Disabled("jira: /CTG-255")
    void simpleBrokenTest() {
        throw new IllegalStateException("Broken test");
    }

    @AfterEach
    @DisplayName("AfterEach тест")
    void simpleAfterEach () {
        a = 2;
        b = 5;
        System.out.println("AfterEach выполнился"+" a = "+a+" b = "+b);
    }

    @AfterAll
    @DisplayName("AfterEach тест")
    static void simpleAfterAll () {
        System.out.println("AfterAll выполнился");
    }


}