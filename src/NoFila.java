public class NoFila {
    private No dado;
    private NoFila proximo;

    public NoFila(No dado) {
        this.dado = dado;
        this.proximo = null;
    }

    public No getDado() {
        return dado;
    }
    public NoFila getProximo() {
        return proximo;
    }
    public void setProximo(NoFila proximo) {
        this.proximo = proximo;
    }

}