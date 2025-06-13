public class Aplicacao
{
    public static void main(String[] args) {

        BPlusTree b = new BPlusTree(4);
        System.out.println("INSERINDO VALORES:");
        for (int i = 1; i <= 1000; i++) {
            System.out.printf(i+" ");
            if(i%20 == 0)
                System.out.println();
            b.inserir(i,0);
        }



    }
}
