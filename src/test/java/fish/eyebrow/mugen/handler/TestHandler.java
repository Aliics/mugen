package fish.eyebrow.mugen.handler;

import fish.eyebrow.mugen.Handler;
import fish.eyebrow.mugen.model.TestInput;
import fish.eyebrow.mugen.model.TestOutput;

import java.util.HashSet;

public final class TestHandler implements Handler<TestInput, TestOutput> {
    @Override
    public TestOutput handle(final TestInput input) {
        final var unique = new HashSet<Byte>();
        for (final byte v : input.getId().getBytes()) {
            unique.add(v);
        }

        return new TestOutput(input.getId(), unique);
    }

    @Override
    public Class<TestInput> inputType() {
        return TestInput.class;
    }

    @Override
    public Class<TestOutput> outputType() {
        return TestOutput.class;
    }
}
