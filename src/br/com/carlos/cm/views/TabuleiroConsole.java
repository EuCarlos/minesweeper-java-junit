package br.com.carlos.cm.views;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.carlos.cm.exceptions.ExplosionException;
import br.com.carlos.cm.exceptions.ExitException;
import br.com.carlos.cm.models.MinesweeperBoard;

public class TabuleiroConsole {
    private MinesweeperBoard tabuleiro;
    private Scanner entrada = new Scanner(System.in);

    public TabuleiroConsole(MinesweeperBoard tabuleiro) {
        this.tabuleiro = tabuleiro;

        executarJogo();
    }

    private void executarJogo() {
        try {
            boolean continuar = true;

            while (continuar) {
                cicloDoJogo();

                System.out.println("Outra partida? (S/n) ");
                String resposta = entrada.nextLine();

                if ("n".equalsIgnoreCase(resposta)) {
                    continuar = false;
                } else {
                    tabuleiro.reset();
                }
            }
        } catch (ExitException e) {
            System.out.println("Tchau!!!");
        } finally {
            entrada.close();
        }
    }

    private void cicloDoJogo() {
        try {

            while (!tabuleiro.goalAchieved()) {
                System.out.println(tabuleiro);

                String digitado = capturarValorDigitado("Digite (x, y): ");

                Iterator<Integer> xy = Arrays.stream(digitado.split(","))
                        .map(e -> Integer.parseInt(e.trim())).iterator();

                digitado = capturarValorDigitado("1 - Abrir ou 2 - (Des)Marcar: ");

                if ("1".equals(digitado)) {
                    tabuleiro.open(xy.next(), xy.next());
                } else if ("2".equals(digitado)) {
                    tabuleiro.toggleMarking(xy.next(), xy.next());
                }
            }

            System.out.println(tabuleiro);
            System.out.println("Você ganhou!!!");
        } catch (ExplosionException e) {
            System.out.println(tabuleiro);
            System.out.println("Você perdeu!");
        }
    }

    private String capturarValorDigitado(String texto) {
        System.out.print(texto);
        String digitado = entrada.nextLine();

        if ("sair".equalsIgnoreCase(digitado)) {
            throw new ExitException();
        }

        return digitado;
    }
}
