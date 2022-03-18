package tracker;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class UserValidationTests {
    @ParameterizedTest
    @ValueSource(strings = { "Jean-Claude", "O'Neill", "f-a" })
    public void testFirstNameCorrect(String arg) {
        assertTrue(UserValidation.isNameCorrect(arg));
    }

    @ParameterizedTest
    @ValueSource(strings = { "Stanisław Oğuz", "-", "'", "" })
    public void testFirstNameIncorrect(String arg) {
        assertFalse(UserValidation.isNameCorrect(arg));
    }

    @ParameterizedTest
    @ValueSource(strings = { "Jemison Van de Graaff", "Jemison" })
    public void testLastNameCorrect(String arg) {
        assertTrue(UserValidation.isNameCorrect(arg));
    }

    @Test
    public void testLastNameIncorrect() {
        assertFalse(UserValidation.isNameCorrect("N--o"));
    }

    @ParameterizedTest
    @ValueSource(strings = { "a@f.com", "qwerty@asdf.com" })
    public void testEmailCorrect(String arg) {
        assertTrue(UserValidation.isEmailCorrect(arg));
    }

    @ParameterizedTest
    @ValueSource(strings = { "qwerty@asdf@qwerty.com", "qwerty@asdf.com.ru.de", "com.f@a" })
    public void testEmailIncorrect(String arg) {
        assertFalse(UserValidation.isEmailCorrect(arg));
    }

    @Test
    public void testGetUserDataFromStringCorrect() {
        UserValidation.UserData userData = UserValidation.getUserDataFromString("John Doe jdoe@mail.net");
        assertEquals("John", Objects.requireNonNull(userData).firstName);
        assertEquals("Doe", userData.lastName);
        assertEquals("jdoe@mail.net", userData.email);
    }

    @Test
    public void testGetUserDataFromStringNull() {
        UserValidation.UserData userData = UserValidation.getUserDataFromString("John Doe");
        assertNull(userData);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "John Doe jdoe@mail.net",
            "Jane Doe jane.doe@yahoo.com",
            "Jean-Clause van Helsing jc@google.it",
            "Mary Luise Johnson maryj@google.com"
    })
    public void testCredentialsCorrect(String arg) {
        assertEquals(StudentAdditionResult.OK, UserValidation.validate(UserValidation.getUserDataFromString(arg)));
    }

    @Test
    public void testCredentialsIncorrect() {
        assertEquals(StudentAdditionResult.CREDENTIALS_INCORRECT, UserValidation.validate(UserValidation.getUserDataFromString("help")));
    }

    @Test
    public void testCredentialsEmailIncorrect() {
        assertEquals(StudentAdditionResult.EMAIL_INCORRECT, UserValidation.validate(UserValidation.getUserDataFromString("John Doe email")));
    }

    @ParameterizedTest
    @ValueSource(strings = { "J. Doe name@domain.com", "陳 港 生" })
    public void testCredentialsFirstNameIncorrect(String arg) {
        assertEquals(StudentAdditionResult.FIRST_NAME_INCORRECT, UserValidation.validate(UserValidation.getUserDataFromString(arg)));
    }

    @Test
    public void testCredentialsLastNameIncorrect() {
        assertEquals(StudentAdditionResult.LAST_NAME_INCORRECT, UserValidation.validate(UserValidation.getUserDataFromString("John D. name@domain.com")));
    }

    @Test
    public void testValidateNullUser() {
        assertEquals(StudentAdditionResult.CREDENTIALS_INCORRECT, UserValidation.validate(null));
    }
}