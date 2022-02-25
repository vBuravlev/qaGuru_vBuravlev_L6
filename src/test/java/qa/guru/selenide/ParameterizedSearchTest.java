package qa.guru.selenide;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;


public class ParameterizedSearchTest {

    @BeforeAll
    static void preconditionAll() {
        Configuration.timeout = 10000;
        Configuration.browserSize = "1920x1280";
    }

    @BeforeEach
    void precondition() {
        Selenide.open("https://catalog.cft.ru/");
    }

    @AfterEach
    void closeBrowser() {
        Selenide.closeWebDriver();
    }

    @DisplayName("Проверка работы чипсов на главной странице")
    @Test
    void chipsMainTest() {
        int collectionNotChipsUse = $$("div.MuiGrid-root.MuiGrid-container.MuiGrid-spacing-xs-3").size();
        $$("#ChipArrayCntr ul li div[role='button']").first().shouldHave(cssValue("background-color", "rgba(255, 255, 255, 1)"));
        $$("#ChipArrayCntr ul li").first().click();
        int collectionChipsUse = $$("div.MuiGrid-root.MuiGrid-container.MuiGrid-spacing-xs-3").size();
        $$("#ChipArrayCntr ul li div[role='button']").first().shouldHave(cssValue("background-color", "rgba(235, 80, 94, 1)"));
        assertThat(collectionNotChipsUse < collectionChipsUse);
        $$("#ChipArrayCntr ul li div[role='button'] button.MuiButtonBase-root.MuiIconButton-root.MuiChip-deleteIcon").first().click();
        $$("#ChipArrayCntr ul li div[role='button']").first().shouldHave(cssValue("background-color", "rgba(255, 255, 255, 1)"));
        int collectionNotChipsUse2 = $$("div.MuiGrid-root.MuiGrid-container.MuiGrid-spacing-xs-3").size();
        assertThat(collectionNotChipsUse2 > collectionChipsUse);
    }

    @ValueSource(strings = {"1B036020", "1B036030", "1B036045"})
    @ParameterizedTest(name = "Проверка наличия приложения \"{0}\" в бизнес-направлении и каталоге на сайте")
    void applicationBusinessDirectionTest(String testData) {
        $x("//div/span[contains(.,'Каталоги Приложений')]").shouldBe(visible).click();
        $x("//a/div/span[contains(.,'ЦФТ-Банк')]").shouldBe(visible).click();
        $x("//div/p[contains(.,'Cash Management')]").shouldBe(visible).click();
        $$("div.MuiGrid-root.MuiGrid-container").findBy(text(testData)).shouldHave(visible);

    }

    @CsvSource(value = {
            "IDE| Плагин IDE PlpCheck",
            "экспресс-карт| Авторизация экспресс-карт",
            "удаленная идентификация| F.ID. Удаленная идентификация клиента в ЕБС"
    }, delimiter = '|')
    @ParameterizedTest(name = "Проверка наличия приложения \"{0}\" в бизнес-направлении и каталоге на сайте")
    void applicationSearchTest(String testData, String expectedText) {
        $("input[placeholder='Поиск по сайту']").setValue(testData).pressEnter();
        $$("div.MuiGrid-root.MuiGrid-container.MuiGrid-item").findBy(text(expectedText)).shouldHave(visible);
    }

    static Stream<Arguments> applicationAllToCartTestDataProvider() {
        return Stream.of(
                Arguments.of("Проверка для каталога ", "ЦФТ-Ритейл банк (РБС)", "Взаимодействие с органами государственной власти", 15, 60.44, 15),
                Arguments.of("Проверка для каталога ", "ЦФТ-Корпорация", "ЦФТ-Корпорация", 42, 1502.12, 24),
                Arguments.of("Проверка для каталога ", "ЦФТ-Электронный архив", "Электронный архив", 10, 82.94, 10)
        );
    }

    @MethodSource(value = "applicationAllToCartTestDataProvider")
    @ParameterizedTest(name = "{0}{1}")
    void applicationAllToCartTest(String nameTest, String catalogName, String BusinessDirectionName, int quantityApplication, double sumApplication, int paginationQuantity) {
        $x("//div/span[text()='Каталоги Приложений']").shouldBe(visible).click();
        $x("//a/div/span[text()='" + catalogName + "']").shouldBe(visible).click();
        $x("//p[text()='Приложения по бизнес-направлениям']").scrollTo();
        $x("//div/p[text()='" + BusinessDirectionName + "']").shouldBe(visible).click();
        $x("//button//p[text()='Добавить в корзину всё']").shouldBe(visible).click();
        $x("//p[text()='Добавлено в корзину Приложений: " + quantityApplication + "']").shouldBe(visible);
        $x("//a[@href='/cart']").click();
        $x("//div/p[text()='Корзина']").shouldBe(visible);
        $x("//button/span[text()='Приложения " + quantityApplication + "']").shouldBe(visible);
        $$x("//span/a[text()='" + BusinessDirectionName + "']").shouldHave(size(paginationQuantity));
        $$("p[compoheht='h3']").findBy(text(Double.toString(sumApplication))).shouldBe(visible);

    }

    @Disabled
    @ValueSource(strings = {"1B290355", "1B960010"})
    @ParameterizedTest(name = "Проверка наличия приложения \"{0}\" в бизнес-направлении и каталоге на сайте")
    void applicationBusinessDirectionDisabledTest(String testData) {
        $x("//div/span[contains(.,'Каталоги Приложений')]").shouldBe(visible).click();
        $x("//a/div/span[contains(.,'ЦФТ-Банк')]").shouldBe(visible).click();
        $x("//div/p[contains(.,'Cash Management')]").shouldBe(visible).click();
        $$("div.MuiGrid-root.MuiGrid-container").findBy(text(testData)).shouldHave(visible);
    }
}
