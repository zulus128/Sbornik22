package com.vkassin.sbornik22;

import java.util.LinkedList;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.widget.ImageView;

public class PainterView extends ImageView {
	
	private Bitmap  mBitmap;
	private Canvas  mCanvas;
	private Path    mPath;
	private Point mPoint;
	Context context;
	public Paint   mPaint, tmpPaint, erasePaint;
	private Paint circlePaint;
	private Paint outercirclePaint;
	private Paint   mBitmapPaint;
	
//	private LinkedList<DrawInf> paths = new LinkedList<DrawInf>();
//	private LinkedList<DrawInf> undonePaths = new LinkedList<DrawInf>();
	
	private boolean dragMode = true;
	private boolean eraseMode = false;
	
	// These matrices will be used to scale points of the image
    Matrix defaultMatrix  = new Matrix(); 
    Matrix tmpMatrix  = new Matrix();
    Matrix tmpSaveMatrix = new Matrix();
    
    static final int MAX_SCALE_FACTOR = 4;

    // The 3 states (events) which the user is trying to perform
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;
    
    int bmWidth = 1;
    int bmHeight = 1;

	@SuppressLint("NewApi")
	public PainterView(Context c, AttributeSet attrs) {
		super(c,attrs);
		setDrawingCacheEnabled(true);
		context=c;
		circlePaint = new Paint();
	    mPaint = new Paint();
	    outercirclePaint = new Paint();
	    outercirclePaint.setAntiAlias(true);
	    circlePaint.setAntiAlias(true);
	    mPaint.setAntiAlias(true);        
	    mPaint.setColor(Color.BLACK);
	    outercirclePaint.setColor(0x44FFFFFF);
	    circlePaint.setColor(0xAADD5522);
	    outercirclePaint.setStyle(Paint.Style.STROKE);
	    circlePaint.setStyle(Paint.Style.FILL);        
	    mPaint.setStyle(Paint.Style.STROKE);
	    mPaint.setStrokeJoin(Paint.Join.ROUND);
	    mPaint.setStrokeCap(Paint.Cap.ROUND);
	    mPaint.setStrokeWidth(5);
	    outercirclePaint.setStrokeWidth(6);   
	    tmpPaint = new Paint(mPaint);
	    erasePaint = new Paint(mPaint);
	    erasePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
	    mCanvas = new Canvas();               
	}
	
	
	int width, height;
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		fitImage(w,h);
		width = w;
		height = h;
		mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_4444);
		mCanvas = new Canvas(mBitmap);
		mCanvas.setMatrix(tmpMatrix);
	}
	
	
	float redundantYSpace;
	float redundantXSpace;
	public void fitImage(int width, int height){
		// Fit to screen.
		mViewMatrix.reset();
		mCurSavedMatrix.reset();
		float scale;
		float scaleX = (float) width / (float) bmWidth;
		float scaleY = (float) height / (float) bmHeight;
		scale = Math.min(scaleX, scaleY);
		mMaxScale = scale * MAX_SCALE_FACTOR;
		mMinScale = scale / MAX_SCALE_FACTOR;
		mViewMatrix.setScale(scale, scale);
		setImageMatrix(mViewMatrix);
		// Center the image
		redundantYSpace = (float) height - (scale * (float) bmHeight);
		redundantXSpace = (float) width - (scale * (float) bmWidth);
		redundantYSpace /= (float) 2;
		redundantXSpace /= (float) 2;

		mViewMatrix.postTranslate(redundantXSpace, redundantYSpace);
		setImageMatrix(mViewMatrix);
		defaultMatrix.set(mViewMatrix);
		redraw = true;
	}
	
	@Override
	public void setImageBitmap(Bitmap bm) {
		super.setImageBitmap(bm);
//		if (bm != 0) {
			if (bmWidth != bm.getWidth() & bmHeight != bm.getHeight()) {
				bmWidth = bm.getWidth();
				bmHeight = bm.getHeight();
				fitImage(width, height);
			} else {
				bmWidth = bm.getWidth();
				bmHeight = bm.getHeight();
			}

//		}
	}
	
	
	private boolean redraw = false;
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(redraw){
			mBitmap.eraseColor(Color.TRANSPARENT);
			mCanvas.setMatrix(tmpMatrix);
//			for (DrawInf inf:paths){
//				tmpPaint.setColor(inf.color);
//				tmpPaint.setStrokeWidth(inf.width);
//				erasePaint.setStrokeWidth(inf.width);
//				if(inf.isPath()){
//					if(inf.erase){
//						mCanvas.drawPath(inf.path, erasePaint);
//					}else{
//						mCanvas.drawPath(inf.path, tmpPaint);
//					}
//				}else{
//					if(inf.erase){
//						mCanvas.drawPoint(inf.point.x, inf.point.y, erasePaint);
//					}else{
//						mCanvas.drawPoint(inf.point.x, inf.point.y, tmpPaint);
//					}
//				}
//			}
			redraw = false;
		}
		
		canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
		setDrawingCacheEnabled(true);
	}

	private float mX, mY;
	private static final float TOUCH_TOLERANCE = 0;

	private void touch_start(float x, float y) {
//		undonePaths.clear();
		mPoint = new Point((int)x,(int)y);
//	    paths.add(new DrawInf(mPoint,mPaint.getColor(),mPaint.getStrokeWidth(), eraseMode));
	    mCanvas.drawPoint(x, y, mPaint);
		mPath = new Path();
//		paths.add(new DrawInf(mPath,mPaint.getColor(),mPaint.getStrokeWidth(), eraseMode));
		mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
	}
	
	private void touch_move(float x, float y) {
		float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
            mX = x;
            mY = y;
        }
        mCanvas.drawPath(mPath, mPaint);
	}
	
	private void touch_up() {
		mPath.lineTo(mX, mY);
        // commit the path to our offscreen
        mCanvas.drawPath(mPath, mPaint);
        // kill this so we don't double draw
	}

	private Matrix mViewMatrix = new Matrix();
	private Matrix mCurSavedMatrix = new Matrix();
	// These PointF objects are used to record the point(s) the user is touching
	private PointF start = new PointF();
	private PointF mCurMidPoint = new PointF();
	private float mOldDist = 1f;
	private float mMinScale;
	private float mMaxScale;
	float[] mTmpValues = new float[9];
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(dragMode){
			float scale;
	        // Handle touch events here...

			switch (event.getAction() & MotionEvent.ACTION_MASK) {
		    case MotionEvent.ACTION_DOWN: // first finger down only
		        mCurSavedMatrix.set(mViewMatrix);
		        tmpSaveMatrix.set(tmpMatrix);
		        start.set(event.getX(), event.getY());
		        mode = DRAG;
		        break;

		    case MotionEvent.ACTION_UP: // first finger lifted
		    case MotionEvent.ACTION_POINTER_UP: // second finger lifted
		        mode = NONE;

		        break;

		    case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down
		        mOldDist = spacing(event);
		        if (mOldDist > 5f) {
		            mCurSavedMatrix.set(mViewMatrix);
		            tmpSaveMatrix.set(tmpMatrix);
		            midPoint(mCurMidPoint, event);
		            mode = ZOOM;
		        }
		        break;

		    case MotionEvent.ACTION_MOVE:
		        if (mode == DRAG) {
		            mViewMatrix.set(mCurSavedMatrix);
		            tmpMatrix.set(tmpSaveMatrix);
		            mViewMatrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
		            tmpMatrix.postTranslate(event.getX() - start.x, event.getY() - start.y);// create the transformation in the matrix  of points
		        } else if (mode == ZOOM) {
		            // pinch zooming
		            float newDist = spacing(event);
		            if (newDist > 1.f) {
		                mViewMatrix.set(mCurSavedMatrix);
		                tmpMatrix.set(tmpSaveMatrix);
		                scale = newDist / mOldDist; // setting the scaling of the
		                                            // matrix...if scale > 1 means
		                                            // zoom in...if scale < 1 means
		                                            // zoom out
		                scale = checkScale(scale);
		                mViewMatrix.postScale(scale, scale, mCurMidPoint.x, mCurMidPoint.y);
		                tmpMatrix.postScale(scale, scale, mCurMidPoint.x, mCurMidPoint.y);
		            }
		        }
		        break;
		    }
			redraw = true;
	        setImageMatrix(mViewMatrix); // display the transformation on screen

	        return true; // indicate event was handled
		}else{
			float x = event.getX();
			float y = event.getY();
			float[] values = new float[9];
			tmpMatrix.getValues(values);
			x = x/values[Matrix.MSCALE_X] - values[Matrix.MTRANS_X]/values[Matrix.MSCALE_X];
			y = y/values[Matrix.MSCALE_Y] - values[Matrix.MTRANS_Y]/values[Matrix.MSCALE_Y];
			
	
			switch (event.getAction()) {
			    case MotionEvent.ACTION_DOWN:
			        touch_start(x, y);
			        invalidate();
			        break;
			    case MotionEvent.ACTION_MOVE:
			    	touch_move(x, y);
				    invalidate();
			        break;
			    
			    case MotionEvent.ACTION_UP:
				    touch_up();
				    invalidate();
			        break;
			}
			
			return true;
		}
	}
	
	 private float spacing(MotionEvent event) 
	    {
	        float x = event.getX(0) - event.getX(1);
	        float y = event.getY(0) - event.getY(1);
	        return FloatMath.sqrt(x * x + y * y);
	    }

	    /*
	     * --------------------------------------------------------------------------
	     * Method: midPoint Parameters: PointF object, MotionEvent Returns: void
	     * Description: calculates the midpoint between the two fingers
	     * ------------------------------------------------------------
	     */

	    private void midPoint(PointF point, MotionEvent event) 
	    {
	        float x = event.getX(0) + event.getX(1);
	        float y = event.getY(0) + event.getY(1);
	        point.set(x / 2, y / 2);
	    }
	    

