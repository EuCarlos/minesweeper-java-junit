package br.com.carlos.cm.models;

import org.junit.Test;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;

import br.com.carlos.cm.exceptions.ExplosionException;

public class MinefieldTest {
    private Minefield minefield;

    public MinefieldTest() {}

    @BeforeEach
    public void startMinefield() {
        minefield = new Minefield(3, 3);
    }

    @Test
    public void shouldTestDistance1ForNeighborOnTheLeftSide() {
        Minefield neighbor = new Minefield(3, 2);
        boolean result = minefield.addNeighbor(neighbor);

        assertTrue(result);
    }

    @Test
    public void shouldTestDistance1ForNeighborOnTheRighttSide() {
        Minefield neighbor = new Minefield(3, 4);
        boolean result = minefield.addNeighbor(neighbor);

        assertTrue(result);
    }

    @Test
    public void shouldTestDistance1ForTopNeighbor() {
        Minefield neighbor = new Minefield(2, 3);
        boolean result = minefield.addNeighbor(neighbor);

        assertTrue(result);
    }

    @Test
    public void shouldTestDistance1ForBottomNeighbor() {
        Minefield neighbor = new Minefield(4, 3);
        boolean result = minefield.addNeighbor(neighbor);

        assertTrue(result);
    }

    @Test
    public void shouldTestNeighborWithDistance2() {
        Minefield neighbor = new Minefield(2, 2);
        boolean result = minefield.addNeighbor(neighbor);

        assertTrue(result);
    }

    @Test
    public void shouldTestNotNeighborWithDistance2() {
        Minefield neighbor = new Minefield(1, 1);
        boolean result = minefield.addNeighbor(neighbor);

        assertFalse(result);
    }

    @Test
    public void shouldTestDefaultValueMarkedAttribute() {
        assertFalse(minefield.isMarked());
    }

    @Test
    public void shouldTestToggleMarking() {
        minefield.toggleMarking();
        assertTrue(minefield.isMarked());
    }

    @Test
    public void shouldTestToggleMarkingTwice() {
        minefield.toggleMarking();
        minefield.toggleMarking();

        assertFalse(minefield.isMarked());
    }

    @Test
    public void shouldTestUnminedAndMarkedMinefield() {
        assertTrue(minefield.open());
    }

    @Test
    public void shouldTestMinedAndMakedMinefield() {
        minefield.toggleMarking();
        minefield.undermine();
        assertFalse(minefield.open());
    }

    @Test
    public void shouldTestMinedAndNotMakedMinefield() {
        minefield.undermine();

        assertThrows(ExplosionException.class, () -> {
            minefield.open();
        });
    }

    @Test
    public void shouldTestOpenNeighbor1() {
        Minefield minefield11 = new Minefield(1, 1); // vizinho1

        Minefield minefield22 = new Minefield(2, 2); // vizinhoDoVizinho1
        minefield22.addNeighbor(minefield11);

        minefield.addNeighbor(minefield22);
        minefield.open();

        assertTrue(minefield22.isOpen() && minefield11.isOpen());
    }

    @Test
    void shouldTestOpenNeighbor2() {
        Minefield minefield11 = new Minefield(1, 1);
        Minefield minefield12 = new Minefield(1, 1);
        minefield12.undermine();

        Minefield minefield22 = new Minefield(2, 2);
        minefield22.addNeighbor(minefield11);
        minefield22.addNeighbor(minefield12);

        minefield.addNeighbor(minefield22);
        minefield.open();

        assertTrue(minefield22.isOpen() && minefield11.isClosed());
    }
}
