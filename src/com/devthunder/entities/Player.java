package com.devthunder.entities;

import com.devthunder.main.Game;
import com.devthunder.world.Camera;
import com.devthunder.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    public boolean right, left;
    private int dir = 1;

    public double life = 100;

    public int currentCoins = 0;
    public int maxCoins = 0;

    private double gravity = 0.3;
    private double vspd = 0;

    private int frames, maxFrames = 7, index = 0, maxIndex = 2;
    private boolean moved = false;

    public boolean jump = false;
    public boolean onJumping = false;
    private boolean takeDamageOnEnemy = false;

    private BufferedImage[] rightPlayer, leftPlayer;

    public Player(int x, int y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);

        rightPlayer = new BufferedImage[3];
        leftPlayer = new BufferedImage[3];

        for (int i = 0; i < 3; i++) {
            rightPlayer[i] = Game.spritesheet.getSprite(32 + (i * Game.SPRITE_SIZE), 0, Game.SPRITE_SIZE, Game.SPRITE_SIZE);
        }
        for (int i = 0; i < 3; i++) {
            leftPlayer[i] = Game.spritesheet.getSprite(32 + (i * Game.SPRITE_SIZE), 16, Game.SPRITE_SIZE, Game.SPRITE_SIZE);
        }
    }

    @Override
    public void tick() {
        depth = 2;
        moved = false;

        if (life <= 0) {
            World.restartGame("/level1.png");
        }

        if (jump) {
            onJumping = true;
        }

        // TODO: Fix collision
        checkEnemyCollision();

        // REAL JUMP ALGORITHM
        vspd += gravity;
        if ((!World.isFree((int) x, (int) (y + 1)) || takeDamageOnEnemy) && jump) {
            vspd = -6;
            jump = false;
        }

        if (!World.isFree((int) x, (int) (y + vspd))) {
            int signVsp;
            if (vspd >= 0) {
                signVsp = 1;
            } else {
                signVsp = -1;
            }
            while (World.isFree((int) x, (int) (y + signVsp))) {
                y = y + signVsp;
            }
            vspd = 0;
        }

        y += vspd;
        // END

        if (!World.isFree(getX(), (int) (y + 2))) {
            onJumping = false;
        }

        if (right && World.isFree((int) (getX() + speed), getY())) {
            moved = true;
            x += speed;
            dir = 1;
        } else if (left && World.isFree((int) (getX() - speed), getY())) {
            moved = true;
            x -= speed;
            dir = -1;
        }

        if (moved) {
            frames++;
            if (frames == maxFrames) {
                frames = 0;
                index++;
                if (index > maxIndex) {
                    index = 0;
                }
            }
        }

        updateCamera();
    }

    public void checkEnemyCollision() {
        for (int i = 0; i < Game.entities.size(); i++) {
            Entity e = Game.entities.get(i);
            if (e instanceof Enemy) {
                if (Entity.isColidding(this, e)) {
                    jump = true;
                    takeDamageOnEnemy = true;
                    ((Enemy) e).life--;
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        BufferedImage spriteSelected = dir == 1 ? rightPlayer[index] : leftPlayer[index];
        if (onJumping) {
            spriteSelected = dir == 1 ? Entity.PLAYER_SPRITE_JUMPING_RIGHT : Entity.PLAYER_SPRITE_JUMPING_LEFT;
        }
        g.drawImage(spriteSelected, getX() - Camera.x, getY() - Camera.y, null);
    }
}
