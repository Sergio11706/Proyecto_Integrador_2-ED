package main.model;

public class Card {
    private Suit palo;
    private int valor;
    private boolean disponible;

    public Card(Suit palo, int valor, boolean disponible) {
        this.palo = palo;
        this.valor = valor;
        this.disponible = disponible;
    }

    public Suit getPalo() {
        return palo;
    }

    public void setPalo(Suit palo) {
        this.palo = palo;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Card{" +
                "palo=" + palo +
                ", valor=" + valor +
                ", disponible=" + disponible +
                '}';
    }
}