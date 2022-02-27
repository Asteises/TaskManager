package model;

public class Subtask extends Task {
    private int epicId;

    public Subtask(int epicId, String name, String description) {
        super(name, description);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }
}
