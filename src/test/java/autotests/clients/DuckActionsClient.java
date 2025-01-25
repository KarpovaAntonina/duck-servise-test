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

    @Autowired
    protected HttpClient yellowDuckService;

    protected boolean isEvenVariable(TestCaseRunner runner, String id) {
        AtomicInteger duckId = new AtomicInteger(0);
        runner.$(action -> {
            duckId.set(Integer.parseInt(action.getVariable(id)));
        });

        return (duckId.get() % 2 == 0);
    }

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

    public void extractId(TestCaseRunner runner, String id) {
        runner.$(http().client(yellowDuckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", id)));
    }

    public void validateCreateResponse(TestCaseRunner runner, String id, String color, double height, String material, String sound, WingState wingsState) {
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

    public void validatePropResponse(TestCaseRunner runner, String color, double height, String material, String sound, WingState wingsState) {
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
                .body(new ClassPathResource(responseMessage)));
    }

    public void createDuck(TestCaseRunner runner, Object body) {
        runner.$(http().client(yellowDuckService)
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(body, new ObjectMapper())));
    }

    public void updateDuck(TestCaseRunner runner, String id, String color, double height, String material, String sound, WingState wingsState) {
        runner.$(http().client(yellowDuckService)
                .send()
                .put("/api/duck/update")
                .queryParam("id", id)
                .queryParam("color", color)
                .queryParam("sound", sound)
                .queryParam("material", material)
                //? WingState
                .queryParam("height", String.valueOf(height)));
    }

    public void deleteDuck(TestCaseRunner runner, String id) {
        runner.$(http().client(yellowDuckService)
                .send()
                .delete("/api/duck/delete")
                .queryParam("id", id));
    }
}
