package manager;

import model.Epic;
import model.Subtask;
import model.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class InMemoryTaskManager implements TaskManager {

    protected final InMemoryHistoryManager inMemoryHistoryManager;
    private final Map<String, Task> taskMap;
    private final Map<String, Epic> epicMap;
    private final Map<String, Subtask> subtaskEpicMap;

    private static Set<Task> taskSet;

    public InMemoryTaskManager() {
        inMemoryHistoryManager = new InMemoryHistoryManager();
        taskMap = new HashMap<>();
        epicMap = new HashMap<>();
        subtaskEpicMap = new HashMap<>();
        taskSet = new TreeSet<>(Comparator.comparing(Task::getStartTime));
    }

    public List<Task> getPrioritizedTasks() {
        return taskSet.stream().toList();
    }

    public static boolean checkTime(LocalDateTime startTime, int duration) {
        if (taskSet == null || (startTime.isAfter(taskSet.stream().toList().get(0).getStartTime()) &&
                startTime.plusMinutes(duration).isBefore(taskSet.stream().toList().
                        get(taskSet.size() - 1).getStartTime()))) {
            return true;
        }
        return false;
    }

    @Override
    public void saveEpic(Epic epic) {
        epicMap.put(epic.getId(), epic);
        if (!epic.getSubtasks().isEmpty()) {
            epic.changeStatus();
            if (checkTime(epic.getStartTime(), epic.getDuration())) {
                taskSet.add(epic);
            }
        }
    }

    @Override
    public void saveTask(Task task) {
        taskMap.put(task.getId(), task);
        if (checkTime(task.getStartTime(), task.getDuration())) {
            taskSet.add(task);
        }
    }

    @Override
    public void saveSubtask(Subtask subtask) {
        Epic epic = epicMap.get(subtask.getEpicId());
        epic.newSubtask(subtask);
        subtaskEpicMap.put(subtask.getId(), subtask);
        if (checkTime(subtask.getStartTime(), subtask.getDuration())) {
            taskSet.add(subtask);
        }
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
            epic.getSubtasks().clear();
        }
    }

    @Override
    public Task getTaskById(String taskId) {
        Task task = taskMap.get(taskId);
        inMemoryHistoryManager.add(task);
        return task;
    }

    @Override
    public Epic getEpicById(String epicId) {
        Epic epic = epicMap.get(epicId);
        inMemoryHistoryManager.add(epic);
        return epic;
    }

    @Override
    public Subtask getSubtaskById(String subtaskId) {
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
        epic.getSubtasks().add(subtask);
        epic.changeStatus();
    }

    @Override
    public void deleteTask(String taskId) {
        taskSet.remove(taskMap.get(taskId));
        inMemoryHistoryManager.remove(taskId);
        taskMap.remove(taskId);

    }

    @Override
    public void deleteEpic(String epicId) {
        taskSet.remove(epicMap.get(epicId));
        inMemoryHistoryManager.remove(epicId);
        epicMap.remove(epicId);
    }

    @Override
    public void deleteSubtask(String subtaskId) {
        for (Epic epic : epicMap.values()) {
            taskSet.remove(subtaskEpicMap.get(subtaskId));
            inMemoryHistoryManager.remove(subtaskId);
            epic.getSubtasks().remove(subtaskId);
        }
    }

    @Override
    public List<Subtask> getAllSubtaskByEpic(String epicId) {
        List<Subtask> subtaskList = new ArrayList<>();
        for (Subtask subtask : epicMap.get(epicId).getSubtasks()) {
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
            for (Subtask subtask : epic.getSubtasks()) {
                System.out.println("    id: " + subtask.getId() + ". " + subtask.getName());
            }
        }
    }

    @Override
    public List<Task> history() {
        List<Task> list = inMemoryHistoryManager.getHistory();
        for (Task task : list) {
//            System.out.println("id: " + task.getId() + ". " + task.getName());
        }
        return list;
    }
}
