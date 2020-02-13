package metier;

public class Compteur {
    private int cpt = 0;

    public int getCpt() {
        return cpt;
    }

    public void ajouter() {
        cpt++;
    }

    public void raz() {
        cpt = 0;
    }
}
