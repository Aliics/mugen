package fish.eyebrow.mugen;

import fish.eyebrow.mugen.handler.TestHandler;
import fish.eyebrow.mugen.util.FileUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

final class RouterTest {
    private Router router;

    @BeforeEach
    void setUp() {
        router = new Router();
        router.handler("/test", new TestHandler());
    }

    @Test
    void happyPath() {
        final var request = FileUtil.readResourceAsBytes("test_http_request.txt");
        final var expectedResponse = FileUtil.readResourceAsBytes("test_http_response.txt");

        final var response = router.delegate(request);

        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    void shouldReturn404WhenAttemptingToAccessNonExistentHandler() {
        final var request = FileUtil.readResourceAsBytes("404_http_request.txt");
        final var expectedResponse = FileUtil.readResourceAsBytes("404_http_response.txt");

        final var response = router.delegate(request);

        assertThat(response).isEqualTo(expectedResponse);
    }
}
