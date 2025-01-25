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

public class DuckQuackTests extends DuckActionsClient {

    @Test(description = "Проверить, что утка с нечетным id крякает")
    @CitrusTest
    public void successfulQuackOddId(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck().color("yellow").height(0.01).material("rubber").sound("quack").wingsState(WingState.FIXED);

        createDuck(runner, duck);
        extractId(runner, "duckId");

        if (isEvenVariable(runner, "duckId")) {
            createDuck(runner, duck);
            extractId(runner, "duckId");
        }

        duckQuack(runner, "${duckId}");
        validateResponse(runner, HttpStatus.OK, "{\n\"sound\": \"quack\"\n}");
    }

    @Test(description = "Проверить, что утка с четным id крякает")
    @CitrusTest
    public void successfulQuackEvenId(@Optional @CitrusResource TestCaseRunner runner) {
        Duck duck = new Duck().color("yellow").height(0.01).material("rubber").sound("quack").wingsState(WingState.FIXED);

        createDuck(runner, duck);
        extractId(runner, "duckId");

        if (!isEvenVariable(runner, "duckId")) {
            createDuck(runner, duck);
            extractId(runner, "duckId");
        }

        duckQuack(runner, "${duckId}");
        validateResponse(runner, HttpStatus.OK, "{\n\"sound\": \"quack\"\n}");
    }
}
