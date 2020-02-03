package fish.eyebrow.mugen;

import java.util.HashMap;
import java.util.Map;

public final class Router {
    private static final byte[] OK_STATUS_LINE = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n\r\n".getBytes();
    private static final byte[] INTERNAL_SERVER_ERROR = "HTTP/1.1 500 Internal Server Error".getBytes();
    private static final byte SPACE = 0x20;
    private static final byte CARRIAGE_RETURN = 0xD;
    private static final byte NEW_LINE = 0xA;
    private final Map<String, HandlerWrapper<?, ?>> handlers;

    public Router() {
        handlers = new HashMap<>();
    }

    public <I, O> void handler(final String path, final Handler<I, O> handler) {
        handlers.put(path, new HandlerWrapper<>(handler));
    }

    byte[] delegate(final byte[] request) {
        final var uri = getUri(request);
        final var body = getBody(request);

        final var handlerOutput = handlers.get(uri).handle(body);

        if (handlerOutput.isPresent()) {
            final var output = handlerOutput.get();

            final var outputBytes = new byte[OK_STATUS_LINE.length + output.length];
            for (int i = 0; i < outputBytes.length; i++) {
                if (i < OK_STATUS_LINE.length) {
                    outputBytes[i] = OK_STATUS_LINE[i];
                } else {
                    outputBytes[i] = output[i - OK_STATUS_LINE.length];
                }
            }

            return outputBytes;
        } else {
            return INTERNAL_SERVER_ERROR;
        }
    }

    private String getUri(final byte[] request) {
        boolean writable = false;
        final var uriBuilder = new StringBuilder();

        for (final byte requestByte : request) {
            if (requestByte == SPACE) {
                writable = true;

                if (uriBuilder.length() > 0) {
                    break;
                }

                continue;
            }

            if (writable) {
                uriBuilder.append((char) requestByte);
            }
        }

        return uriBuilder.toString();
    }

    private byte[] getBody(final byte[] request) {
        int startIndex = 0;

        for (int i = 0; i < request.length; i++) {
            if (i > request.length - 4) {
                break;
            }

            if (isBodyStart(request, i)) {
                startIndex = i + 4;
                break;
            }
        }

        final var body = new byte[request.length - startIndex];
        System.arraycopy(request, startIndex, body, 0, body.length);

        return body;
    }

    private boolean isBodyStart(final byte[] request, final int index) {
        return request[index] == CARRIAGE_RETURN &&
                request[index + 1] == NEW_LINE &&
                request[index + 2] == CARRIAGE_RETURN &&
                request[index + 3] == NEW_LINE;
    }
}
