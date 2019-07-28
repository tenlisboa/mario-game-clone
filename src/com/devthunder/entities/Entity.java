package com.devthunder.entities;

import com.devthunder.main.Game;
import com.devthunder.world.Camera;
import com.devthunder.world.Node;
import com.devthunder.world.Vector2i;
import com.devthunder.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Entity {

    public static BufferedImage PLAYER_SPRITE_JUMPING_RIGHT = Game.spritesheet.getSprite(96, 0, Game.SPRITE_SIZE, Game.SPRITE_SIZE);
    public static BufferedImage PLAYER_SPRITE_JUMPING_LEFT = Game.spritesheet.getSprite(96, 16, Game.SPRITE_SIZE, Game.SPRITE_SIZE);

    protected double x;
    protected double y;
    protected int width;
    protected int height;
    protected double speed;

    public int depth;

    protected List<Node> path;

    public boolean debug = false;

    protected BufferedImage sprite;

    public static Random rand = new Random();

    public Entity(double x, double y, int width, int height, double speed, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
    }

    public static Comparator<Entity> nodeSorter = new Comparator<Entity>() {

        @Override
        public int compare(Entity n0, Entity n1) {
            if (n1.depth < n0.depth)
                return +1;
            if (n1.depth > n0.depth)
                return -1;
            return 0;
        }

    };


    public void updateCamera() {
        Camera.x = Camera.clamp(getX() - (Game.WIDTH / 2), 0, World.WIDTH * 32 - Game.WIDTH);
        Camera.y = Camera.clamp(getY() - (Game.HEIGHT / 2), 0, World.HEIGHT * 32 - Game.HEIGHT);
    }

    public void setX(int newX) {
        x = newX;
    }

    public void setY(int newY) {
        y = newY;
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void tick() {
    }

    public double calculateDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }


    public void followPath(List<Node> path) {
        if (path != null) {
            if (path.size() > 0) {
                Vector2i target = path.get(path.size() - 1).tile;
                //xprev = x;
                //yprev = y;
                if (x < target.x * 16) {
                    x++;
                } else if (x > target.x * 16) {
                    x--;
                }

                if (y < target.y * 16) {
                    y++;
                } else if (y > target.y * 16) {
                    y--;
                }

                if (x == target.x * 16 && y == target.y * 16) {
                    path.remove(path.size() - 1);
                }

            }
        }
    }

    public static boolean isColidding(Entity e1, Entity e2) {
        Rectangle e1Mask = new Rectangle(e1.getX(), e1.getY(), e1.getWidth(), e1.getHeight());
        Rectangle e2Mask = new Rectangle(e2.getX(), e2.getY(), e2.getWidth(), e2.getHeight());

        return e1Mask.intersects(e2Mask);
    }

    public void render(Graphics g) {
        g.drawImage(sprite, getX() - Camera.x, getY() - Camera.y, null);
        //g.setColor(Color.red);
        //g.fillRect(this.getX() + maskx - Camera.x,this.getY() + masky - Camera.y,mwidth,mheight);
    }

}
