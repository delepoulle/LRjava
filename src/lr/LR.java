package lr;

import lr.*;
import lr.format.simple.FormatSimple;
import lr.format.wavefront.WavefrontFormat;

/**
 * Classe principale 
 * 
 * auteurs : Christophe Renaud, Samuel Delepoulle, Franck Vandewiele
 */
class LR {
	static final int LARGEUR = 1980;
	static final int HAUTEUR = 1080;
	static final int NBRAYONS = 10;
	static final int NIVEAU = 2;

	public static void main(String[] args) {

		Renderer r = new Renderer(LARGEUR, HAUTEUR);
		Scene sc = new WavefrontFormat().charger("chaise_simple.obj");
		sc.ajouter(new Source(new Intensite(.6f, .3f, .2f), new Point(0.f, 2.f, 0.f)));
		sc.ajouter(new Source(new Intensite(.2f, .3f, .6f), new Point(0.25f, 3.f, -3.f)));
		sc.ajouter(new Source(new Intensite(.2f, .6f, .25f), new Point(4.25f, 3.f, -12.f)));
		sc.display();
		r.setScene(sc);
		r.setNiveau(NIVEAU);

		 //r.renderFullImage(NBRAYONS);

		 for (int i=0; i<HAUTEUR; i++){
			 r.renderLine(i, NBRAYONS);
		 }
		 Image image = r.getIm();

		
		//Image image = r.getIm();

		image.save("image" + NIVEAU, "png");
	}
}
