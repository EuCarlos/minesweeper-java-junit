package br.com.carlos.cm;

import br.com.carlos.cm.models.Tabuleiro;
import br.com.carlos.cm.views.TabuleiroConsole;

public class App {

    public static void main(String[] args) {

        Tabuleiro tabuleiro = new Tabuleiro(6, 6, 3);
        new TabuleiroConsole(tabuleiro);
    }
}
