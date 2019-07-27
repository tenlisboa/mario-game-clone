package com.devthunder.world;

import com.devthunder.main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {

    public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(0, 0, Game.SPRITE_SIZE, Game.SPRITE_SIZE);
    public static BufferedImage TILE_WALL = Game.spritesheet.getSprite(32, 0, Game.SPRITE_SIZE, Game.SPRITE_SIZE);

    private BufferedImage sprite;
    private int x, y;

    public Tile(int x, int y, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    public void render(Graphics g) {
        g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
    }

}
