package autotests.tests.Duck;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class UpdateDuckTests extends DuckActionsClient {

    @Test(description = "Обновление утки")
    @CitrusTest
    public void successfulUpdate(@Optional @CitrusResource TestCaseRunner runner) {
        String color = "yellow";
        String newColor = "red";
        double height = 0.01;
        double newHeight = 0.02;
        String material = "rubber";
        String sound = "quack";
        String wingsState = "FIXED";

        createDuck(runner, color, height, material, sound, wingsState);
        runner.$(http().client(yellowDuckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));

        updateDuck(runner, "${duckId}", newColor, newHeight, material, sound, wingsState);
        validateResponse(runner, "{\n\"message\": \"Duck with id = ${duckId} is updated\"\n}");
    }
}
