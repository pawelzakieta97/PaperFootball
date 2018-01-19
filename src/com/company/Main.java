package com.company;

import com.company.Const.GameMode;
import com.company.Controller.Controller;
import com.company.Model.Model;

public class Main {
    public static void main(String[] args){
        View view = new View("gra");
        Model model = new Model();
        Controller controller = new Controller(view,model);
        controller.startNewGame(GameMode.PVP);
    }
}
