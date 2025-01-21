package autotests.tests.DuckActions;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import java.util.concurrent.atomic.AtomicReference;

public class DuckQuackTests extends DuckActionsClient {

    @Test(description = "Проверить, что утка с нечетным id крякает")
    @CitrusTest
    public void successfulQuackOddId(@Optional @CitrusResource TestCaseRunner runner) {
        successfulQuack(runner, false);
    }

    @Test(description = "Проверить, что утка с четным id крякает")
    @CitrusTest
    public void successfulQuackEvenId(@Optional @CitrusResource TestCaseRunner runner) {
        successfulQuack(runner, true);
    }

    public void successfulQuack(@Optional @CitrusResource TestCaseRunner runner, boolean isEven) {
        createDuck(runner, "yellow", 0.01, "rubber", "quack", "FIXED");
        saveDuckId(runner, "duckId");

        AtomicReference<String> str = new AtomicReference<>("");
        runner.$(action -> {
            str.set(action.getVariable("duckId"));
        });

        int id = Integer.parseInt(str.get());
        int rest = isEven ? 1 : 0;

        if (id % 2 == rest) {
            createDuck(runner, "yellow", 0.01, "rubber", "quack", "FIXED");
            saveDuckId(runner, "duckId");
        }

        duckQuack(runner, "${duckId}");
        validateResponse(runner, HttpStatus.OK, "{\n\"sound\": \"quack\"\n}");
    }
}
