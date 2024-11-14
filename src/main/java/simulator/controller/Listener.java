package simulator.controller;

import simulator.Utils;
import simulator.view.LogInDialog;
import simulator.view.MainView;
import simulator.view.SigInDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Listener extends WindowAdapter implements ActionListener, WindowListener {
    private final AuthorizationController linkedController;
    
    @Override
    public void actionPerformed(ActionEvent e) {
       MainView view = linkedController.getView();
       var clickedButton = (JButton) e.getSource(); 
       if(clickedButton == view.getLogInButton()) {
           var dialog = new LogInDialog();
           Utils.whileSuccessfulOrCancel(() -> {dialog.setVisible(true); return dialog.getUser();},
                 () -> dialog.getUser().ifPresent(linkedController::logIn));
       } else if(clickedButton == view.getSignUpButton()) {
           var dialog = new SigInDialog();
           Utils.whileSuccessfulOrCancel(() -> {dialog.setVisible(true); return dialog.getUser();},
                   () -> dialog.getUser().ifPresent(linkedController::sigIn));
       } else {
           linkedController.logOut();
       }
    }

    @Override
    public void windowClosing(WindowEvent event) {
        linkedController.onClose();
        System.exit(0);
    }
}
