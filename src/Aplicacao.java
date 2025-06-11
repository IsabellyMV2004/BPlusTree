public class Aplicacao
{
    public static void main(String[] args) {
        BPlusTree b = new BPlusTree();


        for(int i=1; i<=1000; i++)
            b.inserir(i, i);
        System.out.println("------");

        //Teste da exclusão
        for(int i=6; i<=995; i++)
            b.exclusao(i);

        b.in_ordem();
    }
}
