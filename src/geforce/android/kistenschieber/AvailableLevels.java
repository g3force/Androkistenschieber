package geforce.android.kistenschieber;

import java.util.ArrayList;

public class AvailableLevels {
//	private static final String TAG = "AvailableLevels";
	private ArrayList<Integer> fieldid = new ArrayList<Integer>();
	private ArrayList<Integer> infoid = new ArrayList<Integer>();

	public void add(int _fieldid, int _infoid) {
		fieldid.add(_fieldid);
		infoid.add(_infoid);
	}

	public void loadAllAvailableLevels() {
		// all available levels that should be displayed in the open-menu
//		add(R.array.level_000_fields, R.array.level_000_info);
		add(R.array.level_001_fields, R.array.level_001_info);
		add(R.array.level_002_fields, R.array.level_002_info);
		add(R.array.level_003_fields, R.array.level_003_info);
		add(R.array.level_004_fields, R.array.level_004_info);
		add(R.array.level_005_fields, R.array.level_005_info);
		add(R.array.level_006_fields, R.array.level_006_info);
		add(R.array.level_007_fields, R.array.level_007_info);
		add(R.array.level_008_fields, R.array.level_008_info);
		add(R.array.level_009_fields, R.array.level_009_info);
		add(R.array.level_010_fields, R.array.level_010_info);
		add(R.array.level_011_fields, R.array.level_011_info);
		add(R.array.level_012_fields, R.array.level_012_info);
		add(R.array.level_013_fields, R.array.level_013_info);
		add(R.array.level_014_fields, R.array.level_014_info);
		add(R.array.level_015_fields, R.array.level_015_info);
		add(R.array.level_016_fields, R.array.level_016_info);
		add(R.array.level_017_fields, R.array.level_017_info);
		add(R.array.level_018_fields, R.array.level_018_info);
		add(R.array.level_019_fields, R.array.level_019_info);
		add(R.array.level_020_fields, R.array.level_020_info);
		add(R.array.level_021_fields, R.array.level_021_info);
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
		int newindex = fieldid.indexOf(fieldid) + 1;
		if (newindex < fieldid.size()) {
			return fieldid.get(newindex);
		}
		return fieldid.get(0);
	}

	public int getNextIndexFromFieldId(int _fieldid) {
		int newindex = fieldid.indexOf(_fieldid) + 1;
		if (newindex < fieldid.size()) {
			return newindex;
		}
		return 0;
	}

	public boolean remove(int index) {
		if (index > 0 && index < fieldid.size()) {
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