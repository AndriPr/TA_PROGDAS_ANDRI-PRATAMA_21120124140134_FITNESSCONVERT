package fitnessconvert;

import javax.swing.SwingUtilities;

public class MainClass {
    public static void main(String[] args) {
        // Menjalankan aplikasi di thread event dispatching untuk thread safety
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);  // Menampilkan frame login
        });
    }
}
