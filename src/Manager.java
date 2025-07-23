import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    private HashMap<Integer, Task> tasks = new HashMap<Integer, Task>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<Integer, Subtask>();
    private HashMap<Integer, Epic> epics = new HashMap<Integer, Epic>();
    private int nextId = 0;


    public void setTasks(HashMap<Integer, Task> tasks) {
        this.tasks = tasks;
    }

    public void setSubtasks(HashMap<Integer, Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public void setEpics(HashMap<Integer, Epic> epics) {
        this.epics = epics;
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<Task>(tasks.values());
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public void addTask(Task task) {
        task.setId(nextId++);
        tasks.put(task.getId(), task);
    }

    public boolean updateTask(Task task, int id) {
        if (!tasks.containsKey(id)) {
            return false;
        }
        task.setId(id);
        tasks.put(task.getId(), task);
        return true;
    }

    public boolean deleteTask(int id) {
        if (!tasks.containsKey(id)) {
            return false;
        }
        tasks.remove(id);
        return true;
    }

    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<Subtask>(subtasks.values());
    }

    public void deleteAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.getSubtaskIds().clear();
            epic.setStatus(Task.Status.NEW);
        }
        subtasks.clear();
    }

    public Task getSubtask(int id) {
        return subtasks.get(id);
    }

    public boolean addSubtask(Subtask subtask) {
        if (!epics.containsKey(subtask.getEpicId())) {
            return false;
        }
        subtask.setId(nextId++);
        subtasks.put(subtask.getId(), subtask);
        epics.get(subtask.getEpicId()).getSubtaskIds().add(subtask.getId());
        checkEpicStatus(epics.get(subtask.getEpicId()));
        return true;
    }

    public boolean updateSubtask(Subtask subtask, int id) {
        if (!subtasks.containsKey(id)) {
            return false;
        }
        subtask.setId(id);
        subtasks.put(subtask.getId(), subtask);
        checkEpicStatus(epics.get(subtask.getEpicId()));
        return true;
    }

    public boolean deleteSubtask(int id) {
        if (!subtasks.containsKey(id)) {
            return false;
        }
        Epic epic = epics.get(subtasks.get(id).getEpicId());
        epic.getSubtaskIds().remove(subtasks.get(id).getId());
        subtasks.remove(id);
        checkEpicStatus(epic);
        return true;
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public boolean addEpic(Epic epic) {
        epic.setId(nextId++);
        for (int subtaskId : epic.getSubtaskIds()) {
            if (!subtasks.containsKey(subtaskId)) {
                return false;
            }
        }
        epics.put(epic.getId(), epic);
        checkEpicStatus(epic);
        return true;
    }

    public boolean updateEpic(Epic epic, int id) {
        if (!epics.containsKey(id)) {
            return false;
        }
        for (int subtaskId : epic.getSubtaskIds()) {
            if (!subtasks.containsKey(subtaskId)) {
                return false;
            }
        }
        epic.setId(id);
        epics.put(epic.getId(), epic);
        checkEpicStatus(epic);
        return true;
    }

    public boolean deleteEpic(int id) {
        if (!epics.containsKey(id)) {
            return false;
        }
        for (int subtaskId : epics.get(id).getSubtaskIds()) {
            deleteSubtask(subtaskId);
        }
        epics.remove(id);

        return true;
    }

    public ArrayList<Subtask> getAllSubtasksForEpic(int id) {
        if (!epics.containsKey(id)) {
            return null;
        }
        ArrayList<Subtask> subtasksForEpic = new ArrayList<Subtask>();
        for (int subtaskIds : epics.get(id).getSubtaskIds()) {
            subtasksForEpic.add(subtasks.get(subtaskIds));
        }
        return subtasksForEpic;
    }

    public void checkEpicStatus(Epic epic) {
        int news = 0;
        int inProgress = 0;
        int dones = 0;
        for (Integer subtaskId : epic.getSubtaskIds()) {
            if (!subtasks.containsKey(subtaskId)) {
                continue;
            }
            switch (subtasks.get(subtaskId).status) {
                case NEW -> news++;
                case IN_PROGRESS -> {inProgress++; epic.setStatus(Task.Status.IN_PROGRESS); return;}
                case DONE -> dones++;
            }
        }
        if (inProgress == 0 && dones == 0 && news == epic.getSubtaskIds().size()) {
            epic.setStatus(Task.Status.NEW);
            return;
        }
        if (inProgress == 0 && news == 0 && dones == epic.getSubtaskIds().size()) {
            epic.setStatus(Task.Status.DONE);
            return;
        }
    }
}
