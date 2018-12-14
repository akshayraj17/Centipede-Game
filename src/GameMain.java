import java.awt.EventQueue;
import javax.swing.JFrame;



public class GameMain extends JFrame {

    public GameMain() {

        initUI();
    }

    private void initUI() {

        add(new Board());

        setSize(600, 600);
        setResizable(false);

        setTitle("Atari Presents: Centipede!");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            GameMain ex = new GameMain();
            ex.setVisible(true);
        });
    }
}