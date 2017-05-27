import org.jdom2.JDOMException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Ana Paula da Silva Cunha
 * Suena Galoneti
 *
 */
public class Principal {


    // ola
    public static void main (String[] args) throws IOException, JDOMException{
        AFD m1 = new AFD();
        m1.load("para.jff");
        System.out.println("Par A\n"+m1.toString());

        AFD m2 = new AFD();
       /* m2.load("impb.jff");
        System.out.println("Impar b\n"+m2.toString());

        AFD inter = m1.intersection(m2);
        System.out.println("Inters 1 2\n"+inter.toString());

        AFD uni = m1.union(m2);
        System.out.println("Uniao 1 2\n"+uni.toString());

        AFD dif = m1.difference(m2);
        System.out.println("Difer 1 2\n"+dif.toString());

        AFD comp1 = m1.complement();
        System.out.println("Complemento 1\n"+comp1.toString());
        */

       m1.save("bla");


    }
}