//	@Override
//	public void colorChanged(int color) {
//		mPaint.setColor(color);
//	}
//	
//	public void clear(){
//		paths.clear();
//		undonePaths.clear();
//		mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//		mCanvas = new Canvas(mBitmap);
//		mCanvas.setMatrix(tmpMatrix);
//		invalidate();
//	}
	
	public void setWidht(int widht){
		mPaint.setStrokeWidth(widht);
	}

	public void eraserMode() {
		eraseMode = true;
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
	}
	
//	public void paintMode(){
//		eraseMode = false;
//		mPaint.setXfermode(0);
//	}
	
	public Bitmap getBitmap(){
		return getDrawingCache();
	}
	
	public void resetMatrix(){
		mViewMatrix.set(defaultMatrix);
		setImageMatrix(mViewMatrix);
		tmpMatrix.reset();
		redraw = true;
	}
	
//	public void Undo(){
//		if (paths.size()>0) {
//			if(!paths.getLast().isPath()){
//				undonePaths.add(paths.removeLast());
//				invalidate();
//			}else{
//				undonePaths.add(paths.removeLast());
//				undonePaths.add(paths.removeLast());
//				invalidate();
//			}
//		       
//		}
//		redraw = true;
//	}
//	
//	public void Redo(){
//		if (undonePaths.size()>0) {
//		       paths.add(undonePaths.removeLast());
//		       invalidate();
//		       if(undonePaths.getLast().isPath()){
//		    	   paths.add(undonePaths.removeLast());
//		    	   invalidate();
//		       }
//		}
//		redraw = true;
//	}
	
	public void dragMode(){
		dragMode = true;
	}
	
	public void editMode(){
		dragMode = false;
	}

	
	public void changeMode(){
		if(dragMode){
			dragMode = false;
		}else{
			dragMode = true;
		}
	}
	
	public float checkScale(float scale){
		float resultScale = getMatrixScale(mViewMatrix)*scale;
		if(resultScale>mMaxScale){
			return mMaxScale/getMatrixScale(mViewMatrix);
		}
		if(resultScale<mMinScale){
			return mMinScale/getMatrixScale(mViewMatrix);
		}
		return scale;
	}
	
	private float getMatrixScale(Matrix matrix) {
	    matrix.getValues(mTmpValues);
	    return mTmpValues[Matrix.MSCALE_X];
	}
}