package simulator.controller;

import simulator.model.User;

public interface AuthorizationController {
    void logIn(User user);
    void sigIn(User user);
    void logOut();
    void onClose();
}
