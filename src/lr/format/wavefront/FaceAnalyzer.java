package lr.format.wavefront;

import lr.Point;
import lr.Scene;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FaceAnalyzer extends AbstractAnalyzer{
    public FaceAnalyzer(WavefrontFormat format) {
        super(format);
    }

    @Override
    public String getNom() {
        return "f";
    }

    @Override
    public void analyser(StreamTokenizer tokenizer, Scene scene) throws IOException {
        tokenizer.wordChars('/', '/');
        tokenizer.eolIsSignificant(true);
        HashMap<Integer, String> map = new HashMap<>();
        map.put(StreamTokenizer.TT_EOF, "TT_EOF");
        map.put(StreamTokenizer.TT_NUMBER, "TT_NUMBER");
        map.put(StreamTokenizer.TT_WORD, "TT_WORD");
        map.put(StreamTokenizer.TT_EOL, "TT_EOL");

        int type;

        List<Integer> vertices = new ArrayList<>();
        List<Integer> normals = new ArrayList<>();
        System.out.print("polygon");
        type = tokenizer.nextToken();
        do {
            if (type == StreamTokenizer.TT_NUMBER) {
                vertices.add((int) tokenizer.nval);
            } else if (type == StreamTokenizer.TT_WORD) {
                // we're in trouble here : vertex is described as n1/n2/n3
                // and tokenizer.sval = "n2/n3"
                String[] parts = tokenizer.sval.split("/");
                if (parts.length > 2) {
                    System.out.println("parsing normal from " + tokenizer.sval + " => " + parts[2]);
                    int normalIndex = Integer.parseInt(parts[2]);
                    normals.add(normalIndex);
                }
            }
            type = tokenizer.nextToken();
            //System.out.println("2 Type = " + map.get(type) + " sval = " + tokenizer.sval + " nval = " + tokenizer.nval + " toString" + tokenizer);
        } while (type != StreamTokenizer.TT_EOL && type != StreamTokenizer.TT_EOF);

        System.out.println();
        System.out.println("Finished " + tokenizer);
        int[] vertexIndices = new int[vertices.size()];
        int[] normalIndices = new int[normals.size()];
        for (int i = 0 ; i < vertexIndices.length ; i++) {
            vertexIndices[i] = vertices.get(i);
        }
        for (int i = 0 ; i < normalIndices.length ; i++) {
            normalIndices[i] = normals.get(i);
        }
        this.format.addPolygon(vertexIndices, normalIndices);
    }
}
