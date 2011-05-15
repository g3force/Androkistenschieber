package geforce.android.kistenschieber;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

public class LevelInfo {
	private static final String TAG = "LevelInfo";
	private Context mContext;
	private int levelid = 0;
	private int infoid = 0;
	private String author = "";
	private String version = "";
	private String title = "";
	private String description = "";
	
	public LevelInfo(Context _context,int _infoid,int _levelid) {
		mContext = _context;
		infoid = _infoid;
		levelid = _levelid;
		
		loadInfos();
	}
	
	private void loadInfos() {
		Resources res = mContext.getResources();
		String[] infos = res.getStringArray(infoid);
		
		if (infos.length == 4) {
			author = infos[0];
			version = infos[1];
			title = infos[2];
			description = infos[3];
		}
		else {
			Log.i(TAG,"number of information is wrong!");
		}
		if (author.equals("")) {
			author = res.getString(R.string.info_empty);
		}
		if (version.equals("")) {
			version = res.getString(R.string.info_empty);
		}
		if (title.equals("")) {
			title = res.getString(R.string.info_empty);
		}
		if (description.equals("")) {
			description = res.getString(R.string.info_empty);
		}
	}
	
	public String getLevelidString() {
		return String.valueOf(levelid);
	}
	
	public int getLevelid() {
		return levelid;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getVersion() {
		return version;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getinfoid() {
		return infoid;
	}
	
	public void reloadInfos() {
		loadInfos();
	}
}