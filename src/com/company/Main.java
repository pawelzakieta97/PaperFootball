package com.company;

public class Main {
    public static void main(String[] args){
        View view = new View("gra");
        Model model = new Model();

        model.addObserver(view);
        view.assignModel(model);

        Controller controller = new Controller(view,model);
        model.assignController(controller);
        for (long i=0; i<20; i++){
            model.startNewGame(AppState.PVP);
        }

        //controller.run();

    }
}
