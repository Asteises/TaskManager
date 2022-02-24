import java.util.*;

public class Epic extends Task {
    private Map<Integer, Subtask> subtaskMap;

    public Epic(String name, String description) {
        super(name, description);
        subtaskMap = new HashMap<>();
    }

    public void newStatus() {
        int countNew = 0;
        int countDone = 0;
        for (Subtask subtask : subtaskMap.values()) {
            if (subtask.getStatus().equals("NEW")) {
                countNew++;
            } else if (subtask.getStatus().equals("DONE")) {
                countDone++;
            }
        }
        if (countNew == subtaskMap.size()) {
            super.setStatus("NEW");
        } else if (countDone == subtaskMap.size()) {
            super.setStatus("DONE");
        } else {
            super.setStatus("IN_PROGRESS");
        }
    }

    public Map<Integer, Subtask> getSubtaskMap() {
        return subtaskMap;
    }

    public void newSubtask(Subtask subtask) {
        subtaskMap.put(subtask.getId(), subtask);
    }

    @Override
    public String toString() {
        return super.toString() + "SubTasks:\n" + subtaskMap.toString();
    }
}
