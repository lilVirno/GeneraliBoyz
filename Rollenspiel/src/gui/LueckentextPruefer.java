package gui;

import java.util.List;

public class LueckentextPruefer {

    public static boolean alleRichtig(List<String> eingaben, List<String> korrekteAntworten) {
        if (eingaben == null || korrekteAntworten == null) return false;
        if (eingaben.size() != korrekteAntworten.size()) return false;

        for (int i = 0; i < korrekteAntworten.size(); i++) {
            String e = eingaben.get(i) == null ? "" : eingaben.get(i).trim();
            String k = korrekteAntworten.get(i) == null ? "" : korrekteAntworten.get(i).trim();

            if (!e.equalsIgnoreCase(k)) {
                return false;
            }
        }

        return true;
    }

}
