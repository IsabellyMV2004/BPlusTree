public class BPlusTree
{
    private No raiz;
    private int n;

    public BPlusTree(int n)
    {
        raiz = null;
        this.n = n;
    }

   public No navegarAteFolha(int info)
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

    private No localizarNo(int info)
    {
        No no = raiz;
        while (no != null)
        {
            int pos = no.procurarPosicao(info);
            if (no.getvLig(0)==null)
            {
                if (pos < no.getTl() && no.getvInfo(pos) == info)
                    return no;
                return null;
            }
            no = no.getvLig(pos);
        }
        return null;
    }

    public No localizarPai(No folha, int info)
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

    public void excluir(int info){
        excluir(raiz,null,info,0);
    }

    public void excluir(No no, No pai, int info, int posArq) {
        int pos,chave;
        No filho, folha;
        if (no != null)
        {
            pos = no.procurarPosicao(info);
            if (no.getvLig(0) != null)
            {
                filho = no.getvLig(pos);
                excluir(filho, no, info, posArq);
                if (filho.getTl() < (int) Math.ceil(n / 2.0) - 1)
                    tratarUnderflow(no, info, pos);
                if (pos > 0 && pos <= no.getTl() && no.getvInfo(pos - 1) == info)
                {
                    folha = navegarAteFolha(info);
                    if (folha.getTl() > 0) {
                        no.setvInfo(pos - 1, folha.getvInfo(0));
                        no.setvPos(pos - 1, folha.getvPos(0));
                    }
                }
            }
            else
            {
                if (pos < no.getTl() && no.getvInfo(pos) == info)
                {
                    chave = no.getvInfo(pos);
                    no.remanejarExclusao(pos);
                    no.setTl(no.getTl() - 1);
                    if (no == raiz && no.getTl() == 0)
                        raiz = null;
                    else
                        if (pos == 0 && no.getTl() > 0 && pai != null)
                            atualizarChavesPai(pai, no.getvInfo(0), chave);
                }
            }
        }
    }

    private void atualizarChavesPai(No no, int novaChave, int chaveAntiga)
    {
        boolean flag = false;
        if (no != null)
        {
            for (int i = 0; i < no.getTl() && !flag; i++)
                if (no.getvInfo(i) == chaveAntiga) {
                    no.setvInfo(i, novaChave);
                    flag = true;
                }
            for (int i = 0; i <= no.getTl(); i++)
                if (no.getvLig(i) != null)
                    atualizarChavesPai(no.getvLig(i), novaChave, chaveAntiga);
        }
    }

    private void tratarUnderflow(No no, int info, int ordem) {
        No pai, irmaoEsquerdo, irmaoDireito;
        boolean flag;
        int pos = -1, chavePai;
        if (no.getTl() < ordem / 2)
        {
            pai = localizarPai(no, info);
            flag = true;
            if (pai == null)
            {
                if (no.getTl() == 0 && no.getvLig(0) != null)
                    raiz = no.getvLig(0);
                flag = false;
            }
            if (flag)
            {
                flag = false;
                for (int i = 0; i <= pai.getTl() && !flag; i++)
                    if (pai.getvLig(i) == no)
                    {
                        pos = i;
                        flag = true;
                    }
                if (flag)
                {
                    flag= false;
                    if (pos > 0 && !flag)
                    {
                        irmaoEsquerdo = pai.getvLig(pos - 1);
                        if (irmaoEsquerdo.getTl() > ordem / 2)
                        {
                            redistribuirEsquerda(irmaoEsquerdo, no, pai, pos - 1);
                            flag = true;
                        }
                    }
                    if (pos < pai.getTl() && !flag)
                    {
                        irmaoDireito = pai.getvLig(pos + 1);
                        if (irmaoDireito.getTl() > ordem / 2)
                        {
                            redistribuirDireita(no, irmaoDireito, pai, pos);
                            flag = true;
                        }
                    }
                    if (!flag)
                    {
                        if (pos > 0)
                        {
                            irmaoEsquerdo = pai.getvLig(pos - 1);
                            mergeEsquerda(irmaoEsquerdo, no, pai, pos - 1);
                        }
                        else
                        {
                            irmaoDireito = pai.getvLig(pos + 1);
                            mergeDireita(no, irmaoDireito, pai, pos);
                        }
                        if (pai.getTl() < (ordem - 1) / 2)
                        {
                            if(pai.getTl() > 0)
                                chavePai = pai.getvInfo(0);
                            else
                                chavePai = info;
                            tratarUnderflow(pai, chavePai, ordem);
                        }
                    }
                }
            }
        }
    }

    private void redistribuirEsquerda(No irmaoEsq, No no, No pai, int posPai)
    {
        no.remanejar(0);
        no.setvInfo(0, pai.getvInfo(posPai));
        no.setvPos(0, pai.getvPos(posPai));
        no.setTl(no.getTl() + 1);
        pai.setvInfo(posPai, irmaoEsq.getvInfo(irmaoEsq.getTl() - 1));
        pai.setvPos(posPai, irmaoEsq.getvPos(irmaoEsq.getTl() - 1));
        if (irmaoEsq.getvLig(0) != null)
        {
            no.setvLig(0, irmaoEsq.getvLig(irmaoEsq.getTl()));
            irmaoEsq.setvLig(irmaoEsq.getTl(), null);
        }
        irmaoEsq.setTl(irmaoEsq.getTl() - 1);
    }

    private void redistribuirDireita(No no, No irmaoDir, No pai, int posPai)
    {
        no.setvInfo(no.getTl(), pai.getvInfo(posPai));
        no.setvPos(no.getTl(), pai.getvPos(posPai));
        no.setTl(no.getTl() + 1);
        pai.setvInfo(posPai, irmaoDir.getvInfo(0));
        pai.setvPos(posPai, irmaoDir.getvPos(0));
        if (irmaoDir.getvLig(0) != null)
        {
            no.setvLig(no.getTl(), irmaoDir.getvLig(0));
            irmaoDir.remanejarExclusao(0);
        }
        else
            irmaoDir.remanejarExclusao(0);
        irmaoDir.setTl(irmaoDir.getTl() - 1);
    }

    private void mergeEsquerda(No irmaoEsq, No no, No pai, int posPai)
    {
        irmaoEsq.setvInfo(irmaoEsq.getTl(), pai.getvInfo(posPai));
        irmaoEsq.setvPos(irmaoEsq.getTl(), pai.getvPos(posPai));
        irmaoEsq.setTl(irmaoEsq.getTl() + 1);
        for (int i = 0; i < no.getTl(); i++)
        {
            irmaoEsq.setvInfo(irmaoEsq.getTl(), no.getvInfo(i));
            irmaoEsq.setvPos(irmaoEsq.getTl(), no.getvPos(i));
            irmaoEsq.setTl(irmaoEsq.getTl() + 1);
        }
        if (no.getvLig(0) != null)
            for (int i = 0; i <= no.getTl(); i++)
                irmaoEsq.setvLig(irmaoEsq.getTl(), no.getvLig(i));
        pai.remanejarExclusao(posPai);
        pai.setTl(pai.getTl() - 1);
        pai.setvLig(posPai + 1, irmaoEsq);
        if (pai == raiz && pai.getTl() == 0)
            raiz = irmaoEsq;
    }

    private void mergeDireita(No no, No irmaoDir, No pai, int posPai)
    {
        no.setvInfo(no.getTl(), pai.getvInfo(posPai));
        no.setvPos(no.getTl(), pai.getvPos(posPai));
        no.setTl(no.getTl() + 1);
        for (int i = 0; i < irmaoDir.getTl(); i++)
        {
            no.setvInfo(no.getTl(), irmaoDir.getvInfo(i));
            no.setvPos(no.getTl(), irmaoDir.getvPos(i));
            no.setTl(no.getTl() + 1);
        }
        if (irmaoDir.getvLig(0) != null)
            for (int i = 0; i <= irmaoDir.getTl(); i++)
                no.setvLig(no.getTl(), irmaoDir.getvLig(i));
        pai.remanejarExclusao(posPai);
        pai.setTl(pai.getTl() - 1);
        pai.setvLig(posPai + 1, no);
        if (pai == raiz && pai.getTl() == 0)
            raiz = no;
    }

    public void exibir() {
        No atual;
        Fila filaNos = new Fila();
        Fila filaNiveis = new Fila();
        int nivelAtual = -1, nivel;

        if (raiz == null)
            System.out.println("Arvore vazia");
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

    public void in_ordem(No raiz) {
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

