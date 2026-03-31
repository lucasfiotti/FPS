package com.jad;

import com.jad.juniafps.*;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Map map = new Map("map_zebi.bmp");
        Player player = new Player(new Point(429, 349), 0, map);
        Renderer renderer = new Renderer();
        GameWindow window = new GameWindow("SALAMALEYKOUM");
        while (!ActionPlayer.EXIT.isActive()) {
            window.display(renderer.render(player.getPosition(), player.getDirection(), map));
            player.handleActions();
        }
        System.exit(0);
    }
}