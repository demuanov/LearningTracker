package tracker;

public enum StudentAdditionResult {
    CREDENTIALS_INCORRECT("Incorrect credentials."),
    FIRST_NAME_INCORRECT("Incorrect first name."),
    LAST_NAME_INCORRECT("Incorrect last name."),
    EMAIL_INCORRECT("Incorrect email."),
    EMAIL_TAKEN("This email is already taken."),
    OK("The student has been added.");

    private final String message;

    StudentAdditionResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}