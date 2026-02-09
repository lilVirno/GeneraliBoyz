public class Spieler {

    private String name;

    private int Punktekonto;


    public Spieler (String name, int Punkte) {
        setName(name);
        setPunktekonto(Punkte);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPunktekonto() {
        return Punktekonto;
    }

    public void setPunktekonto(int punktekonto) {
        Punktekonto = punktekonto;
    }
}
