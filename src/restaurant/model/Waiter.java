package restaurant.model;

public class Waiter extends Person {
    private static final String role = "Waiter";

    public Waiter(String userName) {
        super(userName);
    }

    @Override
    public String getRoleName() {
        return role;
    }
}