import java.util.HashSet;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        TaskManager inMemoryTaskManager = Managers.getDefault();
        inMemoryTaskManager.addTask(new Task("Task1", "1", Task.Status.NEW));
        inMemoryTaskManager.addTask(new Task("Task2", "2", Task.Status.NEW));
        inMemoryTaskManager.addEpic(new Epic("Epic3", "3", new HashSet<>()));
        inMemoryTaskManager.addEpic(new Epic("Epic4", "4", new HashSet<>()));
        inMemoryTaskManager.addSubtask(new Subtask("Subtask5", "5", Task.Status.NEW, 3));
        inMemoryTaskManager.addSubtask(new Subtask("Subtask6", "6", Task.Status.NEW, 3));
        inMemoryTaskManager.addSubtask(new Subtask("Subtask7", "7", Task.Status.NEW, 3));
        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            int r = rand.nextInt(1, 4);
            if (r == 1) {
                inMemoryTaskManager.getTask(rand.nextInt(0, 2));
            }
            if (r == 2) {
                inMemoryTaskManager.getEpic(rand.nextInt(2, 4));
            }
            if (r == 3) {
                inMemoryTaskManager.getSubtask(rand.nextInt(4, 7));
            }
            System.out.println();
            System.out.println(inMemoryTaskManager.history());
            System.out.println();
        }

    }
}
