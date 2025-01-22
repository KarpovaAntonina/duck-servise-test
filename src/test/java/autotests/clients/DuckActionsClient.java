package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckActionsClient extends TestNGCitrusSpringSupport {

    @Autowired
    protected HttpClient yellowDuckService;

    public void duckSwim(TestCaseRunner runner, String id) {
        runner.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/swim")
                .queryParam("id", id));
    }

    public void duckFly(TestCaseRunner runner, String id) {
        runner.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/fly")
                .queryParam("id", id));
    }

    public void duckQuack(TestCaseRunner runner, String id) {
        int repetitionCount = 1;
        int soundCount = 1;

        runner.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/quack")
                .queryParam("id", id)
                .queryParam("repetitionCount", String.valueOf(repetitionCount))
                .queryParam("soundCount", String.valueOf(soundCount)));
    }

    public void duckProperties(TestCaseRunner runner, String id) {
        runner.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/properties")
                .queryParam("id", id));
    }

    public void saveDuckId(TestCaseRunner runner, String id) {
        runner.$(http().client(yellowDuckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", id)));
    }

    public void validateCreateResponse(TestCaseRunner runner, String id, String color, double height, String material, String sound, String wingsState) {
        runner.$(http().client(yellowDuckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", id))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{" +
                        "  \"id\": ${" + id + "}," +
                        "  \"color\": \"" + color + "\"," +
                        "  \"height\": " + height + "," +
                        "  \"material\": \"" + material + "\"," +
                        "  \"sound\": \"" + sound + "\"," +
                        "  \"wingsState\": \"" + wingsState + "\"" +
                        "}"));
    }

    public void validatePropResponse(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState) {
        runner.$(http().client(yellowDuckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{" +
                        "  \"color\": \"" + color + "\"," +
                        "  \"height\": " + height + "," +
                        "  \"material\": \"" + material + "\"," +
                        "  \"sound\": \"" + sound + "\"," +
                        "  \"wingsState\": \"" + wingsState + "\"" +
                        "}"));
    }

    public void validateResponse(TestCaseRunner runner, HttpStatus status, String responseMessage) {
        runner.$(http().client(yellowDuckService)
                .receive()
                .response(status)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(responseMessage));
    }

    public void createDuck(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState) {
        runner.$(http().client(yellowDuckService)
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n"
                        + "  \"color\": \"" + color + "\",\n"
                        + "  \"height\": " + height + ",\n"
                        + "  \"material\": \"" + material + "\",\n"
                        + "  \"sound\": \"" + sound + "\",\n"
                        + "  \"wingsState\": \"" + wingsState + "\"\n"
                        + "}"));
    }

    public void updateDuck(TestCaseRunner runner, String id, String color, double height, String material, String sound, String wingsState) {
        runner.$(http().client(yellowDuckService)
                .send()
                .put("/api/duck/update")
                .queryParam("id", id)
                .queryParam("color", color)
                .queryParam("sound", sound)
                .queryParam("material", material)
                .queryParam("height", String.valueOf(height)));
    }

    public void deleteDuck(TestCaseRunner runner, String id) {
        runner.$(http().client(yellowDuckService)
                .send()
                .delete("/api/duck/delete")
                .queryParam("id", id));
    }
}
