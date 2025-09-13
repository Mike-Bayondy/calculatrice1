package calculatriceapp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CalculatriceApp extends JFrame implements ActionListener {

    private JTextField ecran;
    private double premierNombre = 0;
    private String operateur = "";
    private boolean nouvelleSaisie = true;

    public CalculatriceApp() {
        super("EDOUARD CALCUL"); // ✅ Nouveau titre

        // Zone d’affichage
        ecran = new JTextField("0");
        ecran.setEditable(false);
        ecran.setFont(new Font("Arial", Font.BOLD, 28));
        ecran.setHorizontalAlignment(SwingConstants.RIGHT);

        // Boutons
        String[] boutons = {
            "7","8","9","/",
            "4","5","6","*",
            "1","2","3","-",
            "0",".","=","+",
            "C"
        };

        JPanel panelBoutons = new JPanel(new GridLayout(5, 4, 8, 8));

        for (String texte : boutons) {
            JButton b = new JButton(texte);
            b.setFont(new Font("Arial", Font.BOLD, 22));
            b.setBackground(new Color(240, 240, 240)); 
            b.setFocusPainted(false);
            b.addActionListener(this);
            panelBoutons.add(b);
        }

        // Layout principal
        setLayout(new BorderLayout(10, 10));
        add(ecran, BorderLayout.NORTH);
        add(panelBoutons, BorderLayout.CENTER);

        // Fenêtre
        setSize(350, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = ((JButton)e.getSource()).getText();

        if ("0123456789.".contains(cmd)) {
            if (nouvelleSaisie) {
                ecran.setText(cmd.equals(".") ? "0." : cmd);
                nouvelleSaisie = false;
            } else {
                ecran.setText(ecran.getText() + cmd);
            }
        } else if ("+-*/".contains(cmd)) {
            premierNombre = Double.parseDouble(ecran.getText());
            operateur = cmd;
            nouvelleSaisie = true;
        } else if (cmd.equals("=")) {
            double deuxiemeNombre = Double.parseDouble(ecran.getText());
            double resultat = 0;
            switch (operateur) {
                case "+": resultat = premierNombre + deuxiemeNombre; break;
                case "-": resultat = premierNombre - deuxiemeNombre; break;
                case "*": resultat = premierNombre * deuxiemeNombre; break;
                case "/": 
                    if (deuxiemeNombre == 0) {
                        ecran.setText("Erreur");
                        return;
                    }
                    resultat = premierNombre / deuxiemeNombre; 
                    break;
            }

            // ✅ Supprimer les ".0" inutiles
            if (resultat == (long) resultat) {
                ecran.setText(String.valueOf((long) resultat));
            } else {
                ecran.setText(String.valueOf(resultat));
            }

            nouvelleSaisie = true;
        } else if (cmd.equals("C")) {
            ecran.setText("0");
            premierNombre = 0;
            operateur = "";
            nouvelleSaisie = true;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculatriceApp());
    }
}
