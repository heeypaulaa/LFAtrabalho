import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by paula on 08/05/17.
 * Ana Paula da Silva Cunha 0011252
 * Suena Batista Galoneti 0011251
 */

public class AFD {
    private List<Integer> estados = new ArrayList<>();
    private List<String> nomeEstados = new ArrayList<>();
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

    public void load(String entrada) throws JDOMException, IOException {
        File fXmlFile = new File(entrada);
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(fXmlFile);
        Element raiz = (Element) doc.getRootElement();

        List estados = raiz.getChild("automaton").getChildren("state");
        List transicoes = raiz.getChild("automaton").getChildren("transition");

        Iterator i = estados.iterator();
        while (i.hasNext()) {
            Element est = (Element) i.next();
            int id = Integer.parseInt(est.getAttributeValue("id"));
            boolean estFim = false;
            boolean estInicio = false;
            if (est.getChild("final") != null) {
                estFim = true;
            }
            if (est.getChild("initial") != null) {
                estInicio = true;
            }
            addState(id, estInicio, estFim);
            this.nomeEstados.add(est.getAttributeValue("name"));
        }

        System.out.println();
        i = transicoes.iterator();
        while (i.hasNext()) {
            Element trans = (Element) i.next();
            int from = Integer.parseInt(trans.getChildText("from"));
            int to = Integer.parseInt(trans.getChildText("to"));
            char read = (trans.getChildText("read")).charAt(0);
            if (!this.alfabeto.contains(read)) {
                this.alfabeto.add(read);
            }
            addTransition(from, to, read);
        }
    }

    public AFD complement() {
        AFD novo = new AFD();
        novo.estados = estados;
        novo.alfabeto = alfabeto;
        novo.listaTrans = listaTrans;
        novo.inicio = inicio;

        for (Integer e : estados) {
            if (!fim.contains(e)) {
                novo.fim.add(e);
            }
        }
        return novo;
    }

    public AFD union(AFD m2) {
        List<TuplaEstados> visitados = new ArrayList<>();
        AFD novo = multiplicacao(this, m2, visitados);
        for(TuplaEstados v : visitados){
            //descobre o estado inicial
            if ((v.getAfd1() == this.inicio) && (v.getAfd2() == m2.inicio)) {
                novo.inicio = visitados.indexOf(v);
            }
            //descobre os estados finais
            if ((this.fim.contains(v.getAfd1())) || (m2.fim.contains(v.getAfd2()))) {
                novo.fim.add(visitados.indexOf(v));
            }
        }
        return novo;
    }

    public AFD intersection(AFD m2) {
        List<TuplaEstados> visitados = new ArrayList<>();
        AFD novo = multiplicacao(this, m2, visitados);
        for(TuplaEstados v : visitados){
            //descobre o estado inicial
            if((v.getAfd1()==this.inicio)&&(v.getAfd2()==m2.inicio)){
                novo.inicio = visitados.indexOf(v);
            }
            //descobre os estados finais
            if((this.fim.contains(v.getAfd1()))&&(m2.fim.contains(v.getAfd2()))){
                novo.fim.add(visitados.indexOf(v));
            }
        }
        return novo;
    }

    public AFD difference(AFD m2) {
        return this.intersection(m2.complement());
    }

    public AFD multiplicacao(AFD m1, AFD m2,List<TuplaEstados> visitados) {
        AFD novo = new AFD();
        /*define novo alfabeto*/
        int i=0,toM1, toM2;
        for (Character a : m2.alfabeto) {
            if (!novo.alfabeto.contains(a) || novo.alfabeto.isEmpty())
                novo.alfabeto.add(a);
        }
        for (Character a : m1.alfabeto) {
            if (!novo.alfabeto.contains(a) || novo.alfabeto.isEmpty())
                novo.alfabeto.add(a);
        }
        /*estados a visitar dos dois autômatos*/
        List<TuplaEstados> visitar = new ArrayList<>();
        visitar.add(new TuplaEstados(m1.initial(), m2.initial()));

        do {
            TuplaEstados v = visitar.get(i);
            if(visitados.isEmpty() || !visitados.contains(v)) {
                novo.addState(i, false, false);
                visitados.add(v);//visita o primeiro par de estados
                for (Character c : novo.alfabeto) {
                    String aux = "" + c;
                    toM1 = m1.move(v.getAfd1(), aux);
                    toM2 = m2.move(v.getAfd2(), aux);
                    TuplaEstados vAux = new TuplaEstados(toM1, toM2);
                    if (!existeNaLista(visitar,vAux))//verifica se já esta na lista para visitar
                        visitar.add(vAux);
                    //adiciona nova transição ao automato, seus estados são os indices das listas de visitar e visitados
                    novo.addTransition(visitados.lastIndexOf(v), getIndex(visitar,vAux), c);
                }
            }
            i++;
        }while (visitar.size()!=i);
        return novo;
    }

