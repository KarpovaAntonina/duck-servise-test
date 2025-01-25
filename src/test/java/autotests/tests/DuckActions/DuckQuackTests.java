package autotests.tests.DuckActions;

import autotests.clients.DuckActionsClient;
import autotests.payloads.Duck;
import autotests.payloads.WingState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.Collections;

public class DuckQuackTests extends DuckActionsClient {

    @Test(description = "Проверить, что утка с нечетным id крякает")
    @CitrusTest
    public void successfulQuackOddId(@Optional @CitrusResource TestCaseRunner runner) {
        int repetitionCount = 2;
        int soundCount = 3;
        Duck duck = new Duck().color("yellow").height(0.01).material("rubber").sound("quack").wingsState(WingState.FIXED);

        createDuck(runner, duck);
        extractId(runner, "duckId");

        if (isEvenVariable(runner, "duckId")) {
            createDuck(runner, duck);
            extractId(runner, "duckId");
        }

        duckQuack(runner, "${duckId}", repetitionCount, soundCount);
        validateResponseMessage(
                runner,
                HttpStatus.OK,
                "{\n\"sound\": \"" + getQuackMessage(repetitionCount, soundCount) + "\"\n}"
        );
    }

    // Тест не проходит, так как утка "moo"
    @Test(description = "Проверить, что утка с четным id крякает")
    @CitrusTest
    public void successfulQuackEvenId(@Optional @CitrusResource TestCaseRunner runner) {
        int repetitionCount = 2;
        int soundCount = 3;
        Duck duck = new Duck().color("yellow").height(0.01).material("rubber").sound("quack").wingsState(WingState.FIXED);

        createDuck(runner, duck);
        extractId(runner, "duckId");

        if (!isEvenVariable(runner, "duckId")) {
            createDuck(runner, duck);
            extractId(runner, "duckId");
        }

        duckQuack(runner, "${duckId}", repetitionCount, soundCount);
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
