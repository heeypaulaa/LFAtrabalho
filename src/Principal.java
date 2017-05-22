import org.jdom2.JDOMException;

import java.io.IOException;

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
        m2.load("impb.jff");
        System.out.println("Impar b\n"+m2.toString());

        AFD inter = m1.intersection(m2);
        System.out.println("Inters 1 2\n"+m2.toString());

        AFD comp1 = m1.complement();
        System.out.println("Complemento 1\n"+comp1.toString());
    }

}
