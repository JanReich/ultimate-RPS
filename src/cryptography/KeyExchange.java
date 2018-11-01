package cryptography;

public class KeyExchange {

    private long P;
    private long G;
    private long x;
    private long a;
    private long y;
    private long b;
    private long kb;
    private long ka;

    public KeyExchange(){

        P =(int)(Math.random()*100000000);

        G =(int)(Math.random()*100000000);

        a =(int)(Math.random()*100000000);

        b =(int)(Math.random()*100000000);

        System.out.println("P ist "+ P);

        System.out.println("G ist "+ G);

        System.out.println("Der erste PrivateKey ist "+ a);

        System.out.println("Der zweite PrivateKey ist "+ b);

        generateKeys();
    }



    public void generateKeys(){


        x = G^a % P;

        y = G^b % P;

        System.out.println("Der erste neue PublicKey ist "+ x);

        System.out.println("Der zweite neue PublicKey ist "+ y);

        ka = y^a % P;
        kb = x^b % P;

        System.out.println("Der erste SecretKey ist "+ ka);

        System.out.println("Der zweite SecretKey ist "+ kb);

    }

}