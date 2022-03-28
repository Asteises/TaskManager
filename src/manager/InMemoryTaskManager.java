package manager;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    private final InMemoryHistoryManager inMemoryHistoryManager;
    private final Map<Integer, Task> taskMap;
    private final Map<Integer, Epic> epicMap;
    private final Map<Integer, Integer> subtaskEpicMap;

    public InMemoryTaskManager() {
        inMemoryHistoryManager = new InMemoryHistoryManager();
        taskMap = new HashMap<>();
        epicMap = new HashMap<>();
        subtaskEpicMap = new HashMap<>();
    }

    @Override
    public void saveEpic(Epic epic) {
        epicMap.put(epic.getId(), epic);
        if (!epic.getSubtaskMap().isEmpty()) {
            epic.getStatus();
        }
    }

    @Override
    public void saveTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    @Override
    public void saveSubtask(Subtask subtask) {
        epicMap.get(subtask.getEpicId()).newSubtask(subtask);
        subtaskEpicMap.put(subtask.getId(), subtask.getEpicId());
    }

    @Override
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        for (Map.Entry<Integer, Task> entry : taskMap.entrySet()) {
            tasks.add(entry.getValue());
        }
        return tasks;
    }

    @Override
    public List<Epic> getAllEpics() {
        List<Epic> epics = new ArrayList<>();
        for (Map.Entry<Integer, Epic> entry : epicMap.entrySet()) {
            epics.add(entry.getValue());
        }
        return epics;
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        List<Subtask> subtasks = new ArrayList<>();
        for (Epic epic : epicMap.values()) {
            for (Subtask subtask : epic.getSubtaskMap().values()) {
                subtasks.add(subtask);
            }
        }
        return subtasks;
    }

    @Override
    public void clearTasks() {
        inMemoryHistoryManager.deleteAllTasksFromHistory(taskMap);
        taskMap.clear();
    }

    @Override
    public void clearEpics() {
        inMemoryHistoryManager.deleteAllEpicsFromHistory(epicMap);
        epicMap.clear();
    }

    @Override
    public void clearSubtasks() {
        inMemoryHistoryManager.deleteAllSubtasksFromHistory(epicMap);
        for (Epic epic : epicMap.values()) {
            epic.getSubtaskMap().clear();
        }
    }

    @Override
    public Task getTaskById(int taskId) {
        inMemoryHistoryManager.add(taskMap.get(taskId));
        return taskMap.get(taskId);
    }

    @Override
    public Epic getEpicById(int epicId) {
        inMemoryHistoryManager.add(epicMap.get(epicId));
        return epicMap.get(epicId);
    }

    @Override
    public Subtask getSubtaskById(int subtaskId) {
        Subtask subtask = epicMap
                .get(subtaskEpicMap
                        .get(subtaskId))
                .getSubtaskMap()
                .get(subtaskId);

        inMemoryHistoryManager.add(subtask);
        return subtask;
    }

    @Override
    public void updateTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        epicMap.put(epic.getId(), epic);
        epicMap.get(epic.getId()).getStatus();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        epicMap.get(subtask.getEpicId()).getSubtaskMap().put(subtask.getId(), subtask);
        epicMap.get(subtask.getEpicId()).getStatus();
    }

    @Override
    public void deleteTask(int taskId) {
        inMemoryHistoryManager.remove(taskId);
        taskMap.remove(taskId);
    }

    @Override
    public void deleteEpic(int epicId) {
        inMemoryHistoryManager.remove(epicId);
        epicMap.remove(epicId);
    }

    @Override
    public void deleteSubtask(int subtaskId) {
        for (Epic epic : epicMap.values()) {
            inMemoryHistoryManager.remove(subtaskId);
            epic.getSubtaskMap().remove(subtaskId);
        }
//        System.out.println("Удален model.Subtask id: " + subtaskId);
    }

    @Override
    public List<Subtask> getAllSubtaskByEpic(int epicId) {
        List<Subtask> subtaskList = new ArrayList<>();
        for (Subtask subtask : epicMap.get(epicId).getSubtaskMap().values()) {
            subtaskList.add(subtask);
        }
        return subtaskList;
    }

    @Override
    public void printAllTasks(List<Task> tasks) {
        for (Task task : tasks) {
            System.out.println("id: " + task.getId() + ". " + task.getName());
        }
    }

    @Override
    public void printAllEpicsAndSubtasks(List<Epic> epics) {
        for (Epic epic : epics) {
            System.out.println("id: " + epic.getId() + ". " + epic.getName());
            for (Subtask subtask : epic.getSubtaskMap().values()) {
                System.out.println("    id: " + subtask.getId() + ". " + subtask.getName());
            }
        }
    }

    // history() - метод выводит последние 10 просмотренных пользователем заметок;
    @Override
    public List<Task> history() {
        for (Task task : inMemoryHistoryManager.getHistory()) {
            System.out.println("id: " + task.getId() + ". " + task.getName());
        }
        return inMemoryHistoryManager.getHistory();
    }
}
