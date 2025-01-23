package autotests.tests.DuckActions;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckPropertiesTests extends DuckActionsClient {

    @Test(description = "ID - целое нечетное число, утка с material = rubber")
    @CitrusTest
    public void successfulRubberIdOddProperties(@Optional @CitrusResource TestCaseRunner runner) {
        String material = "rubber";
        String color = "yellow";
        double height = 0.01;
        String sound = "quack";
        String wingsState = "FIXED";

        createDuck(runner, color, height, material, sound, wingsState);
        extractId(runner, "duckId");

        if (isEvenVariable(runner, "duckId")) {
            createDuck(runner, color, height, material, sound, wingsState);
            extractId(runner, "duckId");
        }

        duckProperties(runner, "${duckId}");
        validatePropResponse(runner, color, height, material, sound, wingsState);
    }

    @Test(description = "ID - целое четное число, утка с material = wood")
    @CitrusTest
    public void successfulWoodIdEvenProperties(@Optional @CitrusResource TestCaseRunner runner) {
        String material = "wood";
        String color = "yellow";
        double height = 0.01;
        String sound = "quack";
        String wingsState = "FIXED";

        createDuck(runner, color, height, material, sound, wingsState);
        extractId(runner, "duckId");

        if (!isEvenVariable(runner, "duckId")) {
            createDuck(runner, color, height, material, sound, wingsState);
            extractId(runner, "duckId");
        }

        duckProperties(runner, "${duckId}");
        validatePropResponse(runner, color, height, material, sound, wingsState);
    }
}

