package lr.format;

import lr.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.HashMap;

public abstract class FormatAbstrait implements Format {

    protected final HashMap<String, Analyseur> listeAnalyseurs;

    public FormatAbstrait() {
        this.listeAnalyseurs = new HashMap<>();
    }

    public void ajouter(Analyseur... desAnalyseurs) {
        for (Analyseur analyseur : desAnalyseurs) {
            this.listeAnalyseurs.put(analyseur.getNom(), analyseur);
        }
    }

    public Scene charger(String nomFichier) {
        int numLigne = 0, res;
        Materiau mat = new Materiau();

        try { // lecture des entrées du fichier
            StreamTokenizer tokenizer = new StreamTokenizer(new BufferedReader(new FileReader(nomFichier)));
            // initialisations de tokenizer
            tokenizer.lowerCaseMode(true);
            tokenizer.slashSlashComments(true);
            tokenizer.commentChar('#');

            while ((res = tokenizer.nextToken()) != StreamTokenizer.TT_EOF) {
                // chaque ligne doit commencer par un mot clé
                numLigne++;
                if (res != StreamTokenizer.TT_WORD) {// on passe la ligne
                    do
                        res = tokenizer.nextToken();
                    while ((res != StreamTokenizer.TT_EOF) && (res != StreamTokenizer.TT_EOL));
                    System.out.println("erreur sur ligne " + numLigne);
                }

                // nom de la directive en début de ligne
                String motCle = tokenizer.sval;
                // interprétation de la ligne par l'analyseur correspondant au nom du mot clé
                System.out.println("Parsing " + tokenizer.sval + " Identifier " + tokenizer);
                this.listeAnalyseurs.get(motCle).analyser(tokenizer);
            }
        } catch (IOException ioe) {
            System.out.println(ioe + " fichier " + nomFichier);
        }
        return generateScene();
    }

    protected abstract Scene generateScene();
}
