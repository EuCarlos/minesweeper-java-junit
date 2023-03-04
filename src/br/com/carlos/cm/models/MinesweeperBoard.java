package br.com.carlos.cm.models;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import br.com.carlos.cm.exceptions.ExplosionException;

public class MinesweeperBoard {
    private int lines;
    private int columns;
    private int mines;

    private final List<Minefield> minefields = new ArrayList<>();

    public MinesweeperBoard(int lines, int columns, int mines) {
        this.lines = lines;
        this.columns = columns;
        this.mines = mines;

        generateMinefields();
        associateNeighbors();
        sortMines();
    }

    public void open(int line, int column) {
        try {
            minefields
                .parallelStream()
                    .filter(c -> c.getLine() == line && c.getColumn() == column)
                        .findFirst()
                            .ifPresent(c -> c.open());
        } catch (ExplosionException e) {
            minefields.forEach(c -> c.setOpen(true));
            throw e;
        }
    }

    public void toggleMarking(int line, int column) {
        minefields
            .parallelStream()
                .filter(c -> c.getLine() == line && c.getColumn() == column)
                    .findFirst()
                        .ifPresent(c -> c.toggleMarking());
    }

    private void generateMinefields() {
        for (int line = 0; line < lines; line++) {
            for (int column = 0; column < columns; column++) {
                minefields.add(new Minefield(line, column));
            }
        }
    }

    private void associateNeighbors() {
        for (Minefield c1 : minefields) {
            for (Minefield c2 : minefields) {
                c1.addNeighbor(c2);
            }
        }
    }

    private void sortMines() {
        long armedMines = 0;
        Predicate<Minefield> undermined = c -> c.isMined();

        do {
            int random = (int) (Math.random() * minefields.size());
            minefields.get(random).undermine();
            armedMines = minefields.stream().filter(undermined).count();
        } while (armedMines < mines);
    }

    public boolean goalAchieved() {
        return minefields.stream().allMatch(c -> c.goalAchieved());
    }

    public void reset() {
        minefields.stream().forEach(c -> c.reset());
        sortMines();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("  ");

        for (int c = 0; c < columns; c++) {
            sb.append(" ");
            sb.append(c);
            sb.append(" ");
        }

        sb.append("\n");

        int i = 0;
        
        for (int l = 0; l < lines; l++) {
            sb.append(l);
            sb.append(" ");
            for (int c = 0; c < columns; c++) {
                sb.append(" ");
                sb.append(minefields.get(i));
                sb.append(" ");
                i++;
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
