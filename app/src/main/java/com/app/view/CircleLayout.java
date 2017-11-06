package com.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/10/12.
 */

public class CircleLayout extends ViewGroup {
    private int radius;

    public CircleLayout(Context context) {
        super(context);
    }

    public CircleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measureWidth=MeasureSpec.getMode(widthMeasureSpec);
        int measureHeight=MeasureSpec.getMode(heightMeasureSpec);

        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);

//        measureChildren(widthMeasureSpec,heightMeasureSpec);
        for(int i=0;i<this.getChildCount();i++){
            View view=this.getChildAt(i);
            measureChild(view,widthMeasureSpec,heightMeasureSpec);
        }

        setMeasuredDimension(widthSize,heightSize);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width=this.getWidth()/5;
        int middleWidth=width;
        int height=this.getHeight()/3;
        Log.d("MeasureHeight",height+"");

        int childWidth=0;
        int childHeight=0;

//        List<Integer> left=new ArrayList<>();
//        List<Integer> top=new ArrayList<>();
//        List<Integer> right=new ArrayList<>();
//        List<Integer> bottom=new ArrayList<>();
//
//        for(int j=0;j<this.getChildCount();j++){
//            if(j==0){
//                left.add(20);
//            }else{
//                left.add(width);
//                width+=middleWidth;
//            }
//
//        }


        View view1=this.getChildAt(0);
        view1.layout(20,height,view1.getMeasuredWidth()+20,view1.getMeasuredHeight()+height);
        int view2Left=view1.getMeasuredWidth();
        int view2Top=view1.getMeasuredHeight()+height+20;
        int view2Right=view1.getMeasuredWidth();
        int view2Bottom=view1.getMeasuredHeight()+height+20;
//        int view4Right=


        View view2=this.getChildAt(1);
        view2.layout(width,view2Top,view2Right+view2.getMeasuredWidth(),view2Bottom+view2.getMeasuredHeight());
        int view3Right=view2Right+view2.getMeasuredWidth()-width;
        int view3Bottom=view2Bottom+view2.getMeasuredHeight()-view2Top;


        View view3=this.getChildAt(2);
        view3.layout(width*3-20,view2Top,view3Right+width*3-20,view2Bottom+view3.getMeasuredHeight());

        View view4=this.getChildAt(3);
        view4.layout(this.getMeasuredWidth()-20-view4.getMeasuredWidth(),height+30,view1.getMeasuredWidth()+this.getMeasuredWidth()-20-view4.getMeasuredWidth(),view4.getMeasuredHeight()+height-30);



//        for(int i=0;i<this.getChildCount();i++){
//            View view=this.getChildAt(i);
//            if(i==0){
//                Log.d("childHeight",view.getMeasuredWidth()+"");  //240
//                view.layout(10,height,view.getMeasuredWidth(),view.getMeasuredHeight()+height);
//            }else{
//                view.layout(width,(height+20+20+childHeight),view.getMeasuredWidth(),view.getMeasuredHeight()+(height+20+20+childHeight));
//                width+=middleWidth;
//            }
//
//
//            childWidth=view.getMeasuredWidth();
//            childHeight=view.getMeasuredHeight();
//        }








//        radius=this.getWidth()/2;
//        int cCount = getChildCount();
//        int lastW = 0;
//
//        //圆心坐标
//        float[] circleCentre = {getWidth()/2*1.0f, getHeight()/2*1.0f};
//
//        //每个占多少个弧度
////        float oItem = 360/cCount*1.0f;
//        float oItem = (float) (2*Math.PI/cCount*1.0f);
//
//        //cCount个坐标
//        float[][] xyPosition = new float[cCount][2];
//        for (int i=0; i<cCount; i++)
//        {
//            xyPosition[i] = MyMath.getXYPoint(circleCentre,radius,oItem*(i));
//
//            //x坐标
//            int xLabel = (int) xyPosition[i][0];
//            //y坐标
//            int yLabel = (int) xyPosition[i][1];
//
////            Log.d(TAG, "position : (" + xLabel + "," + yLabel + ")");
//            View view = getChildAt(i);
//            view.layout((int) (xLabel - view.getMeasuredWidth() / 2 * 1.0f), (int) (yLabel - view.getMeasuredHeight() / 2 * 1.0f), (int) (xLabel + view.getMeasuredWidth() / 2 * 1.0f), (int) (yLabel + view.getMeasuredHeight() / 2 * 1.0f));
//
//        }
    }
}
