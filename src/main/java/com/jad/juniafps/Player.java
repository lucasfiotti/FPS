package com.jad.juniafps;

import java.awt.*;

public class Player {
    static final int MAX_DEGREES = 360;
    private static int playerStep = 10;
    private final Map map;
    private int direction;
    private Point position;


    public Player(Point position, int direction, Map map) {
        this.position = position;
        this.direction = direction;
        this.map = map;
    }


    public int getDirection() {
        return this.direction;
    }

    public void turnLeft() {
        this.direction = (direction - 10) % MAX_DEGREES;
        ActionPlayer.TURN_LEFT.turnOff();

    }

    public void turnRight() {
        this.direction = (direction + 10) % MAX_DEGREES;
        ActionPlayer.TURN_RIGHT.turnOff();
    }

    public void handleActions() {
        if (ActionPlayer.TURN_LEFT.isActive()) {
            this.turnLeft();
        }
        if (ActionPlayer.TURN_RIGHT.isActive()) {
            this.turnRight();
        }
        if (ActionPlayer.FORWARD.isActive()) {
            this.moveForward();
        }
        if (ActionPlayer.BACKWARD.isActive()) {
            this.moveBackward();
        }
        if (ActionPlayer.LEFT.isActive()) {
            this.moveLeft();
        }
        if (ActionPlayer.RIGHT.isActive()) {
            this.moveRight();
        }

    }

    private void moveRight() {
        final double angleRadians = RenderUtils.degreesToRadians(this.direction);
        final int newX = (int) (this.position.x + Player.playerStep * Math.sin(angleRadians));
        final int newY = (int) (this.position.y - Player.playerStep * Math.cos(angleRadians));

        this.move(new Point(newX, newY));
    }

    private void moveLeft() {
        final double angleRadians = RenderUtils.degreesToRadians(this.direction);
        final int newX = (int) (this.position.x - Player.playerStep * Math.sin(angleRadians));
        final int newY = (int) (this.position.y + Player.playerStep * Math.cos(angleRadians));

        this.move(new Point(newX, newY));
    }

    private void moveBackward() {
        final double angleRadians = RenderUtils.degreesToRadians(this.direction);
        final int newX = (int) (this.position.x + Player.playerStep * Math.cos(angleRadians));
        final int newY = (int) (this.position.y + Player.playerStep * Math.sin(angleRadians));

        this.move(new Point(newX, newY));
    }

    private void moveForward() {
        final double angleRadians = RenderUtils.degreesToRadians(this.direction);
        final int newX = (int) (this.position.x - Player.playerStep * Math.cos(angleRadians));
        final int newY = (int) (this.position.y - Player.playerStep * Math.sin(angleRadians));

        this.move(new Point(newX, newY));

    }

    public Point getPosition() {
        return this.position;
    }

    private void move(final Point point) {
        if (this.map.isEmpty(point)) this.position = point;
    }
}
