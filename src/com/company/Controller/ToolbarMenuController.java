package com.company.Controller;

import com.company.Const.GameMode;
import com.company.Model.Player;
import com.company.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * This class handles toolbar events.
 */
public class ToolbarMenuController {
    private Controller controller;
    public ToolbarMenuController(){

    }
    public void assignController(Controller controller){
        this.controller = controller;
    }

    /**
     * this method is called at the beginning of the program. It assigns listeners to toolbar elements.
     */
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
                    if (controller.getModel().getPitch().getCurrentGameState().getCurrentPlayer() == Player.P2){
                        controller.getModel().getPitch().getCurrentGameState().switchPlayers();
                        controller.getModel().makeAIMove(controller.getModel().getDifficulty());
                    }
                    view.difficulty.setEnabled(true);
                }

            }
        });
    }

}
