package geforce.android.kistenschieber;

import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;

public class AkControl implements OnKeyListener, OnTouchListener {
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

	public boolean onTouch(View v, MotionEvent ev) {
		final int historySize = ev.getHistorySize();
	     final int pointerCount = ev.getPointerCount();
	     for (int h = 0; h < historySize; h++) {
	         System.out.printf("At time %d:", ev.getHistoricalEventTime(h));
	         for (int p = 0; p < pointerCount; p++) {
	             System.out.printf("  pointer %d: (%f,%f)",
	                 ev.getPointerId(p), ev.getHistoricalX(p, h), ev.getHistoricalY(p, h));
	         }
	     }
	     System.out.printf("At time %d:", ev.getEventTime());
	     for (int p = 0; p < pointerCount; p++) {
	         System.out.printf("  pointer %d: (%f,%f)",
	             ev.getPointerId(p), ev.getX(p), ev.getY(p));
	     }
		
		return false;
	}
	
	
	
}