package tracker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LearningTrackerTests {
    private LearningTracker learningTracker;

    @BeforeEach
    public void initLearningTracker() {
        learningTracker = new LearningTracker();
    }

    @Test
    public void testAddOneUser() {
        assertEquals(StudentAdditionResult.OK, learningTracker.addStudent("John Doe jdoe@mail.net"));
        assertEquals(1, learningTracker.getStudents().size());
    }

    @Test
    public void testAddIncorrectUser() {
        assertEquals(StudentAdditionResult.CREDENTIALS_INCORRECT, new LearningTracker().addStudent("John Doe"));
        assertEquals(0, learningTracker.getStudents().size());
    }

    @Test
    public void testAddUsersWithEqualEmail() {
        learningTracker.addStudent("John Doe jdoe@mail.net");
        assertEquals(StudentAdditionResult.EMAIL_TAKEN, learningTracker.addStudent("Hacker Hacker jdoe@mail.net"));
        assertEquals(1, learningTracker.getStudents().size());
    }

    @Test
    public void testAddPointsToNonexistentStudent() {
        assertEquals("No student is found for id=10.", learningTracker.addPoints("10 1 1 1 1"));
    }

    @ParameterizedTest
    @ValueSource(strings = { "10000 7 7 7 7 7", "10000 -1 2 2 2", "10000 ? 1 1 1" })
    public void testAddPointsInIncorrectFormat(String arg) {
        assertEquals("Incorrect points format.", learningTracker.addPoints(arg));
    }

    @Test
    public void testSuccessfulAdditionPoints() {
        learningTracker.addStudent("John Doe jdoe@yahoo.com");
        learningTracker.addPoints("1 1 2 3 4");
        Student student = learningTracker.getStudents().get(0);
        assertEquals(1, student.getSkill(Course.JAVA).getPoints());
        assertEquals(2, student.getSkill(Course.DSA).getPoints());
        assertEquals(3, student.getSkill(Course.DATABASES).getPoints());
        assertEquals(4, student.getSkill(Course.SPRING).getPoints());
    }

    @Test
    public void testStatistics() {
        learningTracker.addStudent("John Doe johnd@email.net");
        learningTracker.addStudent("Jane Spark jspark@yahoo.com");
        learningTracker.addPoints("1 8 7 7 5");
        learningTracker.addPoints("1 7 6 9 7");
        learningTracker.addPoints("1 6 5 5 0");
        learningTracker.addPoints("2 8 0 8 6");
        learningTracker.addPoints("2 7 0 0 0");
        learningTracker.addPoints("2 9 0 0 5");
        Statistics statistics = learningTracker.getStatistics();
        Course[] mostPopularCourses = statistics.mostPopular;
        assertEquals(Course.JAVA, mostPopularCourses[0]);
        assertEquals(Course.DATABASES, mostPopularCourses[1]);
        assertEquals(Course.SPRING, mostPopularCourses[2]);
        assertEquals(Course.DSA, statistics.leastPopular[0]);
        assertEquals(Course.JAVA, statistics.highestActivity[0]);
        assertEquals(Course.DSA, statistics.lowestActivity[0]);
        assertEquals(Course.JAVA, statistics.easiestCourse[0]);
        assertEquals(Course.SPRING, statistics.hardestCourse[0]);
        assertNull(learningTracker.getStudentsOnCourse(learningTracker.getCourseFromString("swing")));
        StudentStatisticForCourse[] studentStatisticForCourse = learningTracker
                .getStudentsOnCourse(learningTracker.getCourseFromString("java"));
        assertEquals(2, studentStatisticForCourse[0].getId());
        assertEquals(1, studentStatisticForCourse[1].getId());
        assertEquals(24, studentStatisticForCourse[0].getPoints());
        assertEquals(21, studentStatisticForCourse[1].getPoints());
        assertEquals(4.0, studentStatisticForCourse[0].getCompleted());
        assertEquals(3.5, studentStatisticForCourse[1].getCompleted());

        System.out.println("------------------------------------------\n");

        System.out.println(studentStatisticForCourse[0].getId());
        System.out.println(studentStatisticForCourse[0].getPoints());
        System.out.println(studentStatisticForCourse[0].getCompleted());
    }

    @Test
    public void testStatisticsWithIncreasingValues() {
        learningTracker.addStudent("John Doe johnd@email.net");
        learningTracker.addPoints("1 1 1 1 1");
        learningTracker.addPoints("1 0 1 1 1");
        learningTracker.addPoints("1 0 0 1 1");
        learningTracker.addPoints("1 0 0 0 1");
        Statistics statistics = learningTracker.getStatistics();
        Course[] highestActivityCourses = statistics.highestActivity;
        Course[] lowestActivityCourses = statistics.lowestActivity;
        assertEquals(Course.SPRING, highestActivityCourses[0]);
        assertEquals(Course.JAVA, lowestActivityCourses[0]);
    }

    @Test
    public void testStatisticsWithoutMin() {
        learningTracker.addStudent("John Doe johnd@email.net");
        learningTracker.addPoints("1 1 1 1 1");
        Statistics statistics = learningTracker.getStatistics();
        Course[] highestActivityCourses = statistics.highestActivity;
        Course[] lowestActivityCourses = statistics.lowestActivity;
        assertEquals(4, highestActivityCourses.length);
        assertEquals(0, lowestActivityCourses.length);
    }
}