    public boolean accept(String palavra) {
        int parteDe = inicio;
        //percorre cada caracter da palavra
        for (int i = 0; i < palavra.length(); i++) {
            String aux = ""+palavra.charAt(i);
            //verifica se existe o movimento valido, se não recebe -1
            parteDe = move(parteDe, aux);
            if(parteDe == -1){
                return false;
            }
        }
        //se o ultimo estado que recebeu for um estado final, aceita a palavra
        if (this.fim.contains(parteDe)) {
            return true;
        } else {
            return false;
        }
    }

    public int move(int estado, String palavra) {
        int parteDe = estado;
        AFD aux = this;
        //percorre cada caracter da palavra
        for (int i = 0; i < palavra.length(); i++) {
            //percorre cada item da lista de transiçao(from,to,caracter)
            for (FuncaoTransicao f : aux.listaTrans) {
                //se o caracter e estado de partida forem iguais
                if (palavra.charAt(i) == f.getCaracter() && estado == f.getFrom()) {
                    return f.getTo();
                } else {
                    parteDe = -1;
                }
            }
        }
        return parteDe;
    }

    public void addState(int id, boolean inicial, boolean fim) {
        this.estados.add(id);
        if (inicial == true) {
            this.inicio = id;
        }
        if (fim == true) {
            this.fim.add(id);
        }
    }

    public void addTransition(int parteDe, int vaiPara, char caracter) {
        FuncaoTransicao funcTrans = new FuncaoTransicao(parteDe, vaiPara, caracter);
        listaTrans.add(funcTrans);
    }

    public void deleteState(int estado) {
        for (int e : this.estados) {
            for (char a : this.alfabeto) {
                deleteTransition(e, estado, a);
                deleteTransition(estado, e, a);
            }
        }
        this.estados.remove(estado);
    }

    public void deleteTransition(int parteDe, int vaiPara, char caracter) {
        for (FuncaoTransicao f : listaTrans) {
            if ((f.getCaracter() == caracter) && (f.getFrom() == parteDe) && (f.getTo() == vaiPara)) {
                listaTrans.remove(new FuncaoTransicao(parteDe, vaiPara, caracter));
            }
        }
    }


    private static boolean existeNaLista(List<TuplaEstados> l, TuplaEstados o){
        for(TuplaEstados e: l){
            if((e.getAfd1() == o.getAfd1())&&(e.getAfd2() == o.getAfd2())){
                return true;
            }
        }
        return false;
    }

    private static int getIndex(List<TuplaEstados> l, TuplaEstados o){
        TuplaEstados v;
        for(int i = 0; i<l.size(); i++){
            v= l.get(i);
            if((v.getAfd1() == o.getAfd1())&&(v.getAfd2() == o.getAfd2())){
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        String aux = "Estados{";
        for (Integer e : this.estados)
            aux = aux + e + " ";

        aux = aux + "}; Alfabeto{";
        for (Character a : this.alfabeto)
            aux = aux + a + " ";

        aux = aux + "}; Transicao{";
        for (FuncaoTransicao t : this.listaTrans)
            aux = aux + t.toString() + " ";

        aux = aux + "}; Inicio:" + this.initial() + "; Final{";
        for (Integer f : this.fim)
            aux = aux + f + " ";
        aux = aux + "}.";

        return aux;
    }
}
