package com.jad.juniafps;

import java.awt.*;

public class Renderer {
    private final Screen screen = new Screen(RenderUtils.SCREEN_HEIGHT, RenderUtils.SCREEN_WIDTH);

    public String render(final Point origin, final int direction, final Map map) {
        final double[] perceivedHeights = this.getPerceivedHeights(origin, direction, map);
        int numColumn = 0;
        for (final double perceivedHeight : perceivedHeights) {
            this.renderColumn(numColumn++, perceivedHeight);
        }
        return this.screen.toStr();
    }

    private void renderColumn(final int numColumn, final double perceivedHeight) {
        this.screen.verticalLine(numColumn, 0, (int) (RenderUtils.EYE_HEIGHT - perceivedHeight / 2), RenderUtils.GROUND_PIXEL);
        this.screen.verticalLine(numColumn, (int) (RenderUtils.EYE_HEIGHT - perceivedHeight / 2), (int) (RenderUtils.EYE_HEIGHT + perceivedHeight / 2), RenderUtils.WALL_PIXEL);
        this.screen.verticalLine(numColumn, (int) (RenderUtils.EYE_HEIGHT + perceivedHeight / 2), RenderUtils.SCREEN_HEIGHT, RenderUtils.SKY_PIXEL);
    }

    private double[] getPerceivedHeights(final Point origin, final int direction, final Map map) {
        final double[] perceivedHeights = new double[RenderUtils.SCREEN_WIDTH];
        double angle = direction - RenderUtils.VIEWING_ANGLE / 2;
        final double angleStep = RenderUtils.VIEWING_ANGLE / RenderUtils.SCREEN_WIDTH;
        for (int column = 0; column < RenderUtils.SCREEN_WIDTH; column++, angle += angleStep) {
            perceivedHeights[column] = this.getPerceivedHeight(origin, angle, direction, map);
        }
        return perceivedHeights;
    }

    private double getPerceivedHeight(final Point origin, final double angle, final int direction, final Map map) {
        final double radianAngle = RenderUtils.degreesToRadians(angle);
        final Point end = new Point((int) (origin.x - RenderUtils.VIEWING_DISTANCE * Math.cos(radianAngle)), (int) (origin.y - RenderUtils.VIEWING_DISTANCE * Math.sin(radianAngle)));
        final Point obstacle = this.getFromTo(map, origin, end);
        if (obstacle != null) {
            final double realDistance = Math.sqrt(Math.pow(origin.x - obstacle.x, 2) + Math.pow(origin.y - obstacle.y, 2));
            final double eyeFishCorrection = Math.cos(RenderUtils.degreesToRadians(direction - angle));
            return RenderUtils.SCREEN_DISTANCE * RenderUtils.WALL_HEIGHT / realDistance / eyeFishCorrection;
        }
        return 0.0;
    }

    private Point getFromTo(Map map, Point origin, Point end) {
        if (map.get(origin.x, origin.y) != 0) return null;

        final int deltaX = Math.abs(end.x - origin.x);
        final int deltaY = Math.abs(end.y - origin.y);
        final int stepX = Integer.compare(end.x, origin.x);
        final int stepY = Integer.compare(end.y, origin.y);
        int error = deltaX - deltaY;
        int x = origin.x;
        int y = origin.y;
        while (x != end.x || y != end.y) {
            if (map.get(x, y) != 0) {
                return new Point(x, y);
            }
            final int error2 = error * 2;
            if (error2 > -deltaY) {
                error -= deltaY;
                x += stepX;
            }
            if (error2 < deltaX) {
                error += deltaX;
                y += stepY;
            }
        }
        return null;
    }


}