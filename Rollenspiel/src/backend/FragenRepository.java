package backend;

import enums.Themenbereich;

import java.util.ArrayList;
import java.util.List;

public class FragenRepository {
    /*
    Diese Klasse enthält Mock-Fragen zum Testen bis die Datenbank eingebunden ist
     */

    public static List<Frage> alleFragen = null;

    public static List<Frage> getAlleFragen() {
        if (alleFragen == null) {
            alleFragen = new ArrayList<>();
            initialisiereMockDaten(alleFragen);
        }
        return alleFragen;
    }

    public static List<Frage> getUngeloesteFragen(Themenbereich thema) {
        return getAlleFragen().stream()
                .filter(f -> f.getThemenbereich() == thema)
                .filter(f -> !f.isGeloest())
                .toList();
    }

    public static double berechneFortschrittFuerThema(Themenbereich thema) {
        List<Frage> alleThemenFragen = getAlleFragen().stream()
                .filter(f -> f.getThemenbereich() == thema)
                .toList();

        if (alleThemenFragen.isEmpty()) return 0.0;

        long beantwortet = alleThemenFragen.stream().filter(Frage::isGeloest).count();
        return (double) beantwortet / alleThemenFragen.size();
    }

    private static void initialisiereMockDaten(List<Frage> fragen) {
        List<Antwort> antwortenMultiAntwort = new ArrayList<>();
        antwortenMultiAntwort.add(new Antwort(true, "richtig"));
        antwortenMultiAntwort.add(new Antwort(true, "richtig"));
        antwortenMultiAntwort.add(new Antwort(false, "falsch"));
        antwortenMultiAntwort.add(new Antwort(false, "falsch"));
        fragen.add(new Frage(Themenbereich.SQL, "Richtig oder Falsch?", antwortenMultiAntwort, 100));

        List<Antwort> antwortL = new ArrayList<>();
        antwortL.add(new Antwort(true, "sort by"));
        fragen.add(new Frage(Themenbereich.SQL, "Man kann den Query-Output mit ____ __ sortieren.", antwortL, 4));

        List<Antwort> antwortenWF = new ArrayList<>();
        antwortenWF.add(new Antwort(true, "Wahr"));
        antwortenWF.add(new Antwort(false, "Falsch"));
        fragen.add(new Frage(Themenbereich.SQL, "Man kann mit sort by den Query-Output sortieren.", antwortenWF, 6));

        List<Antwort> antwortenMC = new ArrayList<>();
        antwortenMC.add(new Antwort(true, "order by"));
        antwortenMC.add(new Antwort(false, "group by"));
        antwortenMC.add(new Antwort(false, "sort by"));
        antwortenMC.add(new Antwort(false, "list by"));
        fragen.add(new Frage(Themenbereich.SQL, "Wie sortiere ich mein Query-Output?", antwortenMC, 10));

        fragen.add(new Frage(Themenbereich.SQL, "1Wie sortiere ich mein Query-Output?", antwortenMC, 1));
        fragen.add(new Frage(Themenbereich.SQL, "2Wie sortiere ich mein Query-Output?", antwortenMC, 2));
        fragen.add(new Frage(Themenbereich.SQL, "3Wie sortiere ich mein Query-Output?", antwortenMC, 3));
        fragen.add(new Frage(Themenbereich.SQL, "4Wie sortiere ich mein Query-Output?", antwortenMC, 4));
    }
}
