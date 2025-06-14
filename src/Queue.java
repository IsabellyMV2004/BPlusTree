public class Queue {
    private NoQueue inicio, fim;

    public Queue() {
        inicio = fim = null;
    }

    public boolean isEmpty() {
        return inicio == null;
    }

    public void enqueue(No elemento) {
        NoQueue novo = new NoQueue(elemento);
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