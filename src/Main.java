import java.util.HashMap;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();

        // Создаем задачи
        manager.addTask(new Task("Task1", "Description 1", Task.Status.NEW));
        manager.addTask(new Task("Task2", "Description 2", Task.Status.NEW));

        // Создаем эпики
        manager.addEpic(new Epic("Epic1", "Epic 1 desc", new HashSet<>()));
        manager.addEpic(new Epic("Epic2", "Epic 2 desc", new HashSet<>()));

        // Создаем подзадачи
        manager.addSubtask(new Subtask("Subtask1", "Desc 1", Task.Status.NEW, 2));
        manager.addSubtask(new Subtask("Subtask2", "Desc 2", Task.Status.NEW, 2));
        manager.addSubtask(new Subtask("Subtask3", "Desc 3", Task.Status.NEW, 3));

        // Печатаем
        System.out.println("Tasks: " + manager.getAllTasks());
        System.out.println("Epics: " + manager.getAllEpics());
        System.out.println("Subtasks: " + manager.getAllSubtasks());

        // Обновляем статусы
        manager.updateTask(new Task("Task1", "Updated desc", Task.Status.DONE), 0);
        manager.updateSubtask(new Subtask("Subtask1", "Updated", Task.Status.IN_PROGRESS, 2), 4);

        // Печатаем после обновления
        System.out.println("\nAfter update:");
        System.out.println("Tasks: " + manager.getAllTasks());
        System.out.println("Epics: " + manager.getAllEpics());
        System.out.println("Subtasks: " + manager.getAllSubtasks());

        // Удаляем
        manager.deleteTask(0);
        manager.deleteEpic(2);

        // Печатаем после удаления
        System.out.println("\nAfter deletion:");
        System.out.println("Tasks: " + manager.getAllTasks());
        System.out.println("Epics: " + manager.getAllEpics());
        System.out.println("Subtasks: " + manager.getAllSubtasks());
    }
}
