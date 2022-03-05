package manager;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.*;

/*
TaskManager - класс управляющий всеми задачами.
 */
public class TaskManager {

    private Map<Integer, Task> taskMap;
    private Map<Integer, Epic> epicMap;
    private Map<Integer, Integer> subtaskEpicMap;

    public TaskManager() {
        taskMap = new HashMap<>();
        epicMap = new HashMap<>();
        subtaskEpicMap = new HashMap<>();
    }

    // Создание. Сам объект должен передаваться в качестве параметра:
    public void saveEpic(Epic epic) {
        epicMap.put(epic.getId(), epic);
        if (!epic.getSubtaskMap().isEmpty()) {
            epic.getStatus();
        }
    }

    public void saveTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    public void saveSubtask(Subtask subtask) {
        epicMap.get(subtask.getEpicId()).newSubtask(subtask);
        subtaskEpicMap.put(subtask.getId(), subtask.getEpicId());
    }

    // Получение списка всех задач:
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        for (Map.Entry<Integer, Task> entry : taskMap.entrySet()) {
            tasks.add(entry.getValue());
        }
        return tasks;
    }

    public List<Epic> getAllEpics() {
        List<Epic> epics = new ArrayList<>();
        for (Map.Entry<Integer, Epic> entry : epicMap.entrySet()) {
            epics.add(entry.getValue());
        }
        return epics;
    }

    public List<Subtask> getAllSubtasks() {
        List<Subtask> subtasks = new ArrayList<>();
        for (Epic epic : epicMap.values()) {
            for (Subtask subtask : epic.getSubtaskMap().values()) {
                subtasks.add(subtask);
            }
        }
        return subtasks;
    }

    // Удаление всех задач:
    public void clearTasks() {
        taskMap.clear();
    }

    public void clearEpics() {
        epicMap.clear();
    }

    public void clearSubtasks() {
        for (Epic epic : epicMap.values()) {
            epic.getSubtaskMap().clear();
        }
    }

    // Получение по идентификатору:
    public Task getTaskById(int taskId) {
        return taskMap.get(taskId);
    }

    public Epic getEpicById(int epicId) {
        return epicMap.get(epicId);
    }

    public Subtask getSubtaskById(int subtaskId) {
        return epicMap
                .get(subtaskEpicMap
                        .get(subtaskId))
                .getSubtaskMap()
                .get(subtaskId);
    }

    // Обновление. Новая версия объекта с верным идентификатором передаются в виде параметра:
    public void updateTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        epicMap.put(epic.getId(), epic);
        epicMap.get(epic.getId()).getStatus();
    }

    public void updateSubtask(Subtask subtask) {
        epicMap.get(subtask.getEpicId()).getSubtaskMap().put(subtask.getId(), subtask);
        epicMap.get(subtask.getEpicId()).getStatus();
    }

    // Удаление по идентификатору:
    public void deleteTask(int taskId) {
        taskMap.remove(taskId);
        System.out.println("Удален model.Task id: " + taskId);
    }

    public void deleteEpic(int epicId) {
        epicMap.remove(epicId);
        System.out.println("Удален model.Epic id: " + epicId);
    }

    public void deleteSubtask(int subtaskId) {
        for (Epic epic : epicMap.values()) {
            epic.getSubtaskMap().remove(subtaskId);
        }
        System.out.println("Удален model.Subtask id: " + subtaskId);
    }

    // Получение списка всех подзадач определённого эпика:
    public List<Subtask> getAllSubtaskByEpic(int epicId) {
        List<Subtask> subtaskList = new ArrayList<>();
        for (Subtask subtask : epicMap.get(epicId).getSubtaskMap().values()) {
            subtaskList.add(subtask);
        }
        return subtaskList;
    }

    // Выводим в консоль все таски для проверки:
    public void printAllTasks(List<Task> tasks) {
        for (Task task : tasks) {
            System.out.println("id: " + task.getId() + ". " + task.getName());
        }
    }

    // Выводим в консоль все епики с подзадачами для проверки:
    public void printAllEpicsAndSubtasks(List<Epic> epics) {
        for (Epic epic : epics) {
            System.out.println("id: " + epic.getId() + ". " + epic.getName());
            for (Subtask subtask : epic.getSubtaskMap().values()) {
                System.out.println("    id: " + subtask.getId() + ". " + subtask.getName());
            }
        }
    }
}
