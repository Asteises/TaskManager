package model;

import enums.Status;
import enums.Type;

import java.util.Objects;
import java.util.UUID;

/*
Task - класс описывающий задачу.
 */
public class Task {
    private String name;
    private String description;
    private String id;
    private Status status;
    protected Type type;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = createId();
        status = Status.NEW;
        type = Type.TASK;
    }

    private String createId() {

        String id = String.valueOf(UUID.randomUUID());
        return id;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return id + "," + type + "," + name + "," + status + "," + description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description) && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, status);
    }
}
