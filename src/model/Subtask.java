package model;

import enums.Status;
import enums.Type;

import java.time.LocalDateTime;

/*
Subtask - класс для подзадачи внутри задачи Epic.
 */
public class Subtask extends Task {
    private String epicId;

    public Subtask(String epicId, String name, String description, int duration, LocalDateTime startTime) {
        super(name, description, duration, startTime);
        this.epicId = epicId;
        this.type = Type.SUBTASK;
    }

    public LocalDateTime getEndTime(LocalDateTime startTime, int duration) {
        return startTime.plusMinutes(duration);
    }

    public String getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return super.toString() + "," + getEpicId();
    }
}
