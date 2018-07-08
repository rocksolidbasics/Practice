package interviews.aconex.gedcom.ast.exceptions;

public class GedcomASTException extends Exception {

	private static final long serialVersionUID = 8061980793279276125L;

	public GedcomASTException(String message) {
		super(message);
	}
	
	public GedcomASTException(String message, Throwable exception) {
		super(message, exception);
	}	
}
