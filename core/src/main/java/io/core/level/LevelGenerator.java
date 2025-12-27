package io.core.level;

import io.core.level.biome.Biome;
import io.core.level.biome.Biomes;
import io.core.util.Noise;
import io.core.util.Perlin;

import java.awt.*;
import java.util.Random;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;


public class LevelGenerator {
    private static int worldSeed;
    private static final Random random = new Random();

    // World params
    private static final int WIDTH = 300;
    private static final int HEIGHT = 300;

    // Noise params
    private static final int OCTAVES = 8;
    private static final double PERSISTENCE = 0.5;
    private static final double LACUNARITY = 2.0;




    public static void main(String[] args) {
        worldSeed = (int) System.currentTimeMillis();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel imageLabel = new JLabel();
        JButton nextButton = new JButton("Next");

        // --- INITIAL GENERATION ---
        BufferedImage image = generateBiomeImage(worldSeed);
        imageLabel.setIcon(new ImageIcon(image));
        frame.setTitle(String.valueOf(worldSeed));

        // --- NEXT SEED ---
        nextButton.addActionListener(e -> {
            worldSeed = random.nextInt();
            frame.setTitle(String.valueOf(worldSeed));
            imageLabel.setIcon(new ImageIcon(generateBiomeImage(worldSeed)));
            frame.repaint();
        });

        frame.add(imageLabel, BorderLayout.CENTER);
        frame.add(nextButton, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static BufferedImage generateBiomeImage(int seed) {

        Noise noise = new Noise(
                seed,
                WIDTH,
                HEIGHT,
                OCTAVES,
                PERSISTENCE,
                LACUNARITY,
                0, 0, 0
        );

        BufferedImage image = new BufferedImage(
                WIDTH,
                HEIGHT,
                BufferedImage.TYPE_INT_ARGB
        );

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {

                Biome biome = Biomes.matchBiome(noise, x, y);
                int color = Biomes.getBiomeColor(biome);

                image.setRGB(x, y, color);
            }
        }
        return image;
    }
}
