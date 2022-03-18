package tracker;

public class StudentStatisticForCourse {
    private final int id;
    private final int points;
    private final float completed;

    public StudentStatisticForCourse(int id, int points, float completed) {
        this.id = id;
        this.points = points;
        this.completed = completed;
    }

    public Integer getId() {
        return id;
    }

    public Integer getPoints() {
        return points;
    }


    public float getCompleted() {
        return completed;
    }
}
