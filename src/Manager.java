import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Manager {
    private HashMap<Integer, Task> tasks = new HashMap<Integer, Task>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<Integer, Subtask>();
    private HashMap<Integer, Epic> epics = new HashMap<Integer, Epic>();

    public ArrayList<Task> getTasks() {
        return new ArrayList<Task>(tasks.values());
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public void addTask(Task task) {  //сделать проверку на добавление задачи с уже существующим id
        tasks.put(task.getId(), task);
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void deleteTask(int id) {
        tasks.remove(id);
    }

    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<Subtask>(subtasks.values());
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
    }

    public Task getSubtask(int id) {
        return subtasks.get(id);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
    }

    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
    }

    public void deleteSubtask(int id) {
        subtasks.remove(id);
    }

    
}
