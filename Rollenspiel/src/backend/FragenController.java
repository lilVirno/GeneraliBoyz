package backend;

import java.util.List;

public class FragenController {

    private final List<Frage> fragen;
    private int index = 0;

    public FragenController(List<Frage> fragen) {
        this.fragen = fragen;
    }

    public Frage getAktuelleFrage() {
        return fragen.get(index);
    }

    public boolean hatNaechsteFrage() {
        return index < fragen.size() - 1;
    }

    public Frage naechsteFrage() {
        index++;
        return getAktuelleFrage();
    }
}

