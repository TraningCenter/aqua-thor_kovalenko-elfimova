package com.netcracker.unc.visualizer;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import static com.netcracker.unc.model.FishType.SHARK;
import static com.netcracker.unc.model.Flow.LEFT;
import static com.netcracker.unc.model.Flow.RIGHT;
import com.netcracker.unc.model.Ocean;
import com.netcracker.unc.model.interfaces.IFish;
import java.io.IOException;

/**
 * Ocean visualizer
 */
public class OceanVisualizer {

    Screen screen;
    Terminal terminal;
    TextGraphics textGraphics;

    /**
     * visualizer constructor
     */
    public OceanVisualizer() {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        try {
            terminal = defaultTerminalFactory.createTerminal();
            screen = new TerminalScreen(terminal);
            textGraphics = screen.newTextGraphics();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * start screen
     */
    public void startScreen() {
        try {
            screen.startScreen();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        screen.setCursorPosition(null);
    }

    /**
     * stop screen
     */
    public void stopScreen() {
        try {
            screen.stopScreen();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * visualize ocean system
     */
    public void visualize() {
        try {
            screen.clear();
            printMatrix();
            printInfo();
            screen.refresh();
            Thread.sleep(300);
        } catch (IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * visualize matrix with fishes
     */
    private void printMatrix() {
        Ocean ocean = Ocean.getInstanse();
        int shiftX = 5;
        int shiftY = 0;
        IFish[][] matrix = ocean.getMatrix();
        for (int i = 0; i < ocean.getHeight(); i++) {
            for (int j = 0; j < ocean.getWidth(); j++) {
                screen.doResizeIfNecessary();
                if (matrix[i][j] != null) {
                    if (matrix[i][j].getType() == SHARK) {
                        screen.setCharacter(j + shiftY, i + shiftX, new TextCharacter(' ', TextColor.ANSI.RED, TextColor.ANSI.RED));
                    } else {
                        screen.setCharacter(j + shiftY, i + shiftX, new TextCharacter(' ', TextColor.ANSI.GREEN, TextColor.ANSI.GREEN));
                    }
                } else {
                    switch (ocean.getFlowList().get(i)) {
                        case LEFT:
                            screen.setCharacter(j + shiftY, i + shiftX, new TextCharacter('<', TextColor.ANSI.WHITE, TextColor.ANSI.CYAN, SGR.BOLD));
                            break;
                        case RIGHT:
                            screen.setCharacter(j + shiftY, i + shiftX, new TextCharacter('>', TextColor.ANSI.WHITE, TextColor.ANSI.CYAN, SGR.BOLD));
                            break;
                        default:
                            screen.setCharacter(j + shiftY, i + shiftX, new TextCharacter(' ', TextColor.ANSI.CYAN, TextColor.ANSI.CYAN));
                            break;
                    }
                }
            }
        }
    }

    /**
     * visualize information block
     */
    private void printInfo() {
        Ocean ocean = Ocean.getInstanse();
        String infoLabel = String.format("Aquator [STEP %s]. Tor: %s, sharks: %s, smallFishes: %s. Press Esq to exit!", ocean.getStep(), ocean.isTor(), ocean.getSharks().size(), ocean.getSmallFishes().size());
        TerminalPosition labelBoxTopLeft = new TerminalPosition(0, 1);
        TerminalSize labelBoxSize = new TerminalSize(infoLabel.length() + 2, 3);
        TerminalPosition labelBoxTopRightCorner;
        labelBoxTopRightCorner = labelBoxTopLeft.withRelativeColumn(labelBoxSize.getColumns() - 1);
        //draw horizontal and vertical lines
        TextColor textColor = TextColor.ANSI.YELLOW;
        textGraphics.drawLine(
                labelBoxTopLeft.withRelativeColumn(1),
                labelBoxTopLeft.withRelativeColumn(labelBoxSize.getColumns() - 2),
                new TextCharacter(Symbols.DOUBLE_LINE_HORIZONTAL, textColor, TextColor.ANSI.DEFAULT));
        textGraphics.drawLine(
                labelBoxTopLeft.withRelativeRow(2).withRelativeColumn(1),
                labelBoxTopLeft.withRelativeRow(2).withRelativeColumn(labelBoxSize.getColumns() - 2),
                new TextCharacter(Symbols.DOUBLE_LINE_HORIZONTAL, textColor, TextColor.ANSI.DEFAULT));
        textGraphics.setCharacter(labelBoxTopLeft,
                new TextCharacter(Symbols.DOUBLE_LINE_TOP_LEFT_CORNER, textColor, TextColor.ANSI.DEFAULT));
        textGraphics.setCharacter(labelBoxTopLeft.withRelativeRow(1),
                new TextCharacter(Symbols.DOUBLE_LINE_VERTICAL, textColor, TextColor.ANSI.DEFAULT));
        textGraphics.setCharacter(labelBoxTopLeft.withRelativeRow(2),
                new TextCharacter(Symbols.DOUBLE_LINE_BOTTOM_LEFT_CORNER, textColor, TextColor.ANSI.DEFAULT));
        textGraphics.setCharacter(labelBoxTopRightCorner,
                new TextCharacter(Symbols.DOUBLE_LINE_TOP_RIGHT_CORNER, textColor, TextColor.ANSI.DEFAULT));
        textGraphics.setCharacter(labelBoxTopRightCorner.withRelativeRow(1),
                new TextCharacter(Symbols.DOUBLE_LINE_VERTICAL, textColor, TextColor.ANSI.DEFAULT));
        textGraphics.setCharacter(labelBoxTopRightCorner.withRelativeRow(2),
                new TextCharacter(Symbols.DOUBLE_LINE_BOTTOM_RIGHT_CORNER, textColor, TextColor.ANSI.DEFAULT));
        //put the text inside the box
        textGraphics.putString(labelBoxTopLeft.withRelative(1, 1), infoLabel, SGR.BOLD);
    }

    /**
     * clear screen
     */
    public void clear() {
        screen.clear();
    }

    /**
     * refresh screen
     *
     * @throws IOException
     */
    public void refresh() throws IOException {
        screen.refresh();
    }

    public Screen getScreen() {
        return screen;
    }

    public TextGraphics getTextGraphics() {
        return textGraphics;
    }
}
