package interviews.aconex.gedcom.bnf;

import java.util.Iterator;
import java.util.Map;

import interviews.aconex.gedcom.bnf.types.Term_I;

public class BNFDocument {

	public static final String BNF_ROOT = "BNF_ROOT";
	
	private Map<String,BNFRule> m_BnfRuleMap;

	public BNFDocument(Map<String, BNFRule> ruleMap) {
		this.m_BnfRuleMap = ruleMap;
	}
	
	public BNFRule getRootRule() {
		return this.m_BnfRuleMap.get(BNF_ROOT);
	}
	
	public BNFRule getRule(String ruleTerm) {
		return this.m_BnfRuleMap.get(ruleTerm);
	}
	
	public void validate() {
		BNFTermParser parser = new BNFTermParser();
		Iterator<String> keyIter = this.m_BnfRuleMap.keySet().iterator();
		
		while(keyIter.hasNext()) {
			String key = keyIter.next();
			Term_I term = parser.parse(this.m_BnfRuleMap.get(key).getRuleExpression());
			this.m_BnfRuleMap.get(key).setSubRules(term);
		}
	}

	@Override
	public String toString() {
		String out = "";
		Iterator<String> keyIter = m_BnfRuleMap.keySet().iterator();
		while(keyIter.hasNext()) {
			out += m_BnfRuleMap.get(keyIter.next());
			out += "\n";
		}
		
		return out;
	}
	
}
