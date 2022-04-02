package model;

import enums.Status;

import java.util.*;

/*
Epic - класс для большой задачи. Большая задача содержит в себе подзадачи - Subtask.
 */
public class Epic extends Task {
    private List<Subtask> subtaskList;

    public Epic(String name, String description) {
        super(name, description);
        subtaskList = new ArrayList<>();
    }

    public List<Subtask> getSubtaskList() {
        return subtaskList;
    }

    public void newSubtask(Subtask subtask) {
        subtaskList.add(subtask);
    }

    public Status changeStatus() {
        int countNew = 0;
        int countDone = 0;
        for (Subtask subtask : subtaskList) {
            if (subtask.getStatus().equals(Status.NEW)) {
                countNew++;
            } else if (subtask.getStatus().equals(Status.DONE)) {
                countDone++;
            }
        }
        if (countNew == subtaskList.size()) {
            super.setStatus(Status.NEW);
        } else if (countDone == subtaskList.size()) {
            super.setStatus(Status.DONE);
        } else {
            super.setStatus(Status.IN_PROGRESS);
        }
            return super.getStatus();
    }

    @Override
    public String toString() {
        return super.toString() + "SubTasks:\n" + subtaskList.toString();
    }
}
