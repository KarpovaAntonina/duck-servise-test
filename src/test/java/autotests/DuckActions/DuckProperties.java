package autotests.DuckActions;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckProperties extends TestNGCitrusSpringSupport {

    @Test(description = "Проверить характеристики утки")
    @CitrusTest
    public void successfulProperties(@Optional @CitrusResource TestCaseRunner runner) {

    }
}
