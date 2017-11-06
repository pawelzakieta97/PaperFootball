package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import static com.company.Constants.*;

public class View extends Canvas implements Observer{
    //private Thread thread;
    //LinkedList<GameObject> objects = new LinkedList<GameObject>();
    private Model model;
    private JFrame frame;
    public Choice selectedMode;
    public Choice difficulty;
    public JButton resetButton;
    //private Thread thread;

    public View(String title){


        frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        frame.setMaximumSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        frame.setMinimumSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);


        JPanel toolbar = new JPanel();
        GridLayout toolbarLayout = new GridLayout(  1,3,20,10);
        toolbar.setLayout(toolbarLayout);

        //toolbar.setRollover(true);
        //toolbar.setFloatable(false);

        //toolbar.add( Box.createHorizontalGlue() );
        resetButton = new JButton("Reset");
        selectedMode = new Choice();
        selectedMode.add("Player");
        selectedMode.add("Computer");

        difficulty = new Choice();
        difficulty.add("Easy");
        difficulty.add("Hard");

        JPanel modePanel = new JPanel(new GridLayout(1,2,0,0));
        modePanel.setOpaque(true);
        JPanel difficultyPanel = new JPanel(new GridLayout(1,2,0,0));
        difficultyPanel.setOpaque(true);

        JLabel labelMode = new JLabel("Player vs ");
        labelMode.setHorizontalAlignment(JLabel.RIGHT);

        JLabel labelDifficulty = new JLabel("Difficulty ");
        labelDifficulty.setHorizontalAlignment(JLabel.RIGHT);

        modePanel.add(labelMode);
        modePanel.add(selectedMode);
        difficultyPanel.add(labelDifficulty,BorderLayout.WEST);
        difficultyPanel.add(difficulty);


        toolbar.add(resetButton);
        toolbar.add(modePanel);
        toolbar.add(difficultyPanel);


        //toolbar.add(labelDifficulty);
        //toolbar.add(difficulty);

        frame.add( toolbar , BorderLayout.NORTH );
        frame.pack();

        System.out.println(toolbarLayout.getColumns());

        frame.setVisible(true);

        frame.add(this);


    }



    public void assignModel(Model model){
        this.model=model;
    }

    public void render(){

        BufferStrategy bs=this.getBufferStrategy();
        if (bs==null){
            this.createBufferStrategy(2);
            bs=this.getBufferStrategy();
            //return;
        }
        Graphics g = bs.getDrawGraphics();
        renderBG(g);
        for (GameObject o: model.objects){
            o.render(g);
        }
        //model.getPitch().render(g);
        // nie wiem czemu ale jak jest tylko raz "show" to nie pokazuje na bierzaco, tylko jeden ruch w tyl
        bs.show();
        //System.out.println(model.getPitch().currentGameState.treeSize());
        //bs.show();



        g.dispose();

    }
    public void update(Observable obs, Object obj){
        System.out.println("wywolano update");
        render();
    }
    void renderBG(Graphics g){
        System.out.println("bg rendered");
        Color paper = new Color(254,254,240);
        g.setColor(paper);
        g.fillRect(0,0,Constants.WIDTH, Constants.HEIGHT);
        renderChecker(g);

        BufferedImage img = null;
        try{
            img = ImageIO.read(new File("kolka.png"));
        }
        catch (IOException e){
            System.out.println("nie znaleziono obrazu");
        }
        g.drawImage(img, 0, 0,null);
    }

    void renderChecker(Graphics g){
        int x=0, y=0;
        Color grid = new Color(170,220,255);
        g.setColor(grid);
        while (y<Constants.HEIGHT){

            g.drawLine(0,y,Constants.WIDTH,y);
            y=y+Constants.SQUARE_SIZE;
        }
        while (x<Constants.WIDTH){
            g.drawLine(x,0,x,Constants.HEIGHT);
            x=x+Constants.SQUARE_SIZE;
        }

    }

}
