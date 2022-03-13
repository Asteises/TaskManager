package manager;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> taskListForHistory;
    private final int sizeHistory = 10;

    public InMemoryHistoryManager() {
        taskListForHistory = new ArrayList<>();
    }

    public void checkTaskListForHistory() {
        if (taskListForHistory.size() >= sizeHistory) {
            taskListForHistory.remove(0);
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
