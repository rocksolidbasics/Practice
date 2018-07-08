package interviews.aconex.gedcom.objectmodel;

public class GedcomRecord {
	
	public enum CLASSIFIER {ID, INFO};
	
	private int level;
	private CLASSIFIER recordClassifier;	//ID, INFO
	private String recordType;				//NAME, SURN, GIVN, SEX, ID
	private String recordData;
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getRecordData() {
		return recordData;
	}
	public void setRecordData(String recordData) {
		this.recordData = recordData;
	}
	public CLASSIFIER getRecordClassifier() {
		return recordClassifier;
	}
	public void setRecordClassifier(CLASSIFIER recordClassifier) {
		this.recordClassifier = recordClassifier;
	}
	
	@Override
	public String toString() {
		return "Level => " + this.level + ", Type => " + this.recordType + ", Data => " + this.recordData;
	}

}
