package kfir.lan.image;

import javax.swing.*;
import java.awt.*;

public class Visualizer {

    private JFrame window = new JFrame();

    public Visualizer(int maxWidth, int maxHeight) {
        window.setLayout(new OverlayLayout(window.getContentPane()));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setPreferredSize(new Dimension(maxWidth, maxHeight));
        window.pack();
        window.setVisible(true);
    }

    public void showImage(Image image) {
        Graphics graphics = window.getContentPane().getGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
    }


}
