package br.com.carlos.cm;

import br.com.carlos.cm.models.MinesweeperBoard;
import br.com.carlos.cm.views.MinesweeperBoardConsole;

public class App {

    public static void main(String[] args) {

        MinesweeperBoard minesweeperBoard = new MinesweeperBoard(6, 6, 3);
        new MinesweeperBoardConsole(minesweeperBoard);
    }
}
