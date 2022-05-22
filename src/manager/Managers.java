package manager;

import java.nio.file.Path;

public class Managers {

    public HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public FileBackedTasksManager getDefaultFileBacked(Path path) {
        return new FileBackedTasksManager(path);
    }
}
