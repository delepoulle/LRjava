package lr.format.wavefront;

import java.io.IOException;
import java.io.StreamTokenizer;

public class MaterialLibAnalyzer extends AbstractAnalyzer {

    public MaterialLibAnalyzer(WavefrontFormat format) {
        super(format);
    }

    @Override
    public String getNom() {
        return "mtllib";
    }

    @Override
    public void analyser(StreamTokenizer tokenizer) throws IOException {
        tokenizer.eolIsSignificant(false);
        tokenizer.wordChars('.', '.');
        tokenizer.wordChars('_', '_');
        tokenizer.nextToken();

        //TODO: actually do something here
        String filename = tokenizer.sval;
        System.out.println("Material Library from file " + tokenizer.sval);
    }
}
