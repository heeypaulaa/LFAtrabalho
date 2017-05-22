/**
 * Created by paula on 08/05/17.
 */
public class FuncaoTransicao {
    private int from;
    private int to;
    private char caracter;

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
