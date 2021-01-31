package lr.format.wavefront;

import lr.format.Analyseur;

public abstract class AbstractAnalyzer implements Analyseur {
    protected WavefrontFormat format;

    public AbstractAnalyzer(WavefrontFormat format) {
        this.format = format;
    }
}
