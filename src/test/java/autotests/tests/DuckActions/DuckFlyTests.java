package autotests.tests.DuckActions;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckFlyTests extends DuckActionsClient {

    @Test(description = "Утка cо связанными крыльями")
    @CitrusTest
    public void wingsStateFixed(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.01, "rubber", "quack", "FIXED");
        saveDuckId(runner, "duckId");

        duckFly(runner, "${duckId}");
        validateResponse(runner, HttpStatus.OK, "{\n\"message\": \"I can not fly :C\"\n}");
    }

    @Test(description = "Утка c активными крыльями")
    @CitrusTest
    public void wingsStateActive(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.01, "rubber", "quack", "ACTIVE");
        saveDuckId(runner, "duckId");

        duckFly(runner, "${duckId}");
        validateResponse(runner, HttpStatus.OK, "{\n\"message\": \"I am flying :)\"\n}");
    }

    @Test(description = "Утка c активными крыльями")
    @CitrusTest
    public void wingsStateUndefined(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.01, "rubber", "quack", "UNDEFINED");
        saveDuckId(runner, "duckId");

        duckFly(runner, "${duckId}");
        validateResponse(runner, HttpStatus.OK, "{\n\"message\": \"Wings are not detected :(\"\n}");
    }
}
