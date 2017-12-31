package com.netcracker.unc.visualizer;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import static com.netcracker.unc.model.FishType.SHARK;
import static com.netcracker.unc.model.Flow.LEFT;
import static com.netcracker.unc.model.Flow.RIGHT;
import com.netcracker.unc.model.Ocean;
import com.netcracker.unc.model.impl.Shark;
import com.netcracker.unc.model.impl.SmallFish;
import com.netcracker.unc.model.interfaces.IFish;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OceanVisualizer {

    Screen screen;

    public OceanVisualizer() {
        screen = TerminalFacade.createScreen();
    }

    public void startScreen() {
        screen.startScreen();
    }

    public void stopScreen() {
        screen.stopScreen();
    }

    public void visualize() {
        Ocean ocean = Ocean.getInstanse();
        IFish[][] matrix = ocean.getMatrix();
        try {
            screen.clear();
            printMatrix();
            printInfo();
            screen.refresh();
            Thread.sleep(300);
        } catch (InterruptedException ex) {
            Logger.getLogger(OceanVisualizer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void printMatrix() {
        Ocean ocean = Ocean.getInstanse();
        IFish[][] matrix = ocean.getMatrix();
        for (int i = 0; i < ocean.getHeight(); i++) {
            for (int j = 0; j < ocean.getWidth(); j++) {
                if (matrix[i][j] != null) {
                    if (matrix[i][j].getType() == SHARK) {
                        screen.putString(j + 2, i + 1, "*", Terminal.Color.RED, Terminal.Color.RED);
                    } else {
                        screen.putString(j + 2, i + 1, "*", Terminal.Color.GREEN, Terminal.Color.GREEN);
                    }
                } else {
                    if (ocean.getFlowList().get(i) == LEFT) {
                        screen.putString(j + 2, i + 1, "<", Terminal.Color.WHITE, Terminal.Color.CYAN);
                    } else if ((ocean.getFlowList().get(i) == RIGHT)) {
                        screen.putString(j + 2, i + 1, ">", Terminal.Color.WHITE, Terminal.Color.CYAN);
                    } else {
                        screen.putString(j + 2, i + 1, "*", Terminal.Color.CYAN, Terminal.Color.CYAN);
                    }
                }
            }
        }
    }

    private void printInfo() {
        Ocean ocean = Ocean.getInstanse();
        screen.putString(0, ocean.getHeight() + 3, "Sharks: " + ocean.getSharks().size(), Terminal.Color.WHITE, Terminal.Color.BLACK);
        screen.putString(50, ocean.getHeight() + 3, "Small: " + ocean.getSmallFishes().size(), Terminal.Color.WHITE, Terminal.Color.BLACK);
        for (int i = 0; i < ocean.getSharks().size(); i++) {
            Shark shark = (Shark) ocean.getSharks().get(i);
            screen.putString(0, ocean.getHeight() + 5 + i, String.format("Shark %s: age: %s, hungerCounter: %s", i + 1, shark.getAge(), shark.getHungerCounter()), Terminal.Color.WHITE, Terminal.Color.BLACK);
        }
        for (int i = 0; i < ocean.getSmallFishes().size(); i++) {
            SmallFish fish = (SmallFish) ocean.getSmallFishes().get(i);
            screen.putString(50, ocean.getHeight() + 5 + i, String.format("Small %s: age: %s", i + 1, fish.getAge()), Terminal.Color.WHITE, Terminal.Color.BLACK);

        }
    }

}
