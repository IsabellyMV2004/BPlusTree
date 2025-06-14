/*import java.util.LinkedList;
import java.util.Queue;

public class BPlusTree
{
    private No raiz;
    private int n;

    public BPlusTree(int n)
    {
        raiz = null;
        this.n = n;
    }

   /* private No navegarAteFolha(int info)
    {
        No no = raiz;
        int pos;
        while(no.getvLig(0) != null)
        {
            pos = no.procurarPosicao(info);
            no = no.getvLig(pos);
        }
        return no;
    }

    private No localizarPai(No folha, int info)
    {
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

    private void split(No folha, No pai)
    {
        No cx1 = new No();
        No cx2 = new No();
        int pos;

        for(int i=0; i<No.m; i++)
        {
            cx1.setvInfo(i, folha.getvInfo(i));
            cx1.setvPos(i, folha.getvPos(i));
            cx1.setvLig(i, folha.getvLig(i));
        }
        cx1.setvLig(No.m, folha.getvLig(No.m));
        cx1.setTl(No.m);

        for(int i=No.m+1; i<2*No.m+1; i++)
        {
            cx2.setvInfo(i-(No.m+1), folha.getvInfo(i));
            cx2.setvPos(i-(No.m+1), folha.getvPos(i));
            cx2.setvLig(i-(No.m+1), folha.getvLig(i));
        }
        cx2.setvLig(No.m, folha.getvLig(2*No.m+1));
        cx2.setTl(No.m);

        if(folha == pai)
        {
            folha.setvInfo(0, folha.getvInfo(No.m));
            folha.setvPos(0, folha.getvPos(No.m));
            folha.setTl(1);
            folha.setvLig(0, cx1);
            folha.setvLig(1, cx2);
        }
        else
        {
            pos = pai.procurarPosicao(folha.getvInfo(No.m));
            pai.remanejar(pos);
            pai.setvInfo(pos, folha.getvInfo(No.m));
            pai.setvPos(pos, folha.getvPos(No.m));
            pai.setTl(pai.getTl()+1);
            pai.setvLig(pos, cx1);
            pai.setvLig(pos+1, cx2);
            if(pai.getTl() > 2*No.m)
            {
                folha = pai;
                pai = localizarPai(folha, folha.getvInfo(0));
                split(folha, pai);
            }
        }
    }

    public void inserir(int info, int posArq)
    {
        No folha, pai;
        int pos;
        if(raiz == null)
            raiz = new No(info, posArq);
        else
        {
            folha = navegarAteFolha(info);
            pos = folha.procurarPosicao(info);
            folha.remanejar(pos);
            folha.setvInfo(pos, info);
            folha.setvPos(pos, posArq);
            folha.setTl(folha.getTl() + 1);
            if (folha.getTl() > No.m*2)
            {
                pai = localizarPai(folha, info);
                split(folha, pai);
            }
        }
    }

    public void in_ordem()
    {
        in_ordem(raiz);
    }

    private void in_ordem(No raiz)
    {
        if(raiz!=null)
        {
            for(int i=0; i<raiz.getTl(); i++)
            {
                in_ordem(raiz.getvLig(i));
                System.out.println(raiz.getvInfo(i));
            }
            in_ordem(raiz.getvLig(raiz.getTl()));
        }
    }

    //exclusao
    private No buscarNo(int info)
    {
        No no=raiz;
        int pos;
        boolean flag=false;
        while(no!=null && !flag)
        {
            pos = no.procurarPosicao(info);
            if(pos<no.getTl() && info == no.getvInfo(pos))
                flag=true;
            else
                no=no.getvLig(pos);
        }
        return no;
    }

    private No localizarSubE(No no, int pos)
    {
        no = no.getvLig(pos);
        while(no.getvLig(0)!=null)
            no=no.getvLig(no.getTl());
        return no;
    }

    private No localizarSubD(No no, int pos)
    {
        no = no.getvLig(pos);
        while(no.getvLig(0)!=null)
            no=no.getvLig(0);
        return no;
    }

    private void redistribuir_concatenar(No folha)
    {
        int posPai;
        No pai = localizarPai(folha, folha.getvInfo(0));
        No irmaE, irmaD;
        posPai = pai.procurarPosicao(folha.getvInfo(0));
        if(posPai>0)
            irmaE = pai.getvLig(posPai-1);
        else
            irmaE = null;
        if(posPai<pai.getTl())
            irmaD = pai.getvLig(posPai+1);
        else
            irmaD = null;

        //redistribuicao com a irmaE
        if(irmaE!=null && irmaE.getTl()>No.m)
        {
            folha.remanejar(0);
            folha.setvInfo(0, pai.getvInfo(posPai-1));
            folha.setvPos(0, pai.getvPos(posPai-1));
            folha.setTl(folha.getTl()+1);
            pai.setvInfo(posPai-1, irmaE.getvInfo(irmaE.getTl()-1));
            pai.setvPos(posPai-1, irmaE.getvPos(irmaE.getTl()-1));
            folha.setvLig(0, irmaE.getvLig(irmaE.getTl()));
            irmaE.setTl(irmaE.getTl()-1);
        }
        else
        //redistribuicao com a irmaD
        if(irmaD!=null && irmaD.getTl()>No.m)
        {

        }
        else
        {
            //concatenacao com a irmaE
            if(irmaE!=null)
            {
                irmaE.setvInfo(irmaE.getTl(), pai.getvInfo(posPai-1));
                irmaE.setvPos(irmaE.getTl(), pai.getvPos(posPai-1));
                irmaE.setTl(irmaE.getTl()+1);
                pai.remanejarExclusao(posPai-1);
                pai.setTl(pai.getTl()-1);
                for(int i=0; i<folha.getTl(); i++)
                {
                    irmaE.setvInfo(irmaE.getTl(), folha.getvInfo(i));
                    irmaE.setvPos(irmaE.getTl(), folha.getvPos(i));
                    irmaE.setvLig(irmaE.getTl(), folha.getvLig(i));
                    irmaE.setTl(irmaE.getTl()+1);
                }
                irmaE.setvLig(irmaE.getTl(), folha.getvLig(folha.getTl()));
                pai.setvLig(posPai-1, irmaE);
            }
            else
            //conctecancao com a irmaD
            {

            }

            if(pai==raiz && pai.getTl()==0)
            {
                if(irmaE!=null)
                    raiz=irmaE;
                else
                    raiz=irmaD;
            }
            else
            if(pai!=raiz && pai.getTl()<No.m)
            {
                folha = pai;
                redistribuir_concatenar(folha);
            }
        }
    }

    public void exclusao(int info)
    {
        int pos;
        No subE, subD, folha;
        No no = buscarNo(info);
        if(no != null) //achou a info
        {
            pos = no.procurarPosicao(info);
            if(no.getvLig(0)!=null) //não eh folha
            {
                subE = localizarSubE(no, pos);
                subD = localizarSubD(no, pos+1);
                if(subE.getTl()>No.m || subD.getTl()==No.m)
                {
                    no.setvInfo(pos, subE.getvInfo(subE.getTl()-1));
                    no.setvPos(pos, subE.getvPos(subE.getTl()-1));
                    folha = subE;
                    pos= subE.getTl()-1;
                }
                else
                {
                    no.setvInfo(pos, subD.getvInfo(0));
                    no.setvPos(pos, subD.getvPos(0));
                    folha = subD;
                    pos = 0;
                }
            }
            else
                folha = no;

            //exclui da folha
            folha.remanejarExclusao(pos);
            folha.setTl(folha.getTl()-1);

            if(folha==raiz && raiz.getTl()==0)
                raiz=null;
            else
            if(folha!=raiz && folha.getTl()<No.m)
                redistribuir_concatenar(folha);
        }
    }

    private No navegarAteFolha(int info)
    {
        No no = raiz;
        int pos;
        while(no.getvLig(0) != null)
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

    /*public void split(No folha, No pai)
    {
        No avo;
        No cx1 = new No(n);
        No cx2 = new No(n);
        int i, j, pos, m, promovido;
        boolean ehFolha;

        if (folha.getvLig(0) == null) {
            m = (n + 1) / 2;
            ehFolha = true;
        }
        else {
            m = n / 2;
            ehFolha = false;
        }

        for (i = 0; i < m; i++)
        {
            cx1.setvInfo(i, folha.getvInfo(i));
            cx1.setvPos(i, folha.getvPos(i));
            cx1.setTl(cx1.getTl() + 1);
            if (!ehFolha)
                cx1.setvLig(i, folha.getvLig(i));
        }
        if (!ehFolha)
            cx1.setvLig(i, folha.getvLig(i));

        for (j = 0; i < folha.getTl(); i++, j++)
        {
            cx2.setvInfo(j, folha.getvInfo(i));
            cx2.setvPos(j, folha.getvPos(i));
            cx2.setTl(cx2.getTl() + 1);
            if (!ehFolha)
                cx2.setvLig(j, folha.getvLig(i));
        }
        if (!ehFolha)
            cx2.setvLig(j, folha.getvLig(i));

        promovido = cx2.getvInfo(0);
        if (folha == raiz)
        {
            raiz = new No(n);
            raiz.setvInfo(0, promovido);
            raiz.setvPos(0, cx2.getvPos(0));
            raiz.setvLig(0, cx1);
            raiz.setvLig(1, cx2);
            raiz.setTl(1);
            if (!ehFolha)
                cx2.remanejarExclusao(0);
        }
        else{
            pos = pai.procurarPosicao(promovido);
            pai.remanejar(pos);
            pai.setvInfo(pos, promovido);
            pai.setvPos(pos, cx2.getvPos(0));
            pai.setvLig(pos, cx1);
            pai.setvLig(pos + 1, cx2);
            pai.setTl(pai.getTl() + 1);
            if (!ehFolha)
                cx2.remanejarExclusao(0);
            if (pai.getTl() > n - 1)
            {
                avo = localizarPai(pai, pai.getvInfo(0));
                split(pai, avo);
            }
        }
    }

    public void split(No folha, No pai) {
        No avo;
        No cx1 = new No(n);
        No cx2 = new No(n);
        int i, j, pos, m, promovido;
        boolean ehFolha;

        if (folha.getvLig(0) == null) {
            m = (n + 1) / 2;
            ehFolha = true;
        } else {
            m = n / 2;
            ehFolha = false;
        }

        for (i = 0; i < m; i++) {
            cx1.setvInfo(i, folha.getvInfo(i));
            cx1.setvPos(i, folha.getvPos(i));
            cx1.setTl(cx1.getTl() + 1);
            if (!ehFolha)
                cx1.setvLig(i, folha.getvLig(i));
        }
        if (!ehFolha)
            cx1.setvLig(i, folha.getvLig(i));

        for (j = 0; i < folha.getTl(); i++, j++) {
            cx2.setvInfo(j, folha.getvInfo(i));
            cx2.setvPos(j, folha.getvPos(i));
            cx2.setTl(cx2.getTl() + 1);
            if (!ehFolha)
                cx2.setvLig(j, folha.getvLig(i));
        }
        if (!ehFolha)
            cx2.setvLig(j, folha.getvLig(i));

        if (ehFolha) {
            // encadeamento das folhas
            cx1.setvLig(n, cx2);
            cx2.setvLig(n, folha.getvLig(n));
        }

        promovido = cx2.getvInfo(0); // chave de separação
        if (folha == raiz) {
            raiz = new No(n);
            raiz.setvInfo(0, promovido);
            raiz.setvPos(0, cx2.getvPos(0));
            raiz.setvLig(0, cx1);
            raiz.setvLig(1, cx2);
            raiz.setTl(1);
        } else {
            pos = pai.procurarPosicao(promovido);
            pai.remanejar(pos);
            pai.setvInfo(pos, promovido);
            pai.setvPos(pos, cx2.getvPos(0));
            pai.setvLig(pos, cx1);
            pai.setvLig(pos + 1, cx2);
            pai.setTl(pai.getTl() + 1);

            if (pai.getTl() > n - 1) {
                avo = localizarPai(pai, pai.getvInfo(0));
                split(pai, avo);
            }
        }
    }


    public void inserir(int info, int posArq){

        No folha, pai;
        int pos;

        if(raiz == null)
            raiz = new No(n,info,posArq);
        else{
            folha = navegarAteFolha(info);
            pos = folha.procurarPosicao(info);
            folha.remanejar(pos);
            folha.setvInfo(pos, info);
            folha.setvPos(pos, posArq);
            folha.setTl(folha.getTl() + 1);
            if (folha.getTl() > n-1)
            {
                pai = localizarPai(folha, info);
                split(folha, pai);
            }
        }
    }

    private No buscarNo(int info)
    {
        No no=raiz;
        int pos;
        boolean flag=false;
        while(no!=null && !flag)
        {
            pos = no.procurarPosicao(info);
            if(pos<no.getTl() && info == no.getvInfo(pos))
                flag=true;
            else
                no=no.getvLig(pos);
        }
        return no;
    }

    /*public void redistribuir_concatenar(No filhoE, No pai, No filhoD, int pos){

        if(filhoD.getTl() < (n/2-1) && filhoE.getTl() < (n/2-1)){   // concatenar

        }
        if(filhoD.getTl()<(n/2)-1)
        {
            // insere no pai
            pai.setvInfo(pos, filhoE.getvInfo(filhoE.getTl()-1));
            pai.setvPos(pos, filhoE.getvPos(filhoE.getTl()-1));
            // insere no filho da direita
            filhoD.remanejar(0);
            filhoD.setvInfo(0, filhoE.getvInfo(filhoE.getTl()-1));
            filhoD.setvPos(0, filhoE.getvPos(filhoE.getTl()-1));
            filhoD.setTl(filhoD.getTl() + 1);
            // remove do filho da esquerda
            filhoE.setTl(filhoE.getTl()-1);
        }
        else
        {
            filhoE.setvInfo(filhoE.getTl(), pai.getvInfo(0));
            filhoD.remanejarExclusao(0);
            pai.setvInfo(0,filhoD.getvInfo(0));
        }
    }

    public void exclusao(int info)
    {
        int pos;
        No filhoE, filhoD, folha;
        No no = buscarNo(info), filho;
        if(no != null) //achou a info
        {
            pos = no.procurarPosicao(info);
            if(no.getvLig(0)!=null) //não eh folha
            {
                filhoE = no.getvLig(pos);
                filhoD = no.getvLig(pos+1);
                if(filhoD.getvInfo(0)==info){ // é o primeiro elemento do filho da direita
                    filhoD.remanejarExclusao(0);
                    filhoD.setTl(filhoD.getTl()-1);
                }
                if(filhoD.getTl()>(n/2)-1)
                    no.setvInfo(pos, filhoD.getvInfo(0));
                else{
                    redistribuir_concatenar(filhoE,no,filhoD,pos);
                }
            }
            else {
                if(pos<no.getTl()-1) {
                    no.remanejarExclusao(pos);
                    no.setTl(no.getTl()-1);
                }
                filhoE = no;
                no = localizarPai(filhoE,info);
                pos = no.procurarPosicao(info);
                filhoD = no.getvLig(pos+1);
                redistribuir_concatenar(filhoE,no,filhoD,pos);

            }
        }
    }

    public void excluir(int valor) {
        if (raiz == null) return;

        No folha = navegarAteFolha(valor);
        int pos = folha.procurarPosicao(valor);

        if (pos >= folha.getTl() || folha.getvInfo(pos) != valor) {
            // Valor não está presente
            return;
        }

        // 1. Remove valor da folha
        folha.remanejarExclusao(pos);
        folha.setTl(folha.getTl() - 1);

        // 2. Se é a raiz e está vazia, elimina a raiz
        if (folha == raiz && folha.getTl() == 0) {
            raiz = null;
            return;
        }

        // 3. Verifica se a folha ficou com menos do que o mínimo necessário
        if (folha != raiz && folha.getTl() < Math.ceil(n / 2.0)) {
            No pai = localizarPai(folha, valor);
            int posPai = -1;

            for (int i = 0; i <= pai.getTl(); i++) {
                if (pai.getvLig(i) == folha) {
                    posPai = i;
                    break;
                }
            }

            // Irmãos
            No irmaoEsq = (posPai > 0) ? pai.getvLig(posPai - 1) : null;
            No irmaoDir = (posPai < pai.getTl()) ? pai.getvLig(posPai + 1) : null;

            // 3.1 Tenta redistribuir com irmão esquerdo
            if (irmaoEsq != null && irmaoEsq.getTl() > Math.ceil(n / 2.0)) {
                // Desloca elementos para frente
                folha.remanejar(0);
                folha.setvInfo(0, irmaoEsq.getvInfo(irmaoEsq.getTl() - 1));
                folha.setTl(folha.getTl() + 1);
                irmaoEsq.setTl(irmaoEsq.getTl() - 1);
                pai.setvInfo(posPai - 1, folha.getvInfo(0));
            }

            // 3.2 Tenta redistribuir com irmão direito
            else if (irmaoDir != null && irmaoDir.getTl() > Math.ceil(n / 2.0)) {
                folha.setvInfo(folha.getTl(), irmaoDir.getvInfo(0));
                folha.setTl(folha.getTl() + 1);
                irmaoDir.remanejarExclusao(0);
                irmaoDir.setTl(irmaoDir.getTl() - 1);
                pai.setvInfo(posPai, irmaoDir.getvInfo(0));
            }

            // 3.3 Se não for possível redistribuir, concatena com o irmão
            else if (irmaoEsq != null) {
                // Concatena folha na esquerda
                for (int i = 0; i < folha.getTl(); i++) {
                    irmaoEsq.setvInfo(irmaoEsq.getTl(), folha.getvInfo(i));
                    irmaoEsq.setTl(irmaoEsq.getTl() + 1);
                }

                // Remove referência no pai
                pai.remanejarExclusao(posPai - 1);
                for (int i = posPai; i < pai.getTl(); i++) {
                    pai.setvLig(i, pai.getvLig(i + 1));
                }
                pai.setTl(pai.getTl() - 1);

                // Ajusta raiz se pai ficar vazio
                if (pai == raiz && pai.getTl() == 0) {
                    raiz = irmaoEsq;
                }

            } else if (irmaoDir != null) {
                // Concatena irmão direito na folha
                for (int i = 0; i < irmaoDir.getTl(); i++) {
                    folha.setvInfo(folha.getTl(), irmaoDir.getvInfo(i));
                    folha.setTl(folha.getTl() + 1);
                }

                // Remove referência no pai
                pai.remanejarExclusao(posPai);
                for (int i = posPai + 1; i <= pai.getTl(); i++) {
                    pai.setvLig(i, pai.getvLig(i + 1));
                }
                pai.setTl(pai.getTl() - 1);

                if (pai == raiz && pai.getTl() == 0) {
                    raiz = folha;
                }
            }
        }
    }


    public void exibir() {
        if (raiz == null) {
            System.out.println("(Árvore B+ vazia)");
            return;
        }

        Queue<No> fila = new LinkedList<>();
        Queue<Integer> niveis = new LinkedList<>();

        fila.add(raiz);
        niveis.add(0);

        int nivelAtual = -1;

        while (!fila.isEmpty()) {
            No atual = fila.poll();
            int nivel = niveis.poll();

            if (nivel != nivelAtual) {
                nivelAtual = nivel;
                System.out.println(); // quebra de linha para novo nível
                System.out.print("Nível " + nivel + ": ");
            }

            // Verifica se é folha
            boolean ehFolha = (atual.getvLig(0) == null);
            if (ehFolha)
                System.out.print("FOLHA ");
            else
                System.out.print("INTERNO ");

            // Imprime os valores do nó
            System.out.print("[");
            for (int i = 0; i < atual.getTl(); i++) {
                System.out.print(atual.getvInfo(i));
                if (i < atual.getTl() - 1)
                    System.out.print("|");
            }
            System.out.print("]  ");

            // Adiciona os filhos na fila se não for folha
            if (!ehFolha) {
                for (int i = 0; i <= atual.getTl(); i++) {
                    if (atual.getvLig(i) != null) {
                        fila.add(atual.getvLig(i));
                        niveis.add(nivel + 1);
                    }
                }
            }
        }

        System.out.println(); // última quebra de linha
    }



    public void in_ordem()
    {
        in_ordem(raiz);
    }

    private void in_ordem(No raiz)
    {

    }


}*/


