package com.devthunder.entities;

import com.devthunder.world.Camera;
import com.devthunder.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    public boolean right, left;
    public int dir = 1;
    public double gravity = 2.2;

    public Player(int x, int y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
    }

    @Override
    public void tick() {
        depth = 1;

        if (World.isFree((int) x, (int) (y + gravity))) {
            y += speed;
        }

        if (right && World.isFree((int) (x + speed), (int) y)) {
            x += speed;
            dir = 1;
        } else if (left && World.isFree((int) (x + speed), (int) y)) {
            x -= speed;
            dir = -1;
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(dir == 1 ? Entity.PLAYER_SPRITE_RIGHT : Entity.PLAYER_SPRITE_LEFT, getX() - Camera.x, getY() - Camera.y, null);
    }
}
