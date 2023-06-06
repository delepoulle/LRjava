package lr;

import lr.format.simple.FormatSimple;
import lr.format.wavefront.WavefrontFormat;
import lr.format.wavefront.material.MaterialFormat;

/**
 * Classe principale
 * 
 * auteurs : Christophe Renaud, Samuel Delepoulle, Franck Vandewiele
 */
class LR {
    static final int LARGEUR = 1980;
    static final int HAUTEUR = 1080;
    static final int NBRAYONS = 100;
    static final int NIVEAU = 2;
    static final int NOMBRE_THREADS = 4; // Constante pour le nombre de threads

    public static void main(String[] args) {
        Renderer r = new Renderer(LARGEUR, HAUTEUR);
        Scene sc = new FormatSimple().charger("simple.txt");
        sc.display();
        r.setScene(sc);
        r.setNiveau(NIVEAU);

        // Création des threads
        Thread[] threads = new Thread[HAUTEUR];
        int rowsPerThread = HAUTEUR / NOMBRE_THREADS;
        int remainingRows = HAUTEUR % NOMBRE_THREADS;

        for (int i = 0; i < NOMBRE_THREADS; i++) {
            int startRow = i * rowsPerThread;
            int endRow = (i + 1) * rowsPerThread;
            if (i == NOMBRE_THREADS - 1) {
                endRow += remainingRows;
            }

            ParallelRenderer renderer = new ParallelRenderer(NBRAYONS, startRow, endRow, r);
            threads[i] = new Thread(renderer);
            threads[i].start();
        }

        // Attendre que tous les threads aient terminé
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Image image = r.getIm();
        image.save("image" + NIVEAU, "png");
        // new MaterialFormat().charger("chaise_plan.mtl");
    }
}
