package geforce.android.kistenschieber;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;


<<<<<<< HEAD
public class Surface extends Activity implements OnGesturePerformedListener {
=======
public class Surface extends Activity {//implements OnClickListener {
>>>>>>> branch 'master' of git@github.com:g3force/Androkistenschieber.git
	private static final String TAG = "Surface";
    public static final int DIALOG_WIN_ID = 0,
    						DIALOG_INFO_ID = 1,
    						DIALOG_OPENLEVEL_ID = 2;
    public static final int MENU_ITEM_INFO = Menu.FIRST,
    						MENU_ITEM_OPENLEVEL = Menu.FIRST + 1,
    						MENU_ITEM_RESTART = Menu.FIRST + 2;
    private CharSequence[] menu_openlevel_items;
    private AvailableLevels availableLevels = new AvailableLevels();
    private GridView surfaceView;
	private Level level;
	private AkControl akControl;
<<<<<<< HEAD
	private Resources res;
    private GestureLibrary mLibrary;
=======
	private Resources res; 
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;
>>>>>>> branch 'master' of git@github.com:g3force/Androkistenschieber.git
	
	
	@Override
	public void onCreate(Bundle icicle) {
	  super.onCreate(icicle);
	  setContentView(R.layout.main);
	  
	  if (level == null) {
		  Log.i(TAG,"call init()");
		  init();
	  }
	  
<<<<<<< HEAD
	  // init gestures
	  mLibrary = GestureLibraries.fromRawResource(this, R.raw.controls);
	  if(mLibrary.load()) {
		  Log.i(TAG, "mLibrary loaded");
	  }
	  else {
		  Log.w(TAG, "mLibrary not loaded");
	  }
      GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.gestures);
      gestures.addOnGesturePerformedListener(this);
	} 
=======
	  // Gesture detection
      gestureDetector = new GestureDetector(new Gestures(level));
      gestureListener = new View.OnTouchListener() {
          public boolean onTouch(View v, MotionEvent event) {
              if (gestureDetector.onTouchEvent(event)) {
                  return true;
              }
              return false;
          }
      };
	  
	}
