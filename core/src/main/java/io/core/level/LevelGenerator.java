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
        int width = 512 * 2;
        int height = 512 * 2;
        int octaves = 6;
        float persistence = 0.5f;
        float lacunarity = 2.0f;
        Double[][] values = new Double[width][height];

        //Perlin perlin = new Perlin(worldSeed, 2, 2, 2);
        Perlin perlin = new Perlin(worldSeed, octaves, persistence, lacunarity);

        double scale = 0.01;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                values[x][y] = perlin.noise2D(x * scale, y * scale);
            }
        }

        // DISPLAY THE VALUES AS .PNG IMAGE (DEBUG PURPOSES)

        BufferedImage image = getImage(width, height, values);

        JFrame frame = new JFrame(String.valueOf(worldSeed));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(image));

        JButton nextButton = new JButton("Next");

        nextButton.addActionListener(e -> {
            worldSeed = random.nextInt();
            frame.setTitle(String.valueOf(worldSeed));

            Perlin newPerlin = new Perlin(worldSeed, octaves, persistence, lacunarity);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    values[x][y] = newPerlin.noise2D(x * scale, y * scale);
                }
            }

            BufferedImage newImage = getImage(width, height, values);


            imageLabel.setIcon(new ImageIcon(newImage));
            frame.repaint();
        }); // end event listener

        frame.add(imageLabel, BorderLayout.CENTER);
        frame.add(nextButton, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static BufferedImage getImage(int width, int height, Double[][] values) {
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int gray = (int)((values[x][y] + 1) * 0.5 * 255);
                gray = Math.max(0, Math.min(255, gray));

                int alpha = 255;
                //int argb = (alpha << 24) | (gray << 16) | (gray << 8) | gray;
                int argb = 0xFF003000;  // ALPHA RED GREEN BLUE
                newImage.setRGB(x, y, argb);
            }
        }
        return newImage;
    }
}
