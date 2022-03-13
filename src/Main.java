import enums.Status;
import manager.InMemoryTaskManager;
import manager.TaskManager;
import model.Epic;
import model.Subtask;
import model.Task;

public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager manager = new InMemoryTaskManager();

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
        Subtask subtask3 = new Subtask(epic2.getId(), "Заголовок subtask3", "Текст subtask3");

        manager.saveSubtask(subtask1);
        manager.saveSubtask(subtask2);
        manager.saveSubtask(subtask3);

        System.out.println("************************");

        manager.history();
        System.out.println("************************");

//        System.out.println("========================");
//        System.out.println(manager.getAllTasks());
//        System.out.println(manager.getAllEpics());
//        System.out.println(manager.getAllSubtasks());
//        System.out.println("========================");

        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);
        subtask3.setStatus(Status.IN_PROGRESS);
        task1.setStatus(Status.IN_PROGRESS);

        manager.updateTask(task1);
        manager.updateSubtask(subtask1);
        manager.updateSubtask(subtask2);
        manager.updateSubtask(subtask3);



//        System.out.println(manager.getEpicById(epic1.getId()));
//        System.out.println(manager.getEpicById(epic2.getId()));
//        System.out.println("========================");

        manager.printAllTasks(manager.getAllTasks());
        manager.printAllEpicsAndSubtasks(manager.getAllEpics());

        manager.getTaskById(task1.getId());
        manager.getTaskById(task2.getId());
        manager.getSubtaskById(subtask1.getId());
        manager.getSubtaskById(subtask2.getId());
        manager.getSubtaskById(subtask2.getId());
        manager.getSubtaskById(subtask2.getId());
        manager.getSubtaskById(subtask2.getId());
        manager.getSubtaskById(subtask2.getId());
        manager.getSubtaskById(subtask2.getId());
        manager.getSubtaskById(subtask2.getId());
        System.out.println("************************");

        manager.history();
        System.out.println("************************");

        manager.deleteTask(1);
        manager.deleteEpic(4);
        manager.deleteSubtask(5);
        System.out.println("========================");

        manager.printAllTasks(manager.getAllTasks());
        manager.printAllEpicsAndSubtasks(manager.getAllEpics());

    }
}