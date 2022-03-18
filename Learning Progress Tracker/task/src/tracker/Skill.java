package tracker;

public class Skill {
    private int points;
    private int assignments;
    private boolean checkNotify;

    Skill() {
        points = 0;
        assignments = 0;
        checkNotify = false;
    }

    public void submit(int points) {
        if (points > 0) {
            this.points += points;
            assignments++;
        }
    }

    public boolean isCheckNotify() {
        return checkNotify;
    }

    public void setCheckNotify(boolean result){
        this.checkNotify = result;
    }

    public int getPoints() {
        return points;
    }

    public int getAssignments() {
        return assignments;
    }
}