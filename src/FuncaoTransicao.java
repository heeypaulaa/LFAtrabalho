/**
 * Created by paula on 08/05/17.
 * Ana Paula da Silva Cunha 0011252
 * Suena Batista Galoneti 0011251
 */

/*Classe feita para cada função de transição de um AFD*/
public class FuncaoTransicao {
    private int from; /*estado de partida*/
    private int to; /*estado de chegada*/
    private char caracter; /*gasta*/

    public FuncaoTransicao(int de, int para, char caract) {
        this.setFrom(de);
        this.setTo(para);
        this.setCaracter(caract);
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public char getCaracter() {
        return caracter;
    }

    public void setCaracter(char caracter) {
        this.caracter = caracter;
    }

    public String toString(){
        return "("+this.getFrom()+","+this.getTo()+","+this.getCaracter()+")";
    }
}
