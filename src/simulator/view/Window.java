package simulator.view;

import simulator.Main;
import simulator.Utils;
import simulator.model.User;
import simulator.model.UserRepository;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;
import java.util.Optional;

public class Window extends JFrame implements MainView {
    private JPanel windowContent;
    private JButton loginButton;
    private JButton createAccountButton;
    private JButton logOutButton;
    private JLabel label;
    
    public Window() {
        SwingUtilities.invokeLater(() -> {
            setTitle("Симулятор авторизации и регистрации");
            windowContent = new JPanel(new FlowLayout());
            loginButton = new JButton("Вход");
            logOutButton = new JButton("Выход");
            createAccountButton = new JButton("Регистрация");
            label = new JLabel();
            label.setFont(new Font(null, Font.PLAIN, 17));
            windowContent.add(label);
            showUserAbsence();
            setContentPane(windowContent);
            subscribeToListeners();
            pack();
        });
    }

    private void subscribeToListeners() {
        loginButton.addActionListener(_ -> {
            var dialog = new LogInDialog();
            Utils.whileSuccessfulOrCancel(() -> {dialog.setVisible(true); return dialog.getUser();},
                    () -> dialog.getUser().ifPresent(Main.getController()::logIn));
        });
        createAccountButton.addActionListener(_ -> {
            var dialog = new SigInDialog();
            Utils.whileSuccessfulOrCancel(() -> {dialog.setVisible(true); return dialog.getUser();},
                    () -> dialog.getUser().ifPresent(Main.getController()::sigIn));
        });
        logOutButton.addActionListener(_ -> Main.getController().logOut());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                Main.getController().onClose();
                System.exit(0);
            }
        });
    }

    @Override
    public void setVisible(boolean visible) {
        SwingUtilities.invokeLater(() -> {
            super.setVisible(true);
            if (visible) setLocationRelativeTo(null);
        });
    }

    @Override
    public void updateInfo(UserRepository repository) {
        Objects.requireNonNull(repository);
        Optional<User> currentUser = repository.getCurrent();
        SwingUtilities.invokeLater(() -> {
            if (currentUser.isPresent()) {
                label.setText("<html>Вы вошли в аккаунт <strong>" + currentUser.get() +
                        "</strong>. Случайное число: " + Math.round(Math.random() * 10) + "</html>");
                windowContent.remove(loginButton);
                windowContent.remove(createAccountButton);
                windowContent.add(logOutButton);
            } else {
                showUserAbsence();
            }
            windowContent.updateUI();
        });
    }

    private void showUserAbsence() {
        label.setText("Войдите в аккаунт, чтобы получить случайное число!");
        windowContent.remove(logOutButton);
        windowContent.add(loginButton);
        windowContent.add(createAccountButton);
    }
}
