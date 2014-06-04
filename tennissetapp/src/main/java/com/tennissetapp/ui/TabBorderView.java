package com.tennissetapp.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class TabBorderView extends View {
	private Paint paint = new Paint();
	private int color = Color.GRAY;
	private int tabBackgroundColor = Color.WHITE;
	private float strokeWidth = 2;
	
	public TabBorderView(Context context) {
		super(context);
		init();
	}

	public TabBorderView(Context context, AttributeSet attrs,int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public TabBorderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		
	}
	
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
//		mTextColor = ColorStateList.valueOf(color);
		this.color = color;
	}

	private void init(){
		paint.setAntiAlias(false);
		paint.setColor(color);
		paint.setStrokeWidth(strokeWidth);
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		if(super.isSelected()){
			drawSlected(canvas);
		}
		else{
			drawUnslected(canvas);
		}
	}
	
	private void drawSlected(Canvas canvas){
		int w = getWidth();
		int h = getHeight();
		int arrowWidth = h*3;
		int a = w/2 - arrowWidth/2;
		int b = w/2 + arrowWidth/2;
		
		//fill a triangle
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(tabBackgroundColor);
		Path path = new Path();
		path.setFillType(Path.FillType.EVEN_ODD);
		path.moveTo(a+1, 0);
		path.lineTo(w/2, h);
		path.lineTo(b, 0);
		path.lineTo(a+1, 0);
		path.close();
		canvas.drawPath(path, paint);
		
		//stroke
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(color);
		path = new Path();
		path.moveTo(0, 0);
		path.lineTo(a, 0);
		path.lineTo(w/2, h);
		path.lineTo(b, 0);
		path.lineTo(w, 0);
		
		canvas.drawPath(path, paint);
	}
	
	private void drawUnslected(Canvas canvas){
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(color);
		canvas.drawLine(0, 0, getWidth(), 0, paint);
	}

}