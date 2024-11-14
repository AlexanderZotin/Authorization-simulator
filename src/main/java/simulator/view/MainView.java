package simulator.view;

import simulator.controller.Listener;
import simulator.model.UserRepository;

import javax.swing.JButton;

import lombok.NonNull;

public interface MainView extends Visible {
    void updateInfo(@NonNull UserRepository recovery);
    void subscribeToListener(Listener listener);
    
    JButton getLogInButton();
    JButton getLogOutButton();
    JButton getSignUpButton();
}
