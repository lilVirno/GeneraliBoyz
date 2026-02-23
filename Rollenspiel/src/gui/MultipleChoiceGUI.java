package gui;

import backend.Antwort;
import backend.Frage;

import javax.swing.*;
import java.awt.*;

public class MultipleChoiceGUI extends JPanel {

    public MultipleChoiceGUI(Frage frage) {

        setLayout(new BorderLayout());

        JLabel frageLabel = new JLabel(frage.getFrage(), SwingConstants.CENTER);
        frageLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(frageLabel, BorderLayout.NORTH);

        JPanel antwortPanel = new JPanel(new GridLayout(4, 1, 10, 10));

        for (Antwort antwort : frage.getAntworten()) {
            JButton btn = new JButton(antwort.getAntwort());
            btn.addActionListener(e -> pruefeAntwort(frage, antwort));
            antwortPanel.add(btn);
        }
        add(antwortPanel, BorderLayout.CENTER);
    }

    private void pruefeAntwort(Frage frage, Antwort antwort) {
        if (antwort.isRichtig()) {
            JOptionPane.showMessageDialog(this, "Richtig!");
        } else {
            JOptionPane.showMessageDialog(this, "Falsch!");
        }
    }
}
