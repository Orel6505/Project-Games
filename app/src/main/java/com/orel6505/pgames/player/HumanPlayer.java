package com.orel6505.pgames.player;

import com.orel6505.pgames.action.Action;

import java.util.Scanner;

public class HumanPlayer extends Player {
    private Scanner sn;
    
    public HumanPlayer(String name){
        super(name);
        this.sn = new Scanner(System.in);
    }

    public HumanPlayer(String name, Scanner sn){
        super(name);
        this.sn = sn;
    }
    
    @Override
    public Action selectAction(Action[] actions) {
        Integer chosenAction = -1;
        while (chosenAction < 0 || chosenAction >= actions.length) {
            System.out.println("Please enter a number between 1 and " + (actions.length));
            if (sn.hasNextInt()) {
                chosenAction = sn.nextInt() - 1;
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                sn.next(); // Consume the invalid input
            }
        }
        return actions[chosenAction];
    }

    @Override
    public Action selectActions(Action[] actions) {
        return selectAction(actions);
    }
}
