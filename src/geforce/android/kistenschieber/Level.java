package geforce.android.kistenschieber;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;



public class Level extends BaseAdapter {
    public boolean valid = false; // is the level solvable?
    public static final int GO_UP=0,GO_LEFT=1,GO_DOWN=2,GO_RIGHT=3;
	private static final String TAG = "Level";
    private Context mContext;
    private HashMap<String,Integer> imageAllocation = new HashMap<String,Integer>(); // gives each character of the saved level a corresponding image 
    private ArrayList<Integer> surfaceParts = new ArrayList<Integer>(); // list of all fields from left top to right ground for adapter
    private Integer[][] surface; // array of all fields, accessible through position [x][y]
    private int imageSize = 0; // calculated size of each field-image to fit level on screen
    private GridView surfaceView; // reference to surface of displayed level
    private int height = 0,width = 0; // height and width in amount of fields of level
    private Surface surfaceActivity;
    private int levelfieldid = 0;
    private ArrayList<Integer[][]> movements = new ArrayList<Integer[][]>();
    OnTouchListener gestureListener;
    
    public Level(Context c,Surface a,int _fieldid, GridView _surfaceView, OnTouchListener _gestureListener) {
        // get reference to some objects
    	mContext = c;
    	surfaceActivity = a;
        surfaceView = _surfaceView;
        levelfieldid = _fieldid;
        gestureListener = _gestureListener;
        
        // get content of level from Resource
        Resources res = mContext.getResources();
        String[] surfaceContent = res.getStringArray(levelfieldid);
        
        // load possible fields and their char-equivalent
        initImageAllocation();
        height = surfaceContent.length;
        
        // get greatest width
        width = 0;
        for(int i=0;i<height;i++) {
        	int len = surfaceContent[i].length(); 
        	if (len > width) {
        		width = len;
        	}
        }
        
        // initialize surface-array
        surface = new Integer[width][height];
        
        // get the width and height of the display
		Display display = ((WindowManager) a.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
	    int screenWidth = display.getWidth();
	    int screenHeight = display.getHeight()-50;
        
        
        // choose size of single image so that all fit into the surface at the screen
        imageSize = screenWidth / width;
        if(imageSize > screenHeight / height) {
        	imageSize = screenHeight / height;
        }
        Log.i(TAG,"imageSize(W,H,Current):"+screenWidth / width+";"
        		+screenHeight / height+";"
        		+imageSize);
        
        // fill surface with images according to level content
        loadLevel(surfaceContent);
        
        // not implemented yet
        valid = checkIntegrity();
        
    }
    
    public int getFieldId() {
    	return levelfieldid;
    }
    
    public int getWidth() {
    	return surface.length;
    }
    
    public int getHeight() {
    	return surface[0].length;
    }

    public int getCount() {
        return surfaceParts.size();
    }

    public Object getItem(int position) {
    	return null;
    }

    public long getItemId(int position) {
    	if(position < surfaceParts.size()) { 
    		return surfaceParts.get(position);
    	}
    	else {
    		return 0;
    	}
    }
    
    public boolean isValid() {
    	return valid;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            
            imageView.setLayoutParams(new GridView.LayoutParams(imageSize,imageSize));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
            imageView.setOnTouchListener(gestureListener);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(surfaceParts.get(position));
        return imageView;
    }
    
    
    public void setPart(int x, int y, int part) {
    	surface[x][y] = part;
    }

    // choose image for each surface-part 
    private void loadLevel(String[] surfaceContent) {
    	Log.i(TAG,"loadLevel called");
    	for(int y=0;y < surfaceContent.length;y++) {
    		String add = "";
			for(int i=width;i>surfaceContent[y].length();i--) {
				add = add + "_";
			}
			surfaceContent[y] = surfaceContent[y] + add;
			Log.d(TAG,surfaceContent[y]);
    		for(int x=0;x < width;x++) {
    			surface[x][y] = imageAllocation.get(String.valueOf(surfaceContent[y].charAt(x)));
    		}
    	}
    	reloadSurfaceParts();
    }
    
    // put surface into surfaceParts-Array
    public void reloadSurfaceParts() {
    	Log.i(TAG,"reloadSurfaceParts called");
    	surfaceParts = new ArrayList<Integer>();
    	for(int y=0;y < surface[0].length;y++) {
    		for(int x=0;x < surface.length;x++) {
    			surfaceParts.add(surface[x][y]);
    			//Log.i("Level",String.valueOf(x)+" "+String.valueOf(y));
    		}
    	}
    	surfaceView.setAdapter(this);
		surfaceView.refreshDrawableState();
    }
    
    private boolean checkIntegrity() {
    	return false;
    }
    
    
    public Point getFigPos() {
    	for(int y=0;y < height; y++) {
    		for(int x=0; x < width; x++) {
    			if (surface[x][y] == R.drawable.figur || surface[x][y] == R.drawable.figurpunkt) {
    				return new Point(x,y);
    			}
    		}
    	}
    	return null;
    }
    
    public boolean moveFig(int direction) {
    	Log.i(TAG,"moveFig called");
    	
    	Point oldFigPos = getFigPos();
    	Point newFigPos;
    	Point newBoxPos;
    	int newField = -1,oldField = -1,newBoxField = -1; // save, if new and old Fields were points or not
    	
    	// change new position depending on the direction
    	newFigPos = moveFrom(oldFigPos,direction);
    	newBoxPos = moveFrom(newFigPos,direction);
    	
    	if(newFigPos == null || newBoxPos == null) {
    		Log.e(TAG,"Couldn't get valid new positions");
    		return false;
    	}
    	
    	if (isFieldWalkable(newFigPos)) {
    		if (surface[newFigPos.x][newFigPos.y] == R.drawable.kiste ||
    							surface[newFigPos.x][newFigPos.y] == R.drawable.kistepunkt) {
    			if (isFieldWalkable(newBoxPos) &&
    	    			surface[newBoxPos.x][newBoxPos.y] != R.drawable.kiste &&
    	    			surface[newBoxPos.x][newBoxPos.y] != R.drawable.kistepunkt) {
    				if(surface[newBoxPos.x][newBoxPos.y] == R.drawable.punkt) {
    	        		newBoxField = R.drawable.kistepunkt;
    	        	}
    	        	else {
    	        		newBoxField = R.drawable.kiste;
    	        	}
    			}
    			else {
    				return false;
    			}
    		}
    		
    		if(surface[oldFigPos.x][oldFigPos.y] == R.drawable.figurpunkt) {
        		oldField = R.drawable.punkt;
        	}
        	else {
        		oldField = R.drawable.boden;
        	}
        	
        	if(surface[newFigPos.x][newFigPos.y] == R.drawable.kistepunkt ||
        						surface[newFigPos.x][newFigPos.y] == R.drawable.punkt) {
        		newField = R.drawable.figurpunkt;
        	}
        	else {
        		newField = R.drawable.figur;
        	}
        	// save last position
        	movements.add(surface.clone());
        	Log.d(TAG,surface.toString());
        	// TODO: wird Array richtig gespeichert?
    		surface[newFigPos.x][newFigPos.y] = newField;
			surface[oldFigPos.x][oldFigPos.y] = oldField;
			if(newBoxField != -1) surface[newBoxPos.x][newBoxPos.y] = newBoxField;
			reloadSurfaceParts();
			if(levelWon()) {
				Log.i(TAG,"Level won!");
				surfaceActivity.showDialog(Surface.DIALOG_WIN_ID);
			}
			return true;
    	}
    	return false;
    }
    
    private boolean isFieldWalkable(Point position) {
    	/* check that:
    	 * figure can't leave the level
    	 * figure can't walk through wall
    	 */
    	if(position.x < width && position.y < height && 
    			position.x >= 0 && position.y >= 0 &&
    			surface[position.x][position.y] != R.drawable.wand) { 
    	
    		return true;
    	}
    	
    	return false;
    }
    
    private Point moveFrom(Point position, int direction) {
    	int x = position.x;
    	int y = position.y;
    	
    	switch(direction) {
    	case (GO_DOWN):	y++; break;
    	case (GO_UP): y--; break;
    	case (GO_RIGHT): x++; break;
    	case (GO_LEFT): x--; break;
    	default: return null;
    	}
    	
    	return new Point(x,y);
    }
    
    private boolean levelWon() {
    	
    	for(int y=0;y < height; y++) {
    		for(int x=0; x < width; x++) {
    			if (surface[x][y] == R.drawable.punkt || surface[x][y] == R.drawable.figurpunkt) {
    				return false;
    			}
    		}
    	}
    	return true;
    }
    
    public void revokeMove() {
    	if(movements.size()>0) {
			surface = movements.get(movements.size()-1).clone();
			movements.remove(movements.size()-1);
			Log.d(TAG,movements.get(movements.size()-1).toString());
			// TODO: Array kopiert sich nicht richtig
			reloadSurfaceParts();
    	}
    }
    
    // initialize images for the surface
    private void initImageAllocation() {
    	imageAllocation.put("_",R.drawable.boden);
        imageAllocation.put("@",R.drawable.figur);
        imageAllocation.put("+",R.drawable.figurpunkt);
        imageAllocation.put("$",R.drawable.kiste);
        imageAllocation.put("*",R.drawable.kistepunkt);
        imageAllocation.put(".",R.drawable.punkt);
        imageAllocation.put("#",R.drawable.wand);
    }
}
