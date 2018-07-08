package interviews;

import java.util.ArrayList;
import java.util.List;

public class VersionToNumberConverter {

	public static void main(String[] args) {
		VersionToNumberConverter v = new VersionToNumberConverter();
		List<Long> vl1 = v._getVersionChunks("3.6.3.00.B823");
		System.out.println(vl1);
		System.out.println(Long.valueOf("A", 36));
	}
	
	/**
	 * 3.6.3.00.B823 > 3.6.3.00.B251
	 * 
	 * @param theVersion
	 * @return
	 */
	private ArrayList<Long> _getVersionChunks(String theVersion) {
		int theRadix = 10;
		String subv = "";
		char[] theChars = theVersion.toCharArray();
		ArrayList<Long> theVersionParts = new ArrayList<Long>();
		
		for(int i=0; i<theChars.length; i++) {
			if(!Character.isLetterOrDigit(theChars[i])) {
				if(!"".equals(subv)) {
					theVersionParts.add(Long.valueOf(subv, theRadix));
				}
				theRadix = 10;
				subv = "";
				continue;
			}
			
			if(Character.isLetter(theChars[i]))
				theRadix = 36;
			
			subv += theChars[i];
		}
		
		if(!"".equals(subv)) {
			theVersionParts.add(Long.valueOf(subv, theRadix));
		}
		
		return theVersionParts;
	}

}
