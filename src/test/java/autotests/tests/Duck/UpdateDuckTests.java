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
        String color = "yellow";
        double height = 0.01;
        String material = "rubber";
        String sound = "quack";
        String wingsState = "FIXED";
        String newColor = "red";
        double newHeight = 0.02;

        createDuck(runner, color, height, material, sound, wingsState);
        extractId(runner, "duckId");

        updateDuck(runner, "${duckId}", newColor, newHeight, material, sound, wingsState);
        validateResponse(runner, HttpStatus.OK, "{\n\"message\": \"Duck with id = ${duckId} is updated\"\n}");
    }

    @Test(description = "Обновление цвета и звука утки")
    @CitrusTest
    public void successfulColorSoundUpdate(@Optional @CitrusResource TestCaseRunner runner) {
        String color = "yellow";
        double height = 0.01;
        String material = "rubber";
        String sound = "quack";
        String wingsState = "FIXED";
        String newColor = "red";
        String newSound = "ogo";

        createDuck(runner, color, height, material, sound, wingsState);
        extractId(runner, "duckId");

        updateDuck(runner, "${duckId}", newColor, height, material, newSound, wingsState);
        validateResponse(runner, HttpStatus.OK, "{\n\"message\": \"Duck with id = ${duckId} is updated\"\n}");
    }
}
