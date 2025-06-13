public class Aplicacao
{
    public static void main(String[] args) {

        BPlusTree b = new BPlusTree(4);
        System.out.println("INSERINDO VALORES:");
        for (int i = 1; i <= 100; i++) {
            System.out.printf(i+" ");
            if(i%20 == 0)
                System.out.println();
            b.inserir(i,0);
            b.exibir();
        }


       /* System.out.println("EXCLUIR VALORES:");
        for(int i = 50; i> 0; i--){
            System.out.printf(i+" ");
            if(i%20 == 0)
                System.out.println();
            b.excluir(i);
        }
        System.out.println("------------------------------");
        b.exibir();*/
    }
}
