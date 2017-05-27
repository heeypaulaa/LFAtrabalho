/**
 * Created by paula on 17/05/17.
 * Ana Paula da Silva Cunha 0011252
 * Suena Batista Galoneti 0011251
 */

/*Classe criada para manipular os estados a serem visitados e os que ja foram visitados*/
public class TuplaEstados {
    private int afd1; /*estado do primeiro autômato*/
    private int afd2; /*estado do segundo autômato*/

    public TuplaEstados(int m1, int m2) {
        this.setAfd1(m1);
        this.setAfd2(m2);
    }

    public int getAfd1() {
        return afd1;
    }

    public void setAfd1(int afd1) {
        this.afd1 = afd1;
    }

    public int getAfd2() {
        return afd2;
    }

    public void setAfd2(int afd2) {
        this.afd2 = afd2;
    }

    @Override
    public String toString() {
        return "("+ afd1 +
                ","+ afd2 +
                ')';
    }

}
