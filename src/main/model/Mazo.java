package main.model;

import main.util.Stack;
import java.util.Random;

public class Mazo {
    private Stack<Carta> cartas;

    public Mazo() {
        this.cartas = new Stack<Carta>(54);
        inicializarYBarajar();
    }

    private void inicializarYBarajar() {
        Carta[] tempCartas = new Carta[54];
        Palo[] palosNormales = {Palo.TREBOL, Palo.CORAZON, Palo.DIAMANTE, Palo.PICA};
        int index = 0;

        for (Palo palo : palosNormales) {
            for (int valor = 1; valor <= 13; valor++) {
                tempCartas[index++] = new Carta(palo, valor, true);
            }
        }

        tempCartas[index++] = new Carta(Palo.COMODIN, 100, true);
        tempCartas[index] = new Carta(Palo.COMODIN, 100, true);

        Random rnd = new Random();
        for (int i = tempCartas.length - 1; i > 0; i--) {
            int j = rnd.nextInt(i + 1);
            Carta temp = tempCartas[i];
            tempCartas[i] = tempCartas[j];
            tempCartas[j] = temp;
        }

        for (Carta c : tempCartas) {
            this.cartas.push(c);
        }
    }

    public Carta repartir() {
        if (!cartas.isEmpty()) {
            return cartas.pop();
        }
        return null;
    }
}