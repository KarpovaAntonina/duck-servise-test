package autotests.tests.DuckActions;

import autotests.clients.DuckActionsClient;
import autotests.payloads.Duck;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Flaky;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

@Epic("Тесты на duck-action-controller")
@Feature("Эндпоинт /api/duck/action/swim")
public class DuckSwimTests extends DuckActionsClient {

    // Тест не проходит, так как статус ответа NOT_FOUND с текстом "Paws are not found (((("
    @Flaky
    @Test(description = "Проверить, что утка плавает")
    @CitrusTest
    public void successfulSwim(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck()
                .color("yellow")
                .height(0.01)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.FIXED);

        createDuck(runner, duck);
        extractDuckId(runner);

        duckSwim(runner);
        validateResponse(runner, HttpStatus.OK, "duckActionsTest/successfulSwim.json");
    }

    // Тест не проходит, так как ответ с текстом "Paws are not found (((("
    @Flaky
    @Test(description = "Проверить, что несуществующая утка не плывет")
    @CitrusTest
    public void notExistsNotSwim(@Optional @CitrusResource TestCaseRunner runner) {
        setDuckId(runner, -1);
        duckSwim(runner);
        validateResponse(runner, HttpStatus.NOT_FOUND, "duckTest/notExistDuck.json");
    }
}
