import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

// ---------- THEMENBEREICHE PANEL ----------
        JPanel panelThemen = new JPanel();
        panelThemen.setLayout(new GridLayout(3, 1, 10, 10));

        JLabel auswahlLabel = new JLabel("Wähle einen Themenbereich:", SwingConstants.CENTER);
        auswahlLabel.setFont(new Font("Arial", Font.BOLD, 22));

        JButton thema1 = new JButton("Themenbereich 1");
        JButton thema2 = new JButton("Themenbereich 2");
        JButton thema3 = new JButton("Themenbereich 3");
        JButton thema4 = new JButton("Themenbereich 4");
        JButton thema5 = new JButton("Themenbereich 5");

        panelThemen.add(auswahlLabel);
        panelThemen.add(thema1);
        panelThemen.add(thema2);
        panelThemen.add(thema3);
        panelThemen.add(thema4);
        panelThemen.add(thema5);

// ---------- ACTION: Startbutton ----------
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(panelThemen);
                revalidate();
                repaint();
            }
        });

// ---------- ACTION: Themenbereiche öffnen ----------
        ActionListener themaListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton btn = (JButton) e.getSource();
                String thema = btn.getText();
                JOptionPane.showMessageDialog(
                        Startbildschirm.this,
                        "Themenbereich geöffnet: " + thema,
                        "Thema",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        };

        thema1.addActionListener(themaListener);
        thema2.addActionListener(themaListener);
        thema3.addActionListener(themaListener);
        thema4.addActionListener(themaListener);
        thema5.addActionListener(themaListener);

// ---------- Startscreen zunächst anzeigen ----------
        setContentPane(panelStart);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Startbildschirm gui = new Startbildschirm();
            gui.setVisible(true);
        });
    }
}


