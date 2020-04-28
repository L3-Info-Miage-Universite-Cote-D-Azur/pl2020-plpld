package com.androidapp.reseau;
import android.util.Log;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import constantes.Net;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import metier.Etudiant;
import metier.Identité;
import metier.ToJSON;
import com.androidapp.vue.*;
import com.androidapp.vue.activity.InscriptionActivity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *  Classe Connexion qui s'occupe de traiter les messages réceptionnés par le client
 */
public enum Connexion implements RecevoirMessage {

    CONNEXION;
    /**
     *  Variable mSocket, qui va se charger du emit et de recevoir les messages
     */
    private Socket mSocket;


    /**
     *  Boolean qui traite si la connexion d'un étudiant est autorisée
     */

    private Boolean ConnexionAutorisee = false;
    /**
     *  La variable de la vue principale
     */
    private Vue mainVue;
    /**
     *  La map des prerequis pour chaque UE, qui sera construite à la reception du message du serveur
     */
    private Map<String,List<String>> MapPrerequis = new HashMap<>();
    /**
     *  La map des selection des UE du l'utilisateur
     */
    private Map<Integer, List<String>> selectionUE = new HashMap<>();

    /**
     *  La map des parcours prédefini, qui sera construite à la reception des messages du serveur
     */
    private Map<String, Map<Integer, List<String>>> MapPredefini = new HashMap<>();

    /**
     *  La liste de tous les numéros étudiants inscrits
     */
    private List<String> numEtudiants = new ArrayList<>();


    private Etudiant etudiant;

    private Map<Etudiant, List<String>> consultationUE = new HashMap<>();


    public String predefini = "Personnalisé";
    private boolean InscriptionAutorisee = false;


    /**
     *  Connexion de la socket
     */
    Connexion() {
        try {
            mSocket = IO.socket("http://10.0.2.2:10101");
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
        écouterRéseau();
    }

    /**
     *  méthode qui s'occupe d'écouter et d'interpréter les différents évenements (messages) envoyés par le serveur
     */
    public void écouterRéseau() {

        /**
         *  Ici, on implémente des classes anonymes pour chaque type de message, afin que le client sache quoi faire pour chaque évenement envoyé par le serveur
         */


        /**
         *  Le client recupère la map de l'UE envoyée par le serveur
         *  Elle est sérialisée par Jackson et interprétée en Map<String,List<String>>
         *  On la donne ensuite à la vue pour qu'elle puisse l'afficher, à l'aide d'une autre méthode
         *  Cette implémentation est utilisée à plusieurs reprise dans le code
         */
        recevoirMessage(Net.UE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    HashMap<String, List<String>> map = objectMapper.readValue(args[0].toString(), new TypeReference<Map<String, List<String>>>() {
                    });
                    mainVue.receptionUE(map);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });


        /**
         *  Même méthode que la méthode précedente, sauf qu'elle gère spécifiquement le S1
         */
        recevoirMessage(Net.ENVOIE_S1, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    HashMap<String, List<String>> map = objectMapper.readValue(args[0].toString(), new TypeReference<Map<String, List<String>>>() {
                    });
                    mainVue.receptionUE(map);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        /**
         *  Même méthode, mais pour les parcours prédéfini
         */
        recevoirMessage(Net.PREDEFINI, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    Map<String, Map<Integer, List<String>>> map = objectMapper.readValue(args[0].toString(), new TypeReference<Map<String, Map<Integer, List<String>>>>() {
                    });
                    MapPredefini = map;
                    mainVue.receptionPredefini(map);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });


        /**
         *  Même méthode, à l'exception qu'elle gère la reception des 4 semestres d'un coup
         */
        recevoirMessage(Net.ENVOIE_TOUT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    List<Map<String, List<String>>> ListeUE = objectMapper.readValue(args[0].toString(), new TypeReference<List<Map<String, List<String>>>>() {
                    });
                    mainVue.receptionTout(ListeUE);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });


