package backend;

import enums.Themenbereich;

import java.util.ArrayList;
import java.util.List;

public class FragenRepository {
    /*
    Diese Klasse enthält Mock-Fragen zum Testen bis die Datenbank eingebunden ist
     */
    public static List<Frage> getAlleFragen() {
        List<Frage> fragen = new ArrayList<>();

        List<Antwort> antwortenMultiAntwort = new ArrayList<>();
        antwortenMultiAntwort.add(new Antwort(true, "richtig"));
        antwortenMultiAntwort.add(new Antwort(true, "richtig"));
        antwortenMultiAntwort.add(new Antwort(false, "falsch"));
        antwortenMultiAntwort.add(new Antwort(false, "falsch"));
        fragen.add(new Frage(Themenbereich.SQL, "Richtig oder Falsch?", antwortenMultiAntwort));

        List<Antwort> antwortL = new ArrayList<>();
        antwortL.add(new Antwort(true, "sort by"));
        fragen.add(new Frage(Themenbereich.SQL, "Man kann den Query-Output mit ____ __ sortieren.", antwortL));

        List<Antwort> antwortenWF = new ArrayList<>();
        antwortenWF.add(new Antwort(true, "Wahr"));
        antwortenWF.add(new Antwort(false, "Falsch"));
        fragen.add(new Frage(Themenbereich.SQL, "Man kann mit sort by den Query-Output sortieren.", antwortenWF));

        List<Antwort> antwortenMC = new ArrayList<>();
        antwortenMC.add(new Antwort(true, "order by"));
        antwortenMC.add(new Antwort(false, "group by"));
        antwortenMC.add(new Antwort(false, "sort by"));
        antwortenMC.add(new Antwort(false, "list by"));
        fragen.add(new Frage(Themenbereich.SQL, "Wie sortiere ich mein Query-Output?", antwortenMC));

        fragen.add(new Frage(Themenbereich.SQL, "1Wie sortiere ich mein Query-Output?", antwortenMC));
        fragen.add(new Frage(Themenbereich.SQL, "2Wie sortiere ich mein Query-Output?", antwortenMC));
        fragen.add(new Frage(Themenbereich.SQL, "3Wie sortiere ich mein Query-Output?", antwortenMC));
        fragen.add(new Frage(Themenbereich.SQL, "4Wie sortiere ich mein Query-Output?", antwortenMC));

        return fragen;
    }
}
