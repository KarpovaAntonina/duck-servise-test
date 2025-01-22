package autotests.tests.Duck;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class UpdateDuckTests extends DuckActionsClient {

    @Test(description = "Обновление цвета и высоты утки")
    @CitrusTest
    public void successfulColorHeightUpdate(@Optional @CitrusResource TestCaseRunner runner) {
        successfulUpdate(runner,
                "yellow", 0.01, "rubber", "quack", "FIXED",
                "red", 0.02, "rubber", "quack", "FIXED");
    }

    @Test(description = "Обновление цвета и звука утки")
    @CitrusTest
    public void successfulColorSoundUpdate(@Optional @CitrusResource TestCaseRunner runner) {
        successfulUpdate(runner,
                "yellow", 0.01, "rubber", "quack", "FIXED",
                "red", 0.01, "rubber", "ogo", "FIXED");
    }

    public void successfulUpdate(@Optional @CitrusResource TestCaseRunner runner,
                                 String color, double height, String material, String sound, String wingsState,
                                 String newColor, double newHeight, String newMaterial, String newSound, String newWingsState) {
        createDuck(runner, color, height, material, sound, wingsState);
        saveDuckId(runner, "duckId");

        updateDuck(runner, "${duckId}", newColor, newHeight, newMaterial, newSound, newWingsState);
        validateResponse(runner, HttpStatus.OK, "{\n\"message\": \"Duck with id = ${duckId} is updated\"\n}");
    }
}
