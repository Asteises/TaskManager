package manager;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> taskListForHistory;
    private final int sizeHistory = 10;

    public InMemoryHistoryManager() {
        taskListForHistory = new LinkedList<>();
    }

    public void checkTaskListForHistory() {
        if (taskListForHistory.size() >= sizeHistory) {
            taskListForHistory.remove(0);
        }
    }

    public void deleteTaskFromHistory(Task task) {
        taskListForHistory.remove(task);
    }

    public void deleteAllTasksFromHistory(Map<Integer, Task> taskMap) {
        for (Task task : taskMap.values()) {
            if (taskListForHistory.contains(task)) {
                deleteTaskFromHistory(task);
            }
        }
    }

    public void deleteAllEpicsFromHistory(Map<Integer, Epic> epicMap) {
        for (Epic epicTask : epicMap.values()) {
            if (taskListForHistory.contains(epicTask)) {
                 for (Subtask subtask : epicTask.getSubtaskMap().values()) {
                     taskListForHistory.remove(subtask);
                 }
                deleteTaskFromHistory(epicTask);
            }
        }
    }

    public void deleteAllSubtasksFromHistory(Map<Integer, Epic> epicMap) {
        for (Epic epicTask : epicMap.values()) {
            for (Subtask subtask : epicTask.getSubtaskMap().values()) {
                if (taskListForHistory.contains(subtask)) {
                    deleteTaskFromHistory(subtask);
                }
            }
        }
    }

    @Override
    public void add(Task task) {
        checkTaskListForHistory();
        if (getHistory().contains(task)){
            getHistory().remove(task);
        }
        getHistory().add(task);
    }

    @Override
    public List<Task> getHistory() {
        return taskListForHistory;
    }
}
