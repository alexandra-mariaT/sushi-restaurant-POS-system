package restaurant.controller;

import restaurant.repository.UserRepository;
import restaurant.model.Person;

public class UserController implements ILogin {
    private final UserRepository userRepository;
    public UserController() {
        this.userRepository = new UserRepository();
    }

    @Override
    public Person login(String pin) {
        if (pin == null || pin.length() != 4) {
            return null;
        }
        return userRepository.findUserByPin(pin);
    }
}