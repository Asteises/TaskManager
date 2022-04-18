package model;

import enums.Type;

/*
Subtask - класс для подзадачи внутри задачи Epic.
 */
public class Subtask extends Task {
    private String epicId;

    public Subtask(String epicId, String name, String description) {
        super(name, description);
        this.epicId = epicId;
        this.type = Type.SUBTASK;
    }

    public String getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return super.toString() + "," + getEpicId();
    }
}
