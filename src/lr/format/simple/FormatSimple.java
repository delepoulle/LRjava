package lr.format.simple;

import lr.Materiau;
import lr.Primitive;
import lr.Scene;
import lr.Source;
import lr.format.FormatAbstrait;

import java.util.ArrayList;
import java.util.List;

public class FormatSimple extends FormatAbstrait {

    private Materiau materiau;
    private final List<Primitive> primitiveList;
    private final List<Source> sourceList;

    public FormatSimple() {
        materiau = new Materiau();
        this.primitiveList = new ArrayList<>();
        this.sourceList = new ArrayList<>();
        this.ajouter(
                new AnalyseurMateriau(this),
                new AnalyseurSource(this),
                new AnalyseurSphere(this),
                new AnalyseurPolygone(this)
        );
    }

    @Override
    protected Scene generateScene() {
        Scene scene = new Scene();
        primitiveList.forEach(scene::ajouter);
        sourceList.forEach(scene::ajouter);
        return scene;
    }

    public void setMateriau(Materiau materiau) {
        this.materiau = materiau;
    }

    public Materiau getMateriau() {
        return this.materiau;
    }

    public void add(Primitive primitive) {
        this.primitiveList.add(primitive);
    }

    public void add(Source source) {
        this.sourceList.add(source);
    }
}
