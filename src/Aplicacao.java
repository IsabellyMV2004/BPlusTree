public class Aplicacao
{
    public static void main(String[] args) {

        BPlusTree b = new BPlusTree(4);
        System.out.println("INSERINDO VALORES:");
        for (int i = 1; i <= 1000; i++) {
            System.out.println(i+" inserido");
            b.inserir(i,0);
        }

    }
}
