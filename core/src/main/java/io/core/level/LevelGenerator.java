package io.core.level;

import io.core.util.Perlin;

import java.awt.*;
import java.util.Random;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;


public class LevelGenerator {
    private static int worldSeed = 0;
    private static final Random random = new Random(worldSeed);
    //private final int width, height; // Width and height of the map





    public static void main(String[] args) {
        LevelGenerator.worldSeed = (int)System.currentTimeMillis();
        int width = 512;
        int height = 512;
        Double[][] values = new Double[width][height];

        //Perlin perlin = new Perlin(worldSeed, 2, 2, 2);
        Perlin perlin = new Perlin(worldSeed, 6, 0.5, 2.0);

        double scale = 0.01;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                values[x][y] = perlin.noise2D(x * scale, y * scale);
            }
        }

        // DISPLAY THE VALUES AS .PNG IMAGE (DEBUG PURPOSES)

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int gray = (int)((values[x][y] + 1) * 0.5 * 255);
                gray = Math.max(0, Math.min(255, gray));
                int rgb = (gray << 16) | (gray << 8) | gray;
                image.setRGB(x, y, rgb);
            }
        }

        JFrame frame = new JFrame(String.valueOf(worldSeed));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(image));

        JButton nextButton = new JButton("Next");

        nextButton.addActionListener(e -> {
            worldSeed = random.nextInt();
            frame.setTitle(String.valueOf(worldSeed));

            Perlin newPerlin = new Perlin(worldSeed, 6, 0.5, 2.0);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    values[x][y] = newPerlin.noise2D(x * scale, y * scale);
                }
            }

            BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int gray = (int)((values[x][y] + 1) * 0.5 * 255);
                    gray = Math.max(0, Math.min(255, gray));
                    int rgb = (gray << 16) | (gray << 8) | gray;
                    newImage.setRGB(x, y, rgb);
                }
            }

            imageLabel.setIcon(new ImageIcon(newImage));
            frame.repaint();
        }); // end event listener

        frame.add(imageLabel, BorderLayout.CENTER);
        frame.add(nextButton, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
