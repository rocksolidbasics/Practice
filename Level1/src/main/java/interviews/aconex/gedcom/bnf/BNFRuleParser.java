package interviews.aconex.gedcom.bnf;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import interviews.aconex.gedcom.bnf.exceptions.BNFDocumentBuilderException;

public class BNFRuleParser {
	
	private static final Pattern bnfRegexPattern = Pattern.compile("(.+?)\\s::=\\s(.+)");
	
	public BNFRule parse(BNFLine bnfLine) throws BNFDocumentBuilderException {
		boolean isValid = this.validate(bnfLine);
		if(!isValid)
			throw new BNFDocumentBuilderException("BNF rule on line " + bnfLine.getLineNumber() + " is invalid");
		
		Matcher matcher = bnfRegexPattern.matcher(bnfLine.getRuleExpression());
		matcher.find();
		
		return new BNFRule(bnfLine.getLineNumber(), matcher.group(1), matcher.group(2));
	}

	private boolean validate(BNFLine bnfLine) {
		if(bnfLine.getRuleExpression() == null || bnfLine.getRuleExpression().trim().equals(""))
			return false;
		
		return bnfRegexPattern.matcher(bnfLine.getRuleExpression()).matches();
	}
}