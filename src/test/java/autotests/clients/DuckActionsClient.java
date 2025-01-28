package autotests.clients;

import autotests.EndpointConfig;
import autotests.payloads.WingState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import java.util.concurrent.atomic.AtomicInteger;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckActionsClient extends TestNGCitrusSpringSupport {
    private static final String DUCK_ID_VAR_NAME = "duckId";
    private static final String DUCK_ID_VAR_VALUE = "${" + DUCK_ID_VAR_NAME + "}";

    @Autowired
    protected HttpClient yellowDuckService;

    protected boolean isEvenVariable(TestCaseRunner runner) {
        AtomicInteger duckId = new AtomicInteger(0);
        runner.$(action -> {
            duckId.set(Integer.parseInt(action.getVariable(DUCK_ID_VAR_NAME)));
        });

        return (duckId.get() % 2 == 0);
    }

    public void duckSwimById(TestCaseRunner runner, int id) {
        runner.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/swim")
                .queryParam("id", String.valueOf(id)));
    }

    public void duckSwim(TestCaseRunner runner) {
        runner.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/swim")
                .queryParam("id", DUCK_ID_VAR_VALUE));
    }

    public void duckFly(TestCaseRunner runner) {
        runner.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/fly")
                .queryParam("id", DUCK_ID_VAR_VALUE));
    }

    public void duckQuack(TestCaseRunner runner, int repetitionCount, int soundCount) {
        runner.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/quack")
                .queryParam("id", DUCK_ID_VAR_VALUE)
                .queryParam("repetitionCount", String.valueOf(repetitionCount))
                .queryParam("soundCount", String.valueOf(soundCount)));
    }

    public void duckProperties(TestCaseRunner runner) {
        runner.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/properties")
                .queryParam("id", DUCK_ID_VAR_VALUE));
    }

    public void extractId(TestCaseRunner runner) {
        runner.$(http().client(yellowDuckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", DUCK_ID_VAR_NAME)));
    }

    public void validateCreateResponse(TestCaseRunner runner, String color, double height, String material, String sound, WingState wingsState) {
        runner.$(http().client(yellowDuckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", DUCK_ID_VAR_NAME))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{" +
                        "  \"id\": " + DUCK_ID_VAR_VALUE + "," +
                        "  \"color\": \"" + color + "\"," +
                        "  \"height\": " + height + "," +
                        "  \"material\": \"" + material + "\"," +
                        "  \"sound\": \"" + sound + "\"," +
                        "  \"wingsState\": \"" + wingsState + "\"" +
                        "}"));
    }

    public void validatePropResponse(TestCaseRunner runner, Object body) {
        runner.$(http().client(yellowDuckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(body, new ObjectMapper())));
    }

    public void validateResponse(TestCaseRunner runner, HttpStatus status, String resourcePath) {
        runner.$(http().client(yellowDuckService)
                .receive()
                .response(status)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ClassPathResource(resourcePath)));
    }

    public void validateResponseMessage(TestCaseRunner runner, HttpStatus status, String responseMessage) {
        runner.$(http().client(yellowDuckService)
                .receive()
                .response(status)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(responseMessage));
    }

    public void createDuck(TestCaseRunner runner, Object body) {
        runner.$(http().client(yellowDuckService)
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(body, new ObjectMapper())));
    }

    public void updateDuck(TestCaseRunner runner, String color, double height, String material, String sound, WingState wingsState) {
        runner.$(http().client(yellowDuckService)
                .send()
                .put("/api/duck/update")
                .queryParam("id", DUCK_ID_VAR_VALUE)
                .queryParam("color", color)
                .queryParam("sound", sound)
                .queryParam("material", material)
                //? WingState
                .queryParam("height", String.valueOf(height)));
    }

    public void deleteDuck(TestCaseRunner runner) {
        runner.$(http().client(yellowDuckService)
                .send()
                .delete("/api/duck/delete")
                .queryParam("id", DUCK_ID_VAR_VALUE));
    }
}
