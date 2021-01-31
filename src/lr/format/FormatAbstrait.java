package lr.format;

import lr.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.HashMap;

public abstract class FormatAbstrait implements Format {

    protected final HashMap<String, Analyseur> analyseurs;

    public FormatAbstrait() {
        this.analyseurs = new HashMap<>();
    }

    public void ajouter(Analyseur analyseur) {
        this.analyseurs.put(analyseur.getNom(), analyseur);
    }

    public Scene charger(String nomFichier) {
        int numLigne = 0, res;
        Materiau mat = new Materiau();

        Scene scene = new Scene();

        try { // lecture des entrées du fichier
            StreamTokenizer f = new StreamTokenizer(new BufferedReader(new FileReader(nomFichier)));
            // initialisations de f
            f.lowerCaseMode(true);
            f.slashSlashComments(true);
            f.commentChar('#');

            while ((res = f.nextToken()) != StreamTokenizer.TT_EOF) {
                // chaque ligne doit commencer par un mot clé
                numLigne++;
                if (res != StreamTokenizer.TT_WORD) {// on passe la ligne
                    do
                        res = f.nextToken();
                    while ((res != StreamTokenizer.TT_EOF) && (res != StreamTokenizer.TT_EOL));
                    System.out.println("erreur sur ligne " + numLigne);
                }

                // nom de la directive en début de ligne
                String motCle = f.sval;
                // interprétation de la ligne par l'analyseur correspondant au nom du mot clé
                System.out.println("Parsing " + f.sval + " Identifier " + f);
                this.analyseurs.get(motCle).analyser(f, scene);
            }
        } catch (IOException ioe) {
            System.out.println(ioe + " fichier " + nomFichier);
        }
        return scene;
    }
}
