package autotests.tests.DuckActions;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckSwimTests extends DuckActionsClient {

    @Test(description = "Проверить, что утка плавает")
    @CitrusTest
    public void successfulSwim(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "FIXED");
        extractId(runner, "duckId");

        duckSwim(runner, "${duckId}");
        validateResponse(runner, HttpStatus.OK, "{\n\"message\": \"I am swimming\"\n}");
    }

    @Test(description = "Проверить, что несуществующая утка не плывет")
    @CitrusTest
    public void notExistsNotSwim(@Optional @CitrusResource TestCaseRunner runner) {
        duckSwim(runner, "-1");
        validateResponse(runner, HttpStatus.NOT_FOUND, "{  \"message\":\"Duck does not exist\"}");
    }
}
