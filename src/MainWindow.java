import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private final DrawPanel panel;

    public MainWindow() throws HeadlessException {
        panel = new DrawPanel(900, 650, 50);
        this.add(panel);
    }
}