package com.company;

public class Main {
    public static void main(String[] args){
        View view = new View("gra");
        Model model = new Model();

        //view.assignModel(model);

        Controller controller = new Controller(view,model);
        model.assignController(controller);
        controller.startNewGame(AppState.PVP);
    }
}
