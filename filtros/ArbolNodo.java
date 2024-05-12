package tpe.filtros;

public class ArbolNodo {
    private int valor;
    private ArbolNodo izquierda;
    private ArbolNodo derecha;

    public ArbolNodo(int value) {
        this.valor = value;
        this.izquierda = null;
        this.derecha = null;
    }

    public int getValor() {
        return this.valor;
    }

    public ArbolNodo getDerecha() {
        return this.derecha;
    }

    public ArbolNodo getIzquierda() {
        return this.izquierda;
    }

    public void setIzquierda(ArbolNodo izquierda) {
        this.izquierda = izquierda;
    }

    public void setDerecha(ArbolNodo derecha) {
        this.derecha = derecha;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}