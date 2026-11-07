package main.model;

public class Carta {
    private Palo palo;
    private int valor;
    private boolean disponible;

    public Carta(Palo palo, int valor, boolean disponible) {
        this.palo = palo;
        this.valor = valor;
        this.disponible = disponible;
    }

    public Palo getPalo() {
        return palo;
    }

    public void setPalo(Palo palo) {
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
        return this.valor + " " + this.palo;
    }
}