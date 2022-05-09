package model;

import enums.Status;
import enums.Type;

import java.time.LocalDateTime;
import java.util.*;

/**
Epic - класс для большой задачи. Большая задача содержит в себе подзадачи - Subtask.
 */
public class Epic extends Task {

    LocalDateTime epicEndTime;
    private final List<Subtask> subtasks;

    public Epic(String name, String description) {
        super(name, description, 0, null);
        subtasks = new ArrayList<>();
        this.type = Type.EPIC;
    }

    /**
     * Проходимся по всем сабтаскам епика и складываем продолжительность всех егно подзадач
     */
    public void setDuration() {
        int epicDuration = 0;
        for(Subtask subtask : subtasks) {
            epicDuration += subtask.getDuration();
        }
        super.setDuration(epicDuration);
    }

    private void setStartAndEndTime() {
        List<LocalDateTime> localDateTimeList = new ArrayList<>();
        for(Subtask subtask : subtasks) {
            localDateTimeList.add(subtask.getStartTime());
        }
        localDateTimeList.sort(LocalDateTime::compareTo);
        super.setStartTime(localDateTimeList.get(0));
        this.epicEndTime = localDateTimeList.get(localDateTimeList.size() - 1);
    }

    public LocalDateTime getEpicEndTime() {
        return epicEndTime;
    }

    public LocalDateTime getEndTime(LocalDateTime startTime, int duration) {
        return startTime.plusMinutes(duration);
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void newSubtask(Subtask subtask) {
        subtasks.add(subtask);
        setDuration();
        setStartAndEndTime();
    }

    public Status changeStatus() {
        int countNew = 0;
        int countDone = 0;
        for (Subtask subtask : subtasks) {
            if (subtask.getStatus().equals(Status.NEW)) {
                countNew++;
            } else if (subtask.getStatus().equals(Status.DONE)) {
                countDone++;
            }
        }
        if (countNew == subtasks.size()) {
            super.setStatus(Status.NEW);
        } else if (countDone == subtasks.size()) {
            super.setStatus(Status.DONE);
        } else {
            super.setStatus(Status.IN_PROGRESS);
        }
            return super.getStatus();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
