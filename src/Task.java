public class Task {
    private String name;
    private String description;
    private static int classId = 0;
    private int id;
    private String status;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        id = ++classId;
        status = "NEW";
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "name: " + name + "\ndescription: " + description +
                "\nid: " + id + "\nstatus: " + status + "\n";
    }
}
