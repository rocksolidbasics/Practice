package interviews.aconex.gedcom.objectmodel;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import interviews.aconex.gedcom.adapter.GedcomVisitor_I;
import interviews.aconex.gedcom.adapter.Visitable_I;

public class GedcomTree implements Visitable_I {
	
	private List<GedcomEntity> m_EntityList = new LinkedList<GedcomEntity>();

	public void append(GedcomEntity entity) {
		this.m_EntityList.add(entity);
	}

	@Override
	public void accept(GedcomVisitor_I visitor) {
		visitor.visit(this);
		
		Iterator<GedcomEntity> entities = m_EntityList.iterator();
		while(entities.hasNext()) {
			GedcomEntity entity = entities.next();
			entity.accept(visitor);
		}
	}

}
