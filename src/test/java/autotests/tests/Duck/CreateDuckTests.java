package autotests.tests.Duck;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class CreateDuckTests extends DuckActionsClient {

    @Test(description = "Создать утку с material = rubber")
    @CitrusTest
    public void successfulRubberCreate(@Optional @CitrusResource TestCaseRunner runner) {
        String color = "yellow";
        double height = 0.01;
        String sound = "quack";
        String wingsState = "FIXED";

        createDuck(runner, color, height, "rubber", sound, wingsState);

        String expectedResponseMessage = makeResponseBody(color, height, "rubber", sound, wingsState);

        runner.$(http().client(yellowDuckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(expectedResponseMessage));
    }

    @Test(description = "Создать утку с material = wood")
    @CitrusTest
    public void successfulWoodCreate(@Optional @CitrusResource TestCaseRunner runner) {
        String color = "yellow";
        double height = 0.01;
        String sound = "quack";
        String wingsState = "FIXED";

        createDuck(runner, color, height, "wood", sound, wingsState);

        String expectedResponseMessage = makeResponseBody(color, height, "wood", sound, wingsState);

        runner.$(http().client(yellowDuckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(expectedResponseMessage));
    }

    public String makeResponseBody(String color, double height, String material, String sound, String wingsState) {
        return "{" +
                "  \"id\": ${duckId}," +
                "  \"color\": \"" + color + "\"," +
                "  \"height\": " + height + "," +
                "  \"material\": \"" + material + "\"," +
                "  \"sound\": \"" + sound + "\"," +
                "  \"wingsState\": \"" + wingsState + "\"" +
                "}";
    }
}
