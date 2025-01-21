package autotests.tests.DuckActions;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckPropertiesTests extends DuckActionsClient {

    @Test(description = "Проверить характеристики утки")
    @CitrusTest
    public void successfulProperties(@Optional @CitrusResource TestCaseRunner runner) {

    }
}

