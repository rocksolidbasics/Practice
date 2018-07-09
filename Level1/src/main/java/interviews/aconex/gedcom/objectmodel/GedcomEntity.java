package interviews.aconex.gedcom.objectmodel;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import interviews.aconex.gedcom.adapter.GedcomVisitor_I;
import interviews.aconex.gedcom.adapter.Visitable_I;

public class GedcomEntity implements Visitable_I {

	private String id;
	private String type;
	
	private List<GedcomData> childNodes = new LinkedList<GedcomData>();
	
	private int level = 0;
	private GedcomData curContext;
	private GedcomData prevContext;
	
	public GedcomEntity(GedcomRecord record) {
		this.id = record.getRecordData();
		this.type = record.getRecordType();
	}

	public void appendData(GedcomRecord record) {
		GedcomData data = createDataRecord(record);
		if(record.getLevel() == 1) {
			childNodes.add(data);
			curContext = data;
			level = 1;
		} else if(record.getLevel() == level + 1) {
			curContext.add(data);
			prevContext = curContext;
			curContext = data;
			level++;
		} else if(record.getLevel() == level){
			prevContext.add(data);
		}
		
	}

	private GedcomData createDataRecord(GedcomRecord record) {
		GedcomData data = new GedcomData();
		data.setType(record.getRecordType());
		data.setValue(record.getRecordData());
		
		return data;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
