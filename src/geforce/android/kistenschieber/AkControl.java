package geforce.android.kistenschieber;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;

public class AkControl implements OnKeyListener {
	private static final String TAG = "AkControl";
	private Level level;
    
	public AkControl(Level _level) {
		level = _level;
	}	

	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			switch(event.getKeyCode()) {
			case (KeyEvent.KEYCODE_DPAD_CENTER):
				Log.i(TAG, "CENTER pressed");
				return true;
			case (KeyEvent.KEYCODE_DPAD_DOWN):
				Log.i(TAG, "DOWN pressed");
				level.moveFig(Level.GO_DOWN);
				return true;
			case (KeyEvent.KEYCODE_DPAD_LEFT):
				Log.i(TAG, "LEFT pressed");
				level.moveFig(Level.GO_LEFT);
				return true;
			case (KeyEvent.KEYCODE_DPAD_RIGHT):
				Log.i(TAG, "RIGHT pressed");
				level.moveFig(Level.GO_RIGHT);
				return true;
			case (KeyEvent.KEYCODE_DPAD_UP):
				Log.i(TAG, "UP pressed");
				level.moveFig(Level.GO_UP);
				return true;
			case (KeyEvent.KEYCODE_BACK):
				Log.i(TAG, "BACK pressed");
				level.revokeMove();
				return true;	
			}
		}
		return false;
	}
}