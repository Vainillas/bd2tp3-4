package ar.unrn.tp.api.exceptions;
public class ServiceException extends RuntimeException{
    public ServiceException(String s) {
        super(s);
    }
    public ServiceException(String s, Throwable e) {
        super(s, e);
    }
    public ServiceException(Throwable e) {
        super(e);
    }
}
