package autotests.Duck;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class CreateDuckTests extends TestNGCitrusSpringSupport {

    @Test(description = "Создать утку с material = rubber")
    @CitrusTest
    public void successfulRubberCreate(@Optional @CitrusResource TestCaseRunner runner) {
        successfulMaterialCreate(runner, "rubber");
    }

    @Test(description = "Создать утку с material = wood")
    @CitrusTest
    public void successfulWoodCreate(@Optional @CitrusResource TestCaseRunner runner) {
        successfulMaterialCreate(runner, "wood");
    }

    public void createDuck(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState) {
        runner.$(http().client("http://localhost:2222")
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n  \"color\": \"" + color + "\",\n"
                        + "  \"height\": " + height + ",\n"
                        + "  \"material\": \"" + material + "\",\n"
                        + "  \"sound\": \"" + sound + "\",\n"
                        + "  \"wingsState\": \"" + wingsState
                        + "\"\n" + "}"));
    }

    public void validateResponse(TestCaseRunner runner, String responseMessage) {
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE).body(responseMessage));
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

    public void successfulMaterialCreate(TestCaseRunner runner, String material) {
        String color = "yellow";
        double height = 0.01;
        String sound = "quack";
        String wingsState = "FIXED";

        createDuck(runner, color, height, material, sound, wingsState);

        String expectedResponseMessage = makeResponseBody(color, height, material, sound, wingsState);

        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(expectedResponseMessage));
    }
}
