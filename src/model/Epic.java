package model;

import enums.Status;

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
            if (subtask.getStatus().equals(Status.NEW)) {
                countNew++;
            } else if (subtask.getStatus().equals(Status.DONE)) {
                countDone++;
            }
        }
        if (countNew == subtaskMap.size()) {
            super.setStatus(Status.NEW);
        } else if (countDone == subtaskMap.size()) {
            super.setStatus(Status.DONE);
        } else {
            super.setStatus(Status.IN_PROGRESS);
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
