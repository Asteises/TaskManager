import manager.Managers;
import manager.TaskManager;
import model.Epic;
import model.Subtask;
import model.Task;

public class Main {
    public static void main(String[] args) {
        Managers managers = new Managers();
        TaskManager manager = managers.getDefault();

        Task task1 = new Task("Заголовок task1", "Текст task1");
        Task task2 = new Task("Заголовок task2", "Текст task2");
        manager.saveTask(task1);
        manager.saveTask(task2);

        Epic epic1 = new Epic("Заголовок epic1", "Текст epic1");
        Epic epic2 = new Epic("Заголовок epic2", "Текст epic2");
        manager.saveEpic(epic1);
        manager.saveEpic(epic2);

        Subtask subtask1 = new Subtask(epic1.getId(), "Заголовок subtask1", "Текст subtask1");
        Subtask subtask2 = new Subtask(epic1.getId(), "Заголовок subtask2", "Текст subtask2");
        Subtask subtask3 = new Subtask(epic1.getId(), "Заголовок subtask3", "Текст subtask3");

        manager.saveSubtask(subtask1);
        manager.saveSubtask(subtask2);
        manager.saveSubtask(subtask3);

        System.out.println("************************");
        manager.getTaskById(1);
        manager.history();
        System.out.println("************************");
        manager.getTaskById(2);
        manager.history();
        System.out.println("************************");
        manager.getEpicById(3);
        manager.history();
        System.out.println("************************");
        manager.deleteTask(task1.getId());
        manager.history();
        System.out.println("************************");
        manager.deleteEpic(epic1.getId());
        manager.history();
    }
}