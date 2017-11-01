package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
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
            this.createBufferStrategy(3);
            bs=this.getBufferStrategy();
            //return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.gray);
        g.fillRect(0,0, Constants.WIDTH, Constants.HEIGHT);
        for (GameObject o: model.objects){
            o.render(g);
        }
        // nie wiem czemu ale jak jest tylko raz "show" to nie pokazuje na bierzaco, tylko jeden ruch w tyl
        bs.show();
        bs.show();


        g.dispose();

    }
    public void update(Observable obs, Object obj){
        System.out.println("wywolano update");
        render();
    }

}
