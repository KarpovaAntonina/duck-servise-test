package autotests.tests;

import autotests.EndpointConfig;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class BaseTest extends TestNGCitrusSpringSupport {

    protected static final String DUCK_ID_VAR_NAME = "duckId";
    protected static final String DUCK_ID_VAR_VALUE = "${" + DUCK_ID_VAR_NAME + "}";

    @Autowired
    protected HttpClient yellowDuckService;

    protected void sendGetRequest(TestCaseRunner runner, String path) {
        runner.$(http()
                .client(yellowDuckService)
                .send()
                .get(path)
                .queryParam("id", DUCK_ID_VAR_VALUE));
    }

    protected void sendPutRequest(TestCaseRunner runner, String path, String color, double height, String material, String sound, WingsState wingsState) {
        runner.$(http()
                .client(yellowDuckService)
                .send()
                .put(path)
                .queryParam("id", DUCK_ID_VAR_VALUE)
                .queryParam("color", color)
                .queryParam("height", String.valueOf(height))
                .queryParam("material", material)
                .queryParam("sound", sound)
                .queryParam("wingsState", String.valueOf(wingsState)));
    }

    protected void sendPostRequest(TestCaseRunner runner, String path, Object body) {
        runner.$(http()
                .client(yellowDuckService)
                .send()
                .post(path)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(body, new ObjectMapper())));
    }

    protected void sendDeleteRequest(TestCaseRunner runner, String path) {
        runner.$(http()
                .client(yellowDuckService)
                .send()
                .delete(path)
                .queryParam("id", DUCK_ID_VAR_VALUE));
    }
}
