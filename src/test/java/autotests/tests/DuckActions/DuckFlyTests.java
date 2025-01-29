package autotests.tests.DuckActions;

import autotests.clients.DuckActionsClient;
import autotests.payloads.Duck;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@Epic("Тесты на duck-action-controller")
@Feature("Эндпоинт /api/duck/action/fly")
public class DuckFlyTests extends DuckActionsClient {

    // Тест не проходит, так как expected 'I can’t fly' but was 'I can not fly :C'
    @Test(description = "Утка cо связанными крыльями")
    @CitrusTest
    public void wingsStateFixed(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck()
                .color("yellow")
                .height(0.01)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.FIXED);

        createDuck(runner, duck);
        extractDuckId(runner);

        duckFly(runner);
        validateResponse(runner, HttpStatus.OK, "duckActionsTest/unsuccessfulFly.json");
    }

    @Test(description = "Утка c активными крыльями")
    @CitrusTest
    public void wingsStateActive(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck()
                .color("yellow")
                .height(0.01)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);

        createDuck(runner, duck);
        extractDuckId(runner);

        duckFly(runner);
        validateResponse(runner, HttpStatus.OK, "duckActionsTest/successfulFly.json");
    }

    // Тест не проходит, так как expected 'Duck does not exist' but was 'Wings are not detected :('
    @Test(description = "Утка c крыльями в неопределенном состоянии")
    @CitrusTest
    public void wingsStateUndefined(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck()
                .color("yellow")
                .height(0.01)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.UNDEFINED);

        createDuck(runner, duck);  // Возможно утка с крыльями UNDEFINED вообще не должна создаваться?
        // (в документации нет такого состояния крыльев). Этот тест нужно перенести в Create? В задании он должен быть в этом эндпоинте.
        extractDuckId(runner);

        duckFly(runner);
        validateResponse(runner, HttpStatus.OK, "duckTest/notExistDuck.json");
    }
}
