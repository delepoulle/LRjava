package lr.format.wavefront;

import lr.Point;
import lr.Scene;

import java.io.IOException;
import java.io.StreamTokenizer;

public class VertexAnalyzer extends AbstractAnalyzer {

    public VertexAnalyzer(WavefrontFormat format) {
        super(format);
    }

    @Override
    public String getNom() {
        return "v";
    }

    @Override
    public void analyser(StreamTokenizer tokenizer, Scene scene) throws IOException {
        tokenizer.eolIsSignificant(false);
        int type;
        tokenizer.nextToken();
        // v x y z  format
        float x = (float) tokenizer.nval;
        tokenizer.nextToken();
        float y = (float) tokenizer.nval;
        tokenizer.nextToken();
        float z = (float) tokenizer.nval;

        System.out.println("Vextex described as " + new Point(x, y, z));
        this.format.add(new Point(x, y, z));

    }
}
