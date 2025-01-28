package autotests.tests.DuckActions;

import autotests.clients.DuckActionsClient;
import autotests.payloads.Duck;
import autotests.payloads.WingState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckSwimTests extends DuckActionsClient {

    // Тест не проходит, так как статус ответа NOT_FOUND с текстом "Paws are not found (((("
    @Test(description = "Проверить, что утка плавает")
    @CitrusTest
    public void successfulSwim(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck()
                .color("yellow")
                .height(0.01)
                .material("rubber")
                .sound("quack")
                .wingsState(WingState.FIXED);

        createDuck(runner, duck);
        extractId(runner);

        duckSwim(runner);
        validateResponse(runner, HttpStatus.OK, "duckActionsTest/successfulSwim.json");
    }

    // Тест не проходит, так как ответ с текстом "Paws are not found (((("
    @Test(description = "Проверить, что несуществующая утка не плывет")
    @CitrusTest
    public void notExistsNotSwim(@Optional @CitrusResource TestCaseRunner runner) {
        duckSwimById(runner, -1);
        validateResponse(runner, HttpStatus.NOT_FOUND, "duckTest/notExistDuck.json");
    }
}
