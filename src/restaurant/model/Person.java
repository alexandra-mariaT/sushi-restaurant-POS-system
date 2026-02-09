package restaurant.model;

public abstract class Person {
    protected String userName;

    public Person(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

    public abstract String getRoleName();
}