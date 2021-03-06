package Simulation;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * Created by msi on 23/11/2016.
 */
public class SimulationInterface {


    /* Infos du capteur */
    private String id;
    private String type;
    private Localisation localisation;
    private int valeurMin;
    private int valeurMax;

    /* Info reseau */
    private ServicesReseau servicesReseau;
    private String ip;
    private int port;
    private int valeurEnvoi;
    private EnvoiThread envoiThread = null;

    /* Fenetres de l'application */
    private ParametresCapteur parametresCapteur;
    private FenetreConnexionIP fenetreConnexionIP;
    private FenetreGestionEnvoi fenetreGestionEnvoi;


    public SimulationInterface () {
        /* Gestionnaire des services reseau */
        this.servicesReseau = new ServicesReseau();

        /* fenetre de creation du capteur */
        this.parametresCapteur = new ParametresCapteur();

        /* creation de la fenetre de connexion IP */
        this.fenetreConnexionIP = new FenetreConnexionIP();
        this.fenetreConnexionIP.setVisible(false);

        /* creation de la fenetre de gestion d'envoi */
        this.fenetreGestionEnvoi = new FenetreGestionEnvoi();
        this.fenetreGestionEnvoi.setVisible(false);

        /* Gestion evenement de la fenetre de creatin du capteur */
        this.parametresCapteur.getValider().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent clickValider) {
                super.mouseClicked(clickValider);
                if (parametresCapteur.checkFormulaireParamCapteur()) {
                    parametresCapteur.getErreur().setText("");
                    parametresCapteur.setSize(400, 350);
                    id = parametresCapteur.getIdCapteur().getText();
                    type = parametresCapteur.getTypeDonnees().getSelectedItem().toString();
                    valeurMin = Integer.parseInt(parametresCapteur.getMin().getText());
                    valeurMax = Integer.parseInt(parametresCapteur.getMax().getText());

                    if (parametresCapteur.isExterieur()) {
                        try {
                            localisation = new LocalisationExterieur("exterieur", Double.parseDouble(parametresCapteur.getLatitude().getText()), Double.parseDouble(parametresCapteur.getLongitude().getText()));
                        } catch (Exception jLang1) {
                            jLang1.printStackTrace();
                        }
                    } else {
                        try {
                            localisation = new LocalisationInterieur("interieur",
                                    parametresCapteur.getBatiment().getSelectedItem().toString(),
                                    parametresCapteur.getEtage().getSelectedItem().toString(),
                                    parametresCapteur.getSalle().getSelectedItem().toString(),
                                    parametresCapteur.getPosRelative().getText());
                        } catch (Exception jLang2) {
                            jLang2.printStackTrace();
                        }
                    }
                    parametresCapteur.setVisible(false);
                    fenetreConnexionIP.setVisible(true);
                    fenetreConnexionIP.setFocusable(true);

                }
            }
        });

        /* Gestion d'evenement de la fenetre de connexion */
        this.fenetreConnexionIP.getButtonCancel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                fenetreConnexionIP.setVisible(false);
                parametresCapteur.setVisible(true);
                parametresCapteur.setFocusable(true);
            }
        });

        this.fenetreConnexionIP.getButtonConnection().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (fenetreConnexionIP.verifIP(fenetreConnexionIP.getJtfIP().getText()) && (int)fenetreConnexionIP.getJtfPORT().getValue() >= 0) {
                    fenetreConnexionIP.getLabelError().setText("");
                    ip = fenetreConnexionIP.getJtfIP().getText();
                    port = (int)fenetreConnexionIP.getJtfPORT().getValue();

                    try {
                        if(connexionCapteur().equals("ConnexionKO")) {
                            fenetreConnexionIP.printErr("Erreur lors de la connexion au serveur");
                        } else {
                            fenetreConnexionIP.setVisible(false);
                            fenetreGestionEnvoi.setVisible(true);
                            fenetreGestionEnvoi.setFocusable(true);
                        }
                    } catch (IOException e1) {
                        fenetreConnexionIP.printErr("Erreur lors de la connexion au serveur");
                    }
                } else {
                    fenetreConnexionIP.printErr("L'adresse IP et le port doivent etre valide.");
                }
            }
        });

        /* Gestion des evenements de la fenetre d'envoi de donnees */
        this.fenetreGestionEnvoi.getDeconnexion().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                callbackDeconnexion();
            }
        });

        this.fenetreGestionEnvoi.getEnvoi().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (envoiThread != null)
                    envoiThread.interrupt();
                if (((int) fenetreGestionEnvoi.getFrequenceEnvoi().getValue() > 0) &&
                        (fenetreGestionEnvoi.isAlea() || (((int) fenetreGestionEnvoi.getValeur().getValue() >= valeurMin) && (int) fenetreGestionEnvoi.getValeur().getValue() <= valeurMax)))
                {
                    envoiThread = new EnvoiThread(servicesReseau,
                            (int) fenetreGestionEnvoi.getFrequenceEnvoi().getValue(),
                            fenetreGestionEnvoi.isAlea(),
                            (int) fenetreGestionEnvoi.getValeur().getValue(),
                            valeurMin,
                            valeurMax);

                    envoiThread.start();
                    fenetreGestionEnvoi.getEnvoi().setText("Rafraichir");
                    fenetreGestionEnvoi.getErreurText().setText("");
                } else {
                    if (((int) fenetreGestionEnvoi.getFrequenceEnvoi().getValue() <= 0))
                        fenetreGestionEnvoi.printErr("Erreur : La fréquence doit être supérieure a 0.");
                    else
                        fenetreGestionEnvoi.printErr("Erreur : La valeur doit être comprise entre : " + valeurMin + " et " + valeurMax);
                }
            }
        });
    }

    /**
     * connecte un capteur au serveur de gestion
     * @return String : etat de la connexion
     * @throws IOException
     */
    public String connexionCapteur () throws IOException {
        String res = "";
        if (!this.servicesReseau.estConnecte()) {
            this.servicesReseau.connexion(this.ip, this.port);
            this.servicesReseau.envoyer("ConnexionCapteur;" + this.id + ";" + this.type + ";" + this.localisation.getStringForConnexion() + "\n");
        } else {
            throw new IOException();
        }
        res = this.servicesReseau.recevoir();
        if (res.equals("ConnexionKO")) {
            this.servicesReseau.deconnexion();
        }
        return res;
    }

    /**
     * deconnecte un capteur du serveur de gestion
     * @return String : etat de la deconnexion
     * @throws IOException
     */
    public String deconnexionCapteur () throws IOException {
        String res = "DeconnexionKO";
        if (this.servicesReseau.estConnecte()) {
            this.servicesReseau.envoyer("DeconnexionCapteur;" + this.id + "\n");
            res = this.servicesReseau.recevoir();
            if (res.equals("DeconnexionOK")) {
                this.servicesReseau.deconnexion();
            } else {
                this.servicesReseau.deconnexion();
            }
        }
        return res;
    }

    /**
     * fonction de deconnection (gestion du thread d'envoi)
     */
    public void callbackDeconnexion () {
        try {
            deconnexionCapteur();
        } catch (IOException e) {
            /*ne rien faire*/
        } finally {
            if (this.envoiThread != null && this.envoiThread.isRunning())
                this.envoiThread.setRunning(false);
            this.fenetreGestionEnvoi.setVisible(false);
            this.fenetreGestionEnvoi.getEnvoi().setText("Envoi des données");
            this.fenetreConnexionIP.setVisible(true);
            this.fenetreConnexionIP.setFocusable(true);
        }
    }

    public static void main (String [] args) {
        SimulationInterface simulationInterface = new SimulationInterface();
    }
}
