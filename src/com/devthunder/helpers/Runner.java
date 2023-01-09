package com.devthunder.helpers;

import com.devthunder.main.Game;

public class Runner {
    long lastUpdateTime;
    double amountOfTicks;
    double nanoSecondsInSeconds;
    double timeSinceLastGameUpdate;
    int frames;
    double timer;

    public Runner() {
        lastUpdateTime = System.nanoTime();
        amountOfTicks = 60.0;
        nanoSecondsInSeconds = 1000000000 / amountOfTicks;
        timeSinceLastGameUpdate = 0;
        frames = 0;
        timer = System.currentTimeMillis();
    }

    public void run() {
        Game game = Game.getInstance();
        game.requestFocus();
        gameLooping(game);
        game.stop();
    }

    private void gameLooping(Game game) {
        while (game.isRunning) {
            updateGameTimes();
            updateGameFrame(game);
            updateFramesPerSecond();
        }
    }

    private void updateGameTimes() {
        long nowInNanoSeconds = System.nanoTime();
        timeSinceLastGameUpdate += (nowInNanoSeconds - lastUpdateTime) / nanoSecondsInSeconds;
        lastUpdateTime = nowInNanoSeconds;
    }

    private void updateFramesPerSecond() {
        if (isPassedOneSecond()) {
            System.out.println("FPS: " + frames);
            frames = 0;
            timer += 1000;
        }
    }

    private boolean isPassedOneSecond() {
        return System.currentTimeMillis() - timer >= 1000;
    }

    private void updateGameFrame(Game game) {
        if (timeSinceLastGameUpdate >= 1) {
            game.tick();
            game.render();
            frames++;
            timeSinceLastGameUpdate--;
        }
    }
}
