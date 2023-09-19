package ar.unrn.tp.ui;

public class GUIException extends Throwable {
    public GUIException(String s) {
        super(s);
    }
    public GUIException(String s, Throwable e) {
        super(s, e);
    }
}
