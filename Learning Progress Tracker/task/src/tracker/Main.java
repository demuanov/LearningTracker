package tracker;

import java.util.*;
import java.util.function.Function;

public class Main {
    private static Scanner scanner;
    private static LearningTracker learningTracker;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        learningTracker = new LearningTracker();
        System.out.println("Learning Progress Tracker");
        while (scanner.hasNextLine()) {
            switch (scanner.nextLine().trim()) {
                case "":
                    System.out.println("No input.");
                    break;
                case "exit":
                    exit();
                case "add students":
                    addStudents();
                    break;
                case "list":
                    listStudents();
                    break;
                case "add points":
                    addPoints();
                    break;
                case "find":
                    findStudent();
                    break;
                case "statistics":
                    statistics();
                    break;
                case "back":
                    System.out.println("Enter 'exit' to exit the program");
                    break;
                case "notify":
                    notifyMessages();
                    break;
                default:
                    System.out.println("Error: unknown command!");
            }
        }
    }

    public static void exit() {
        System.out.println("Bye!");
        System.exit(0);
    }

    public static void addStudents() {
        System.out.println("Enter student credentials or 'back' to return:");
        int counter = 0;
        String input;
        while (true) {
            input = scanner.nextLine();
            if (input.equals("back")) {
                break;
            }

            StudentAdditionResult result = learningTracker.addStudent(input);
            if (result == StudentAdditionResult.OK) {
                counter++;
            }
            System.out.println(result.getMessage());
        }
        System.out.println("Total " + counter + " students have been added.");
    }

    public static void listStudents() {
        if (learningTracker.getStudents().size() > 0) {
            System.out.println("Students:");
            learningTracker.getStudents().forEach(student -> System.out.println(student.getId()));
        } else {
            System.out.println("No students found");
        }
    }

    public static void addPoints() {
        System.out.println("Enter an id and points or 'back' to return:");
        String input;
        while (true) {
            input = scanner.nextLine();
            if (input.equals("back")) break;
            System.out.println(learningTracker.addPoints(input));
        }
    }

    public static void findStudent() {
        System.out.println("Enter an id or 'back' to return:");
        String input;
        while (true) {
            input = scanner.nextLine();
            if (input.equals("back")) break;
            int id = Integer.parseInt(input);
            Student student = learningTracker.getStudent(id);
            if (student == null) {
                System.out.println("No student is found for id=" + id + ".");
            } else {
                System.out.println(student);
            }
        }
    }

    public static void statistics() {
        System.out.println("Type the name of a course to see details or 'back' to quit:");
        Statistics statistics = learningTracker.getStatistics();
        Function<Course[], String> format = (courses) -> courses.length == 0 ? "n/a" :
                String.join(", ", Arrays.stream(courses).map(Course::getName).toArray(String[]::new));
        System.out.println("Most popular: " + format.apply(statistics.mostPopular));
        System.out.println("Least popular: " + format.apply(statistics.leastPopular));
        System.out.println("Highest activity: " + format.apply(statistics.highestActivity));
        System.out.println("Lowest activity: " + format.apply(statistics.lowestActivity));
        System.out.println("Easiest course: " + format.apply(statistics.easiestCourse));
        System.out.println("Hardest course: " + format.apply(statistics.hardestCourse));
        String input;
        while (true) {
            input = scanner.nextLine();
            if (input.equals("back")) break;
            Course course = learningTracker.getCourseFromString(input);
            if (course == null) {
                System.out.println("Unknown course.");
            } else {
                StudentStatisticForCourse[] students = learningTracker.getStudentsOnCourse(course);
                Function<String, String> formatNumber = (str) -> str + " ".repeat(str.length() > 7 ? 0 : 7 - str.length());
                System.out.println(course.getName());
                System.out.println(formatNumber.apply("id") +
                        formatNumber.apply("points") +
                        formatNumber.apply("completed"));
                Arrays.stream(students).forEach(student -> System.out.println(
                        formatNumber.apply(String.valueOf(student.getId()))
                                + formatNumber.apply(String.valueOf(student.getPoints()))
                                + formatNumber.apply(String.format(Locale.US, "%.1f", student.getCompleted()) + "%")
                ));
            }
        }
    }

    public static void notifyMessages() {
        List<Student> student2 = learningTracker.getStudents();
        HashSet<Integer> notifiedStudents = new HashSet<>();
        Message mes = new Message();
        for (Course course : Course.values()) {
            StudentStatisticForCourse[] students = learningTracker.getStudentsOnCourse(course);
            Arrays.stream(students).forEach(student -> {
                if ((student.getCompleted() == 100) && (!student2.get(student.getId() - 1).getNotify(course))) {
                    notifiedStudents.add(student.getId());
                    student2.get(student.getId() - 1).setNotify(course, true);
                    System.out.println(mes.generateMessage(
                            student2.get(student.getId() - 1).getEmail(),
                            student2.get(student.getId() - 1).getName(),
                            course.getName()));
                }
            });
        }
        System.out.printf("Total %d students have been notified.\n", notifiedStudents.size());
        notifiedStudents.clear();
    }
}

