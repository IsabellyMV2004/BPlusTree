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
    }*/

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

    public void split(No folha, No pai)
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
    }*/

    private void concatenar(No filhoE, No pai, No filhoD, int pos)
    {
        // Move valor do pai entre os dois para o filhoE
        filhoE.setvInfo(filhoE.getTl(), pai.getvInfo(pos));
        filhoE.setvPos(filhoE.getTl(), pai.getvPos(pos));
        filhoE.setTl(filhoE.getTl() + 1);

        for (int i = 0; i < filhoD.getTl(); i++)
        {
            filhoE.setvInfo(filhoE.getTl(), filhoD.getvInfo(i));
            filhoE.setvPos(filhoE.getTl(), filhoD.getvPos(i));
            filhoE.setTl(filhoE.getTl() + 1);
        }

        pai.remanejarExclusao(pos);
        for (int i = pos + 1; i <= pai.getTl(); i++)
            pai.setvLig(i, pai.getvLig(i + 1));

        pai.setTl(pai.getTl() - 1);
    }

    public void redistribuir_concatenar(No filhoE, No pai, No filhoD, int pos)
    {
        // Redistribui da esquerda para a direita
        if (filhoE.getTl() > (n / 2))
        {
            // Move a última chave de filhoE para a esquerda de filhoD
            filhoD.remanejar(0);
            filhoD.setvInfo(0, pai.getvInfo(pos));
            filhoD.setvPos(0, pai.getvPos(pos));
            filhoD.setTl(filhoD.getTl() + 1);

            pai.setvInfo(pos, filhoE.getvInfo(filhoE.getTl() - 1));
            pai.setvPos(pos, filhoE.getvPos(filhoE.getTl() - 1));

            filhoE.setTl(filhoE.getTl() - 1);
        }
        // Redistribui da direita para a esquerda
        else if (filhoD.getTl() > (n / 2))
        {
            filhoE.setvInfo(filhoE.getTl(), pai.getvInfo(pos));
            filhoE.setvPos(filhoE.getTl(), pai.getvPos(pos));
            filhoE.setTl(filhoE.getTl() + 1);

            pai.setvInfo(pos, filhoD.getvInfo(0));
            pai.setvPos(pos, filhoD.getvPos(0));

            filhoD.remanejarExclusao(0);
            filhoD.setTl(filhoD.getTl() - 1);
        }
    }


    public void exclusao(int info)
    {
        int pos, posPai;
        No folha = buscarNo(info), pai, irmaE, irmaD;
        if (folha != null)
        {
            pos = folha.procurarPosicao(info);

            // Caso 1: Nó interno
            if (folha.getvLig(0) != null)
            {
                // Substitui valor por seu sucessor
                irmaD = folha.getvLig(pos + 1);
                while (irmaD.getvLig(0) != null)
                    irmaD = irmaD.getvLig(0);

                folha.setvInfo(pos, irmaD.getvInfo(0));
                folha.setvPos(pos, irmaD.getvPos(0));
                folha = irmaD;
                pos = 0;
            }

            // Agora temos o valor na folha
            folha.remanejarExclusao(pos);
            folha.setTl(folha.getTl() - 1);

            // Verifica se precisa redistribuir/concatenar
            if (folha != raiz && folha.getTl() < (n / 2))
            {
                pai = localizarPai(folha, folha.getvInfo(0));
                posPai = pai.procurarPosicao(folha.getvInfo(0));
                irmaE = (posPai > 0) ? pai.getvLig(posPai - 1) : null;
                irmaD = (posPai < pai.getTl()) ? pai.getvLig(posPai + 1) : null;

                // Redistribuir ou concatenar
                if (irmaE != null && irmaE.getTl() > (n / 2))
                    redistribuir_concatenar(irmaE, pai, folha, posPai - 1);
                else if (irmaD != null && irmaD.getTl() > (n / 2))
                    redistribuir_concatenar(folha, pai, irmaD, posPai);
                else if (irmaE != null)
                    concatenar(irmaE, pai, folha, posPai - 1);
                else if (irmaD != null)
                    concatenar(folha, pai, irmaD, posPai);

            }

            // Se a raiz ficou vazia, ajusta
            if (raiz.getTl() == 0 && raiz.getvLig(0) != null)
                raiz = raiz.getvLig(0);
            else if (raiz.getTl() == 0)
                raiz = null;
        }
    }


    public void exibir(){

    }

    public void in_ordem()
    {
        in_ordem(raiz);
    }

    private void in_ordem(No raiz)
    {

    }


}
