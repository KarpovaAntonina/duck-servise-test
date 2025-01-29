package autotests.tests.Duck;

import autotests.clients.DuckActionsClient;
import autotests.payloads.Duck;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

@Epic("Тесты на duck-controller")
@Feature("Эндпоинт /api/duck/delete")
public class DeleteDuckTest extends DuckActionsClient {

    @Test(description = "Удалить утку")
    @CitrusTest
    public void successfulDelete(@Optional @CitrusResource TestCaseRunner runner) {
        String color = "yellow";
        double height = 0.01;
        String material = "wood";
        String sound = "quack";
        WingsState wingsState = WingsState.FIXED;
        setDuckId(runner, 12345678);

        runner.$(doFinally().actions(context ->
                databaseExecute(runner, "DELETE FROM DUCK WHERE ID=" + DUCK_ID_VAR_VALUE)));
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

        deleteDuck(runner);
        validateResponse(runner, HttpStatus.OK, "duckTest/successfulDelete.json");

        validateDuckInNotDatabase(runner);
    }
}
