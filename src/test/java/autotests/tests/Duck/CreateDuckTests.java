package autotests.tests.Duck;

import autotests.clients.DuckActionsClient;
import autotests.payloads.Duck;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@Epic("Тесты на duck-controller")
@Feature("Эндпоинт /api/duck/create")
public class CreateDuckTests extends DuckActionsClient {

    @DataProvider(name = "duckListCreate")
    public Object[][] DuckCreateProvider() {
        return new Object[][]{
                {null, "yellow", 0.01, "rubber", "quack", WingsState.ACTIVE},
                {null, "red", 0.02, "metal", "muu", WingsState.FIXED},
                {null, "blue", 0.03, "wood", "moo", WingsState.UNDEFINED},
                {null, "black", 0.04, "snow", "gav", WingsState.ACTIVE},
                {null, "green", 0.05, "plastic", "meow", WingsState.FIXED}
        };
    }

    @Test(description = "Создать утки по списку, с дополнительной проверкой по базе", dataProvider = "duckListCreate")
    @CitrusTest
    @CitrusParameters({"runner", "color", "height", "material", "sound", "wingsState"})
    public void successfulListCreate(@Optional @CitrusResource TestCaseRunner runner, String color, double height, String material, String sound, WingsState wingsState) {
        Duck duck = new Duck()
                .color(color)
                .height(height)
                .material(material)
                .sound(sound)
                .wingsState(wingsState);

        createDuck(runner, duck);
        validateCreateResponse(runner, color, height, material, sound, wingsState);

        validateDuckInDatabase(runner, color, height, material, sound, wingsState);
    }

    @Test(description = "Создать утку с material = rubber, с дополнительной проверкой по базе")
    @CitrusTest
    public void successfulRubberCreate(@Optional @CitrusResource TestCaseRunner runner) {
        String color = "yellow";
        double height = 0.01;
        String material = "rubber";
        String sound = "quack";
        WingsState wingsState = WingsState.FIXED;
        Duck duck = new Duck()
                .color(color)
                .height(height)
                .material(material)
                .sound(sound)
                .wingsState(wingsState);

        createDuck(runner, duck);
        validateCreateResponse(runner, color, height, material, sound, wingsState);

        validateDuckInDatabase(runner, color, height, material, sound, wingsState);
    }

    @Test(description = "Создать утку с material = wood, с дополнительной проверкой по базе")
    @CitrusTest
    public void successfulWoodCreate(@Optional @CitrusResource TestCaseRunner runner) {
        String color = "yellow";
        double height = 0.01;
        String material = "wood";
        String sound = "quack";
        WingsState wingsState = WingsState.FIXED;
        Duck duck = new Duck()
                .color(color)
                .height(height)
                .material(material)
                .sound(sound)
                .wingsState(wingsState);

        createDuck(runner, duck);
        validateCreateResponse(runner, color, height, material, sound, wingsState);

        validateDuckInDatabase(runner, color, height, material, sound, wingsState);
    }
}
