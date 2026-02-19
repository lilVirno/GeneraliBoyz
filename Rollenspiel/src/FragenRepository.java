import enums.Themenbereich;

import java.util.ArrayList;
import java.util.List;

public class FragenRepository {
    public static List<Frage> getAlleFragen() {
        List<Frage> fragen = new ArrayList<>();

        List<Antwort> antwortenMC = new ArrayList<>();
        antwortenMC.add(new Antwort(true, "order by"));
        antwortenMC.add(new Antwort(false, "group by"));
        antwortenMC.add(new Antwort(false, "sort by"));
        antwortenMC.add(new Antwort(false, "list by"));
        fragen.add(new Frage(Themenbereich.SQL, "Wie sortiere ich mein Query-Output?", antwortenMC));

        List<Antwort> antwortenWF = new ArrayList<>();
        antwortenWF.add(new Antwort(true, "Wahr"));
        antwortenWF.add(new Antwort(false, "Falsch"));
        fragen.add(new Frage(Themenbereich.SQL, "Man kann mit sort by den Query-Output sortieren.", antwortenWF));

        List<Antwort> antwortL = new ArrayList<>();
        antwortL.add(new Antwort(true, "sort by"));
        fragen.add(new Frage(Themenbereich.SQL, "Man kann den Query-Output mit ____ __ sortieren.", antwortL));

        fragen.add(new Frage(Themenbereich.SQL, "Wie sortiere ich mein Query-Output?", antwortenMC));
        fragen.add(new Frage(Themenbereich.SQL, "Wie sortiere ich mein Query-Output?", antwortenMC));
        fragen.add(new Frage(Themenbereich.SQL, "Wie sortiere ich mein Query-Output?", antwortenMC));
        fragen.add(new Frage(Themenbereich.SQL, "Wie sortiere ich mein Query-Output?", antwortenMC));

        return fragen;
    }
}
