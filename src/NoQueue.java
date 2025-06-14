public class NoQueue {
    private No dado;
    private NoQueue proximo;

    public NoQueue(No dado) {
        this.dado = dado;
        this.proximo = null;
    }

    public No getDado() {
        return dado;
    }
    public NoQueue getProximo() {
        return proximo;
    }
    public void setProximo(NoQueue proximo) {
        this.proximo = proximo;
    }

}