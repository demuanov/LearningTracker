package tracker;

public class Student {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final Skill javaCourse;
    private final Skill dsaCourse;
    private final Skill databasesCourse;
    private final Skill springCourse;

    Student(int id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        javaCourse = new Skill();
        dsaCourse = new Skill();
        databasesCourse = new Skill();
        springCourse = new Skill();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(id + " points:");
        for (Course course : Course.values()) {
            result.append(" ");
            result.append(course.getName());
            result.append("=");
            result.append(getSkill(course).getPoints());
            result.append(";");
        }
        return result.toString();
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Skill getSkill(Course course) {
        switch (course) {
            case JAVA:
                return javaCourse;
            case DSA:
                return dsaCourse;
            case DATABASES:
                return databasesCourse;
            default:
                return springCourse;
        }
    }

    public boolean getNotify(Course course) {
        switch (course) {
            case JAVA:
                return javaCourse.isCheckNotify();
            case DSA:
                return dsaCourse.isCheckNotify();
            case DATABASES:
                return databasesCourse.isCheckNotify();
            case SPRING:
                return springCourse.isCheckNotify();
            default:
                return  false;
        }
    }

    public void setNotify(Course course, boolean result) {
        switch (course) {
            case JAVA:
                javaCourse.setCheckNotify(result);
                return;
            case DSA:
                dsaCourse.setCheckNotify(result);
                return;
            case DATABASES:
                databasesCourse.setCheckNotify(result);
                return;
            case SPRING:
                springCourse.setCheckNotify(result);
                return;
            default:
        }
    }

    public String getName() {
        return firstName + " " + lastName;
    }

}