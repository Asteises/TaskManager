package manager;

public class Managers {

    public HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public TaskManager getDefault() {
        return new InMemoryTaskManager();
    }
}
