package br.com.carlos.cm.models;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import br.com.carlos.cm.exceptions.ExplosionException;

public class CampoTeste {

    private Campo campo;

    public CampoTeste() {
    }

    @BeforeEach
    public void iniciarCampo() {
        campo = new Campo(3, 3);
    }

    @Test
    public void testeVizinhoDistancia1Esquerda() {
        Campo vizinho = new Campo(3, 2);
        boolean resultado = campo.adicionarVizinho(vizinho);

        assertTrue(resultado);
    }

    @Test
    public void testeVizinhoDistancia1Direira() {
        Campo vizinho = new Campo(3, 4);
        boolean resultado = campo.adicionarVizinho(vizinho);

        assertTrue(resultado);
    }

    @Test
    public void testeVizinhoDistancia1Emcima() {
        Campo vizinho = new Campo(2, 3);
        boolean resultado = campo.adicionarVizinho(vizinho);

        assertTrue(resultado);
    }

    @Test
    public void testeVizinhoDistancia1Embaixo() {
        Campo vizinho = new Campo(4, 3);
        boolean resultado = campo.adicionarVizinho(vizinho);

        assertTrue(resultado);
    }

    @Test
    public void testeVizinhoDistancia2() {
        Campo vizinho = new Campo(2, 2);
        boolean resultado = campo.adicionarVizinho(vizinho);

        assertTrue(resultado);
    }

    @Test
    public void testeNaoVizinhoDistancia2() {
        Campo vizinho = new Campo(1, 1);
        boolean resultado = campo.adicionarVizinho(vizinho);

        assertFalse(resultado);
    }

    @Test
    public void testeValorPadraoAtributoMarcado() {
        assertFalse(campo.isMarcado());
    }

    @Test
    public void testeAlternarMarcacao() {
        campo.alteranarMarcacao();
        assertTrue(campo.isMarcado());
    }

    @Test
    public void testeAlternarMarcacaoDuasChamadas() {
        campo.alteranarMarcacao();
        campo.alteranarMarcacao();
        assertFalse(campo.isMarcado());
    }

    @Test
    public void testeAbrirNaoMinadoMarcado() {
        assertTrue(campo.abrir());
    }

    @Test
    public void testeAbrirMinadoMarcado() {
        campo.alteranarMarcacao();
        campo.minar();
        assertFalse(campo.abrir());
    }

    @Test
    public void testeAbrirMinadoNaoMarcado() {
        campo.minar();

        assertThrows(ExplosionException.class, () -> {
            campo.abrir();
        });
    }

    @Test
    public void testeAbrirComVizinho1() {
        Campo campo11 = new Campo(1, 1); // vizinho1

        Campo campo22 = new Campo(2, 2); // vizinhoDoVizinho1
        campo22.adicionarVizinho(campo11);

        campo.adicionarVizinho(campo22);
        campo.abrir();

        assertTrue(campo22.isAberto() && campo11.isAberto());
    }

    @Test
    void testeAbrirComVizinhos2() {
        Campo campo11 = new Campo(1, 1);
        Campo campo12 = new Campo(1, 1);
        campo12.minar();

        Campo campo22 = new Campo(2, 2);
        campo22.adicionarVizinho(campo11);
        campo22.adicionarVizinho(campo12);

        campo.adicionarVizinho(campo22);
        campo.abrir();

        assertTrue(campo22.isAberto() && campo11.isFechado());
    }
}
