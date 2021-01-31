package lr.format;

import java.io.IOException;
import java.io.StreamTokenizer;

public interface Analyseur {
    public String getNom();
    public void analyser(StreamTokenizer tokenizer) throws IOException;
}
