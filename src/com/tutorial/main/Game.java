package com.tutorial.main;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable{

    public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;

    private Thread thread;
    private Boolean running = false;

    public Game() {
        new Window(WIDTH, HEIGHT, "Lets make a Game", this);
    }

    public synchronized void start() {
        thread = new Thread(this);
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running) {
            long now = System.nanoTime();
            delta += (now = lastTime) / ns;
            //lastTime = now;
            while(delta >= 1) {
                tick();
                delta--;
            }
            if(running) {
                render();
                frames++;
            }

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {

    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.red);
        //g.setColor(Color.BLUE);
        g.fillRect(0,0, WIDTH, HEIGHT);

        g.dispose();
        bs.show();
    }

    public static void main(String[] args){
        new Game();
        /**
        System.out.printf("Byte minimum= %s, maximum= %s%n", Byte.MIN_VALUE, Byte.MAX_VALUE);
        System.out.printf("Short minimum= %s, maximum= %s%n", Short.MIN_VALUE, Short.MAX_VALUE);
        System.out.printf("Int minimum= %s, maximum= %s%n", Integer.MIN_VALUE, Integer.MAX_VALUE);
        System.out.printf("Long minimum= %s, maximum= %s%n", Long.MIN_VALUE, Long.MAX_VALUE);

         This stuff is from Tim B's video on Udemy...
         **/
    }
}
