package kfir.lan;

import kfir.lan.shapes.FeatureFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args){
        if (args.length != 1) {
            System.out.println("need to pass one image as parameter");
            return;
        }
        try {
            BufferedImage origImage = ImageIO.read(new File(args[0]));
            ImageApproximator approximator = new SimulatedAnnealingApproximator();
            approximator.approximate(origImage, FeatureFactory.FeatureType.Ellipse);
        } catch (IOException e) {
            System.out.println("cant access file: " + args[0]);
            e.printStackTrace();
        }
    }
}
