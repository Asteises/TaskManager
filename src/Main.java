import manager.FileBackedTasksManager;
import manager.InMemoryHistoryManager;
import manager.Managers;
import manager.TaskManager;
import model.Epic;
import model.Subtask;
import model.Task;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Managers managers = new Managers();
        TaskManager manager = managers.getDefault();
        String backup = "C:\\Users\\User\\IdeaProjects\\TaskManagerGitHub\\src\\resourses\\backup.csv";
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(backup);

        Task task1 = new Task("Заголовок task1", "Текст task1");
        Task task2 = new Task("Заголовок task2", "Текст task2");
        fileBackedTasksManager.saveTask(task1);
        fileBackedTasksManager.saveTask(task2);

        Epic epic1 = new Epic("Заголовок epic1", "Текст epic1");
        Epic epic2 = new Epic("Заголовок epic2", "Текст epic2");
        fileBackedTasksManager.saveEpic(epic1);
        fileBackedTasksManager.saveEpic(epic2);

        Subtask subtask1 = new Subtask(epic1.getId(), "Заголовок subtask1", "Текст subtask1");
        Subtask subtask2 = new Subtask(epic1.getId(), "Заголовок subtask2", "Текст subtask2");
        Subtask subtask3 = new Subtask(epic1.getId(), "Заголовок subtask3", "Текст subtask3");
        fileBackedTasksManager.saveSubtask(subtask1);
        fileBackedTasksManager.saveSubtask(subtask2);
        fileBackedTasksManager.saveSubtask(subtask3);

        fileBackedTasksManager.getTaskById(task1.getId());
        fileBackedTasksManager.getTaskById(task2.getId());
        fileBackedTasksManager.deleteTask(task1.getId());

        File file = new File(backup);
        FileBackedTasksManager backUpManager = FileBackedTasksManager.loadFromFile(file);
        backUpManager.toString();
    }
}