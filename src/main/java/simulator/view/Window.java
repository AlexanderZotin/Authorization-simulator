package simulator.view;

import simulator.controller.Listener;
import simulator.model.User;
import simulator.model.UserRepository;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Optional;

import lombok.Getter;

public class Window extends JFrame implements MainView {
    private JPanel windowContent = new JPanel(new FlowLayout());
    private @Getter JButton logInButton = new JButton("Вход");
    private @Getter JButton signUpButton = new JButton("Регистрация");
    private @Getter JButton logOutButton = new JButton("Выход");
    private JLabel label = new JLabel();
    
    public Window() {
        setTitle("Симулятор авторизации и регистрации");
        label.setFont(new Font(null, Font.PLAIN, 17));
        windowContent.add(label);
        showUserAbsence();
        setContentPane(windowContent);
        pack();
    }

    @Override
    public void updateInfo(UserRepository repository) {
        Optional<User> currentUser = repository.getCurrent();
        if (currentUser.isPresent()) {
            label.setText("<html>Вы вошли в аккаунт <strong>" + currentUser.get() +
                    "</strong>. Случайное число: " + Math.round(Math.random() * 10) + "</html>");
            windowContent.remove(logInButton);
            windowContent.remove(signUpButton);
            windowContent.add(logOutButton);
        } else {
            showUserAbsence();
        }
        windowContent.updateUI();
    }

    @Override
    public void subscribeToListener(Listener listener) {
        addWindowListener(listener);
        logInButton.addActionListener(listener);
        logOutButton.addActionListener(listener);
        signUpButton.addActionListener(listener);
    }
    
    private void showUserAbsence() {
        label.setText("Войдите в аккаунт, чтобы получить случайное число!");
        windowContent.remove(logOutButton);
        windowContent.add(logInButton);
        windowContent.add(signUpButton);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(true);
        if (visible) setLocationRelativeTo(null);
    }
}