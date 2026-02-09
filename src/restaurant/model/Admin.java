package restaurant.model;

public class Admin extends Person {
    private static final String role = "Admin";

    public Admin(String userName) {
        super(userName);
    }

    @Override
    public String getRoleName() {
        return role;
    }
}