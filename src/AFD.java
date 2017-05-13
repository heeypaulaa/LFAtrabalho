import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * E – conjunto finito de estados.
 * Σ – alfabeto, formado por um conjunto finito de símbolos.
 * δ – função de transição, tipada como δ : (E ×Σ) → E.
 * i – estado inicial, tal que i ∈ E.
 * F – conjunto de estados finais, com F ⊆ E.
 */

public class AFD {
    private List<Integer> estados = new ArrayList<>();
    private List<Character> alfabeto = new ArrayList<>();
    private List<FuncaoTransicao> listaTrans = new ArrayList<>();
    private int inicio;
    private List<Integer> fim = new ArrayList<>();

    public int initial() {
        return inicio;
    }

    public List<Integer> finals() {
        return fim;
    }

    public void load(String entrada) throws JDOMException, IOException{
        File fXmlFile = new File(entrada);
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(fXmlFile);
        Element raiz = (Element) doc.getRootElement();

        List estados = raiz.getChild("automaton").getChildren("state");
        List transicoes = raiz.getChild("automaton").getChildren("transition");

        Iterator i = estados.iterator();
        while(i.hasNext()) {
            Element est = (Element) i.next();
            int id = Integer.parseInt(est.getAttributeValue("id"));
            boolean estFim = false;
            boolean estInicio = false;
            if(est.getChild("final") != null){
                estFim = true;
            }if(est.getChild("initial") !=null){
                estInicio = true;
            }
            addState(id,estInicio,estFim);
            //System.out.println("Nome: " + est.getAttributeValue("name"));
        }

        System.out.println();
        i = transicoes.iterator();
        while (i.hasNext()){
            Element trans = (Element)i.next();
            int from = Integer.parseInt(trans.getChildText("from"));
            int to = Integer.parseInt(trans.getChildText("to"));
            char read = (trans.getChildText("read")).charAt(0);
            System.out.println(from);//"FROM: "+trans.getChildText("from"));
            System.out.println(to);//"PARA: "+trans.getChildText("to"));
            System.out.println(read);//"letra: "+trans.getChildText("read"));
            addTransition(from,to,read);
        }
    }

    public void salve(String saida) {

    }

    public boolean equivalents(AFD m1, AFD m2) {
        return false;
    }

    public List<String> equivalents() {
        return null;
    }

    public AFD minimum() {
        return null;
    }

    public AFD complement() {
        return null;
    }

    public AFD union(AFD m) {
        return null;
    }

    public AFD intersection(AFD m) {
        return null;
    }

    public AFD difference(AFD m) {
        return null;
    }

    public boolean accept(String palavra) {
        return false;
    }

    public String move(int estado, String palavra) {
        return null;
    }

    public void addState(int id, boolean inicial, boolean fim) {
        this.estados.add(id);
        if(inicial == true){
            this.inicio = id;
        }if(fim == true){
            this.fim.add(id);
        }
    }


    public void addTransition(int parteDe, int vaiPara, char caracter) {
        FuncaoTransicao funcTrans = new FuncaoTransicao(parteDe,vaiPara,caracter);
        listaTrans.add(funcTrans);
    }

    public void deleteState(int estado) {
        

    }

    public void deleteTransition(int parteDe, int vaiPara, char caracter) {

    }
}
