package simulator;

import simulator.controller.ControllerImplementation;
import simulator.controller.Listener;
import simulator.view.Window;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
    public static void main(String [] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch(UnsupportedLookAndFeelException | ClassNotFoundException
                | InstantiationException | IllegalAccessException e) {
            System.err.println("A problem has occurred trying set Lok&Feel:" + e);
        }
        UIManager.put("OptionPane.yesButtonText", "Да"); 
        UIManager.put("OptionPane.noButtonText", "Нет");
        
        SwingUtilities.invokeLater(() -> {
            var view = new Window();
            var controller = new ControllerImplementation(view);
            view.subscribeToListener(new Listener(controller));
            view.setVisible(true);
        });
    }
}
