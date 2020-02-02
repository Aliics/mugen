package fish.eyebrow.mugen.model;

import java.util.Objects;

public final class TestInput {
    private String id;

    public TestInput() {}

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TestInput testInput = (TestInput) o;
        return Objects.equals(id, testInput.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TestInput{" +
                "id='" + id + '\'' +
                '}';
    }
}
