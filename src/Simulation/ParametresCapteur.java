package Simulation;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by jb on 28/11/16.
 */
public class ParametresCapteur extends JFrame {


    private JFormattedTextField idCapteur = new JFormattedTextField();
    private JComboBox typeDonnees = new JComboBox();
    private JTextField latitude = new JTextField();
    private JTextField longitude = new JTextField();
    private JComboBox batiment = new JComboBox();
    private JComboBox etage = new JComboBox();
    private JComboBox salle = new JComboBox();
    private JFormattedTextField posRelative = new JFormattedTextField();
    private ButtonGroup intExt = new ButtonGroup();
    private JFormattedTextField min = new JFormattedTextField();
    private JFormattedTextField max = new JFormattedTextField();
    private JButton valider = new JButton("Valider");
    private boolean isExterieur = false;
    private boolean isInterieur = false;
    private JLabel erreur = new JLabel();
    private ArrayList<String> typeExterieur = new ArrayList<>();
    private ArrayList<String> typeInterieur = new ArrayList<>();

    public ParametresCapteur() {
        super("Paramètres Capteurs");

        ImageIcon icone = new ImageIcon("icon.png");
        this.setIconImage(icone.getImage());

        typeInterieur.add("Température");
        typeInterieur.add("Humidité");
        typeInterieur.add("Luminosité");
        typeInterieur.add("Volume Sonore");
        typeInterieur.add("Consomation éclairage");
        typeInterieur.add("Eau froide");
        typeInterieur.add("Eau Chaude");

        typeExterieur.add("Température");
        typeExterieur.add("Humidité");
        typeExterieur.add("Luminosité");
        typeExterieur.add("Vitesse vent");
        typeExterieur.add("Pression atmosphérique");

        JPanel pLocalisation = new JPanel();
        JPanel spLoc = new JPanel();
        JPanel spBatiment = new JPanel();
        JPanel spEtage = new JPanel();
        JPanel spSalle = new JPanel();
        JPanel spPositionRelative = new JPanel();
        JPanel spLongLat = new JPanel();
        JLabel lLocalisation = new JLabel("Localisation");
        JLabel lBatiment = new JLabel("Bâtiment ");
        JLabel lEtage = new JLabel("Étage       ");
        JLabel lSalle = new JLabel("Salle        ");
        JLabel lLongitude = new JLabel("Longitude ");
        JLabel lLatitude = new JLabel("Latitude ");
        JLabel lPosRelative = new JLabel("Position relative");
        JPanel princParam = new JPanel();
        JPanel pIdCapteur = new JPanel();
        JLabel lidCapteur = new JLabel("Identifiant du capteur");
        JPanel pTypeDonnees = new JPanel();
        JLabel lTypedonnees = new JLabel("Type de donnée");
        JPanel pInterval = new JPanel();
        JLabel lInter1 = new JLabel("Intervalle de ");
        JLabel lInter2 = new JLabel(" à ");
        JPanel spValider = new JPanel();
        JPanel pValider = new JPanel();

        JRadioButton interieur = new JRadioButton("Intérieur ");
        JRadioButton exterieur = new JRadioButton("Extérieur ");


        batiment.addItem("Sélection");
        etage.addItem("Sélection");
        salle.addItem("Sélection");

        ArrayList<String> listeBat;
        listeBat = Parseur.getBatiments("position_capteur.txt");
        ListIterator<String> iterateur = listeBat.listIterator();
        batiment.removeAllItems();
        while (iterateur.hasNext()) {
            batiment.addItem(iterateur.next());
        }

        latitude.setEditable(false);
        longitude.setEditable(false);
        batiment.setEnabled(false);
        etage.setEnabled(false);
        salle.setEnabled(false);
        posRelative.setEditable(false);

        this.setTitle("Paramètres Capteurs");
        this.setSize(400, 350);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        princParam.setLayout(new FlowLayout(FlowLayout.LEFT));

        idCapteur.setColumns(20);
        pIdCapteur.add(lidCapteur);
        pIdCapteur.add(idCapteur);

        typeDonnees.addItem("Sélection");
        pTypeDonnees.add(lTypedonnees);
        pTypeDonnees.add(typeDonnees);

        pLocalisation.setLayout(new BoxLayout(pLocalisation, BoxLayout.Y_AXIS));
        posRelative.setColumns(20);
        latitude.setColumns(3);
        longitude.setColumns(3);
        batiment.setSize(10, 10);
        intExt.add(interieur);
        intExt.add(exterieur);
        spBatiment.add(lBatiment);
        spBatiment.add(batiment);
        spEtage.add(lEtage);
        spEtage.add(etage);
        spSalle.add(lSalle);
        spSalle.add(salle);
        spLoc.add(lLocalisation);
        spLoc.add(exterieur);
        spLoc.add(interieur);


        spLoc.setLayout(new FlowLayout(FlowLayout.LEFT));
        spBatiment.setLayout(new FlowLayout(FlowLayout.LEFT));
        spEtage.setLayout(new FlowLayout(FlowLayout.LEFT));
        spSalle.setLayout(new FlowLayout(FlowLayout.LEFT));
        spLongLat.setLayout(new FlowLayout(FlowLayout.LEFT));
        spPositionRelative.setLayout(new FlowLayout(FlowLayout.LEFT));
        valider.setLayout(new FlowLayout(FlowLayout.CENTER));

        spPositionRelative.add(lPosRelative);
        spPositionRelative.add(posRelative);
        spLongLat.add(lLatitude);
        spLongLat.add(latitude);
        spLongLat.add(lLongitude);
        spLongLat.add(longitude);
        pLocalisation.add(spLoc);
        pLocalisation.add(spLongLat);
        pLocalisation.add(spBatiment);
        pLocalisation.add(spEtage);
        pLocalisation.add(spSalle);
        pLocalisation.add(spPositionRelative);

        min.setColumns(3);
        max.setColumns(3);
        pInterval.add(lInter1);
        pInterval.add(min);
        pInterval.add(lInter2);
        pInterval.add(max);

        spValider.add(pValider);
        pValider.add(valider);
        princParam.add(pIdCapteur);
        princParam.add(pLocalisation);
        princParam.add(pTypeDonnees);
        princParam.add(pInterval);
        princParam.add(spValider);
        princParam.add(erreur);


        interieur.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                int i;
                isInterieur = true;
                isExterieur = false;
                latitude.setEditable(false);
                longitude.setEditable(false);
                batiment.setEnabled(true);
                etage.setEnabled(true);
                salle.setEnabled(true);
                posRelative.setEditable(true);
                typeDonnees.removeAllItems();
                for (i = 0; i < typeInterieur.size(); i++) {
                    typeDonnees.addItem(typeInterieur.get(i));
                }
            }
        });

        exterieur.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                int i;
                isExterieur = true;
                isInterieur = false;
                latitude.setEditable(true);
                longitude.setEditable(true);
                batiment.setEnabled(false);
                etage.setEnabled(false);
                salle.setEnabled(false);
                posRelative.setEditable(false);
                typeDonnees.removeAllItems();
                for (i = 0; i < typeExterieur.size(); i++) {
                    typeDonnees.addItem(typeExterieur.get(i));
                }
            }
        });


                batiment.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent eventBatiment) {
                        int i;
                        ArrayList<String> listeEtage;
                        listeEtage = Parseur.getEtagesForBatiment("position_capteur.txt", batiment.getSelectedItem().toString());
                        etage.removeAllItems();
                        for (i = 0; i < listeEtage.size(); i++) {
                            etage.addItem(listeEtage.get(i));
                        }
                    }
                });

        etage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent eventEtage) {
                int i;
                if (etage.getSelectedItem() != null) {
                    ArrayList<String> listeSalle;
                    listeSalle = Parseur.getSallesForEtageAndBatiment("position_capteur.txt", batiment.getSelectedItem().toString(), etage.getSelectedItem().toString());
                    salle.removeAllItems();
                    for (i = 0; i < listeSalle.size(); i++) {
                        salle.addItem(listeSalle.get(i));
                    }
                }
            }
        });

        this.setContentPane(princParam);

        this.setVisible(true);
        this.setFocusable(true);
    }


    public JFormattedTextField getIdCapteur() {
        return idCapteur;
    }

    public JComboBox getTypeDonnees() {
        return typeDonnees;
    }

    public JTextField getLatitude() {
        return latitude;
    }

    public JTextField getLongitude() {
        return longitude;
    }

    public JComboBox getBatiment() {
        return batiment;
    }

    public JComboBox getEtage() {
        return etage;
    }

    public JComboBox getSalle() {
        return salle;
    }

    public JFormattedTextField getPosRelative() {
        return posRelative;
    }

    public ButtonGroup getIntExt() {
        return intExt;
    }

    public JFormattedTextField getMin() {
        return min;
    }

    public JFormattedTextField getMax() {
        return max;
    }

    public JButton getValider() {
        return valider;
    }

    public JLabel getErreur(){
        return erreur;
    }

    public boolean isExterieur() {
        return isExterieur;
    }

    public boolean isInterieur() {
        return isInterieur;
    }

    public void setExterieur(boolean exterieur) {
        isExterieur = exterieur;
    }

    public void setInterieur(boolean interieur) {
        isInterieur = interieur;
    }

    private void printErr(String msg)
    {
        this.erreur.setForeground(Color.red);
        this.erreur.setText(msg);
        this.setSize(400,400);
    }

    private boolean checkComboBox()
    {
        if (isInterieur) {
            if (this.getBatiment().getSelectedItem().toString().equals("Sélection") || this.getEtage().getSelectedItem().toString().equals("Sélection") || this.getSalle().getSelectedItem().toString().equals("Sélection")) {
                printErr("Erreur : Où se trouve votre capteur ?");
                return false;
            }
        }
            return true;
    }

    private boolean checkChampTexte(JFormattedTextField champText)
    {
        if (champText.getText().equals("")) {
            printErr("Erreur : Aucun champs ne doit être vide !");
            return false;
        }
        else if (champText.getText().contains(";"))
        {
            printErr("Erreur : Aucun champs de doit contenir le symbole \";\" !");
            return false;
        }
        else
        {
            return true;
        }

    }

    private boolean checkIntervalle()
    {
        try{
            if (isExterieur) {
                Integer.parseInt(latitude.getText());
                Integer.parseInt(longitude.getText());
            }
            Integer.parseInt(min.getText());
            Integer.parseInt(max.getText());
        }catch (NumberFormatException numFE)
        {
            printErr("Erreur : Des entiers sont attendu !");
            return false;
        }
        if (isExterieur) {
            if (latitude.getText().equals("") || longitude.getText().equals(""))
            {
                printErr("Erreur : La latitude comprise entre -90° et 90° !");
                return false;
            }
            if (Double.parseDouble(latitude.getText()) < -90 || Double.parseDouble(latitude.getText()) > 90) {
                printErr("Erreur : La latitude comprise entre -90° et 90° !");
                return false;
            }
            if (Double.parseDouble(longitude.getText()) < -180 || Double.parseDouble(longitude.getText()) > 180) {
                printErr("Erreur : La longitude comprise entre -180° et 180° !");
                return false;
            }
        }
        if (min.getText().equals("") || max.getText().equals(""))
        {
            printErr("Erreur : L'intervalle doit possèder des valeurs");
            return false;
        }
        if (Integer.parseInt(min.getText()) > Integer.parseInt(max.getText()))
        {
            printErr("Erreur : Cet intervalle n'est pas sensé !");
            return false;
        }
        return true;
    }

    public boolean checkFormulaireParamCapteur()
    {
        if (isExterieur == false && isInterieur == false)
        {
            printErr("Erreur : Où se trouve votre capteur ?");
            return false;
        }
        if (isExterieur)
        {
            return (this.checkIntervalle() && this.checkChampTexte(idCapteur) && this.checkComboBox());
        }
        else
        {
            return (this.checkIntervalle() && this.checkChampTexte(idCapteur) && this.checkChampTexte(posRelative) && this.checkComboBox());
        }
    }
}
