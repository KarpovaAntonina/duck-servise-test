package autotests.tests.DuckActions;

import autotests.clients.DuckActionsClient;
import autotests.payloads.Duck;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@Epic("Тесты на duck-action-controller")
@Feature("Эндпоинт /api/duck/action/properties")
public class DuckPropertiesTests extends DuckActionsClient {

    // Тест не проходит, так как в ответе height*100
    @Test(description = "ID - целое нечетное число, утка с material = rubber")
    @CitrusTest
    public void successfulRubberIdOddProperties(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck()
                .color("yellow")
                .height(0.01)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.FIXED);

        createDuck(runner, duck);
        extractId(runner);

        if (isEvenDuckIdVariable(runner)) {
            createDuck(runner, duck);
            extractId(runner);
        }

        duckProperties(runner);
        validatePropResponse(runner, duck);
    }

    // Тест не проходит, если материал не "rubber", пустое тело ответа
    @Test(description = "ID - целое четное число, утка с material = wood")
    @CitrusTest
    public void successfulWoodIdEvenProperties(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck()
                .color("yellow")
                .height(0.01)
                .material("wood")
                .sound("quack")
                .wingsState(WingsState.FIXED);

        createDuck(runner, duck);
        extractId(runner);

        if (!isEvenDuckIdVariable(runner)) {
            createDuck(runner, duck);
            extractId(runner);
        }

        duckProperties(runner);
        validatePropResponse(runner, duck);
    }
}

