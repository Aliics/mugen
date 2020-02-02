package fish.eyebrow.mugen;

public interface Handler<I, O> {
    O handle(final I input);

    Class<I> inputType();

    Class<O> outputType();
}
