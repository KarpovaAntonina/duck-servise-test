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

    // Тест не проходит, так как ответ "Paws are not found (((("
    @Test(description = "Проверить, что утка плавает")
    @CitrusTest
    public void successfulSwim(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck().color("yellow").height(0.01).material("rubber").sound("quack").wingsState(WingState.FIXED);

        createDuck(runner, duck);
        extractId(runner, "duckId");

        duckSwim(runner, "${duckId}");
        validateResponse(runner, HttpStatus.OK, "duckActionsTest/successfulSwim.json");
    }

    @Test(description = "Проверить, что несуществующая утка не плывет")
    @CitrusTest
    public void notExistsNotSwim(@Optional @CitrusResource TestCaseRunner runner) {
        duckSwim(runner, "-1");
        validateResponse(runner, HttpStatus.NOT_FOUND, "duckActionsTest/notExistDuck.json");
    }
}
