package simulation;

import javax.swing.*;

public class Main extends JFrame {

    static int WIDTH = 800;
    static int HEIGHT = 800;
    static JFrame PEPE;

    public static void main(String[] args){
        PEPE = new JFrame();
        PEPE.setTitle("Particles");
        PEPE.setBounds(0, 0, WIDTH, HEIGHT);
        PEPE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PEPE.setResizable(false);
        PEPE.setLocationRelativeTo(null);
        PEPE.add(new Engine(WIDTH, HEIGHT, 400, 400));
        PEPE.setVisible(true);
    }
}
