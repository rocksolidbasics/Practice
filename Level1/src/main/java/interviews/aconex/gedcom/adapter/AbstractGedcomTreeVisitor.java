package interviews.aconex.gedcom.adapter;

import interviews.aconex.gedcom.objectmodel.GedcomData;
import interviews.aconex.gedcom.objectmodel.GedcomEntity;
import interviews.aconex.gedcom.objectmodel.GedcomTree;

public abstract class AbstractGedcomTreeVisitor implements GedcomVisitor_I {

	@Override
	public abstract void visit(GedcomData data);

	@Override
	public abstract void visit(GedcomEntity entity);

	@Override
	public abstract void visit(GedcomTree tree);

}
