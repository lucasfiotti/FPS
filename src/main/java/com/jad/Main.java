package com.jad;

import com.jad.juniafps.GameWindow;
import com.jad.juniafps.Map;
import com.jad.juniafps.Renderer;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Map map = new Map("map_zebi.bmp");
        Renderer renderer = new Renderer();
        GameWindow window = new GameWindow("SALAMALEYKOUM");
        window.display(renderer.render(new Point(343, 346), 45, map));
    }
}