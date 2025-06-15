public class Aplicacao
{
    public static void main(String[] args) {

        System.out.println("\n\nN = 4");
        BPlusTree b = new BPlusTree(4);
        System.out.println("\nINSERINDO VALORES:");
        for (int i = 1; i <= 500; i++) {
            System.out.printf(i+" ");
            if(i%20 == 0)
                System.out.println();
            b.inserir(i,0);
        }
        System.out.println("\n\n# # # ARVORE B+TREE # # #");
        b.exibir();
        System.out.println("\n\n# # # # IN-ORDEM DA ARVORE TREE # # #");
        b.in_ordem();

        System.out.println("\n\nEXCLUIR VALORES:");
        for(int i = 1; i <= 5; i++){
            System.out.printf(i+" ");
            b.excluir(i);
            System.out.printf((i+10)+" ");
            b.excluir(i+10);
            System.out.printf((i+100)+" ");
            b.excluir(i+100);
            System.out.printf((i+200)+" ");
            b.excluir(i+200);
            System.out.printf((i+300)+" ");
            b.excluir(i+300);
            System.out.printf((i+400)+" ");
            b.excluir(i+400);
        }
        System.out.println("\n\n\n# # # ARVORE B+TREE DEPOIS DA EXCLUSÃO # # #");
        b.exibir();


        System.out.println("\n\n\n--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        System.out.println("N = 5");
        b = new BPlusTree(5);
        System.out.println("\nINSERINDO VALORES:");
        for (int i = 1; i <= 500; i++) {
            System.out.printf(i+" ");
            if(i%20 == 0)
                System.out.println();
            b.inserir(i,0);
        }
        System.out.println("\n\n# # # ARVORE B+TREE # # #");
        b.exibir();
        System.out.println("\n\n# # # # IN-ORDEM DA ARVORE TREE # # #");
        b.in_ordem();

        System.out.println("\n\nEXCLUIR VALORES:");
        for(int i = 1; i <= 5; i++){
            System.out.printf(i+" ");
            b.excluir(i);
            System.out.printf((i+10)+" ");
            b.excluir(i+10);
            System.out.printf((i+100)+" ");
            b.excluir(i+100);
            System.out.printf((i+200)+" ");
            b.excluir(i+200);
            System.out.printf((i+300)+" ");
            b.excluir(i+300);
            System.out.printf((i+400)+" ");
            b.excluir(i+400);
        }
        System.out.println("\n\n\n# # # ARVORE B+TREE DEPOIS DA EXCLUSÃO # # #");
        b.exibir();
    }
}
