/** 
 * Classe principale 
 * 
 * auteurs : Christophe Renaud, Samuel Delepoulle, Franck Vandewiele
 */
class LR {
	static final int LARGEUR = 1980;
	static final int HAUTEUR = 1080;
	static final int NBRAYONS = 1;
	static final boolean ANTIALIASAGE = true;
	static final int NIVEAU = 2;

	public static void main(String[] args) {

		Renderer r = new Renderer(LARGEUR, HAUTEUR);
		Scene sc = new Scene("simple.txt");
		sc.display();
		r.setScene(sc);
		r.setNiveau(NIVEAU);

		Image image = new Image(LARGEUR, HAUTEUR);

		for (int y = 0; y < HAUTEUR; y++) {
			for (int x = 0; x < LARGEUR; x++) {
				image.setPixel(x, y, (r.getIntensite(x, y, NBRAYONS)).getColor());
			}
		}
		image.save("image" + NIVEAU, "png");
	}
}
