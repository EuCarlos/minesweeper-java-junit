
package br.com.carlos.cm.models;

import java.util.ArrayList;
import br.com.carlos.cm.exceptions.ExplosionException;

public class Minefield {
    private final int line;
    private final int column;

    private boolean open = false;
    private boolean undermined = false;
    private boolean marked = false;

    private ArrayList<Minefield> neighbors = new ArrayList<>();

    Minefield(int line, int column) {
        this.line = line;
        this.column = column;
    }

    boolean addNeighbor(Minefield neighbor) {
        boolean lineDifferent = line != neighbor.line;
        boolean columnDifferent = column != neighbor.column;
        boolean diagonal = lineDifferent && columnDifferent;

        int deltaLine = Math.abs(line - neighbor.line);
        int deltaColumn = Math.abs(line - neighbor.line);
        int deltaGeral = deltaColumn + deltaLine;

        if (deltaGeral == 1 && !diagonal) {
            neighbors.add(neighbor);
            return true;
        }

        if (deltaGeral == 2 && diagonal) {
            neighbors.add(neighbor);
            return true;
        }

        return false;
    }

    void toggleMarking() {
        if (!open) marked = !marked;
    }

    boolean open() {
        if (!open && !marked) {
            open = true;

            if (undermined) throw new ExplosionException();
            if (safeNeighborhood()) neighbors.forEach(neighbor -> neighbor.open());

            return true;
        }

        return false;
    }

    boolean safeNeighborhood() {
        return neighbors.stream().noneMatch(neighbor -> neighbor.undermined);
    }

    void undermine() {
        undermined = true;
    }

    public boolean isMined() {
        return undermined;
    }

    public boolean isMarked() {
        return marked;
    }

    void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isClosed() {
        return !isOpen();
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    boolean goalAchieved() {
        boolean unraveled = !undermined && open;
        boolean sheltered = undermined && marked;

        return unraveled || sheltered;
    }

    long minesInTheNeighborhood() {
        return neighbors.stream().filter(neighbor -> neighbor.undermined).count();
    }

    void reset() {
        open = false;
        undermined = false;
        marked = false;
    }

    public String toString() {
        if (marked) return "x";
        if (open && undermined) return "*";
        if (open && minesInTheNeighborhood() > 0) return Long.toString(minesInTheNeighborhood());
        if (open) return " ";
        
        return "?";
    }
}
