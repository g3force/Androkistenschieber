package geforce.android.kistenschieber;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

public class Surface extends Activity {
	private static final String TAG = "Surface";
	public static final int DIALOG_WIN_ID = 0, DIALOG_INFO_ID = 1,
			DIALOG_OPENLEVEL_ID = 2;
	public static final int MENU_ITEM_INFO = Menu.FIRST,
			MENU_ITEM_OPENLEVEL = Menu.FIRST + 1,
			MENU_ITEM_RESTART = Menu.FIRST + 2;
	private CharSequence[] menu_openlevel_items;
	private AvailableLevels availableLevels = new AvailableLevels();
	private GridView surfaceView;
	private Level level;
	private AkControl akControl;
	private Resources res;
//	private GestureLibrary mLibrary;
//	private OnTouchListener gestureListener;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);

		if (level == null) {
			Log.i(TAG, "call init()");
			init();
		}

//		// init gestures
//		mLibrary = GestureLibraries.fromRawResource(this, R.raw.controls);
//		if (mLibrary.load()) {
//			Log.i(TAG, "mLibrary loaded");
//		} else {
//			Log.w(TAG, "mLibrary not loaded");
//		}
//		GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.gestures);
//		gestures.addOnGesturePerformedListener(akControl);
	}

	private void init() {
		// get a reference to the surface
		surfaceView = (GridView) findViewById(R.id.surfaceView);
		
//		frameLayout.setOnTouchListener(akControl);
		
		// load Resources
		res = getResources();

		// load levels from resource
		availableLevels.loadAllAvailableLevels();

		// load all titles of available levels into the open-menu
		menu_openlevel_items = new CharSequence[availableLevels.size()];
		for (int i = 0; i < availableLevels.size(); i++) {
			LevelInfo levelinfo = new LevelInfo(this,
					availableLevels.getInfoid(i), availableLevels.getFieldid(i));
			menu_openlevel_items[i] = (CharSequence) levelinfo.getTitle();
		}

		// create a new level with the specified content and load it on the
		// surface
		loadLevel(0);

		level.moveFig(Level.GO_UP);
	}
	
	private void loadLevel(int index) {
		// create level with specified content and some references
		level = new Level(this, this, availableLevels.getFieldid(index),
				surfaceView);

		// put fields of the level on the surface
		surfaceView.setAdapter(level);
		surfaceView.setNumColumns(level.getWidth());

		// handle inputs
		akControl = new AkControl(this);
		surfaceView.setOnKeyListener(akControl);
	}

	protected Dialog onCreateDialog(int id) {
		AlertDialog dialog;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		Log.d(TAG, "Dialog called with id " + id);

		switch (id) {
		case DIALOG_WIN_ID:
			builder.setMessage(R.string.text_won)
					.setCancelable(false)
					.setNeutralButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
									int newindex = availableLevels
											.getNextIndexFromFieldId(level
													.getFieldId());
									Log.i(TAG, "Index of new level: "
											+ newindex);
									loadLevel(newindex);
								}
							});
			break;
		case DIALOG_INFO_ID:
			String text = res.getString(R.string.text_info) + "\n"
					+ res.getString(R.string.text_version) + " "
					+ res.getString(R.string.version);
			builder.setMessage(text)
					.setCancelable(false)
					.setNeutralButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});
			break;
		case DIALOG_OPENLEVEL_ID:
			// String[] levels = res.getStringArray(R.array.level_available);
			builder.setTitle(R.string.text_chooselevel);
			builder.setItems(menu_openlevel_items,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int item) {
							Toast.makeText(getApplicationContext(),
									menu_openlevel_items[item],
									Toast.LENGTH_SHORT).show();
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
		// if (_newConfig.orientation != Configuration.ORIENTATION_LANDSCAPE) {
		super.onConfigurationChanged(_newConfig);
		// }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		int groupId = 0;

		menu.add(groupId, MENU_ITEM_INFO, Menu.NONE, R.string.menu_info);
		menu.add(groupId, MENU_ITEM_OPENLEVEL, Menu.NONE,
				R.string.menu_openlevel);
		menu.add(groupId, MENU_ITEM_RESTART, Menu.NONE, R.string.menu_restart);

		return true;
	}  

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);

		switch (item.getItemId()) {
		case (MENU_ITEM_INFO):
			showDialog(Surface.DIALOG_INFO_ID);
			return true;
		case (MENU_ITEM_OPENLEVEL):
			showDialog(Surface.DIALOG_OPENLEVEL_ID);
			return true;
		case (MENU_ITEM_RESTART):
			restart();
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

	}

	public Level getLevel() {
		return level;
	}

	public void restart() {
		loadLevel(availableLevels.getIndex(level.getFieldId()));
	}

}
