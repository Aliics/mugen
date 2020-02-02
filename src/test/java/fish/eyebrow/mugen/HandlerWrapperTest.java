package fish.eyebrow.mugen;

import fish.eyebrow.mugen.handler.TestHandler;
import fish.eyebrow.mugen.model.TestInput;
import fish.eyebrow.mugen.model.TestOutput;
import fish.eyebrow.mugen.util.FileUtil;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

final class HandlerWrapperTest {
    private HandlerWrapper<TestInput, TestOutput> handlerWrapper;

    @BeforeEach
    void setUp() {
        handlerWrapper = new HandlerWrapper<>(new TestHandler());
    }

    @Test
    void happyPath() throws JSONException {
        final var testInput = FileUtil.readResourceAsBytes("test_input.json");
        final var expectedOutput = FileUtil.readResourceAsString("test_output.json");

        final var output = handlerWrapper.handle(testInput);

        assertThat(output.isPresent()).isTrue();
        assertEquals(new String(output.get()), expectedOutput, true);
    }
}