package lr;


public class ParallelRenderer implements Runnable {
    private int rayon;
    private int min;
    private int max;
    private Renderer renderer;

    public ParallelRenderer(int r, int min, int max, Renderer renderer ) {
        this.rayon = r;
        this.min = min;
        this.max = max;
        this.renderer = renderer;
    }

    public void run() {
        for (int i = min; i < max; i++) {
            renderer.renderLine(i, rayon);
        }
    }
    
    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

}
