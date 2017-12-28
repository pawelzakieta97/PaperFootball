package com.company;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ToolbarMenuController {
    private Controller controller;
    public ToolbarMenuController(){

    }
    public void assignController(Controller controller){
        this.controller = controller;
    }
    public void toolbarSetup(){
        View view = controller.getView();
        view.resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.selectedMode.getSelectedIndex()==0) {
                    controller.startNewGame(GameMode.PVP);
                }
                if (view.selectedMode.getSelectedIndex()==1) {
                    controller.startNewGame(GameMode.PVE);
                }
            }
        });
        view.difficulty.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                controller.getModel().setDifficulty(view.difficulty.getSelectedIndex()+1);
                System.out.print("difficulty changed to ");
                System.out.println(view.difficulty.getSelectedIndex()+1);
            }
        });
        view.selectedMode.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (view.selectedMode.getSelectedIndex()==0) {
                    controller.getModel().getPitch().getCurrentGameState().setGameMode(GameMode.PVP);
                    view.difficulty.setEnabled(false);
                }
                if (view.selectedMode.getSelectedIndex()==1) {
                    controller.getModel().getPitch().getCurrentGameState().setGameMode(GameMode.PVE);
                    view.difficulty.setEnabled(true);
                }

            }
        });
    }

}
