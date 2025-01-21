package autotests.tests.Duck;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class CreateDuckTests extends DuckActionsClient {

    @Test(description = "Создать утку с material = rubber")
    @CitrusTest
    public void successfulRubberCreate(@Optional @CitrusResource TestCaseRunner runner) {
        successfulCreate(runner, "yellow", 0.01, "rubber", "quack", "FIXED");
    }

    @Test(description = "Создать утку с material = wood")
    @CitrusTest
    public void successfulWoodCreate(@Optional @CitrusResource TestCaseRunner runner) {
        successfulCreate(runner, "yellow", 0.01, "wood", "quack", "FIXED");
    }

    public void successfulCreate(@Optional @CitrusResource TestCaseRunner runner, String color, double height, String material, String sound, String wingsState) {
        createDuck(runner, color, height, material, sound, wingsState);
        validateCreateResponse(runner, "duckId", color, height, material, sound, wingsState);
    }
}
