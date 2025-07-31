import java.util.HashSet;
import java.util.Objects;

public class Epic extends Task {
    private HashSet<Integer> subtaskIds;

    public Epic(String name, String description, HashSet<Integer> subtaskIds) {
        super(name, description, Status.NEW);
        this.subtaskIds = subtaskIds;
    }

    public HashSet<Integer> getSubtaskIds() {
        return new HashSet<>(subtaskIds);
    }

    public void setSubtaskIds(HashSet<Integer> subtaskIds) {
        this.subtaskIds = subtaskIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtaskIds, epic.subtaskIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtaskIds);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtaskIds=" + subtaskIds +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}' + '\n';
    }
}
