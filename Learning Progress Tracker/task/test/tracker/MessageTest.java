package tracker;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static tracker.Course.JAVA;

public class MessageTest {
private Message messageTest;
public Student studentTest;

    @BeforeEach
    public void initMessage(){
    messageTest = new Message();
}
@BeforeEach
public void initStudent(){
    LearningTracker tracker = new LearningTracker();
    studentTest = new Student(1,"Jane","Spark", "mybest@mail.com");
    tracker.addStudent("John Doe johnd@email.net");
    tracker.addPoints("1 600 400 0 0");
}

@ParameterizedTest
@CsvSource({"jspark@yahoo.com,Jane Spark,Java"})
    public void testMessage(String eMail, String name, String courseName){
    System.out.println(messageTest.generateMessage(eMail, name, courseName));
}

@Test
    public void setStudentTest(){
    System.out.println(    messageTest.generateMessage(studentTest.getEmail(), studentTest.getName(),  studentTest.getSkill(JAVA).toString() ));}

}
