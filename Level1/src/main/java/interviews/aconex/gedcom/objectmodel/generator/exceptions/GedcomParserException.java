package interviews.aconex.gedcom.objectmodel.generator.exceptions;

public class GedcomParserException extends Exception {

	private static final long serialVersionUID = -3214723413906598543L;

	public GedcomParserException(String message) {
		super(message);
	}
	
	public GedcomParserException(String message, Throwable exception) {
		super(message, exception);
	}
}
