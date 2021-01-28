
class LR {
	static final int LARGEUR = 1980;
	static final int HAUTEUR = 1080;
	static final int NBRAYONS = 1;
	static final boolean ANTIALIASAGE = true;
	static final int NIVEAU = 0;

	public static void main(String[] args) {
		double largeurPixel = 2.0f / LARGEUR;
		double hauteurPixel = 2.0f / HAUTEUR;

		double ratio = (double)LARGEUR/(double)HAUTEUR;

		Scene sc = new Scene("simple.txt");
		sc.display();

		Image image = new Image(LARGEUR, HAUTEUR);

		Point obs = new Point(0.0f, 0.0f, 2.0f);

		for (int y = 0; y < HAUTEUR; y++) {
			for (int x = 0; x < LARGEUR; x++) {
				// création d'un intensité nulle
				Intensite in = new Intensite();
				for (int n = 0; n < NBRAYONS; n++) {

					// milieu du pixel sauf si anti-aliasage
					double dx = 0.5f;
					double dy = 0.5f;
					if (ANTIALIASAGE){
						dx = Math.random();
						dy = Math.random();
					}

					double posX = -1.0f + (x + dx) * largeurPixel;
					double posY =  1.0f - (y + dy) * hauteurPixel;

					// générer le rayon primaire
					Point dir = new Point(posX, posY/ratio, 0.0f);
					Rayon ray = new Rayon(obs, dir);

					// calculer l'intersection du rayon primaire avec la scene
					Intersection inter = sc.intersecte(ray);

					// lancer le calcul d'eclairage du point d'intersection
					if (inter != null) {
						in.add(inter.eclairer(sc, obs, NIVEAU, x, y));
					}
				} // for n
				in.div(NBRAYONS);
				image.setPixel(x, y, in.getColor());
			}
		}
		image.save("image" + NIVEAU, "png");
	}
}
