package gui;

import backend.Frage;

import javax.swing.*;
import java.awt.*;

public class LueckentextGUI extends JPanel {

    public LueckentextGUI(Frage frage) {

        setLayout(new BorderLayout());

        JLabel frageLabel = new JLabel(frage.getFrage(), SwingConstants.CENTER);
        frageLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(frageLabel, BorderLayout.NORTH);

        JTextField eingabe = new JTextField();
        add(eingabe, BorderLayout.CENTER);

        JButton pruefen = new JButton("Prüfen");
        pruefen.addActionListener(e -> {
            String korrekt = frage.getKorrekteAntwort();
            if (eingabe.getText().trim().equalsIgnoreCase(korrekt)) {
                JOptionPane.showMessageDialog(this, "Richtig!");
            } else {
                JOptionPane.showMessageDialog(this, "Falsch!");
            }
        });

        add(pruefen, BorderLayout.SOUTH);
    }
}
