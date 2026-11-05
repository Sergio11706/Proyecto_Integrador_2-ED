package main.model;

import main.util.Stack;
import java.util.Random;

public class Deck {
    private Stack<Card> cartas;

    public Deck() {
        this.cartas = new Stack<Card>(54);
        inicializarYBarajar();
    }

    private void inicializarYBarajar() {
        Card[] tempCartas = new Card[54];
        Suit[] palosNormales = {Suit.TREBOL, Suit.CORAZON, Suit.DIAMANTE, Suit.PICA};
        int index = 0;

        for (Suit palo : palosNormales) {
            for (int valor = 1; valor <= 13; valor++) {
                tempCartas[index++] = new Card(palo, valor, true);
            }
        }

        tempCartas[index++] = new Card(Suit.COMODIN, 100, true);
        tempCartas[index] = new Card(Suit.COMODIN, 100, true);

        Random rnd = new Random();
        for (int i = tempCartas.length - 1; i > 0; i--) {
            int j = rnd.nextInt(i + 1);
            Card temp = tempCartas[i];
            tempCartas[i] = tempCartas[j];
            tempCartas[j] = temp;
        }

        for (Card c : tempCartas) {
            this.cartas.push(c);
        }
    }

    public Card repartir() {
        if (!cartas.isEmpty()) {
            return cartas.pop();
        }
        return null;
    }
}