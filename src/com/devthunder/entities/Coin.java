package com.devthunder.entities;

import com.devthunder.main.Game;

import java.awt.image.BufferedImage;

public class Coin extends Entity {
    public Coin(double x, double y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
    }

    @Override
    public void tick() {
        checkPlayerCollision();
    }

    public void checkPlayerCollision() {
        for (int i = 0; i < Game.entities.size(); i++) {
            Entity e = Game.entities.get(i);
            if (e instanceof Player) {
                if (Entity.isColidding(this, e)) {
                    Game.player.currentCoins++;
                    Game.entities.remove(this);
                    return;
                }
            }
        }
    }
}
