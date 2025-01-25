package autotests.tests.Duck;

import autotests.clients.DuckActionsClient;
import autotests.payloads.Duck;
import autotests.payloads.WingState;
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
        WingState wingsState = WingState.FIXED;
        String newColor = "red";
        double newHeight = 0.02;
        Duck duck = new Duck().color(color).height(height).material(material).sound(sound).wingsState(wingsState);

        createDuck(runner, duck);
        extractId(runner, "duckId");

        updateDuck(runner, "${duckId}", newColor, newHeight, material, sound, wingsState);
        validateResponse(runner, HttpStatus.OK, "duckActionsTest/successfulUpdate.json");
    }

    @Test(description = "Обновление цвета и звука утки")
    @CitrusTest
    public void successfulColorSoundUpdate(@Optional @CitrusResource TestCaseRunner runner) {
        String color = "yellow";
        double height = 0.01;
        String material = "rubber";
        String sound = "quack";
        WingState wingsState = WingState.FIXED;
        String newColor = "red";
        String newSound = "ogo";
        Duck duck = new Duck().color(color).height(height).material(material).sound(sound).wingsState(wingsState);

        createDuck(runner, duck);
        extractId(runner, "duckId");

        updateDuck(runner, "${duckId}", newColor, height, material, newSound, wingsState);
        validateResponse(runner, HttpStatus.OK, "duckActionsTest/successfulUpdate.json");
    }
}