        /**
         *  Cette méthode interprète la réponse du serveur de la tentative de connexion qui a autorisé la connexion ou non.
         */
        recevoirMessage(Net.NV_CONNEXION, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("BOOLEAN",args[0].toString());
                if(args[0].toString().equals("true"))
                {
                    Log.d("BOOLEAN","PASSE");

                    ConnexionAutorisee = true;
                }}
        });

        /**
         *  Cette méthode interprète la map envoyée par le serveur, et la copie dans le champ de la classe
         */
        recevoirMessage(Net.PREREQUIS, new Emitter.Listener() {
            ObjectMapper objectMapper2 = new ObjectMapper();
            @Override
            public void call(Object... args) {
                try {
                    MapPrerequis = objectMapper2.readValue(args[0].toString(), new TypeReference<Map<String, List<String>>>() {
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        /**
         *  Cette méthode s'occupe d'interpréter le message du serveur quand le client fait une requête de mot de passe oublié
         *  Un objet étudiant est envoyé au client qui est interprété par cette méthode et qui assigne le message au champ étudiant
         */
        recevoirMessage(Net.ENVOIE_PASSWORD, new Emitter.Listener() {
            ObjectMapper objectMapper2 = new ObjectMapper();
            @Override
            public void call(Object... args) {
                try {
                    etudiant = objectMapper2.readValue(args[0].toString(), new TypeReference<Etudiant>() {
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        /**
         *  Même type de méthode que pour la connexion
         */
        recevoirMessage(Net.NV_ETU, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                if(args[0].toString().equals("true"))
                {
                    InscriptionAutorisee = true;
                }}
        });

        /**
         *  Cette méthode s'occupe d'interpréter le message du serveur quand le client fait une requête pour consulter les UE des autres étudiants.
         *  Une map contenant tous les étudiants et les UE qu'ils ont choisies est alors transmise
         */
        recevoirMessage(Net.ENVOIE_CONSULTATION, new Emitter.Listener() {
            ObjectMapper objectMapper2 = new ObjectMapper();
            @Override
            public void call(Object... args) {
                try {
                    consultationUE = objectMapper2.readValue(args[0].toString(), new TypeReference<Map<Etudiant, List<String>>>() {
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }});

        /**
         *  Cette méthode s'occupe de recenser tous les numéros étudiants déjà inscrits
         */
        recevoirMessage(Net.NUM_ETUDIANTS, new Emitter.Listener() {
            ObjectMapper objectMapper2 = new ObjectMapper();
            @Override
            public void call(Object... args) {
                Log.d("Num étudiants activé", "");
                try {
                    numEtudiants = objectMapper2.readValue(args[0].toString(), new TypeReference<List<String>>() {
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }});
    }


    public void setSocket(Socket socket) {
        mSocket = socket;
    }

    public void recevoirMessage(String event, Emitter.Listener fn) {
        mSocket.on(event, fn);
    }

    public void démarrerÉcoute() {
        mSocket.connect();
    }

    public void envoyerMessage(String msg, ToJSON obj) {
        Log.d("Message envoyé", msg);
        mSocket.emit(msg, obj.toJSON());
    }

    public void envoyerMessage2(String msg, ToJSON obj) {
        mSocket.emit(msg, obj);
    }

    public void setMainVue(Vue v) {
        mainVue = v;
    }

    public void disconnect() {
        if (mSocket != null) mSocket.disconnect();
    }

    public void envoyer(String msg) {
        mSocket.emit(msg);
    }

    public Boolean getConnexionAutorisee() {
        return ConnexionAutorisee;
    }

    public void setConnexionAutorisee(Boolean connexionAutorisee) {
        ConnexionAutorisee = connexionAutorisee;
    }

    public Socket getmSocket() {
        return mSocket;
    }

    public Vue getMainVue() {
        return mainVue;
    }

    public Map<String, List<String>> getMapPrerequis() {
        return MapPrerequis;
    }

    public Map<Integer, List<String>> getSelectionUE() {
        return selectionUE;
    }

    public Map<String, Map<Integer, List<String>>> getMapPredefini() {
        return MapPredefini;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public String getPredefini() {
        return predefini;
    }

    public boolean isInscriptionAutorisee() {
        return InscriptionAutorisee;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Map<Etudiant, List<String>> getConsultationUE() {
        return consultationUE;
    }

    public void resetConsultationUE() {
        consultationUE = new HashMap<>();
    }

    public List<String> getNumEtudiants() {
        return numEtudiants;
    }
}