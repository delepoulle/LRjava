package lr.format;

import lr.Scene;

import java.io.IOException;
import java.io.StreamTokenizer;

public interface Analyseur {
    public String getNom();
    public void analyser(StreamTokenizer tokenizer, Scene scene) throws IOException;
}
