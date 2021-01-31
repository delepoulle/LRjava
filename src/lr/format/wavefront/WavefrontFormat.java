package lr.format.wavefront;

import lr.*;
import lr.format.FormatAbstrait;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WavefrontFormat extends FormatAbstrait {

    private List<Point> points;
    private final List<Vecteur> normals;
    private final List<WavefrontObject> objects;
    private final List<Primitive> primitives;

    public WavefrontFormat() {
        super();
        this.normals = new ArrayList<>();
        this.points = new ArrayList<>();
        this.primitives = new ArrayList<>();
        this.objects = new ArrayList<>();
        this.ajouter(new ObjectAnalyzer(this));
        this.ajouter(new VertexAnalyzer(this));
        this.ajouter(new NormalAnalyzer(this));
        this.ajouter(new SmoothingAnalyzer(this));
        this.ajouter(new TextureCoordinatesAnalyzer(this));
        this.ajouter(new FaceAnalyzer(this));
        this.ajouter(new MaterialLibAnalyzer(this));
        this.ajouter(new UseMaterialAnalyzer(this));
    }

    public void add(WavefrontObject object) {
        this.objects.add(object);
    }

    public void add(Point point) {
        this.points.add(point);
        this.currentObject().ifPresent((o)-> o.addNormal(this.points.size() - 1));
    }

    public void add(Vecteur normal) {
        this.normals.add(normal);
        this.currentObject().ifPresent((o)-> o.addNormal(this.normals.size() - 1));
    }

    public void addPolygon(int[] vertices, int[] normals) {

        Vecteur[] n = new Vecteur[normals.length];
        Point[] p = new Point[vertices.length];
        System.out.println("Adding a polygon");
        System.out.println(" - " + vertices.length + " vertices");
        System.out.println(" - " + normals.length + " normals");
        if (vertices.length != normals.length) {
            System.out.println("No normals provided");
        }

        for (int i = 0 ; i < p.length; i++) {
            p[i] = this.getVertex(vertices[i]);
        }

        if (normals.length > 0) {
            for (int i = 0 ; i < p.length; i++) {
                try {
                    n[i] = this.getNormal(normals[i]);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(points.size() + " known vertices");
                    System.out.println(this.normals.size() + " known normals");
                    System.out.println("Trying to access vertex #" + normals[i]);
                    throw e;
                }
            }
        } else {
            n = new Vecteur[vertices.length];
            Vecteur v1 = new Vecteur(p[0], p[1]);
            Vecteur v2 = new Vecteur(p[1], p[2]);
            Vecteur v3 = v1.produitVectoriel(v2);
            v3.div(v3.norme());
            for (int i = 0 ; i < n.length; i++) {
                n[i] = new Vecteur(v3);
            }
        }

        //TODO: à améliorer
        this.primitives.add(new Polygone(p, n, new Materiau()));

    }

    public Point getVertex(int index) {
        return this.points.get(index - 1);
    }

    public Vecteur getNormal(int index) {
        return this.normals.get(index - 1);
    }

    @Override
    public Scene charger(String nomFichier) {
        this.points = new ArrayList<>();
        super.charger(nomFichier);
        Scene scene = new Scene();
        this.primitives.forEach(scene::ajouter);
        return scene;
    }

    private Optional<WavefrontObject> currentObject() {
        if (this.objects.size() > 0)
            return Optional.of(this.objects.get(this.objects.size() - 1));
        else return Optional.empty();
    }

}
