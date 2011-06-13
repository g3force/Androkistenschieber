package geforce.android.kistenschieber;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;

public class AkControl implements OnKeyListener, /*OnTouchListener,*/ OnClickListener {
	private static final String TAG = "AkControl";
	private Surface surface;
//	private GestureLibrary mLibrary;

	public AkControl(Surface _surface) {
		surface = _surface;
		Button button_up = (Button) surface.findViewById(R.id.button_up);
		Button button_left = (Button) surface.findViewById(R.id.button_left);
		Button button_right = (Button) surface.findViewById(R.id.button_right);
		Button button_down = (Button) surface.findViewById(R.id.button_down);
		button_up.setOnClickListener(this);
		button_left.setOnClickListener(this);
		button_right.setOnClickListener(this);
		button_down.setOnClickListener(this);
		
//		mLibrary = GestureLibraries.fromRawResource(surface, R.raw.controls);
//		if (mLibrary.load()) {
//			Log.i(TAG, "mLibrary loaded");
//		} else {
//			Log.w(TAG, "mLibrary not loaded");
//		}
	}

	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			switch (event.getKeyCode()) {
			case (KeyEvent.KEYCODE_DPAD_CENTER):
				Log.i(TAG, "CENTER pressed");
				return true;
			case (KeyEvent.KEYCODE_DPAD_DOWN):
				Log.i(TAG, "DOWN pressed");
				surface.getLevel().moveFig(Level.GO_DOWN);
				return true;
			case (KeyEvent.KEYCODE_DPAD_LEFT):
				Log.i(TAG, "LEFT pressed");
				surface.getLevel().moveFig(Level.GO_LEFT);
				return true;
			case (KeyEvent.KEYCODE_DPAD_RIGHT):
				Log.i(TAG, "RIGHT pressed");
				surface.getLevel().moveFig(Level.GO_RIGHT);
				return true;
			case (KeyEvent.KEYCODE_DPAD_UP):
				Log.i(TAG, "UP pressed");
				surface.getLevel().moveFig(Level.GO_UP);
				return true;
			case (KeyEvent.KEYCODE_BACK):
				Log.i(TAG, "BACK pressed");
				surface.finish();
				return true;
			}
		}
		return false;
	}

//	public boolean onTouch(View v, MotionEvent ev) {
//		final int historySize = ev.getHistorySize();
//		final int pointerCount = ev.getPointerCount();
//		Log.e(TAG,"Touch");
//		for (int h = 0; h < historySize; h++) {
//			Log.w(TAG, "At time "+ev.getHistoricalEventTime(h)+":");
//			for (int p = 0; p < pointerCount; p++) {
//				Log.w(TAG,"  pointer "+ev.getPointerId(p)+": ("+ev.getHistoricalX(p, h)+","+ev.getHistoricalY(p, h)+")");
//			}
//		}
//		System.out.printf("At time %d:", ev.getEventTime());
//		for (int p = 0; p < pointerCount; p++) {
//			System.out.printf("  pointer %d: (%f,%f)", ev.getPointerId(p),
//					ev.getX(p), ev.getY(p));
//		}
//
//		return false;
//	}

	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.button_up: surface.getLevel().moveFig(Level.GO_UP); break;
		case R.id.button_left: surface.getLevel().moveFig(Level.GO_LEFT); break;
		case R.id.button_right: surface.getLevel().moveFig(Level.GO_RIGHT); break;
		case R.id.button_down: surface.getLevel().moveFig(Level.GO_DOWN); break;
		}
	}

//	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
//		ArrayList<Prediction> predictions = mLibrary.recognize(gesture);
//		Log.d(TAG, "Gesture");
//		// We want at least one prediction
//		if (predictions.size() > 0) {
//			Prediction prediction = predictions.get(0);
//			// We want at least some confidence in the result
//			if (prediction.score > 1.0) {
//				// Show the spell
//				Toast.makeText(surface, prediction.name, Toast.LENGTH_SHORT)
//						.show();
//				surface.getLevel().moveFig(Level.GO_UP);
//				if (prediction.name.equals("restart")) {
//					surface.restart();
//				} else if (prediction.name.equals("up")) {
//					surface.getLevel().moveFig(Level.GO_UP);
//				}
//			}
//		}
//	}
}
