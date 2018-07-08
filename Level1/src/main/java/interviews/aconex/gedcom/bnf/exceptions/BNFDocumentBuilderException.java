package interviews.aconex.gedcom.bnf.exceptions;

public class BNFDocumentBuilderException extends Exception {

	private static final long serialVersionUID = -1335206510558971531L;

	public BNFDocumentBuilderException(String message) {
		super(message);
	}
	
	public BNFDocumentBuilderException(String message, Throwable exception) {
		super(message, exception);
	}
}
