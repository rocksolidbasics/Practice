package interviews.aconex.gedcom.bnf;

import interviews.aconex.gedcom.bnf.types.BinaryTerm;
import interviews.aconex.gedcom.bnf.types.ConstantTerm;
import interviews.aconex.gedcom.bnf.types.SimpleTerm;
import interviews.aconex.gedcom.bnf.types.Term_I;

class BNFTermParser {

	public Term_I parse(String expression) {
		expression = this.normalize(expression);
		
		String[] termsOrExpr = expression.split(" ");
		
		if(termsOrExpr.length > 1) {
			BinaryTerm bTerm = new BinaryTerm("&");
			
			for(int i=0; i<termsOrExpr.length; i++) {
				if(isOrCondition(termsOrExpr[i]))
					bTerm.addTerm(parse(termsOrExpr[i]));
				else if(isConstant(termsOrExpr[i]))
					bTerm.addTerm(new ConstantTerm(cleanConstant(termsOrExpr[i])));
				else
					bTerm.addTerm(new SimpleTerm(termsOrExpr[i]));
			}
			
			return bTerm;
		} else {
			if(termsOrExpr.length == 1) {
				String expr = termsOrExpr[0];
				boolean isRepeating = expr.startsWith("{");
				boolean isOrExpr = isOrCondition(expr);
				
				if(isRepeating) {
					expr = expr.substring(1, expr.length()-1);
				}
				
				if(isOrExpr) {
					BinaryTerm bTerm = new BinaryTerm("|");
					String[] orTerms = expr.split("\\|");
					for(int i=0; i<orTerms.length; i++) {
						if(isConstant(orTerms[i]))
							bTerm.addTerm(new ConstantTerm(cleanConstant(orTerms[i])));
						else
							bTerm.addTerm(new SimpleTerm(orTerms[i]));
					}
					
					bTerm.setRepeating(isRepeating);
					return bTerm;
				} else {
					SimpleTerm sTerm = new SimpleTerm(expr);
					sTerm.setRepeating(isRepeating);
					return sTerm;
				}
			}
		}
		
		return null;
	}
	
	private String cleanConstant(String expr) {
		if(expr.startsWith("{") && expr.endsWith("}"))
			return expr.substring(1, expr.length()-1);
		if(expr.startsWith("\"") && expr.endsWith("\""))
			return expr.substring(1, expr.length()-1);
		
		return expr;
	}
	
	private boolean isConstant(String expr) {
		return ((expr.startsWith("\"") && expr.endsWith("\"")) ||
				(expr.startsWith("'") && expr.endsWith("'")) ||
				expr.equals("DIGIT") || expr.equals("CHARS"));
	}

	private boolean isOrCondition(String expr) {
		return expr.contains("|");
	}

	private String normalize(String expr) {
		while(expr.contains(" | "))
			expr = expr.replace(" | ", "|");
		
		while(expr.contains(" |"))
			expr = expr.replace(" |", "|");
		
		while(expr.contains("| "))
			expr = expr.replace("| ", "|");
		
		while(expr.contains("{ "))
			expr = expr.replace("{ ", "{");
		
		while(expr.contains(" }"))
			expr = expr.replace(" }", "}");
		
		return expr;
	}
}
