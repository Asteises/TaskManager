package enums;

/**
Status - последовательность, содержащая значения для задач.
 */
public enum Status {

    NEW ("NEW"),
    IN_PROGRESS ("IN_PROGRESS"),
    DONE ("DONE");

    private final String value;

    Status(String status) {
        this.value = status;
    }

    public String getValue() {
        return this.value;
    }

    public static Status fromString(String text) {
        for (Status b : Status.values()) {
            if (b.value.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
