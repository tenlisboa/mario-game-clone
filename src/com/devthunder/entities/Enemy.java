package com.devthunder.entities;

import com.devthunder.main.Game;
import com.devthunder.world.World;

import java.awt.image.BufferedImage;


public class Enemy extends Entity {

    public boolean right = true, left = false;

    public int life = 3;

    public Enemy(int x, int y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
    }

    @Override
    public void tick() {
        if (World.isFree((int) x, (int) (y + 1))) {
            y += 1;
        }

        if (life <= 0) {
            Game.entities.remove(this);
            return;
        }

        if (right) {
            if (World.isFree((int) (x + speed), getY())) {
                x += speed;
                if (World.isFree((int) (x + Game.SPRITE_SIZE), (int) y + 1)) {
                    right = false;
                    left = true;
                }
            } else {
                right = false;
                left = true;
            }
        } else if (left) {
            if (World.isFree((int) (x - speed), getY())) {
                x -= speed;
                if (World.isFree((int) (x - Game.SPRITE_SIZE), (int) y + 1)) {
                    right = true;
                    left = false;
                }
            } else {
                right = true;
                left = false;
            }
        }
    }
}
