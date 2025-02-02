package autotests.tests;

import autotests.EndpointConfig;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class BaseTest extends TestNGCitrusSpringSupport {

    protected static final String DUCK_ID_VAR_NAME = "duckId";
    protected static final String DUCK_ID_VAR_VALUE = "${" + DUCK_ID_VAR_NAME + "}";

    @Autowired
    protected HttpClient yellowDuckService;

    @Step("Отправка Get-запроса")
    protected void sendGetRequest(TestCaseRunner runner, String path) {
        runner.$(http()
                .client(yellowDuckService)
                .send()
                .get(path)
                .queryParam("id", DUCK_ID_VAR_VALUE));
    }

    @Step("Отправка Put-запроса")
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

    @Step("Отправка Post-запроса")
    protected void sendPostRequest(TestCaseRunner runner, String path, Object body) {
        runner.$(http()
                .client(yellowDuckService)
                .send()
                .post(path)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(body, new ObjectMapper())));
    }
    @Step("Отправка Delete-запроса")
    protected void sendDeleteRequest(TestCaseRunner runner, String path) {
        runner.$(http()
                .client(yellowDuckService)
                .send()
                .delete(path)
                .queryParam("id", DUCK_ID_VAR_VALUE));
    }

    @Step("Валидация ответа создания утки")
    public void validateCreateResponse(TestCaseRunner runner, String color, double height, String material, String sound, WingsState wingsState) {
        runner.$(http()
                .client(yellowDuckService)
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

    @Step("Валидация ответа получения свойств утки по объекту")
    public void validatePropResponse(TestCaseRunner runner, Object body) {
        runner.$(http()
                .client(yellowDuckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(body, new ObjectMapper())));
    }

    @Step("Валидация ответа по статусу и ресурсу")
    public void validateResponse(TestCaseRunner runner, HttpStatus status, String resourcePath) {
        runner.$(http()
                .client(yellowDuckService)
                .receive()
                .response(status)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ClassPathResource(resourcePath)));
    }

    @Step("Валидация ответа по статусу и тексту")
    public void validateResponseMessage(TestCaseRunner runner, HttpStatus status, String responseMessage) {
        runner.$(http()
                .client(yellowDuckService)
                .receive()
                .response(status)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(responseMessage));
    }

}
