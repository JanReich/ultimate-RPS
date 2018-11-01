package cryptography;

import toolBox.Math;

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

        //Es wird eine Primzahl P und eine natürliche Zahl G bestimmt

        P = Math.generatePrime((int) (java.lang.Math.random() * 1000));

        G =(int)(java.lang.Math.random() * 1000);

        //Es werden zwei private Schlüssel a und b generiert/ausgesucht

        a =(int)(java.lang.Math.random() * 1000);

        b =(int)(java.lang.Math.random() * 1000);

        System.out.println("Die gemeinsame Primzahl ist "+ P);

        System.out.println("Der gemeinsame natürliche Zahl ist "+ G);

        System.out.println("Der erste PrivateKey ist "+ a);

        System.out.println("Der zweite PrivateKey ist "+ b);

        generateKeys();
    }



    public void generateKeys(){

        //Es wird nach einer Formel die öffentlichen Schlüssel x und y berechnet/generiert

        x = G^a % P;

        y = G^b % P;

        System.out.println("Der erste neue PublicKey ist "+ x);

        System.out.println("Der zweite neue PublicKey ist "+ y);

        //Es wird nach einer Formel der gemeinsame geheime Schlüssel gefunden

        ka = y^a % P;
        kb = x^b % P;

        System.out.println("Der erste SecretKey ist "+ ka);

        System.out.println("Der zweite SecretKey ist "+ kb);

    }

}