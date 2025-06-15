public class No
{
    private int n;
    private int vInfo[];
    private int vPos[];
    private No vLig[];
    private int tl;

    public No(int m) {
        n = m;
        vInfo = new int[n+1];
        vPos = new int[n+1];
        vLig = new No[n+2];
        tl = 0;
    }

    public No(int n, int info, int posArq)
    {
        this(n);
        vInfo[0] = info;
        vPos[0] = posArq;
        tl = 1;
    }

    public int procurarPosicao(int info)
    {
        int pos=0;
        while(pos<tl && info>vInfo[pos])
            pos++;
        return pos;
    }

    public void remanejar(int pos)
    {
        vLig[tl+1] = vLig[tl];
        for(int i=tl; i>pos; i--)
        {
            vInfo[i] = vInfo[i-1];
            vPos[i] = vPos[i-1];
            vLig[i] = vLig[i-1];
        }
    }

    public void remanejarExclusao(int pos)
    {
        for(int i=pos; i<tl-1; i++)
        {
            vInfo[i] = vInfo[i+1];
            vPos[i] = vPos[i+1];
            vLig[i] = vLig[i+1];
        }
        vLig[tl-1] = vLig[tl];
    }

    public int getvInfo(int p) {
        return vInfo[p];
    }

    public void setvInfo(int p, int info) {
        vInfo[p] = info;
    }

    public int getvPos(int p) {
        return vPos[p];
    }

    public void setvPos(int p, int posArq) {
        vPos[p] = posArq;
    }

    public No getvLig(int p) {
        return vLig[p];
    }

    public void setvLig(int p, No lig) {
        vLig[p] = lig;
    }

    public int getTl() {
        return tl;
    }

    public void setTl(int tl) {
        this.tl = tl;
    }
}
