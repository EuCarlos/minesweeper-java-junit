package br.com.carlos.cm.models;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import br.com.carlos.cm.exceptions.ExplosionException;

public class CampoTeste {

    private Minefield campo;

    public CampoTeste() {
    }

    @BeforeEach
    public void iniciarCampo() {
        campo = new Minefield(3, 3);
    }

    @Test
    public void testeVizinhoDistancia1Esquerda() {
        Minefield vizinho = new Minefield(3, 2);
        boolean resultado = campo.addNeighbor(vizinho);

        assertTrue(resultado);
    }

    @Test
    public void testeVizinhoDistancia1Direira() {
        Minefield vizinho = new Minefield(3, 4);
        boolean resultado = campo.addNeighbor(vizinho);

        assertTrue(resultado);
    }

    @Test
    public void testeVizinhoDistancia1Emcima() {
        Minefield vizinho = new Minefield(2, 3);
        boolean resultado = campo.addNeighbor(vizinho);

        assertTrue(resultado);
    }

    @Test
    public void testeVizinhoDistancia1Embaixo() {
        Minefield vizinho = new Minefield(4, 3);
        boolean resultado = campo.addNeighbor(vizinho);

        assertTrue(resultado);
    }

    @Test
    public void testeVizinhoDistancia2() {
        Minefield vizinho = new Minefield(2, 2);
        boolean resultado = campo.addNeighbor(vizinho);

        assertTrue(resultado);
    }

    @Test
    public void testeNaoVizinhoDistancia2() {
        Minefield vizinho = new Minefield(1, 1);
        boolean resultado = campo.addNeighbor(vizinho);

        assertFalse(resultado);
    }

    @Test
    public void testeValorPadraoAtributoMarcado() {
        assertFalse(campo.isMarked());
    }

    @Test
    public void testeAlternarMarcacao() {
        campo.toggleMarking();
        assertTrue(campo.isMarked());
    }

    @Test
    public void testeAlternarMarcacaoDuasChamadas() {
        campo.toggleMarking();
        campo.toggleMarking();
        assertFalse(campo.isMarked());
    }

    @Test
    public void testeAbrirNaoMinadoMarcado() {
        assertTrue(campo.open());
    }

    @Test
    public void testeAbrirMinadoMarcado() {
        campo.toggleMarking();
        campo.undermine();
        assertFalse(campo.open());
    }

    @Test
    public void testeAbrirMinadoNaoMarcado() {
        campo.undermine();

        assertThrows(ExplosionException.class, () -> {
            campo.open();
        });
    }

    @Test
    public void testeAbrirComVizinho1() {
        Minefield campo11 = new Minefield(1, 1); // vizinho1

        Minefield campo22 = new Minefield(2, 2); // vizinhoDoVizinho1
        campo22.addNeighbor(campo11);

        campo.addNeighbor(campo22);
        campo.open();

        assertTrue(campo22.isOpen() && campo11.isOpen());
    }

    @Test
    void testeAbrirComVizinhos2() {
        Minefield campo11 = new Minefield(1, 1);
        Minefield campo12 = new Minefield(1, 1);
        campo12.undermine();

        Minefield campo22 = new Minefield(2, 2);
        campo22.addNeighbor(campo11);
        campo22.addNeighbor(campo12);

        campo.addNeighbor(campo22);
        campo.open();

        assertTrue(campo22.isOpen() && campo11.isClosed());
    }
}
