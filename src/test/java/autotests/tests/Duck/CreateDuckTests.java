package autotests.tests.Duck;

import autotests.clients.DuckActionsClient;
import autotests.payloads.Duck;
import autotests.payloads.WingState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@Epic("Тесты на duck-controller")
@Feature("Эндпоинт /api/duck/create")
public class CreateDuckTests extends DuckActionsClient {

    @Test(description = "Создать утку с material = rubber")
    @CitrusTest
    public void successfulRubberCreate(@Optional @CitrusResource TestCaseRunner runner) {
        String color = "yellow";
        double height = 0.01;
        String material = "rubber";
        String sound = "quack";
        WingState wingsState = WingState.FIXED;
        Duck duck = new Duck()
                .color(color)
                .height(height)
                .material(material)
                .sound(sound)
                .wingsState(wingsState);

        createDuck(runner, duck);
        validateCreateResponse(runner, color, height, material, sound, wingsState);
    }

    @Test(description = "Создать утку с material = wood")
    @CitrusTest
    public void successfulWoodCreate(@Optional @CitrusResource TestCaseRunner runner) {
        String color = "yellow";
        double height = 0.01;
        String material = "wood";
        String sound = "quack";
        WingState wingsState = WingState.FIXED;
        Duck duck = new Duck()
                .color(color)
                .height(height)
                .material(material)
                .sound(sound)
                .wingsState(wingsState);

        createDuck(runner, duck);
        validateCreateResponse(runner, color, height, material, sound, wingsState);
    }
}
