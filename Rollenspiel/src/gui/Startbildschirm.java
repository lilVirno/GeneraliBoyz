package gui;

import backend.Frage;
import backend.FragenRepository;
import enums.Fragenkategorie;
import enums.Themenbereich;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class Startbildschirm extends JFrame {

    public Startbildschirm() {
        setTitle("Gamification – Lernspiel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // zentrieren

// ---------- STARTBILDSCHIRM PANEL ----------
        JPanel panelStart = new JPanel();
        panelStart.setLayout(new BorderLayout());

        JLabel titel = new JLabel("Willkommen zum Lernspiel!", SwingConstants.CENTER);
        titel.setFont(new Font("Arial", Font.BOLD, 26));

        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.PLAIN, 22));

        panelStart.add(titel, BorderLayout.CENTER);
        panelStart.add(startButton, BorderLayout.SOUTH);

        // ---------- ACTION: Themenbereiche öffnen ----------
        ActionListener themaListener = e -> {
            JButton btn = (JButton) e.getSource();
            String text = btn.getText();
            // passenden Enum finden
            Themenbereich thema = Arrays.stream(Themenbereich.values())
                    .filter(t -> t.name().equals(text))
                    .findFirst()
                    .orElse(null);
            if (thema == null) { JOptionPane.showMessageDialog(this, "Unbekanntes Thema: " + text); return; }
            // Fragen laden
            List<Frage> fragen = FragenRepository.getAlleFragen().stream()
                    .filter(f -> f.getThemenbereich() == thema)
                    .toList();
            if (fragen.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Keine Fragen für dieses Thema gefunden.");
                return;
            }
            oeffneFrageGUI(fragen.get(0));
        };

// ---------- THEMENBEREICHE PANEL ----------
        JPanel panelThemen = new JPanel(new GridLayout(0, 1, 10, 10));
        //panelThemen.setLayout(new GridLayout(3, 1, 10, 10));

        JLabel auswahlLabel = new JLabel("Wähle einen Themenbereich:", SwingConstants.CENTER);
        auswahlLabel.setFont(new Font("Arial", Font.BOLD, 22));
        panelThemen.add(auswahlLabel);

        for (Themenbereich tb : Themenbereich.values()) {
            JButton btn = new JButton(tb.name());
            btn.setFont(new Font("Arial", Font.PLAIN, 20));
            btn.addActionListener(themaListener);
            panelThemen.add(btn);
        }

// ---------- ACTION: Startbutton ----------
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(panelThemen);
                revalidate();
                repaint();
            }
        });

// ---------- Startscreen zunächst anzeigen ----------
        setContentPane(panelStart);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Startbildschirm gui = new Startbildschirm();
            gui.setVisible(true);
        });
    }

    private void oeffneFrageGUI(Frage frage) {

        JPanel panel;

        switch (frage.getFragenkategorie()) {
            case Fragenkategorie.MULTIPLE_CHOICE -> panel = new MultipleChoiceGUI(frage);
            case Fragenkategorie.WAHR_FALSCH -> panel = new WahrFalschGUI(frage);
            case Fragenkategorie.LUECKENTEXT -> panel = new LueckentextGUI(frage);
            default -> {
                JOptionPane.showMessageDialog(this, "Unbekannte Fragenkategorie!");
                return;
            }
        }

        JFrame frame = new JFrame("backend.Frage");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}


