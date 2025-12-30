package client;

import client.swing.EcoembesController;
import client.swing.LoginWindow;

public class EcoembesApp {
    public static void main(String[] args) {
        EcoembesController controller = new EcoembesController();
        new LoginWindow(controller).setVisible(true);
    }
}