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


        fragen.add(new Frage(Themenbereich.SQL, "Richtig oder Falsch?", antwortenMultiAntwort,100));


        List<Antwort> antwortL1 = new ArrayList<>();
        antwortL1.add(new Antwort(true, "Sort By"));
        fragen.add(new Frage(Themenbereich.SQL, "Man kann den Query-Output mit ____ __ sortieren.", antwortL1, 4));


        List<Antwort> antwortL2 = new ArrayList<>();
        antwortL2.add(new Antwort(true, "WHERE"));
        antwortL2.add(new Antwort(true, "GROUP BY"));
        fragen.add(new Frage(
                Themenbereich.SQL,
                "In SQL kann man Daten mit ____ filtern und mit ____ gruppieren.",
                antwortL2,
                6   // Punkte
        ));



        List<Antwort> antwortL7 = new ArrayList<>();
        antwortL7.add(new Antwort(true, "FROM"));
        antwortL7.add(new Antwort(true, "JOIN"));
        antwortL7.add(new Antwort(true, "WHERE"));
        antwortL7.add(new Antwort(true, "GROUP BY"));
        antwortL7.add(new Antwort(true, "HAVING"));
        antwortL7.add(new Antwort(true, "SELECT"));
        antwortL7.add(new Antwort(true, "ORDER BY"));

        fragen.add(new Frage(
                Themenbereich.SQL,
                "Sortiere die logische Ausführungsreihenfolge einer SQL-Abfrage: \n" +
                        "WHERE, HAVING, FROM, ORDER BY, SELECT, JOIN und GROUP BY. \n"+
                        "1) ____, 2) ____, 3) ____, 4) ____, 5) ____, 6) ____ 7) ____.",
                antwortL7, 14

                ));


        List<Antwort> antwortenWF = new ArrayList<>();
        antwortenWF.add(new Antwort(true, "Wahr"));
        antwortenWF.add(new Antwort(false, "Falsch"));
        fragen.add(new Frage(Themenbereich.SQL, "Man kann mit sort by den Query-Output sortieren.", antwortenWF,6));


        List<Antwort> antwortenMC = new ArrayList<>();
        antwortenMC.add(new Antwort(true, "order by"));
        antwortenMC.add(new Antwort(false, "group by"));
        antwortenMC.add(new Antwort(false, "sort by"));
        antwortenMC.add(new Antwort(false, "list by"));
        fragen.add(new Frage(Themenbereich.SQL, "Wie sortiere ich mein Query-Output?",  antwortenMC ,10));


        fragen.add(new Frage(Themenbereich.SQL, "1Wie sortiere ich mein Query-Output?", antwortenMC ,1));
        fragen.add(new Frage(Themenbereich.SQL, "2Wie sortiere ich mein Query-Output?", antwortenMC,2));
        fragen.add(new Frage(Themenbereich.SQL, "3Wie sortiere ich mein Query-Output?", antwortenMC, 3));
        fragen.add(new Frage(Themenbereich.SQL, "4Wie sortiere ich mein Query-Output?", antwortenMC,4));
    }
}
