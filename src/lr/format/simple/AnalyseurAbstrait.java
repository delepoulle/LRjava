package lr.format.simple;

import lr.format.Analyseur;

public abstract class AnalyseurAbstrait implements Analyseur {
    protected FormatSimple format;

    public AnalyseurAbstrait(FormatSimple format) {
        this.format = format;
    }
}
