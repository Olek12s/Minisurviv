package io.core.level;

import io.core.level.biome.Biome;
import io.core.level.biome.Biomes;
import io.core.level.tile.TileData;
import io.core.util.FloatConsumer;
import io.core.util.Noise;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LevelGenerator {

    private static int worldSeed = 0;
    private static final Random random = new Random();


    public static void generateMapLevel(Level level, FloatConsumer progress) {

        int chunksX = level.width / Chunk.CHUNK_SIZE;
        int chunksY = level.height / Chunk.CHUNK_SIZE;

        int totalChunks = chunksX * chunksY;
        int done = 0;

        for (int cx = 0; cx < chunksX; cx++) {
            for (int cy = 0; cy < chunksY; cy++) {

                Chunk chunk = new Chunk();
                level.chunks[cx][cy] = chunk;

                // world-space offset of this chunk
                int chunkWorldX = cx * Chunk.CHUNK_SIZE - level.width / 2;
                int chunkWorldY = cy * Chunk.CHUNK_SIZE - level.height / 2;

                // Noise LOCAL to this chunk
                Noise noise = new Noise(
                        level.seed,
                        chunkWorldX,
                        chunkWorldY,
                        0,
                        Chunk.CHUNK_SIZE,
                        Chunk.CHUNK_SIZE
                );

                chunk.setNoise(noise);

                // generating biomes and Tile layers
                for (int tx = 0; tx < Chunk.CHUNK_SIZE; tx++) {
                    for (int ty = 0; ty < Chunk.CHUNK_SIZE; ty++) {

                        int worldX = chunkWorldX + tx;
                        int worldY = chunkWorldY + ty;

                        Biome biome = Biomes.matchBiome(noise, worldX, worldY);

                        TileData tileData = new TileData(Biomes.getBiomeId(biome), worldX, worldY);
                        chunk.tileDats[tx][ty] = tileData;
                        biome.generate(chunk, tx, ty);
                    }
                }
                done++;
                progress.accept(done / (float) totalChunks);
                chunk.setNoise(noise);  // IMPORTANT! clear noise from the chunk after generating

                if (done == totalChunks) {
                    System.out.println("[World Generator] Generated level: " + LevelsManager.getLevelName(level.getLevelNumber()));
                }
            }
        }
    }

    public static void main(String[] args) {

        // worldSeed = (int) System.currentTimeMillis();
        //worldSeed = 750;
        worldSeed = 781;
        random.setSeed(worldSeed);

        int width = 512;
        int height = 512;

        JFrame frame = new JFrame("Seed: " + worldSeed);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel imageLabel = new JLabel();

        JButton nextButton = new JButton("Next seed");

        nextButton.addActionListener(e -> {
            worldSeed = random.nextInt();
            frame.setTitle("Seed: " + worldSeed);
            imageLabel.setIcon(new ImageIcon(generateBiomeImage(width, height, worldSeed)));
        });

        imageLabel.setIcon(new ImageIcon(generateBiomeImage(width, height, worldSeed)));

        frame.add(imageLabel, BorderLayout.CENTER);
        frame.add(nextButton, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static BufferedImage generateBiomeImage(int width, int height, int seed) {

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Noise noise = new Noise(seed, 0, 0, 0, width, height);

        Map<Biome, Integer> biomeCounts = new HashMap<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                Biome biome = Biomes.matchBiome(noise, x, y);
                int color = Biomes.getBiomeColor(biome);

                image.setRGB(x, y, color);

                biomeCounts.merge(biome, 1, Integer::sum);
            }
        }

        // DEBUG OUTPUT
        int total = width * height;
        System.out.println("=== BIOME DISTRIBUTION (seed " + seed + ") ===");

        for (Biome biome : Biomes.getAllBiomes()) {

            int count = biomeCounts.getOrDefault(biome, 0);
            double percent = (count * 100.0) / total;

            System.out.printf(
                    "%-15s : %6.2f %% (%d tileDats)%n",
                    biome.getClass().getSimpleName(),
                    percent,
                    count
            );
        }
        System.out.println("====================================\n");

        return image;
    }
}
