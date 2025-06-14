public class Fila {
    private NoFila inicio, fim;

    public Fila() {
        inicio = fim = null;
    }

    public boolean isEmpty() {
        return inicio == null;
    }

    public void enqueue(No elemento) {
        NoFila novo = new NoFila(elemento);
        if (isEmpty()) {
            inicio = fim = novo;
        } else {
            fim.setProximo(novo);
            fim = novo;
        }
    }

    public No dequeue() {
        if (isEmpty())
            return null;
        No dado = inicio.getDado();
        inicio = inicio.getProximo();
        if (inicio == null)
            fim = null;
        return dado;
    }
}