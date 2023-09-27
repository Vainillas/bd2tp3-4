package ar.unrn.tp.ui.exceptions;

public class GUIException extends RuntimeException {
    public GUIException(String s) {
        super(s);
    }
    public GUIException(String s, Throwable e) {
        super(s, e);
    }
    public GUIException(Throwable e) {
        super(e);
    }
}
