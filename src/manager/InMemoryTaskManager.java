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
    private final Map<Integer, Subtask> subtaskEpicMap;

    public InMemoryTaskManager() {
        inMemoryHistoryManager = new InMemoryHistoryManager();
        taskMap = new HashMap<>();
        epicMap = new HashMap<>();
        subtaskEpicMap = new HashMap<>();
    }

    @Override
    public void saveEpic(Epic epic) {
        epicMap.put(epic.getId(), epic);
        if (!epic.getSubtaskList().isEmpty()) {
            epic.changeStatus();
        }
    }

    @Override
    public void saveTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    @Override
    public void saveSubtask(Subtask subtask) {
        Epic epic = epicMap.get(subtask.getEpicId());
        epic.newSubtask(subtask);
        subtaskEpicMap.put(subtask.getId(), subtask);
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(taskMap.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epicMap.values());
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtaskEpicMap.values());
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
            epic.getSubtaskList().clear();
        }
    }

    @Override
    public Task getTaskById(int taskId) {
        Task task = taskMap.get(taskId);
        inMemoryHistoryManager.add(task);
        return task;
    }

    @Override
    public Epic getEpicById(int epicId) {
        Epic epic = epicMap.get(epicId);
        inMemoryHistoryManager.add(epic);
        return epic;
    }

    @Override
    public Subtask getSubtaskById(int subtaskId) {
        Subtask subtask = subtaskEpicMap.get(subtaskId);
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
        epicMap.get(epic.getId()).changeStatus();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        subtaskEpicMap.put(subtask.getId(), subtask);
        Epic epic = epicMap.get(subtask.getEpicId());
        epic.getSubtaskList().add(subtask);
        epic.changeStatus();
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
            epic.getSubtaskList().remove(subtaskId);
        }
    }

    @Override
    public List<Subtask> getAllSubtaskByEpic(int epicId) {
        List<Subtask> subtaskList = new ArrayList<>();
        for (Subtask subtask : epicMap.get(epicId).getSubtaskList()) {
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
            for (Subtask subtask : epic.getSubtaskList()) {
                System.out.println("    id: " + subtask.getId() + ". " + subtask.getName());
            }
        }
    }

    @Override
    public List<Task> history() {
        List<Task> list = inMemoryHistoryManager.getHistory();
        for (Task task : list) {
            System.out.println("id: " + task.getId() + ". " + task.getName());
        }
        return list;
    }
}
