package geforce.android.kistenschieber;

import java.util.ArrayList;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.util.Log;
import android.widget.Toast;

class Gestures implements OnGesturePerformedListener {
	private static final String TAG = "Gestures";
    private Surface surface;
    private GestureLibrary mLibrary;
    
    public Gestures(Surface _surface) {
		surface = _surface;
		mLibrary = GestureLibraries.fromRawResource(surface, R.raw.controls);
		if(mLibrary.load()) {
			Log.i(TAG, "mLibrary loaded");
		}
		else {
			Log.w(TAG, "mLibrary not loaded");
		}
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
				Toast.makeText(surface, prediction.name, Toast.LENGTH_SHORT).show();
			}
		}		
	}
}
