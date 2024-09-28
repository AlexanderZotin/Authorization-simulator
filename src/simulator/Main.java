package simulator;

import simulator.controller.AuthorizationController;
import simulator.controller.Controller;
import simulator.view.MainView;
import simulator.view.Window;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
    private static AuthorizationController controller;

    @SuppressWarnings("SpellCheckingInspection")
    public static void main(String [] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch(UnsupportedLookAndFeelException | ClassNotFoundException
                | InstantiationException | IllegalAccessException e) {
            System.err.println("Не удалось установить желаемый Look&Feel! Программа будет работать корректно, но может изменить внешний вид.");
        }
        UIManager.put("OptionPane.yesButtonText", "Да"); 
        UIManager.put("OptionPane.noButtonText", "Нет");
        MainView view = new Window();
        controller = new Controller(view);
        view.setVisible(true);
    }

    public static AuthorizationController getController() {
        return controller;
    }
}