//import java.util.LinkedList;
//import java.util.Queue;

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
        if (folha == null || folha == raiz)
            return null;

        No no = raiz, pai = null;
        int pos;
        while (no != null && no.getvLig(0) != null)
        {
            pos = no.procurarPosicao(info);
            if (no.getvLig(pos) == folha)
                return no;
            pai = no;
            no = no.getvLig(pos);
        }
        return null;
    }

    public void split(No no, No pai)
    {
        No aux1 = new No(n), aux2 = new No(n), avo;
        int meio, promovido, pos, j = 0;

        if (no.getvLig(0) == null)
        {
            meio = (int) Math.ceil((n - 1) / 2.0);
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
            meio = (int) Math.ceil((n/2.0)-1);
            promovido = no.getvInfo(meio);
            for (int i = 0; i < meio; i++) {
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
        if (raiz == null)
            raiz = new No(n, info, posArq);
        else {
            No folha = navegarAteFolha(info);
            int pos = folha.procurarPosicao(info);
            folha.remanejar(pos);
            folha.setvInfo(pos, info);
            folha.setvPos(pos, posArq);
            folha.setTl(folha.getTl() + 1);
            if (folha.getTl() > n - 1) {
                No pai = localizarPai(folha, info);
                split(folha, pai);
            }
        }
    }
    private void removerPai(No pai, int pos) {
        // Remove chave e ligação no pai
        for (int j = pos; j < pai.getTl() - 1; j++) {
            pai.setvInfo(j, pai.getvInfo(j + 1));
            pai.setvLig(j + 1, pai.getvLig(j + 2));
        }
        pai.setTl(pai.getTl() - 1);
        pai.setvInfo(pai.getTl(), 0);
        pai.setvLig(pai.getTl() + 1, null);
    }

    private void balancearFilhoInterno(No pai, int pos) {
        No filho = pai.getvLig(pos);
        int min = (int) Math.ceil((n - 1) / 2.0);
        No irmaoE = pos > 0 ? pai.getvLig(pos - 1) : null;
        No irmaoD = pos < pai.getTl() ? pai.getvLig(pos + 1) : null;

        // Redistribui com irmão esquerdo
        if (irmaoE != null && irmaoE.getTl() > min) {
            for (int j = filho.getTl(); j > 0; j--) {
                filho.setvInfo(j, filho.getvInfo(j - 1));
                filho.setvLig(j + 1, filho.getvLig(j));
            }
            filho.setvInfo(0, pai.getvInfo(pos - 1));
            filho.setvLig(1, filho.getvLig(0));
            filho.setvLig(0, irmaoE.getvLig(irmaoE.getTl()));
            pai.setvInfo(pos - 1, irmaoE.getvInfo(irmaoE.getTl() - 1));
            irmaoE.setTl(irmaoE.getTl() - 1);
            filho.setTl(filho.getTl() + 1);
        }

        // Redistribui com irmão direito
        else if (irmaoD != null && irmaoD.getTl() > min) {
            filho.setvInfo(filho.getTl(), pai.getvInfo(pos));
            filho.setvLig(filho.getTl() + 1, irmaoD.getvLig(0));
            pai.setvInfo(pos, irmaoD.getvInfo(0));
            for (int j = 0; j < irmaoD.getTl() - 1; j++) {
                irmaoD.setvInfo(j, irmaoD.getvInfo(j + 1));
                irmaoD.setvLig(j, irmaoD.getvLig(j + 1));
            }
            irmaoD.setvLig(irmaoD.getTl() - 1, irmaoD.getvLig(irmaoD.getTl()));
            irmaoD.setTl(irmaoD.getTl() - 1);
            filho.setTl(filho.getTl() + 1);
        }

        // Concatena com irmão esquerdo
        else if (irmaoE != null) {
            int tlIrmao = irmaoE.getTl();
            irmaoE.setvInfo(tlIrmao, pai.getvInfo(pos - 1));
            irmaoE.setTl(tlIrmao + 1);
            for (int j = 0; j < filho.getTl(); j++) {
                irmaoE.setvInfo(tlIrmao + 1 + j, filho.getvInfo(j));
            }
            for (int j = 0; j <= filho.getTl(); j++) {
                irmaoE.setvLig(tlIrmao + 1 + j, filho.getvLig(j));
            }
            irmaoE.setTl(irmaoE.getTl() + filho.getTl());
            removerPai(pai, pos - 1);
        }

        // Concatena com irmão direito
        else if (irmaoD != null) {
            filho.setvInfo(filho.getTl(), pai.getvInfo(pos));
            filho.setTl(filho.getTl() + 1);
            for (int j = 0; j < irmaoD.getTl(); j++) {
                filho.setvInfo(filho.getTl() + j, irmaoD.getvInfo(j));
            }
            for (int j = 0; j <= irmaoD.getTl(); j++) {
                filho.setvLig(filho.getTl() + j, irmaoD.getvLig(j));
            }
            filho.setTl(filho.getTl() + irmaoD.getTl());
            removerPai(pai, pos);
        }
    }

    public void excluir(int info) {

    }

    public void exibir() {
        No atual;
        Queue filaNos = new Queue();
        Queue filaNiveis = new Queue();
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

    public No getRaiz() {
        return raiz;
    }

    public void setRaiz(No raiz) {
        this.raiz = raiz;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}

