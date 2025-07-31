import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private Map<Integer, TaskNode> tasksMap = new HashMap<Integer, TaskNode>();
    private TaskNode firstTask;
    private TaskNode lastTask;
    private int size;

    @Override
    public void add(Task task) {
        if (task != null) {
            remove(task.getId());
            linkLast(task);
        }
    }

    @Override
    public void remove(int id) {
        TaskNode taskNode = tasksMap.remove(id);
        if (taskNode != null) {
            removeNode(taskNode);
        }
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    public void linkLast(Task task) {
        if (task != null) {
            TaskNode taskNode = new TaskNode(task, null, lastTask);
            if (size == 0) {
                firstTask = taskNode;
            }
            else {
                lastTask.setNextTask(taskNode);
            }
            lastTask = taskNode;
            tasksMap.put(task.getId(), taskNode);
            size++;
        }
    }

    public List<Task> getTasks() {
        List<Task> history = new ArrayList<>();
        TaskNode currentNode = firstTask;
        while (currentNode != null) {
            history.add(currentNode.getTask());
            currentNode = currentNode.getNextTask();
        }
        return history;
    }

    public void removeNode(TaskNode taskNode) {
        if (taskNode != null) {
            if (taskNode.prevTask != null) {
                taskNode.prevTask.setNextTask(taskNode.nextTask);
            }
            else {
                firstTask = taskNode.nextTask;
            }
            if (taskNode.nextTask != null) {
                taskNode.nextTask.setPrevTask(taskNode.prevTask);
            }
            else {
                lastTask = taskNode.prevTask;
            }
        }
    }

    private static class TaskNode {
        private Task task;
        private TaskNode nextTask;
        private TaskNode prevTask;

        public TaskNode(Task task, TaskNode nextTask, TaskNode prevTask) {
            this.task = task;
            this.nextTask = nextTask;
            this.prevTask = prevTask;
        }

        public Task getTask() {
            return task;
        }

        public void setTask(Task task) {
            this.task = task;
        }

        public TaskNode getNextTask() {
            return nextTask;
        }

        public void setNextTask(TaskNode nextTask) {
            this.nextTask = nextTask;
        }

        public TaskNode getPrevTask() {
            return prevTask;
        }

        public void setPrevTask(TaskNode prevTask) {
            this.prevTask = prevTask;
        }
    }

}


