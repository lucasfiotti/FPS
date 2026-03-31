package com.jad.juniafps;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Map {
    private final int[][] maze;
    private final int height;
    private final int width;

    public Map(final String imageName) {
        final URL url = Map.class.getClassLoader().getResource(imageName);
        final BufferedImage image;
        try {
            image = ImageIO.read(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.height = image.getHeight();
        this.width = image.getWidth();
        this.maze = new int[height][width];
        for (int row = 0; row < this.height; row++) {
            for (int column = 0; column < this.width; column++) {
                switch (image.getRGB(column, row)) {
                    case RenderUtils.BLACK_COLOR_IMAGE -> this.maze[row][column] = 1;
                    default -> this.maze[row][column] = 0;
                }
                ;
            }
        }
    }

    public int get(final int x, final int y) {
        return this.maze[y][x];
    }

    public boolean isEmpty(final Point point) {
        return this.maze[point.y][point.x] == 0;
    }
}
