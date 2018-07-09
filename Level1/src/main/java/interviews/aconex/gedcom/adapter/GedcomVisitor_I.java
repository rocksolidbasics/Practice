package interviews.aconex.gedcom.adapter;

import interviews.aconex.gedcom.objectmodel.GedcomData;
import interviews.aconex.gedcom.objectmodel.GedcomEntity;
import interviews.aconex.gedcom.objectmodel.GedcomTree;

public interface GedcomVisitor_I {

	public void visit(GedcomData data);
	
	public void visit(GedcomEntity entity);
	
	public void visit(GedcomTree tree);
	
}
