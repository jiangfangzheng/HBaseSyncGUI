import javax.swing.*;

import static gui.MainGui.createGUI;

/**
 * @author Sandeepin
 * 2018/4/12 0012
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
                                       @Override
                                       public void run() {
                                           createGUI();
                                       }
                                   }
        );
    }
}
