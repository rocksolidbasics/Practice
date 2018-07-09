package interviews.aconex.gedcom.adapter;

import interviews.aconex.gedcom.objectmodel.GedcomData;
import interviews.aconex.gedcom.objectmodel.GedcomEntity;
import interviews.aconex.gedcom.objectmodel.GedcomTree;

public class GedcomTreeVisitor implements GedcomVisitor_I {

	@Override
	public void visit(GedcomData data) {
		System.out.println(data.getType());
	}

	@Override
	public void visit(GedcomEntity entity) {
		System.out.println(entity.getType());
	}

	@Override
	public void visit(GedcomTree tree) {
		System.out.println("Tree climbed");
	}

}
