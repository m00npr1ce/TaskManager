import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private int nextId = 0;
    HistoryManager inMemoryHistoryManager;

    public InMemoryTaskManager(HistoryManager inMemoryHistoryManager) {
        this.inMemoryHistoryManager = inMemoryHistoryManager;
    }

    @Override
    public void setTasks(HashMap<Integer, Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void setSubtasks(HashMap<Integer, Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    @Override
    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    @Override
    public void setEpics(HashMap<Integer, Epic> epics) {
        this.epics = epics;
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void deleteAllTasks() {
        for (Integer id : tasks.keySet()) {
            inMemoryHistoryManager.remove(id);
        }
        tasks.clear();
    }

    @Override
    public Task getTask(int id) {
        if (tasks.get(id) != null) {
            inMemoryHistoryManager.add(tasks.get(id));
        }
        return tasks.get(id);
    }

    @Override
    public void addTask(Task task) {
        if (task != null) {
            task.setId(nextId++);
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public boolean updateTask(Task task, int id) {
        if (!tasks.containsKey(id)) {
            return false;
        }
        task.setId(id);
        tasks.put(task.getId(), task);
        return true;
    }

    @Override
    public boolean deleteTask(int id) {
        if (!tasks.containsKey(id)) {
            return false;
        }
        tasks.remove(id);
        inMemoryHistoryManager.remove(id);
        return true;
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void deleteAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.getSubtaskIds().clear();
            epic.setStatus(Task.Status.NEW);
        }
        for (Integer id : subtasks.keySet()) {
            inMemoryHistoryManager.remove(id);
        }
        subtasks.clear();
    }

    @Override
    public Task getSubtask(int id) {
        if (subtasks.get(id) != null) {
            inMemoryHistoryManager.add(subtasks.get(id));
        }
        return subtasks.get(id);
    }

    @Override
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

    @Override
    public boolean updateSubtask(Subtask subtask, int id) {
        if (!subtasks.containsKey(id)) {
            return false;
        }
        subtask.setId(id);
        subtasks.put(subtask.getId(), subtask);
        checkEpicStatus(epics.get(subtask.getEpicId()));
        return true;
    }

    @Override
    public boolean deleteSubtask(int id) {
        if (!subtasks.containsKey(id)) {
            return false;
        }
        Epic epic = epics.get(subtasks.get(id).getEpicId());
        epic.getSubtaskIds().remove(id);
        subtasks.remove(id);
        checkEpicStatus(epic);
        inMemoryHistoryManager.remove(id);
        return true;
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void deleteAllEpics() {
        for (Integer id : epics.keySet()) {
            inMemoryHistoryManager.remove(id);
        }
        epics.clear();
        for (Integer id : subtasks.keySet()) {
            inMemoryHistoryManager.remove(id);
        }
        subtasks.clear();
    }

    @Override
    public Epic getEpic(int id) {
        if (epics.get(id) != null) {
            inMemoryHistoryManager.add(epics.get(id));
        }
        return epics.get(id);
    }

    @Override
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

    @Override
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

    @Override
    public boolean deleteEpic(int id) {
        if (!epics.containsKey(id)) {
            return false;
        }
        for (int subtaskId : epics.get(id).getSubtaskIds()) {
            subtasks.remove(subtaskId);
            inMemoryHistoryManager.remove(subtaskId);
        }
        epics.remove(id);
        inMemoryHistoryManager.remove(id);
        return true;
    }

    @Override
    public ArrayList<Subtask> getAllSubtasksForEpic(int id) {
        if (!epics.containsKey(id)) {
            return null;
        }
        ArrayList<Subtask> subtasksForEpic = new ArrayList<>();
        for (int subtaskIds : epics.get(id).getSubtaskIds()) {
            subtasksForEpic.add(subtasks.get(subtaskIds));
        }
        return subtasksForEpic;
    }

    @Override
    public void checkEpicStatus(Epic epic) {
        int news = 0;
        int dones = 0;
        for (Integer subtaskId : epic.getSubtaskIds()) {
            if (!subtasks.containsKey(subtaskId)) {
                continue;
            }
            switch (subtasks.get(subtaskId).status) {
                case NEW -> news++;
                case IN_PROGRESS -> {epic.setStatus(Task.Status.IN_PROGRESS); return;}
                case DONE -> dones++;
            }
        }
        if (dones == 0 && news == epic.getSubtaskIds().size()) {
            epic.setStatus(Task.Status.NEW);
            return;
        }
        if (news == 0 && dones == epic.getSubtaskIds().size()) {
            epic.setStatus(Task.Status.DONE);
        }
    }

    @Override
    public List<Task> history() {
        return inMemoryHistoryManager.getHistory();
    }
}
