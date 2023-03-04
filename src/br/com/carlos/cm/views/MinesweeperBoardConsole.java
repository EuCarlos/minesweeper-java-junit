package br.com.carlos.cm.views;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.carlos.cm.exceptions.ExplosionException;
import br.com.carlos.cm.exceptions.ExitException;
import br.com.carlos.cm.models.MinesweeperBoard;

public class MinesweeperBoardConsole {
    private MinesweeperBoard minesweeperBoard;
    private Scanner input = new Scanner(System.in);

    public MinesweeperBoardConsole(MinesweeperBoard minesweeperBoard) {
        this.minesweeperBoard = minesweeperBoard;

        runGame();
    }

    private void runGame() {
        try {
            boolean continueGame = true;

            while (continueGame) {
                gameCycle();

                System.out.println("Outra partida? (S/n) ");
                String response = input.nextLine();

                if ("n".equalsIgnoreCase(response)) {
                    continueGame = false;
                } else {
                    minesweeperBoard.reset();
                }
            }
        } catch (ExitException e) {
            System.out.println("Tchau!!!");
        } finally {
            input.close();
        }
    }

    private void gameCycle() {
        try {

            while (!minesweeperBoard.goalAchieved()) {
                System.out.println(minesweeperBoard);

                String typed = captureTypedValue("Digite (x, y): ");

                Iterator<Integer> xy = Arrays.stream(
                    typed
                        .split(","))
                            .map(e -> Integer.parseInt(e.trim())).iterator();

                typed = captureTypedValue("1 - Abrir ou 2 - (Des)Marcar: ");

                if ("1".equals(typed)) {
                    minesweeperBoard.open(xy.next(), xy.next());
                } else if ("2".equals(typed)) {
                    minesweeperBoard.toggleMarking(xy.next(), xy.next());
                }
            }

            System.out.println(minesweeperBoard);
            System.out.println("Você ganhou!!!");
        } catch (ExplosionException e) {
            System.out.println(minesweeperBoard);
            System.out.println("Você perdeu!");
        }
    }

    private String captureTypedValue(String str) {
        System.out.print(str);
        String typed = input.nextLine();

        if ("sair".equalsIgnoreCase(typed)) throw new ExitException();

        return typed;
    }
}
