public class Task {
    String name;
    String description;
    int id;
    enum Status {
        NEW,
        IN_PROGRESS,
        DONE
    }

    public int getId() {
        return id;
    }
}
