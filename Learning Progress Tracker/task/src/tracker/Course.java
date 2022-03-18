package tracker;

public enum Course {
    JAVA("Java"),
    DSA("DSA"),
    DATABASES("Databases"),
    SPRING("Spring");

    private final String name;

    Course(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}