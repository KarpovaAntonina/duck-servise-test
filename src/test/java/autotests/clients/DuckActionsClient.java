package autotests.clients;

import autotests.EndpointConfig;
import autotests.payloads.WingsState;
import autotests.tests.BaseTest;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;

import java.util.concurrent.atomic.AtomicInteger;

import static autotests.payloads.WingsState.FIXED;
import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;
import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckActionsClient extends BaseTest {

    @Autowired
    protected SingleConnectionDataSource testDb;

    @Step("Проверка четности duckId")
    protected boolean isEvenDuckId(TestCaseRunner runner) {
        AtomicInteger duckId = new AtomicInteger(0);
        runner.$(action -> {
            duckId.set(Integer.parseInt(action.getVariable(DUCK_ID_VAR_NAME)));
        });

        return (duckId.get() % 2 == 0);
    }

    @Step("Эндпоинт для плавания утки")
    public void duckSwim(TestCaseRunner runner) {
        sendGetRequest(runner, "/api/duck/action/swim");
    }

    @Step("Эндпоинт для полета утки")
    public void duckFly(TestCaseRunner runner) {
        sendGetRequest(runner, "/api/duck/action/fly");
    }

    @Step("Эндпоинт для кряканья утки")
    public void duckQuack(TestCaseRunner runner, int repetitionCount, int soundCount) {
        runner.$(http()
                .client(yellowDuckService)
                .send()
                .get("/api/duck/action/quack")
                .queryParam("id", DUCK_ID_VAR_VALUE)
                .queryParam("repetitionCount", String.valueOf(repetitionCount))
                .queryParam("soundCount", String.valueOf(soundCount)));
    }

    @Step("Эндпоинт для получения свойств утки")
    public void duckProperties(TestCaseRunner runner) {
        sendGetRequest(runner, "/api/duck/action/properties");
    }

    @Step("Извлечение id из текста ответа создания утки")
    public void extractDuckId(TestCaseRunner runner) {
        runner.$(http()
                .client(yellowDuckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", DUCK_ID_VAR_NAME)));
    }

    @Step("Установка переменной id")
    public void setDuckId(TestCaseRunner runner, int value) {
        runner.variable(DUCK_ID_VAR_NAME, value);
    }

    @Step("Эндпоинт для создания утки")
    public void createDuck(TestCaseRunner runner, Object body) {
        sendPostRequest(runner, "/api/duck/create", body);
    }

    @Step("Добавление утки в базу")
    public void insertDuckToDatabase(TestCaseRunner runner, String color, double height, String material, String sound, WingsState wingsState) {
        databaseExecute(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n"
                        + "values ("
                        + DUCK_ID_VAR_VALUE + ", "
                        + "'" + color + "', "
                        + height + ", "
                        + "'" + material + "', "
                        + "'" + sound + "', "
                        + "'" + wingsState + "'"
                        + ")");
    }

    @Step("Удаление утки из базы")
    public void deleteDuckFromDatabase(TestCaseRunner runner) {
        databaseExecute(runner, "DELETE FROM DUCK WHERE ID=" + DUCK_ID_VAR_VALUE);
    }

    @Step("Эндпоинт для обновления утки")
    public void updateDuck(TestCaseRunner runner, String color, double height, String material, String sound, WingsState wingsState) {
        sendPutRequest(runner, "/api/duck/update", color, height, material, sound, wingsState);
    }

    @Step("Эндпоинт для удаления утки")
    public void deleteDuck(TestCaseRunner runner) {
        sendDeleteRequest(runner, "/api/duck/delete");
    }

    @Step("Выполнения SQL")
    public void databaseExecute(TestCaseRunner runner, String sql) {
        runner.$(sql(testDb).statement(sql));
    }

    @Step("Валидация данных в базе")
    protected void validateDuckInDatabase(TestCaseRunner runner, String color, double height, String material, String sound, WingsState wingsState) {
        runner.$(query(testDb)
                .statement("SELECT * FROM DUCK WHERE ID=" + DUCK_ID_VAR_VALUE)
                .validate("COLOR", color)
                .validate("HEIGHT", String.valueOf(height))
                .validate("MATERIAL", material)
                .validate("SOUND", sound)
                .validate("WINGS_STATE", String.valueOf(wingsState)));
    }

    @Step("Валидация, что данных нет в базе")
    protected void validateDuckInNotDatabase(TestCaseRunner runner) {
        runner.$(query(testDb)
                .statement("SELECT COUNT(1) AS ALL_COUNT FROM DUCK WHERE ID=" + DUCK_ID_VAR_VALUE)
                .validate("ALL_COUNT", "0"));
    }
}
