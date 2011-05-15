package geforce.android.kistenschieber;

import java.util.ArrayList;

public class AvailableLevels {
	private static final String TAG = "AvailableLevels";
	private ArrayList<Integer> fieldid = new ArrayList<Integer>();
	private ArrayList<Integer> infoid = new ArrayList<Integer>();
	//private Integer[] infoid = {}; 
	
	public AvailableLevels() {
		
	}
	
	public void add(int _fieldid, int _infoid) {
		/*Integer[] tempLevelid = fieldid;
		Integer[] tempInfoid = infoid;
		
		fieldid = new Integer[tempLevelid.length+1];
		infoid = new Integer[tempInfoid.length+1];*/
		
		fieldid.add(_fieldid);
		infoid.add(_infoid);			
	}
	
	public int getIndex(int _fieldid) {
		return fieldid.indexOf(_fieldid);
	}
	
	public int getFieldid(int index) {
		return fieldid.get(index);
	}
	
	public int getInfoid(int index) {
		return infoid.get(index);
	}
	
	public int getFieldidFromInfoid(int _infoid) {
		return fieldid.get(infoid.indexOf(_infoid));
	}
	
	public int getInfoidFromFieldid(int _fieldid) {
		return infoid.get(fieldid.indexOf(_fieldid));
	}
	
	public int getNextFieldId(int _fieldid) {
		int newindex = fieldid.indexOf(fieldid)+1;
		if (newindex < fieldid.size()) {
			return fieldid.get(newindex);
		}
		return fieldid.get(0);		
	}
	
	public int getNextIndexFromFieldId(int _fieldid) {
		int newindex = fieldid.indexOf(_fieldid)+1;
		if (newindex < fieldid.size()) {
			return newindex;
		}
		return 0;		
	}
	
	public boolean remove(int index) {
		if (index >0 && index < fieldid.size()) {
			if (fieldid.remove(index) != null) {
				return true;
			}
		}
		return false;
	}
	
	public int size() {
		return infoid.size();
	}
	
}