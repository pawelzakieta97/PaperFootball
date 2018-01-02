package com.company;

import com.company.Const.Constants;
import com.company.Const.GameMode;
import com.company.Controller.Controller;
import com.company.Model.Pitch;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;

public class View{
    private JFrame frame;
    private JPanel gamePanel;
    private Pitch pitch;

    private Controller controller;

    public Choice selectedMode;
    public Choice difficulty;
    public JButton resetButton;

    public Pitch getPitch() {
        return pitch;
    }

    public void setPitch(Pitch pitch) {
        this.pitch = pitch;
    }


    public View(String title){

        gamePanel = new JPanel(){
            public void paint(Graphics g){
                renderBG(g);
                if (pitch!=null){
                    pitch.render(g);
                }
                if (pitch.getCurrentGameState().getScored()==1){
                    renderWin((Graphics2D)g,"PLAYER 1 WON!");
                }
                if (pitch.getCurrentGameState().getScored()==-1){
                    if (pitch.getCurrentGameState().getGameMode()== GameMode.PVP){
                        renderWin((Graphics2D)g,"PLAYER 2 WON!");
                    }
                    else renderWin((Graphics2D)g,"YOU LOST");
                }

                g.dispose();
            }
        };


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

        resetButton = new JButton("Reset");
        selectedMode = new Choice();
        selectedMode.add("Player");
        selectedMode.add("Computer");

        difficulty = new Choice();
        difficulty.add("Easy");
        difficulty.add("Hard");
        difficulty.setEnabled(false);

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
        frame.add( toolbar , BorderLayout.NORTH );
        frame.pack();
        System.out.println(toolbarLayout.getColumns());
        frame.setVisible(true);
        frame.add(gamePanel);
    }

    public void assignController(Controller c){
        controller = c;
        gamePanel.addMouseListener(controller.getMouseEventHandler());
    }
    public void modelPropertyChange(PropertyChangeEvent evt){
        pitch = (Pitch)evt.getNewValue();
        System.out.println(pitch.getCurrentGameState().getScored());
    }

    public void paint(Graphics g){

        renderBG(g);
        if (pitch!=null){
            pitch.render(g);
        }
        g.dispose();
    }

    void renderBG(Graphics g){
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
    void renderWin(Graphics2D g, String S){
        g.setColor(new Color(0, 0, 0, 200));
        g.setFont(new Font("Helvetica", Font.PLAIN, 36));
        g.setColor(new Color(200, 48, 46));
        g.drawString(S, (int)(pitch.getPosX()+0.3*pitch.getSquareSize()),(int)(pitch.getPosY()-1*pitch.getSquareSize()));
    }

    public void render(){
        gamePanel.repaint();
    }

}
