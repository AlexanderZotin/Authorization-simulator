package simulator.view;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import java.util.Arrays;

public class SigInDialog extends LogInDialog {
    private final JPasswordField inputPasswordAgain;

    public SigInDialog() {
        super();
        setTitle("Регистрация");
        remove(go);
        remove(showPasswordCheckBox);
        add(new JLabel("Введите пароль ещё раз:"));
        inputPasswordAgain = new JPasswordField(13);
        inputPasswordAgain.setEchoChar('●');
        add(inputPasswordAgain);
        add(showPasswordCheckBox);
        add(go);
        showPasswordCheckBox.addItemListener(_ -> {
            inputPasswordAgain.setEchoChar(showPasswordCheckBox.isSelected() ? 0 : '●');
            inputPasswordAgain.repaint();
        });
        setSize(200, 350);
    }

    @Override
    protected boolean areDataIncorrect() {
        return super.areDataIncorrect() || inputPasswordAgain.getPassword().length == 0
                || !Arrays.equals(passwordField.getPassword(), inputPasswordAgain.getPassword());
    }
}
