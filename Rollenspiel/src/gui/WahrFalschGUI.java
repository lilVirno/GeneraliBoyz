package gui;

import backend.Antwort;
import backend.Frage;

import javax.swing.*;
import java.awt.*;

public class WahrFalschGUI extends JPanel {

    public WahrFalschGUI(Frage frage) {
        setLayout(new BorderLayout());

        JLabel frageLabel = new JLabel(frage.getFrage(), SwingConstants.CENTER);
        frageLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(frageLabel, BorderLayout.NORTH);

        JPanel buttons = new JPanel(new GridLayout(1, 2, 20, 20));

        for (Antwort antwort : frage.getAntworten()) {
            JButton btn = new JButton(antwort.getAntwort());
            btn.addActionListener(e -> pruefeAntwort(antwort));
            buttons.add(btn);
        }

        add(buttons, BorderLayout.CENTER);
    }

    private void pruefeAntwort(Antwort antwort) {
        if (antwort.isRichtig()) {
            JOptionPane.showMessageDialog(this, "Richtig!");
        } else {
            JOptionPane.showMessageDialog(this, "Falsch!");
        }
    }
}
