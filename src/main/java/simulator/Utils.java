package simulator;

import javax.swing.JOptionPane;
import java.util.Optional;
import java.util.function.Supplier;

public final class Utils {
    private Utils() {
        throw new AssertionError("No instances of class Utils for you!");
    }

    public static void whileSuccessfulOrCancel(Supplier<Optional<?>> getting, Runnable action) {
        while (true) {
            try {
                Optional<?> optional = getting.get();
                if(optional.isPresent()) {
                    action.run();
                } else if(yesNoDialog("Неверный ввод. Попробовать ещё раз?")) {
                    continue;
                }
            } catch (Exception e) {
                if(yesNoDialog(e.getMessage() + "\nПопробовать ещё раз?")) {
                    continue;
                }
            }
            break;
        }
    }

    private static boolean yesNoDialog(String message) {
        int answer = JOptionPane.showConfirmDialog(null,
                message, "Неверный ввод", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        return answer == JOptionPane.YES_OPTION;
    }
}
