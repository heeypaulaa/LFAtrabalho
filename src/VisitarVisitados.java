/**
 * Created by paula on 23/05/17.
 */
public class VisitarVisitados {
    private int afd1;
    private int afd2;

    public VisitarVisitados(int m1, int m2) {
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
