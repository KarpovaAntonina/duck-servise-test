package autotests.tests.DuckActions;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckQuackTests extends DuckActionsClient {

    @Test(description = "Проверить, что утка с нечетным id крякает")
    @CitrusTest
    public void successfulQuackOddId(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.01, "rubber", "quack", "FIXED");
        extractId(runner, "duckId");

        if (isEvenVariable(runner, "duckId")) {
            createDuck(runner, "yellow", 0.01, "rubber", "quack", "FIXED");
            extractId(runner, "duckId");
        }

        duckQuack(runner, "${duckId}");
        validateResponse(runner, HttpStatus.OK, "{\n\"sound\": \"quack\"\n}");
    }

    @Test(description = "Проверить, что утка с четным id крякает")
    @CitrusTest
    public void successfulQuackEvenId(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.01, "rubber", "quack", "FIXED");
        extractId(runner, "duckId");

        if (!isEvenVariable(runner, "duckId")) {
            createDuck(runner, "yellow", 0.01, "rubber", "quack", "FIXED");
            extractId(runner, "duckId");
        }

        duckQuack(runner, "${duckId}");
        validateResponse(runner, HttpStatus.OK, "{\n\"sound\": \"quack\"\n}");
    }
}
