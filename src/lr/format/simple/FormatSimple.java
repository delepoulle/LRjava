package lr.format.simple;

import lr.Materiau;
import lr.format.FormatAbstrait;

public class FormatSimple extends FormatAbstrait {

    Materiau materiau;

    public FormatSimple() {
        materiau = new Materiau();
        this.ajouter(new AnalyseurMateriau(this));
        this.ajouter(new AnalyseurSource(this));
        this.ajouter(new AnalyseurSphere(this));
        this.ajouter(new AnalyseurPolygone(this));
    }

    public void setMateriau(Materiau materiau) {
        this.materiau = materiau;
    }

    public Materiau getMateriau() {
        return this.materiau;
    }
}
