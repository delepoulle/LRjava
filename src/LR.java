/** 
 * Classe principale 
 * 
 * auteurs : Christophe Renaud, Samuel Delepoulle, Franck Vandewiele
 */
class LR {
	static final int LARGEUR = 1980;
	static final int HAUTEUR = 1080;
	static final int NBRAYONS = 1;
	static final int NIVEAU = 2;

	public static void main(String[] args) {

		Renderer r = new Renderer(LARGEUR, HAUTEUR);
		Scene sc = new Scene("simple.txt");
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
