package fish.eyebrow.mugen.model;

import java.util.Objects;
import java.util.Set;

public final class TestOutput {
    private final String id;
    private final Set<Byte> unique;

    public TestOutput(final String id, final Set<Byte> unique) {
        this.id = id;
        this.unique = unique;
    }

    public String getId() {
        return id;
    }

    public Set<Byte> getUnique() {
        return unique;
    }

    @Override
    public boolean equals(final Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TestOutput that = (TestOutput) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(unique, that.unique);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, unique);
    }

    @Override
    public String toString() {
        return "TestOutput{" +
                "id='" + id + '\'' +
                ", unique=" + unique +
                '}';
    }
}
