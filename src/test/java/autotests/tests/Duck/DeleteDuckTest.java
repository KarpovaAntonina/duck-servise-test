package autotests.tests.Duck;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DeleteDuckTest extends DuckActionsClient {

    @Test(description = "Удалить утку")
    @CitrusTest
    public void successfulDelete(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.01, "rubber", "quack", "FIXED");
        saveDuckId(runner,"duckId");

        deleteDuck(runner, "${duckId}");
        validateResponse(runner, "{\n\"message\": \"Duck is deleted\"\n}");
    }
}
