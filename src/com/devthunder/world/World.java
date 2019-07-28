package com.devthunder.world;

import com.devthunder.entities.Coin;
import com.devthunder.entities.Enemy;
import com.devthunder.entities.Entity;
import com.devthunder.entities.Player;
import com.devthunder.main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class World {

    public static Tile[] tiles;
    public static int WIDTH, HEIGHT;
    public static final int TILE_SIZE = Game.SPRITE_SIZE;

    public World(String path) {
        try {
            BufferedImage map = ImageIO.read(getClass().getResource(path));
            int[] pixels = new int[map.getWidth() * map.getHeight()];
            WIDTH = map.getWidth();
            HEIGHT = map.getHeight();
            tiles = new Tile[map.getWidth() * map.getHeight()];
            map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
            for (int xx = 0; xx < map.getWidth(); xx++) {
                for (int yy = 0; yy < map.getHeight(); yy++) {
                    int actualPixel = pixels[xx + (yy * map.getWidth())];
                    tiles[xx + (yy * WIDTH)] = new FloorTile(xx * Game.SPRITE_SIZE, yy * Game.SPRITE_SIZE, Tile.TILE_FLOOR);
                    if (actualPixel == 0xFF000000) {
                        tiles[xx + (yy * WIDTH)] = new FloorTile(xx * Game.SPRITE_SIZE, yy * Game.SPRITE_SIZE, Tile.TILE_FLOOR);
                    } else if (actualPixel == 0xFFffffff) {
                        tiles[xx + (yy * WIDTH)] = new WallTile(xx * Game.SPRITE_SIZE, yy * Game.SPRITE_SIZE, Tile.TILE_WALL);
                        if (yy - 1 >= 0 && pixels[xx + ((yy - 1) * map.getWidth())] == 0xFFffffff) {
                            tiles[xx + (yy * WIDTH)] = new WallTile(xx * Game.SPRITE_SIZE, yy * Game.SPRITE_SIZE, Tile.TILE_WALL_PLAIN);
                        }
                    } else if (actualPixel == 0xFF0000ff) { // Player
                        Game.player.setX(xx * Game.SPRITE_SIZE);
                        Game.player.setY(yy * Game.SPRITE_SIZE);
                    } else if (actualPixel == 0xFFff0000) { // Enemy
                        Enemy enemy = new Enemy(xx * Game.SPRITE_SIZE, yy * Game.SPRITE_SIZE, Game.SPRITE_SIZE, Game.SPRITE_SIZE, 0.7, Entity.ENEMY);
                        Game.entities.add(enemy);
                    } else if (actualPixel == 0xFFfff300) {
                        Coin coin = new Coin(xx * Game.SPRITE_SIZE, yy * Game.SPRITE_SIZE, Game.SPRITE_SIZE, Game.SPRITE_SIZE, 0, Entity.COIN);
                        Game.entities.add(coin);
                        Game.player.maxCoins++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isFree(int xnext, int ynext) {

        int x1 = xnext / TILE_SIZE;
        int y1 = ynext / TILE_SIZE;

        int x2 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
        int y2 = ynext / TILE_SIZE;

        int x3 = xnext / TILE_SIZE;
        int y3 = (ynext + TILE_SIZE - 1) / TILE_SIZE;

        int x4 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
        int y4 = (ynext + TILE_SIZE - 1) / TILE_SIZE;
        
        boolean outOfWorld = x1 + (y1 * World.WIDTH) <= 0;

        return !(outOfWorld ||
                (tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile) ||
                (tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile) ||
                (tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile) ||
                (tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile));
    }

    public static void restartGame(String level) {
        Game.entities.clear();
        Game.player = new Player(0, 0, Game.SPRITE_SIZE, Game.SPRITE_SIZE, 1, null);
        Game.world = new World("/level1.png");

        Game.entities.add(Game.player);
        return;
    }

    public void render(Graphics g) {
        int xstart = Camera.x / TILE_SIZE;
        int ystart = Camera.y / TILE_SIZE;

        int xfinal = xstart + (Game.WIDTH / TILE_SIZE);
        int yfinal = ystart + (Game.HEIGHT / TILE_SIZE);

        for (int xx = xstart; xx <= xfinal; xx++) {
            for (int yy = ystart; yy <= yfinal; yy++) {
                if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
                    continue;
                Tile tile = tiles[xx + (yy * WIDTH)];
                tile.render(g);
            }
        }
    }

}
