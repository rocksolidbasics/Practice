package interviews.aconex.gedcom.adapter.xml;

import java.io.StringWriter;

import interviews.aconex.gedcom.adapter.AbstractGedcomTreeVisitor;
import interviews.aconex.gedcom.objectmodel.GedcomData;
import interviews.aconex.gedcom.objectmodel.GedcomEntity;
import interviews.aconex.gedcom.objectmodel.GedcomTree;

public class GedcomXMLGenerator extends AbstractGedcomTreeVisitor {

	private StringWriter writer = new StringWriter();
	
	private final String GEDCOM_TAG = "gedcom";
	private final String NAME_TAG = "name";
	
	@Override
	public void visit(GedcomData data) {
		String tagName = data.getType().toLowerCase();
		
		if(tagName.equalsIgnoreCase(NAME_TAG)) {
			openAttributeTag(tagName, "value", data.getValue());
		} else {
			openDataTag(tagName, data.getValue());
		}
	}
	
	@Override
	public void done(GedcomData data) {
		closeTag(data.getType().toLowerCase());
	}

	@Override
	public void visit(GedcomEntity entity) {
		openAttributeTag(entity.getType().toLowerCase(), "id", entity.getId());
	}
	
	@Override
	public void done(GedcomEntity entity) {
		closeTag(entity.getType().toLowerCase());
	}

	@Override
	public void visit(GedcomTree tree) {
		openTag(GEDCOM_TAG);
	}
	
	@Override
	public void done(GedcomTree tree) {
		closeTag(GEDCOM_TAG);
	}
	
	public String getXML() {
		return writer.toString();
	}
	
	private void openTag(String tag) {
		writer.append("<");
		writer.append(tag);
		writer.append(">");
		writer.append("\n");
	}
	
	private void openDataTag(String tag, String data) {
		writer.append("<");
		writer.append(tag);
		writer.append(">");
		writer.append(data);
	}
	
	private void openAttributeTag(String tag, String attrName, String attrValue) {
		writer.append("<");
		writer.append(tag);
		writer.append(" ");
		writer.append(attrName);
		writer.append("=\"");
		writer.append(attrValue);
		writer.append("\"");
		writer.append(">");
		writer.append("\n");
	}
	
	private void closeTag(String tag) {
		writer.append("</");
		writer.append(tag);
		writer.append(">");
		writer.append("\n");
	}

}
