public class BPlusTree
{
    private No raiz;
    private int n;

    public BPlusTree(int n)
    {
        raiz = null;
        this.n = n;
    }

    private No navegarAteFolha(int info)
    {
        No no = raiz;
        int pos;
        while (no.getvLig(0) != null)
        {
            pos = no.procurarPosicao(info);
            no = no.getvLig(pos);
        }
        return no;
    }

    private No localizarPai(No folha, int info)
    {
        if(folha==null)
            return null;

        No no = raiz, pai = raiz;
        int pos;
        while(no != folha)
        {
            pai = no;
            pos = no.procurarPosicao(info);
            no = no.getvLig(pos);
        }
        return pai;
    }

    public void split(No no, No pai)
    {
        No aux1 = new No(n), aux2 = new No(n), avo;
        int meio, promovido, pos, j = 0;

        if (no.getvLig(0) == null)
        {
            meio = (int) Math.ceil((n - 1) / 2.0); // arredonda pra cima
            for (int i = 0; i < meio; i++)
            {
                aux1.setvInfo(i, no.getvInfo(i));
                aux1.setvPos(i, no.getvPos(i));
            }
            aux1.setTl(meio);
            for (int i = meio; i < no.getTl(); i++, j++)
            {
                aux2.setvInfo(j, no.getvInfo(i));
                aux2.setvPos(j, no.getvPos(i));
            }
            aux2.setTl(no.getTl() - meio);
            aux2.setvLig(n, no.getvLig(n));
            aux1.setvLig(n, aux2);
            promovido = aux2.getvInfo(0);

            if (no == raiz || pai == null)
            {
                raiz = new No(n);
                raiz.setvInfo(0, promovido);
                raiz.setvLig(0, aux1);
                raiz.setvLig(1, aux2);
                raiz.setTl(1);
            }
            else
            {
                pos = pai.procurarPosicao(promovido);
                pai.remanejar(pos);
                pai.setvInfo(pos, promovido);
                pai.setvLig(pos, aux1);
                pai.setvLig(pos + 1, aux2);
                pai.setTl(pai.getTl() + 1);
                if (pai.getTl() > n - 1)
                {
                    avo = localizarPai(pai, pai.getvInfo(0));
                    split(pai, avo);
                }
            }
        }
        else
        {
            meio = (int) Math.ceil((n/2.0)-1);  // arredonda pra cima
            promovido = no.getvInfo(meio);
            for (int i = 0; i < meio; i++)
            {
                aux1.setvInfo(i, no.getvInfo(i));
                aux1.setvLig(i, no.getvLig(i));
            }
            aux1.setvLig(meio, no.getvLig(meio));
            aux1.setTl(meio);

            for (int i = meio + 1; i < no.getTl(); i++, j++)
            {
                aux2.setvInfo(j, no.getvInfo(i));
                aux2.setvLig(j, no.getvLig(i));
            }
            aux2.setvLig(j, no.getvLig(no.getTl()));
            aux2.setTl(no.getTl() - meio - 1);

            if (no == raiz || pai == null)
            {
                raiz = new No(n);
                raiz.setvInfo(0, promovido);
                raiz.setvLig(0, aux1);
                raiz.setvLig(1, aux2);
                raiz.setTl(1);
            }
            else
            {
                pos = pai.procurarPosicao(promovido);
                pai.remanejar(pos);
                pai.setvInfo(pos, promovido);
                pai.setvLig(pos, aux1);
                pai.setvLig(pos + 1, aux2);
                pai.setTl(pai.getTl() + 1);
                if (pai.getTl() > n - 1)
                {
                    avo = localizarPai(pai, pai.getvInfo(0));
                    split(pai, avo);
                }
            }
        }
    }

    public void inserir(int info, int posArq) {
        No folha, pai;
        int pos;
        if (raiz == null)
            raiz = new No(n, info, posArq);
        else {
            folha = navegarAteFolha(info);
            pos = folha.procurarPosicao(info);
            folha.remanejar(pos);
            folha.setvInfo(pos, info);
            folha.setvPos(pos, posArq);
            folha.setTl(folha.getTl() + 1);
            if (folha.getTl() > n - 1)
            {
                pai = localizarPai(folha, info);
                split(folha, pai);
            }
        }
    }

    public void excluir(int info) {

    }

    public void exibir() {
        No atual;
        Fila filaNos = new Fila();
        Fila filaNiveis = new Fila();
        int nivelAtual = -1, nivel;

        if (raiz == null)
            System.out.println("Árvore vazia.");
        else
        {
            filaNos.enqueue(raiz);
            filaNiveis.enqueue(new No(n, 0, 0));
            while (!filaNos.isEmpty())
            {
                atual = filaNos.dequeue();
                nivel = filaNiveis.dequeue().getvInfo(0);
                if (nivel != nivelAtual)
                {
                    nivelAtual = nivel;
                    System.out.println();
                    System.out.print("Nível " + nivel + ": ");
                }
                System.out.print("[");
                for (int i = 0; i < atual.getTl(); i++)
                {
                    System.out.print(atual.getvInfo(i));
                    if (i < atual.getTl() - 1)
                        System.out.print("|");
                }
                System.out.print("]  ");
                if (atual.getvLig(0) != null)
                    for (int i = 0; i <= atual.getTl(); i++)
                        if (atual.getvLig(i) != null)
                        {
                            filaNos.enqueue(atual.getvLig(i));
                            filaNiveis.enqueue(new No(n, nivel + 1, 0));
                        }
            }
            System.out.println();
        }
    }

    public void in_ordem()
    {
        in_ordem(raiz);
        System.out.println();
    }

    private void in_ordem(No raiz) {
        if (raiz != null)
        {
            if (raiz.getvLig(0) == null)
                for (int i = 0; i < raiz.getTl(); i++)
                    System.out.print(raiz.getvInfo(i) + " ");
            else
            {
                for (int i = 0; i < raiz.getTl(); i++)
                {
                    in_ordem(raiz.getvLig(i));
                    System.out.print(raiz.getvInfo(i) + " ");
                }
                in_ordem(raiz.getvLig(raiz.getTl()));
            }
        }
    }
}

