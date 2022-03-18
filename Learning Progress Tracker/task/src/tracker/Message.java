package tracker;

public class Message {


    public String generateMessage(String eMail, String userName, String courseName) {

        return "To: " + eMail + "\n" +
                "Re: Your Learning Progress\n" +
                "Hello, " + userName + "! You have accomplished our "
                + courseName + " course!";
    }
}
