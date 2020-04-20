package metier;

import java.util.*;

public class Graphe {
    /** Graphe orienté reliant chaque une UE 1 à une UE 2 s'il est possible de séléctionner UE 2 en ayant validé UE1
     * Graphe representé grace à une map : la clé (String) représente le sommet, la valeur (List<String>) est la liste des sommets vers lesquels les arêtes pointent */

    private Map<String, List<String>> prerequis;
    private Map<String, List<String>> graphe;
    public Map<String, List<String>> getGraphe() {
        return graphe;
    }
    public void setGraphe(Map<String, List<String>> graphe) {
        this.graphe = graphe;
    }

    public Graphe(Map<String, List<String>> prerequis) {
        this.prerequis = prerequis;
        graphe = new HashMap<>();
        creationSommet();
        creationAretes();
    }

    /** Création des sommets du graphe */
    private void creationSommet() {
        //On commence par créer un point d'origine du graphe qui sera relié à toutes les UE ne demandant aucun prérequis
        List<String> origine = new ArrayList<>();
        for(String UE : prerequis.keySet()) {
            if(prerequis.get(UE).size()==0) {
                origine.add(UE);
            }
        }
        graphe.put("Origine", origine);
    }

    /** Création des arêtes du graphe */
    private void creationAretes() {
        List<String> origine = graphe.get("Origine");
        for(String UE : prerequis.keySet()) {
            List<String> aretes = new ArrayList<>(); //On crée une liste pour représenter les aretes de notre sommet
            aretes.addAll(origine); //Les arêtes pointent toutes vers les UE qui ne requierent aucun prérequis (car forcément on pourra toujours les séléctionner sans prendre en compte les prérequis)
            if(graphe.get(UE)!=null) aretes.addAll(graphe.get(UE));
            for(String R: prerequis.get(UE)) {
                //Cette boucle permet d'ajouter une arrête si une UE demande un prérequis
                List<String> ajoutArrete = new ArrayList<>();
                if(graphe.get(UE)!=null) ajoutArrete = graphe.get(UE);
                ajoutArrete.add(UE);
                graphe.put(R, ajoutArrete);
            }
            graphe.put(UE, aretes);
        }
    }

    /** @return la liste des UE séléctionnables après avoir validé l'UE passée en paramètre */
    public List<String> selectionnable(String UEValidée) {
        return graphe.get(UEValidée);
    }

    /** @return la liste des UE séléctionnables après avoir validé la liste d'UE passée en paramètre */
    public List<String> selectionnable(List<String> UE) {
        List<String> L = new ArrayList<>();
        for(String M: UE) {
            L.add(M);
        }
        List<String> UEValidées = L;
        HashSet<String> selectionnable = new HashSet<>();
        selectionnable.addAll(graphe.get("Origine"));
        for(String S: UEValidées) {
            selectionnable.addAll(graphe.get(S));
        }
        selectionnable.removeAll(UEValidées);
        return new ArrayList<>(selectionnable);
    }

    @Override
    public String toString() {
        return graphe.toString();
    }
}