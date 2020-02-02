package fish.eyebrow.mugen;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;
import java.util.Optional;

final class HandlerWrapper<I, O> {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final Handler<I, O> handler;
    private final ObjectReader inputWriter;
    private final ObjectWriter outputReader;

    public HandlerWrapper(final Handler<I, O> handler) {
        this.handler = handler;

        inputWriter = objectMapper.readerFor(handler.inputType());
        outputReader = objectMapper.writerFor(handler.outputType());
    }

    public Optional<byte[]> handle(final byte[] input) {
        try {
            final I deserializedInput = inputWriter.readValue(input);
            final var deserializedOutput = handler.handle(deserializedInput);

            return Optional.of(outputReader.writeValueAsString(deserializedOutput).getBytes());
        } catch (final IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
