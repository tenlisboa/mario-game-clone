package com.devthunder.entities;

import com.devthunder.world.World;

import java.awt.image.BufferedImage;

public class Player extends Entity {

    public boolean right, up, left, down;

    public Player(int x, int y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
    }

    @Override
    public void tick() {
        depth = 1;
        if (right && World.isFree((int) (x + speed), getY())) {
            x += speed;
        } else if (left && World.isFree((int) (x - speed), getY())) {
            x -= speed;
        }
        if (up && World.isFree(getX(), (int) (y - speed))) {
            y -= speed;
        } else if (down && World.isFree(getX(), (int) (y + speed))) {
            y += speed;
        }

        updateCamera();
    }
}
