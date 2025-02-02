package autotests.tests.DuckActions;

import autotests.clients.DuckActionsClient;
import autotests.payloads.Duck;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Flaky;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.Collections;

@Epic("Тесты на duck-action-controller")
@Feature("Эндпоинт /api/duck/action/quack")
public class DuckQuackTests extends DuckActionsClient {

    @Test(description = "Проверить, что утка с нечетным id крякает")
    @CitrusTest
    public void successfulQuackOddId(@Optional @CitrusResource TestCaseRunner runner) {
        int repetitionCount = 2;
        int soundCount = 3;
        Duck duck = new Duck()
                .color("yellow")
                .height(0.01)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.FIXED);

        createDuck(runner, duck);
        extractDuckId(runner);

        if (isEvenDuckId(runner)) {
            createDuck(runner, duck);
            extractDuckId(runner);
        }

        duckQuack(runner, repetitionCount, soundCount);
        validateResponseMessage(
                runner,
                HttpStatus.OK,
                "{\n\"sound\": \"" + getQuackMessage(repetitionCount, soundCount) + "\"\n}"
        );
    }

    // Тест не проходит, так как утка "moo"
    @Flaky
    @Test(description = "Проверить, что утка с четным id крякает")
    @CitrusTest
    public void successfulQuackEvenId(@Optional @CitrusResource TestCaseRunner runner) {
        int repetitionCount = 2;
        int soundCount = 3;
        Duck duck = new Duck()
                .color("yellow")
                .height(0.01)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.FIXED);

        createDuck(runner, duck);
        extractDuckId(runner);

        if (!isEvenDuckId(runner)) {
            createDuck(runner, duck);
            extractDuckId(runner);
        }

        duckQuack(runner, repetitionCount, soundCount);
        validateResponseMessage(
                runner,
                HttpStatus.OK,
                "{\n\"sound\": \"" + getQuackMessage(repetitionCount, soundCount) + "\"\n}"
        );
    }

    private String getQuackMessage(int repetitionCount, int soundCount) {
        String repeatedSound = String.join("-", Collections.nCopies(repetitionCount, "quack"));
        return String.join(", ", Collections.nCopies(soundCount, repeatedSound));
    }
}
