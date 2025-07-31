import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface TaskManager {

    void setTasks(HashMap<Integer, Task> tasks);

    void setSubtasks(HashMap<Integer, Subtask> subtasks);

    HashMap<Integer, Epic> getEpics();

    void setEpics(HashMap<Integer, Epic> epics);

    ArrayList<Task> getAllTasks();

    void deleteAllTasks();

    Task getTask(int id);

    void addTask(Task task);

    boolean updateTask(Task task, int id);

    boolean deleteTask(int id);

    ArrayList<Subtask> getAllSubtasks();

    void deleteAllSubtasks();

    Task getSubtask(int id);

    boolean addSubtask(Subtask subtask);

    boolean updateSubtask(Subtask subtask, int id);

    boolean deleteSubtask(int id);

    ArrayList<Epic> getAllEpics();

    void deleteAllEpics();

    Epic getEpic(int id);

    boolean addEpic(Epic epic);

    boolean updateEpic(Epic epic, int id);

    boolean deleteEpic(int id);

    ArrayList<Subtask> getAllSubtasksForEpic(int id);

    void checkEpicStatus(Epic epic);

    List<Task> history();
}
