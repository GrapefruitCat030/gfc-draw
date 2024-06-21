package nju.gfcat;

import javax.swing.SwingUtilities;

import nju.gfcat.view.MainWindow;

public class Application {
    private MainWindow mainWindow;

    public Application() {
        mainWindow = new MainWindow();
        mainWindow.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Application();
            }
        });
    }
}


