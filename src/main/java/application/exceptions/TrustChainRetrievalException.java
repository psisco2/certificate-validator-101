package application.exceptions;

public class TrustChainRetrievalException extends Exception {

    private static final long serialVersionUID = -3984759051864513896L;

    public TrustChainRetrievalException(String message) {
        super(message);
    }

    public TrustChainRetrievalException(String message, Throwable cause) {
        super(message, cause);
    }

    public TrustChainRetrievalException(Throwable cause) {
        super(cause);
    }

}