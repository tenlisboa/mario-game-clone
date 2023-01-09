package com.devthunder.main;

import com.devthunder.entities.Entity;
import com.devthunder.entities.Player;
import com.devthunder.graphics.Spritesheet;
import com.devthunder.graphics.UI;
import com.devthunder.helpers.Runner;
import com.devthunder.world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    private static Game instance;
    @Serial
    private static final long serialVersionUID = 1L;
    public static JFrame frame;
    public boolean isRunning = true;
    private Thread thread;
    public static final int WIDTH = 240;
    public static final int HEIGHT = 160;
    public static final int SCALE = 3;

    private BufferedImage image;

    public static List<Entity> entities;
    public static Spritesheet spritesheet;
    public static World world;
    public static Player player;

    public static final int SPRITE_SIZE = 16;

    public UI ui;

    public Game() {
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        initFrame();
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        // Initializing objects.
        entities = new ArrayList<Entity>();
        spritesheet = new Spritesheet("/spritesheet.png");
        player = new Player(0, 0, SPRITE_SIZE, SPRITE_SIZE, 1, null);
        world = new World("/level1.png");
        ui = new UI();

        entities.add(player);
    }

    public static Game getInstance() {
        if (instance != null) {
            instance = new Game();
        }

        return instance;
    }

    public void initFrame() {
        frame = new JFrame("Super Mario");
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public synchronized void start() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public synchronized void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        Game game = Game.getInstance();
        game.start();
    }

    public void tick() {
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            e.tick();
        }
    }


    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = image.getGraphics();
        g.setColor(new Color(99, 155, 255));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // World render
        world.render(g);
        Collections.sort(entities, Entity.nodeSorter);
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            e.render(g);
        }
        // End World render
        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        ui.render(g);
        bs.show();
    }

    @Override
    public void run() {
        Runner runner = new Runner();
        runner.run();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT ||
                e.getKeyCode() == KeyEvent.VK_D) {
            player.right = true;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT ||
                e.getKeyCode() == KeyEvent.VK_A) {
            player.left = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            player.jump = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT ||
                e.getKeyCode() == KeyEvent.VK_D) {
            player.right = false;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT ||
                e.getKeyCode() == KeyEvent.VK_A) {
            player.left = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent arg0) {

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {

    }

    @Override
    public void mouseExited(MouseEvent arg0) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {

    }

    @Override
    public void mouseDragged(MouseEvent arg0) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
