package autotests.tests.Duck;

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

@Epic("Тесты на duck-controller")
@Feature("Эндпоинт /api/duck/delete")
public class DeleteDuckTest extends DuckActionsClient {

    @Test(description = "Удалить утку")
    @CitrusTest
    public void successfulDelete(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck()
                .color("yellow")
                .height(0.01)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.FIXED);

        createDuck(runner, duck);
        extractId(runner);

        deleteDuck(runner);
        validateResponse(runner, HttpStatus.OK, "duckTest/successfulDelete.json");
    }
}
