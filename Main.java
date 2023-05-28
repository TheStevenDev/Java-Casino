import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            LoadingPage results = new LoadingPage();
            results.setVisible(true);
        });

        try {
            Thread.sleep(100); // Pausa di 1 secondo per caricare correttamente il frame
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }


    }
}