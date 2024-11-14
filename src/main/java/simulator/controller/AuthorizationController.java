package simulator.controller;

import simulator.model.User;
import simulator.view.MainView;

public interface AuthorizationController {
    MainView getView();
    
    void logIn(User user);
    void sigIn(User user);
    void logOut();
    void onClose();
}
