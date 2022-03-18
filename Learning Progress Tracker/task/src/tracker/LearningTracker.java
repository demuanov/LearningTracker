package tracker;

import java.util.*;
import java.util.function.Function;

public class LearningTracker {
    private final List<Student> students;
    private final Map<Course, Integer> maxPoints = Map.of(
            Course.JAVA, 600,
            Course.DSA, 400,
            Course.DATABASES, 480,
            Course.SPRING, 550
    );

    LearningTracker() {
        students = new ArrayList<>();
    }

    public StudentAdditionResult addStudent(String data) {
        UserValidation.UserData userData = UserValidation.getUserDataFromString(data);
        StudentAdditionResult result = UserValidation.validate(userData);
        if (result == StudentAdditionResult.OK) {
            if (students.stream().anyMatch(el -> el.getEmail().equals(Objects.requireNonNull(userData).email))) {
                return StudentAdditionResult.EMAIL_TAKEN;
            }
            students.add(new Student(students.size() + 1, Objects.requireNonNull(userData).firstName, userData.lastName, userData.email));
        }
        return result;
    }

    public String addPoints(String data) {
        String INCORRECT_POINTS_FORMAT = "Incorrect points format.";
        try {
            String[] strings = data.split(" ");
            String id = strings[0];
            String[] stringPoints = Arrays.copyOfRange(strings, 1, strings.length);
            int[] points = Arrays.stream(stringPoints).mapToInt(Integer::parseInt).toArray();
            if (points.length != Course.values().length || Arrays.stream(points).anyMatch(el -> el < 0)) {
                return INCORRECT_POINTS_FORMAT;
            }
            Optional<Student> optionalStudent = students.stream()
                    .filter(student -> String.valueOf(student.getId()).equals(id)).findFirst();
            if (optionalStudent.isPresent()) {
                Student student = optionalStudent.get();
                int i = 0;
                for (Course course : Course.values()) {
                    student.getSkill(course).submit(points[i]);
                    i++;
                }
                return "Points updated.";
            } else {
                return "No student is found for id=" + id + ".";
            }
        } catch (NumberFormatException e) {
            return INCORRECT_POINTS_FORMAT;
        }
    }

    public List<Student> getStudents() {
        return students;
    }

    public Student getStudent(int id) {
        try {
            return students.get(id - 1);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public Course  getCourseFromString(String data) {
        return Arrays.stream(Course.values())
                .filter(course -> course.getName().equalsIgnoreCase(data)).findFirst().orElse(null);
    }

    public StudentStatisticForCourse[] getStudentsOnCourse(Course course) {
        if (course == null) {
            return null;
        }
        return students.stream()
                .filter(student -> student.getSkill(course).getAssignments() > 0)
                .map(student -> new StudentStatisticForCourse(
                        student.getId(),
                        student.getSkill(course).getPoints(),
                        (float) student.getSkill(course).getPoints() / maxPoints.get(course) * 100
                )).sorted((a, b) -> a.getPoints().compareTo(b.getPoints()) == 0 ?
                        a.getId().compareTo(b.getId()) :
                        -a.getPoints().compareTo(b.getPoints()))
                .toArray(StudentStatisticForCourse[]::new);
    }

    public Statistics getStatistics() {
        Course[][] courses = getStatisticProperty((course) -> (float) students.stream()
                .filter(student -> student.getSkill(course).getAssignments() > 0).count());
        Course[] mostPopular = courses[0];
        Course[] leastPopular = courses[1];
        courses = getStatisticProperty((course) -> (float) students.stream()
                .mapToInt(student -> student.getSkill(course).getAssignments()).sum());
        Course[] highestActivity = courses[0];
        Course[] lowestActivity = courses[1];
        courses = getStatisticProperty((course) -> (float) students.stream()
                .mapToInt(student -> student.getSkill(course).getPoints()).sum()
                / students.stream()
                .mapToInt(student -> student.getSkill(course).getAssignments()).sum());
        Course[] easiestCourse = courses[0];
        Course[] hardestCourse = courses[1];
        return new Statistics(
                mostPopular,
                leastPopular,
                highestActivity,
                lowestActivity,
                easiestCourse,
                hardestCourse
        );
    }

    private Course[][] getStatisticProperty(Function<Course, Float> fun) {
        float max = -1;
        float min = Long.MAX_VALUE;
        float current;
        List<Course> maxCourses = new ArrayList<>();
        List<Course> minCourses = new ArrayList<>();
        for (Course course : Course.values()) {
            current = fun.apply(course);
            if (current > 0) {
                if (current > max) {
                    max = current;
                    maxCourses.clear();
                    maxCourses.add(course);
                } else if (current == max) {
                    maxCourses.add(course);
                }
                if (current < min) {
                    min = current;
                    minCourses.clear();
                    minCourses.add(course);
                } else if (current == min) {
                    minCourses.add(course);
                }
            }
        }
        minCourses.removeAll(maxCourses);
        Course[][] courses = new Course[2][Math.max(maxCourses.size(), minCourses.size())];
        courses[0] = maxCourses.toArray(Course[]::new);
        courses[1] = minCourses.toArray(Course[]::new);
        return courses;
    }
}