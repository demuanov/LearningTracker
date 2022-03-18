package tracker;

public class Statistics {
    public Course[] mostPopular;
    public Course[] leastPopular;
    public Course[] highestActivity;
    public Course[] lowestActivity;
    public Course[] easiestCourse;
    public Course[] hardestCourse;

    public Statistics(Course[] mostPopular,
                      Course[] leastPopular,
                      Course[] highestActivity,
                      Course[] lowestActivity,
                      Course[] easiestCourse,
                      Course[] hardestCourse) {
        this.mostPopular = mostPopular;
        this.leastPopular = leastPopular;
        this.highestActivity = highestActivity;
        this.lowestActivity = lowestActivity;
        this.easiestCourse = easiestCourse;
        this.hardestCourse = hardestCourse;
    }
}