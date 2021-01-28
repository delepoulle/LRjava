import java.util.*;
import java.io.*;

public class Scene {

	private static double EPSILON = 1E-3f;
	private HashSet<Primitive> objets;
	private HashSet<Source> sources;

	// Scene(){
	// objets = new HashSet();
	// sources = new HashSet();
	// }

	/**
	 * Construit une scène
	 * 
	 * @param nomFichier nom du fichier à partir duquel la description de la scène
	 *                   va être lue.
	 */
	public Scene(String nomFichier) {

		objets = new HashSet<>();
		sources = new HashSet<>();

		load(nomFichier);
	}

	/**
	 * fournit un iterateur sur la liste des objets composant la scène
	 * 
	 * @return l'itérateur
	 */
	public Iterator<Primitive> objetsIterator() {
		return objets.iterator();
	}

	/**
	 * fournit un iterateur sur la liste des sources éclairant la scène
	 * 
	 * @return l'itérateur
	 */
	public Iterator<Source> sourcesIterator() {
		return sources.iterator();
	}

	/**
	 * Calcule l'intersection entre un rayon et tous les objets composant la scène
	 * 
	 * @param r le rayon qui traverse la scène
	 * @return retourne une intersection ou null si aucune intersection n'a été
	 *         trouvée
	 */
	public Intersection intersecte(Rayon r) {
		Intersection p, pproche;
		Primitive obj;

		pproche = null;
		Iterator<Primitive> li = objets.iterator();
		while (li.hasNext()) {
			obj =  li.next();
			p = obj.intersecte(r);
			if (p != null) {// conserver l'intersection la plus proche
				if (p.compareTo(pproche) < 0){
					pproche = p;
				}
			}
		}
		return pproche;

	}// intersecte

	/**
	 * détermine si un objet coupe un segment de droite 3D issu de P1 et s'arrêtant
	 * en P2.
	 * 
	 * @param p1 le point de départ du segment
	 * @praam le point d'arrivée du segment
	 * @return true si un objet coupe le segment [p1,p2], false sinon
	 */
	boolean coupe(Point p1, Point p2) {
		Intersection p;
		Primitive obj;
		Rayon r = new Rayon(p1, p2);

		Iterator<Primitive> li = objets.iterator();
		while (li.hasNext()) {
			obj = li.next();
			if (obj.coupe(r, 1.0f) == true)
				return true;
		}
		return false;
	}

	/**
	 * affiche la liste des objets et des sources présents dans la scène
	 */
	public void display() {
		
		// affichage des sources
		System.out.println("-- liste des sources presentes dans la scene --");

		for (Source s : sources){
			System.out.println(s);
		}

		// affichage des objets
		System.out.println("-- liste des objets presents dans la scene --");

		for (Primitive p : objets){
			System.out.println(p);
		}
	}
	// fonctions privées à la classe

	private void load(String nomFichier) {
		int numLigne = 0, res;
		Materiau mat = new Materiau();

		try { // lecture des entrées du fichier
			StreamTokenizer f = new StreamTokenizer(new BufferedReader(new FileReader(nomFichier)));
			// initialisations de f
			f.lowerCaseMode(true);
			f.slashSlashComments(true);

			while ((res = f.nextToken()) != StreamTokenizer.TT_EOF) {
				// chaque ligne doit commencer par un mot clé
				numLigne++;
				if (res != StreamTokenizer.TT_WORD) {// on passe la ligne
					do
						res = f.nextToken();
					while ((res != StreamTokenizer.TT_EOF) && (res != StreamTokenizer.TT_EOL));
					System.out.println("erreur sur ligne " + numLigne);
				}
				if (f.sval.compareTo("sphere") == 0) {
					double xc, yc, zc, r;
					res = f.nextToken();
					xc = (double) f.nval;
					res = f.nextToken();
					yc = (double) f.nval;
					res = f.nextToken();
					zc = (double) f.nval;
					res = f.nextToken();
					r = (double) f.nval;

					Sphere s = new Sphere(r, new Point(xc, yc, zc), mat);
					objets.add(s);
					continue;
				}

				if (f.sval.compareTo("polygone") == 0) {
					double x, y, z;
					int nbsom;
					Point[] tabsom;

					res = f.nextToken();
					nbsom = (int) f.nval;
					tabsom = new Point[nbsom + 1];
					for (int s = 0; s < nbsom; s++) {
						res = f.nextToken();
						x = (double) f.nval;
						res = f.nextToken();
						y = (double) f.nval;
						res = f.nextToken();
						z = (double) f.nval;
						tabsom[s] = new Point(x, y, z);
					}
					tabsom[nbsom] = new Point(tabsom[0]);
					Polygone p = new Polygone(tabsom, mat);
					objets.add(p);
					continue;
				}

				// if(f.sval.compareTo("plan")==0){
				// float a, b, c, d;

				// res = f.nextToken(); a = (float)f.nval;
				// res = f.nextToken(); b = (float)f.nval;
				// res = f.nextToken(); c = (float)f.nval;
				// res = f.nextToken(); d = (float)f.nval;
				// Plan p = new Plan(a, b, c, d, mat);
				// objets.add(p);
				// continue;
				// }

				if (f.sval.compareTo("source") == 0) {
					double x, y, z;
					double r, v, b;

					// saisie des coordonnées de la source
					res = f.nextToken();
					x = (double) f.nval;
					res = f.nextToken();
					y = (double) f.nval;
					res = f.nextToken();
					z = (double) f.nval;

					// saisie de l'intensite de la source
					res = f.nextToken();
					r = (double) f.nval;
					res = f.nextToken();
					v = (double) f.nval;
					res = f.nextToken();
					b = (double) f.nval;
					Intensite puiss = new Intensite(r, v, b);
					Source light = new Source(puiss, new Point(x, y, z));
					sources.add(light);
					continue;
				}

				if (f.sval.compareTo("materiau") == 0) {
					double[] ambiant = new double[3];
					double[] diffus = new double[3];
					double[] speculaire = new double[3];
					double coeff;

					res = f.nextToken();
					ambiant[0] = (double) f.nval;
					res = f.nextToken();
					ambiant[1] = (double) f.nval;
					res = f.nextToken();
					ambiant[2] = (double) f.nval;

					res = f.nextToken();
					diffus[0] = (double) f.nval;
					res = f.nextToken();
					diffus[1] = (double) f.nval;
					res = f.nextToken();
					diffus[2] = (double) f.nval;

					res = f.nextToken();
					speculaire[0] = (double) f.nval;
					res = f.nextToken();
					speculaire[1] = (double) f.nval;
					res = f.nextToken();
					speculaire[2] = (double) f.nval;

					res = f.nextToken();
					coeff = (double) f.nval;

					mat = new Materiau(ambiant, diffus, speculaire, coeff);
					continue;
				}
			}

		} catch (IOException ioe) {
			System.out.println(ioe + " fichier " + nomFichier);
		}
	}

}// Scene
