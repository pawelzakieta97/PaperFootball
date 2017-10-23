package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;

public class View extends Canvas{
    public static final int WIDTH = 640, HEIGHT = 480;
    //private Thread thread;
    //LinkedList<GameObject> objects = new LinkedList<GameObject>();
    private Model model;
    private JFrame frame;
    //private Thread thread;

    public View(String title){
        frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.add(this);
    }

    public void assignModel(Model model){
        this.model=model;
    }

    public void render(){

        BufferStrategy bs=this.getBufferStrategy();
        if (bs==null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0,0, WIDTH, HEIGHT);
        for (GameObject o: model.objects){
            o.render(g);
        }
        g.dispose();
        bs.show();
    }
}
