public class Aplicacao
{
    public static void main(String[] args) {

        System.out.println("\n\nN = 4");
        BPlusTree b = new BPlusTree(4);
        System.out.println("\nINSERINDO VALORES:");
        for (int i = 1; i <= 300; i++) {
            System.out.printf(i+" ");
            if(i%20 == 0)
                System.out.println();
            b.inserir(i,0);
            b.exibir();
        }
        System.out.println("\n\n# # # ARVORE B+TREE # # #");
        //b.exibir();

        /*System.out.println("EXCLUIR VALORES:");
        for(int i = 50; i> 0; i--){
            System.out.printf(i+" ");
            if(i%20 == 0)
                System.out.println();
            b.excluir(i);
        }
        System.out.println("------------------------------");
        b.exibir();*/


        System.out.println("\n\n\n-------------------------------------------------------------------------------\n");
        System.out.println("N = 5");
        b = new BPlusTree(5);
        System.out.println("\nINSERINDO VALORES:");
        for (int i = 1; i <= 300; i++) {
            System.out.printf(i+" ");
            if(i%20 == 0)
                System.out.println();
            b.inserir(i,0);
        }
        System.out.println("\n\n# # # ARVORE B+TREE # # #");
        b.exibir();

        /*System.out.println("EXCLUIR VALORES:");
        for(int i = 50; i > 0; i--){
            System.out.printf(i+" ");
            if(i%20 == 0)
                System.out.println();
            b.excluir(i);
            b.exibir();
        }
        System.out.println("------------------------------");
        b.exibir();*/


    }
}
