package br.com.carlos.cm;

import br.com.carlos.cm.models.MinesweeperBoard;
import br.com.carlos.cm.views.TabuleiroConsole;

public class App {

    public static void main(String[] args) {

        MinesweeperBoard tabuleiro = new MinesweeperBoard(6, 6, 3);
        new TabuleiroConsole(tabuleiro);
    }
}
