package interviews.aconex.gedcom.objectmodel;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import interviews.aconex.gedcom.adapter.GedcomVisitor_I;
import interviews.aconex.gedcom.adapter.Visitable_I;

public class GedcomData implements Visitable_I {
	
	private String type;
	private String value;

	private List<GedcomData> childNodes = new LinkedList<GedcomData>();
	
	public void add(GedcomData data) {
		this.childNodes.add(data);
	}
	
	@Override
	public void accept(GedcomVisitor_I visitor) {
		visitor.visit(this);
		
		Iterator<GedcomData> dataIter = this.childNodes.iterator();
		while(dataIter.hasNext()) {
			GedcomData data = dataIter.next();
			data.accept(visitor);
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
