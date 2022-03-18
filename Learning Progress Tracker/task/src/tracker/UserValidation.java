package tracker;

import java.util.regex.Pattern;

public class UserValidation {
    static class UserData {
        public String firstName;
        public String lastName;
        public String email;

        public UserData(String firstName, String lastName, String email) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        }
    }

    public static UserData getUserDataFromString(String input) {
        String[] strings = input.split(" ");
        if (strings.length >= 3) {
            String firstName = strings[0];
            StringBuilder builder = new StringBuilder();
            for (int i = 1; i < strings.length - 2; i++) {
                builder.append(strings[i]);
                builder.append(" ");
            }
            builder.append(strings[strings.length - 2]);
            String lastName = builder.toString();
            String email = strings[strings.length - 1];
            return new UserData(firstName, lastName, email);
        }
        return null;
    }

    public static StudentAdditionResult validate(UserData userData) {
        if (userData == null) {
            return StudentAdditionResult.CREDENTIALS_INCORRECT;
        }
        if (!isNameCorrect(userData.firstName)) {
            return StudentAdditionResult.FIRST_NAME_INCORRECT;
        } else if (!isNameCorrect(userData.lastName)) {
            return StudentAdditionResult.LAST_NAME_INCORRECT;
        } else if (!isEmailCorrect(userData.email)) {
            return StudentAdditionResult.EMAIL_INCORRECT;
        }
        return StudentAdditionResult.OK;
    }

    public static boolean isNameCorrect(String name) {
        for (String word : name.split(" ")) {
            if (!Pattern.compile("[A-Za-z]+([-']?[A-Za-z]+[-']?|[-'])?[A-Za-z]+").matcher(word).matches()) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmailCorrect(String name) {
        return Pattern.compile("[\\w.]+@\\w+\\.\\w+").matcher(name).matches();
    }
}
