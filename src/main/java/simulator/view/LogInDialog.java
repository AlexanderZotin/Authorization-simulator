package simulator.view;

import simulator.model.User;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Optional;

public class LogInDialog extends JDialog implements DialogView {
    protected JTextField loginTextField;
    protected JPasswordField passwordField;
    protected JCheckBox showPasswordCheckBox;
    protected JButton go;

    public LogInDialog() {
        super.setTitle("Вход");
        initializeComponents();
        addComponents();
        setUpDialog();
    }

    private void initializeComponents() {
        loginTextField = new JTextField(13);
        passwordField = new JPasswordField(13);
        passwordField.setEchoChar('●');
        showPasswordCheckBox = new JCheckBox("Показать пароль");
        showPasswordCheckBox.addItemListener(_ ->
            passwordField.setEchoChar(showPasswordCheckBox.isSelected() ? 0 : '●'));
        go = new JButton("Войти!");
        go.setPreferredSize(new Dimension(150, 30));
        go.addActionListener(_ -> setVisible(false));
    }

    private void addComponents() {
        var panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 13));
        panel.add(new JLabel("Логин:"));
        panel.add(loginTextField);
        panel.add(new JLabel("Пароль:"));
        panel.add(passwordField);
        panel.add(showPasswordCheckBox);
        panel.add(go);
        setContentPane(panel);
    }

    private void setUpDialog() {
        setSize(200, 300);
        setModal(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    @Override
    public Optional<User> getUser() {
        if(areDataIncorrect()) return Optional.empty();
        else return Optional.of(new User(loginTextField.getText(), passwordField.getPassword()));
    }

    protected boolean areDataIncorrect() {
        return loginTextField.getText().isEmpty() || passwordField.getPassword().length == 0;
    }
}
