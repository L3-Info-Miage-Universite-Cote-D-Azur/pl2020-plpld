package com.androidapp.reseau;
import android.util.Log;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import constantes.Net;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import metier.Etudiant;
import metier.ToJSON;
import com.androidapp.vue.*;
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

    public Boolean ConnexionAutorisee = false;
    /**
     *  La variable de la vue principale
     */
    public Vue mainVue;
    /**
     *  La map des prerequis pour chaque UE, qui sera construite à la reception du message du serveur
     */
    public Map<String,List<String>> MapPrerequis = new HashMap<>();
    /**
     *  La map des selection des UE du l'utilisateur
     */
    public Map<Integer, List<String>> selectionUE = new HashMap<>();

    /**
     *  La map des parcours prédefini, qui sera construite à la reception des messages du serveur
     */
    public Map<String, Map<Integer, List<String>>> MapPredefini = new HashMap<>();

    public Etudiant etudiant;


    public String predefini = "Personnalisé";


    /**
     *  Connexion de la socket
     */
    Connexion() {
        try {
            mSocket = IO.socket("http://10.0.2.2:10101");
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
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
}