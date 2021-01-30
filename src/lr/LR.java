package lr;

import lr.*;
import lr.format.simple.FormatSimple;

/**
 * Classe principale 
 * 
 * auteurs : Christophe Renaud, Samuel Delepoulle, Franck Vandewiele
 */
class LR {
	static final int LARGEUR = 1980;
	static final int HAUTEUR = 1080;
	static final int NBRAYONS = 500;
	static final int NIVEAU = 12;

	public static void main(String[] args) {

		Renderer r = new Renderer(LARGEUR, HAUTEUR);
		Scene sc = new FormatSimple().charger("simple.txt");
		sc.display();
		r.setScene(sc);
		r.setNiveau(NIVEAU);

		Image image = r.renderFullImage(NBRAYONS);
		image.save("image" + NIVEAU, "png");
	}
}