>>>>>>> branch 'master' of git@github.com:g3force/Androkistenschieber.git
	
	private void init() {
		// get a reference to the surface
		surfaceView = (GridView)findViewById(R.id.surfaceLayout);
		
		//surfaceView.setOnClickListener(this); 
		surfaceView.setOnTouchListener(gestureListener);
		
		// load Resources
		res = getResources();		
		
		getAllAvailableLevels();
		
		// load all titles of available levels into the open-menu
		menu_openlevel_items = new CharSequence[availableLevels.size()];
		for(int i=0;i<availableLevels.size();i++) {
			LevelInfo levelinfo = new LevelInfo(this, availableLevels.getInfoid(i),availableLevels.getFieldid(i));
			menu_openlevel_items[i] = (CharSequence) levelinfo.getTitle();
		}
		
		// create a new level with the specified content and load it on the surface
		loadLevel(0);
	}

	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		ArrayList<Prediction> predictions = mLibrary.recognize(gesture);
		Log.d(TAG,"Gesture");
		// We want at least one prediction
		if (predictions.size() > 0) {
			Prediction prediction = predictions.get(0);
			// We want at least some confidence in the result
			if (prediction.score > 1.0) {
				// Show the spell
				Toast.makeText(this, prediction.name, Toast.LENGTH_SHORT).show();
			}
		}		
	}
	
	private void getAllAvailableLevels() {
		// all available levels that should be displayed in the open-menu
		availableLevels.add(R.array.level_000_fields, R.array.level_000_info);
		availableLevels.add(R.array.level_001_fields, R.array.level_001_info);
		availableLevels.add(R.array.level_002_fields, R.array.level_002_info);
		availableLevels.add(R.array.level_003_fields, R.array.level_003_info);
		availableLevels.add(R.array.level_004_fields, R.array.level_004_info);
		availableLevels.add(R.array.level_005_fields, R.array.level_005_info);
		availableLevels.add(R.array.level_006_fields, R.array.level_006_info);
		availableLevels.add(R.array.level_007_fields, R.array.level_007_info);
		availableLevels.add(R.array.level_008_fields, R.array.level_008_info);
		availableLevels.add(R.array.level_009_fields, R.array.level_009_info);
		availableLevels.add(R.array.level_010_fields, R.array.level_010_info);
		availableLevels.add(R.array.level_011_fields, R.array.level_011_info);
		availableLevels.add(R.array.level_012_fields, R.array.level_012_info);
		availableLevels.add(R.array.level_013_fields, R.array.level_013_info);
		availableLevels.add(R.array.level_014_fields, R.array.level_014_info);
		availableLevels.add(R.array.level_015_fields, R.array.level_015_info);
		availableLevels.add(R.array.level_016_fields, R.array.level_016_info);
		availableLevels.add(R.array.level_017_fields, R.array.level_017_info);
		availableLevels.add(R.array.level_018_fields, R.array.level_018_info);
		availableLevels.add(R.array.level_019_fields, R.array.level_019_info);
		availableLevels.add(R.array.level_020_fields, R.array.level_020_info);
		availableLevels.add(R.array.level_021_fields, R.array.level_021_info);
	}
	
	private void loadLevel(int index) {		
		// create level with specified content and some references
		level = new Level(this,this,availableLevels.getFieldid(index),surfaceView,gestureListener);
		
		// put fields of the level on the surface
		surfaceView.setAdapter(level);
		surfaceView.setNumColumns(level.getWidth());
		
		// handle inputs
		akControl = new AkControl(level);
		surfaceView.setOnKeyListener(akControl);
	}

	protected Dialog onCreateDialog(int id) {
	    AlertDialog dialog;
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    
	    Log.d(TAG,"Dialog called with id "+id);
	    
	    switch(id) {
	    case DIALOG_WIN_ID:
	    	builder.setMessage(R.string.text_won)
	    	       .setCancelable(false)
	    	       .setNeutralButton("OK", new DialogInterface.OnClickListener() {
	    	           public void onClick(DialogInterface dialog, int id) {
	    	                dialog.cancel();
	    	                int newindex = availableLevels.getNextIndexFromFieldId(level.getFieldId());
	    	                Log.i(TAG,"Index of new level: "+newindex);
	    	                loadLevel(newindex);
	    	           }
	    	       });
	        break;
	    case DIALOG_INFO_ID:
	    	 String text = res.getString(R.string.text_info) + "\n" 
	    	 					+ res.getString(R.string.text_version) + " " + res.getString(R.string.version);
	    	 builder.setMessage(text)
 	       			.setCancelable(false)
 	       			.setNeutralButton("OK", new DialogInterface.OnClickListener() {
 	       				public void onClick(DialogInterface dialog, int id) {
 	       					dialog.cancel();
 	       				}
 	       			});
	    	break;
	    case DIALOG_OPENLEVEL_ID:
	    	//String[] levels = res.getStringArray(R.array.level_available);	    	
	    	builder.setTitle(R.string.text_chooselevel);
    		builder.setItems(menu_openlevel_items, new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int item) {
    		        Toast.makeText(getApplicationContext(), menu_openlevel_items[item], Toast.LENGTH_SHORT).show();
    		        loadLevel(item);
    		    }
    		});

	    	break;
	    default:
	        dialog = null;
	    }
	    dialog = builder.create();
	    return dialog;
	}
	
	@Override
	public void onConfigurationChanged(Configuration _newConfig) {
		//if (_newConfig.orientation != Configuration.ORIENTATION_LANDSCAPE) {
			super.onConfigurationChanged(_newConfig);
		//}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 super.onCreateOptionsMenu(menu);
		 
		 int groupId = 0;
		 
		 menu.add(groupId,MENU_ITEM_INFO,Menu.NONE,R.string.menu_info);
		 menu.add(groupId,MENU_ITEM_OPENLEVEL,Menu.NONE,R.string.menu_openlevel);
		 menu.add(groupId,MENU_ITEM_RESTART,Menu.NONE,R.string.menu_restart);
		 
		 return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		
		switch(item.getItemId()) {
		case (MENU_ITEM_INFO):
			showDialog(Surface.DIALOG_INFO_ID);
			return true;
		case (MENU_ITEM_OPENLEVEL):
			showDialog(Surface.DIALOG_OPENLEVEL_ID);
			return true;
		case (MENU_ITEM_RESTART):
			loadLevel(availableLevels.getIndex(level.getFieldId()));
			return true;
		}
		
		
		return false;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		
		// modify menu items
		
		return true;
	}


	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
}
