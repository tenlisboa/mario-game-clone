package com.devthunder.graphics;

import com.devthunder.main.Game;

import java.awt.*;

public class UI {

    public void render(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.BOLD, 18));
        g.drawString("Life: " + (int) Game.player.life, 20, 25);
        g.drawString("Coins: " + Game.player.currentCoins + "/" + Game.player.maxCoins, 20, 50);
    }

}
