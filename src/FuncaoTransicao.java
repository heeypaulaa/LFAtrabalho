/**
 * Created by paula on 08/05/17.
 */
public class FuncaoTransicao {
    private int from;
    private int to;
    private String from1;
    private String to1;
    private char caracter;

    public FuncaoTransicao(int de, int para, char caract) {
        this.setFrom(de);
        this.setTo(para);
        this.setCaracter(caract);
    }

    public FuncaoTransicao(String from1,String to1, char caract){
        this.from1 = from1;
        this.to1 = to1;
        this.caracter = caract;
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
