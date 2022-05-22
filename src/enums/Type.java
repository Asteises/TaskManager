package enums;

/**
 Type - последовательность, содержащая названия задач.
 */
public enum Type {
    TASK("TASK"),
    EPIC("EPIC"),
    SUBTASK("SUBTASK");

    private final String value;


    Type(String status) {
        this.value = status;
    }

    public String getText() {
        return this.value;
    }

    public static Type fromString(String text) {
        for (Type b : Type.values()) {
            if (b.value.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
