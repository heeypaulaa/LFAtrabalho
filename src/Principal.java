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
        AFD m = new AFD();
        m.load("teste.jff");

    }

}
