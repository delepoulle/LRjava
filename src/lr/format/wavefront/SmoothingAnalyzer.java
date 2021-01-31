package lr.format.wavefront;

import lr.Scene;

import java.io.IOException;
import java.io.StreamTokenizer;

public class SmoothingAnalyzer extends AbstractAnalyzer{
    public SmoothingAnalyzer(WavefrontFormat format) {
        super(format);
    }

    @Override
    public String getNom() {
        return "s";
    }

    @Override
    public void analyser(StreamTokenizer tokenizer, Scene scene) throws IOException {
        tokenizer.eolIsSignificant(false);
        //TODO: actually do something here
        tokenizer.nextToken();
        String smoothingState = tokenizer.sval;
        System.out.println("Setting smoothing = " + smoothingState);
    }
}
