package autotests.tests.DuckActions;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import java.util.concurrent.atomic.AtomicReference;

public class DuckPropertiesTests extends DuckActionsClient {

    @Test(description = "ID - целое нечетное число, утка с material = rubber")
    @CitrusTest
    public void successfulProperties1(@Optional @CitrusResource TestCaseRunner runner) {
        successfulProperties(runner, false, "rubber");
    }

    @Test(description = "ID - целое четное число, утка с material = wood")
    @CitrusTest
    public void successfulProperties2(@Optional @CitrusResource TestCaseRunner runner) {
        successfulProperties(runner, true, "wood");
    }

    public void successfulProperties(@Optional @CitrusResource TestCaseRunner runner, boolean isEven, String material) {
        String color = "yellow";
        double height = 0.01;
        String sound = "quack";
        String wingsState = "FIXED";

        createDuck(runner, color, height, material, sound, wingsState);
        saveDuckId(runner, "duckId");

        AtomicReference<String> str = new AtomicReference<>("");
        runner.$(action -> {
            str.set(action.getVariable("duckId"));
        });

        int id = Integer.parseInt(str.get());
        int rest = isEven ? 1 : 0;

        if (id % 2 == rest) {
            createDuck(runner, color, height, material, sound, wingsState);
            saveDuckId(runner, "duckId");
        }

        duckProperties(runner, "${duckId}");
        validatePropResponse(runner, color, height, material, sound, wingsState);
    }
}

