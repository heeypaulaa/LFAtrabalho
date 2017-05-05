import java.util.ArrayList;

/**
 * E – conjunto finito de estados.
 * Σ – alfabeto, formado por um conjunto finito de símbolos.
 * δ – função de transição, tipada como δ : (E ×Σ) → E.
 * i – estado inicial, tal que i ∈ E.
 * F – conjunto de estados finais, com F ⊆ E.
 */
public class AFD {
    private ArrayList<String> estados = new ArrayList<>();
    private ArrayList<String> alfabeto = new ArrayList<>();
    private String funcaoTransicao;
    private String inicio;
    private ArrayList<String> fim = new ArrayList<>();

    public String getInicio(){
        return inicio;
    }

    public void setInicio(String inicio){
        this.inicio = inicio;
    }


}
