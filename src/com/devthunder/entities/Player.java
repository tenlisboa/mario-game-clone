package com.devthunder.entities;

import com.devthunder.main.Game;
import com.devthunder.world.Camera;
import com.devthunder.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    public boolean right, left;
    private int dir = 1;
    private double gravity = 2.2;

    private int frames, maxFrames = 7, index = 0, maxIndex = 2;
    private boolean moved = false;

    public boolean jump = false;
    public int jumpHeight = 40;
    public int jumpFrames = 0;
    public boolean isJumping = false;
    private boolean isDown = false;

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
        depth = 1;
        moved = false;

        if (World.isFree((int) x, (int) (y + gravity)) && !isJumping) {
            isDown = true;
            y += gravity;
        } else {
            isDown = false;
        }

        if (right && World.isFree((int) (getX() + speed), getY())) {
            moved = true;
            if (isJumping || isDown) {
                x += (speed + 0.5);
            } else {
                x += speed;
            }
            dir = 1;
        } else if (left && World.isFree((int) (getX() - speed), getY())) {
            moved = true;
            if (isJumping || isDown) {
                x -= (speed + 0.5);
            } else {
                x -= speed;
            }
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

        if (jump) {
            if (!World.isFree(getX(), getY() + 5)) {
                isJumping = true;
                isDown = false;
            } else {
                jump = false;
            }
        }

        if (isJumping) {
            isDown = false;
            if (World.isFree(getX(), getY() - 2)) {
                y -= 2;
                jumpFrames += 2;
                if (jumpFrames == jumpHeight) {
                    isJumping = false;
                    isDown = true;
                    jump = false;
                    jumpFrames = 0;
                }
            } else {
                isJumping = false;
                isDown = true;
                jumpFrames = 0;
                jump = false;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        BufferedImage spriteSelected = dir == 1 ? rightPlayer[index] : leftPlayer[index];
        if (isJumping || isDown) {
            spriteSelected = dir == 1 ? Entity.PLAYER_SPRITE_JUMPING_RIGHT : Entity.PLAYER_SPRITE_JUMPING_LEFT;
        }
        g.drawImage(spriteSelected, getX() - Camera.x, getY() - Camera.y, null);
    }
}
