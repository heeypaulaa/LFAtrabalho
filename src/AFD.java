import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;


/**
 * E – conjunto finito de estados.
 * Σ – alfabeto, formado por um conjunto finito de símbolos.
 * δ – função de transição, tipada como δ : (E ×Σ) → E.
 * i – estado inicial, tal que i ∈ E.
 * F – conjunto de estados finais, com F ⊆ E.
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
            this.nomeEstados.add(est.getAttributeValue("name"));
            //System.out.println("Nome: " + est.getAttributeValue("name"));
        }

        System.out.println();
        i = transicoes.iterator();
        while (i.hasNext()){
            Element trans = (Element)i.next();
            int from = Integer.parseInt(trans.getChildText("from"));
            int to = Integer.parseInt(trans.getChildText("to"));
            char read = (trans.getChildText("read")).charAt(0);
            //System.out.println(from);//"FROM: "+trans.getChildText("from"));
            //System.out.println(to);//"PARA: "+trans.getChildText("to"));
            //System.out.println(read);//"letra: "+trans.getChildText("read"));
            if(!this.alfabeto.contains(read)){
                this.alfabeto.add(read);
            }
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

    /*OK*/
    public AFD complement() {
        AFD novo = new AFD();
        novo.estados = estados;
        novo.alfabeto = alfabeto;
        novo.listaTrans = listaTrans;
        novo.inicio = inicio;

        for(Integer e : estados){
            if(!fim.contains(e)) {
                novo.fim.add(e);
            }
        }
        return novo;
    }

    public AFD union(AFD m2){
        List<Integer[]> visitados = new ArrayList<Integer[]>();
        AFD novo = multiplicacao(this, m2, visitados);

        for(Integer[] v : visitados){
            //descobre o estado inicial
            if((v[0]==this.inicio)&&(v[1]==m2.inicio)){
                novo.inicio = visitados.indexOf(v);
            }
            //descobre os estados finais
            if((this.fim.contains(v[0]))||(m2.fim.contains(v[1]))){
                novo.fim.add(visitados.indexOf(v));
            }
        }
        return novo;
    }

    public AFD multi(AFD m1, AFD m2, int[][]visitados){
        AFD novo = new AFD();

        int toM1, toM2, fromM1, fromM2;
        novo.alfabeto = m1.alfabeto;
        for(Character a : m2.alfabeto) {
            if (!novo.alfabeto.contains(a))
                novo.alfabeto.add(a);
        }

        fromM1=m1.inicio; fromM2=m2.inicio;
        /* 0 nao aconteceu nada
        * 1 visitado
        * 2 visitar
        * */
        visitados[fromM1][fromM2]=2;
        int novosEst=0;
        for (int i = 0; i < m1.alfabeto.size()*m2.alfabeto.size(); i++) {
            if(visitados[fromM1][fromM2]==2) {
                visitados[fromM1][fromM2] = 1;
                novo.addState(novosEst, false, false);
                for (Character c : novo.alfabeto) {
                    String aux = "" + c;
                    toM1 = m1.move(fromM1, aux);
                    toM2 = m2.move(fromM2, aux);
                    if (visitados[toM1][toM2] != 1) {
                        visitados[toM1][toM2] = 2;
                        novosEst++;
                    }
                }
            }
        }


        for (int i = 0; i < m1.alfabeto.size(); i++) {
            for (int j = 0; j < m2.alfabeto.size(); j++) {
                if(visitados[fromM1][fromM2]==2) {
                    visitados[fromM1][fromM2] = 1;
                    novo.addState(novosEst, false, false);
                    for (Character c : novo.alfabeto) {
                        String aux = "" + c;
                        toM1 = m1.move(fromM1, aux);
                        toM2 = m2.move(fromM2, aux);
                        if (visitados[toM1][toM2] != 1) {
                            visitados[toM1][toM2] = 2;
                            novosEst++;
                        }
                    }
                }
            }
        }
        return null;
    }


    public AFD multiplicacao(AFD m1, AFD m2, List<Integer[]>visitados) {
        AFD novo = new AFD();
        //int estM1 = this.estados.size();
        //int estM2 = m2.estados.size();
        List<FuncaoTransicao>vis = new ArrayList<>();
        List<Integer[]> visitar = new ArrayList<>();
        //List<Integer[]> visitados = new ArrayList<Integer[]>();
        //FuncaoTransicao[][] matrizMult = new FuncaoTransicao[estM1][estM2];
        int toM1, toM2;
        novo.alfabeto = m1.alfabeto;
        //for(Character a : novo.alfabeto) {
          //  if (!novo.alfabeto.contains(a))
            //    novo.alfabeto.add(a);
        //}
        //Character c;
        Integer e[] = new Integer[2];
        FuncaoTransicao n1;
        //visitar iniciando pelos estados iniciais de cada AFD
        e[0]=m1.inicio; e[1]=m2.inicio;
        n1 = new FuncaoTransicao(m1.inicio, m2.inicio, 'x');
        visitar.add(e);
        vis.add(n1);

        int fromM1, fromM2, idv=0,ide=-1;
        for(int i = 0; i<visitar.size(); ){
            Integer[] v = visitar.get(i);
            FuncaoTransicao v1 = vis.get(i);
            fromM1=v[0]; fromM2=v[1];
            System.out.print("vi:"+i);
            System.out.println(" v:"+v[0]+" "+v[1]);
            System.out.println("vt:"+v1.toString());
            for (int j = 0; j < novo.alfabeto.size(); j++) {
                c = novo.alfabeto.get(j);

           // for(Character c: novo.alfabeto) {
                System.out.println("aki v:"+v[0]+" "+v[1]);
                System.out.println("aki vt:"+v1.toString());
                String aux = "" + c;
                toM1 = m1.move(fromM1, aux);
                toM2 = m2.move(fromM2, aux);
                //adiciona a lista de estados para visitar
                System.out.println("aki v:"+visitar.get(i)[0]+" "+visitar.get(i)[1]);
                e[0]=toM1; e[1]=toM2;
                n1.setFrom(0);n1.setTo(toM2);

                System.out.println("aki v:"+i+visitar.get(i)[0]+" "+visitar.get(i)[1]);
                System.out.println("aki vt:"+i+v1.toString());
                System.out.println("("+e[0]+" "+e[1]+")");
                //visitar.i

                if(!visitados.contains(v)){
                    visitados.add(v);
                }

                if(visitados.isEmpty() || !visitados.contains(e)) {
                    ide++;
                    visitar.add(ide,e);
                    novo.addState(ide,false,false);

                    System.out.println("index e"+ide);
                    System.out.println("index v"+i);
                }
                //adiciona f.trans a lista de transição (index de v, index do novo elesmento de visitar, c)
                novo.addTransition(i,ide,c);
            }/*
            for(Integer i : novo.estados){
                System.out.println(i);
            }*/
            //adiciona o primeiro da lista de visitar a lista de visitados
            i++;

        }
        return novo;
    }

    public AFD intersection(AFD m2) {
        List<Integer[]> visitados = new ArrayList<Integer[]>();
        AFD novo = multiplicacao(this, m2, visitados);
        System.out.print("inter\n");
        for(Integer[] v : visitados){
            //descobre o estado inicial
            if((v[0]==this.inicio)&&(v[1]==m2.inicio)){
                novo.inicio = visitados.indexOf(v);
            }
            //descobre os estados finais
            if((this.fim.contains(v[0]))&&(m2.fim.contains(v[1]))){
                novo.fim.add(visitados.indexOf(v));
            }
        }
        return novo;
    }

    public AFD difference(AFD m2) {
        return this.intersection(m2.complement());
    }

    public boolean accept(String palavra) {
        int parteDe = inicio;
        boolean mudouEst;
        //percorre cada caracter da palavra
        for(int i=0; i<palavra.length(); i++){
            mudouEst = false;
            //percorre cada item da lista de transiçao(from,to,caracter)
            for(FuncaoTransicao f : listaTrans){
                //se o caracter e estado de partida forem iguais
                if(palavra.charAt(i)==f.getCaracter() && parteDe==f.getFrom()){
                    mudouEst = true;//mudou de estado
                    parteDe = f.getTo();//recebe o novo estado de partida ou ultimo estado
                    break;
                }
            }
            //se percorreu toda a lista de transiçao e nao achou um novo estado, nao aceita a palavra
            if(mudouEst==false){
                return false;
            }
        }
        //se o ultimo estado que recebeu for um estado final, aceita a palavra
        if(this.fim.contains(parteDe)){
            return true;
        }else{
            return false;
        }
    }

    public int move(int estado, String palavra) {
        int parteDe = estado;
        AFD aux = this;
        //percorre cada caracter da palavra
        for(int i=0; i<palavra.length(); i++){
            //percorre cada item da lista de transiçao(from,to,caracter)
            for(FuncaoTransicao f : aux.listaTrans) {
                //se o caracter e estado de partida forem iguais
                if (palavra.charAt(i) == f.getCaracter() && estado == f.getFrom()) {
                    return f.getTo();
                }else {
                    parteDe = -1;
                }
            }
        }
        return parteDe;
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
        for(int e : this.estados){
            for(char a : this.alfabeto) {
                deleteTransition(e, estado, a);
                deleteTransition(estado, e, a);
            }
        }
        this.estados.remove(estado);
    }

    public void deleteTransition(int parteDe, int vaiPara, char caracter) {
        for(FuncaoTransicao f : listaTrans){
            if((f.getCaracter()==caracter) && (f.getFrom()==parteDe) && (f.getTo()==vaiPara)){
                listaTrans.remove(new FuncaoTransicao(parteDe,vaiPara,caracter));
            }
        }
    }

    @Override
    public String toString() {
        String aux = "Estados{";
        for(Integer e : this.estados)
            aux=  aux + e+" ";

        aux=aux+"}; Alfabeto{";
        for(Character a : this.alfabeto)
            aux = aux+a+" ";

        aux=aux+"}; Transicao{";
        for(FuncaoTransicao t: this.listaTrans)
            aux = aux+t.toString()+" ";

        aux=aux+"}; Inicio:"+this.initial()+"; Final{";
        for(Integer f: this.fim)
            aux=aux+f+" ";
        aux=aux+"}.";

        return  aux;
    }
}
