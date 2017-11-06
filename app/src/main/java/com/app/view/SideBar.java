package com.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.appproject.R;

public class SideBar extends View {
    private static String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};
    private Paint paint=new Paint();
    private Context context;
    private int choose=-1;
    private TextView home_fraA2_contactHeader_TextView;
    private int index;
    private ISideBarSelectCallBack callBack;

    public TextView getHome_fraA2_contactHeader_TextView() {
        return home_fraA2_contactHeader_TextView;
    }

    public void setHome_fraA2_contactHeader_TextView(TextView home_fraA2_contactHeader_TextView) {
        this.home_fraA2_contactHeader_TextView = home_fraA2_contactHeader_TextView;
    }

    public SideBar(Context context) {
        super(context, null);
        this.context=context;
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //规定画布的大小,getHeight是该自定义控件的高度
        int height=getHeight();
        int width=getWidth();

        //得到每一个字母的高度(整个画布的高度除以所有字母个数)
        int singleHeight=height/letters.length;

        //循环画出所有字母
        for(int i=0;i<letters.length;i++){
            paint.setColor(Color.BLACK);

            //设置抗锯齿
            paint.setAntiAlias(true);
            paint.setTextSize(42);

            //
            float x=(width-paint.measureText(letters[i]))/2;
            float y=singleHeight*(i+1);

            if(i==choose){
                x=(width-paint.measureText(letters[i]))/2-30;
                y=singleHeight*(i+1)+8;
                paint.setTextSize(60);
                //设置粗体
                paint.setTypeface(Typeface.DEFAULT_BOLD);
                paint.setColor(getResources().getColor(R.color.colorPrimary));

            }
            canvas.drawText(letters[i],x,y,paint);
            paint.reset();
        }

    }

    //事件分发
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action=event.getAction();
        float y=event.getY();

        //得到字母的下标
        /**
         *              index               y
         *         --------------- = ---------------
         *         letters.length       getHeight()
         * */
        index=(int) ((y/getHeight())*letters.length);

        //防止手指超出自定义框时，数组下标越界
        if(index>=letters.length){
            index=letters.length-1;
        }else if(index<=0){
            index=0;
        }
        if (callBack != null) {
            callBack.onSelectStr(letters[index]);
        }
        switch (action){
            //手指抬起
            case MotionEvent.ACTION_UP:
                if(getHome_fraA2_contactHeader_TextView()!=null){
                    getHome_fraA2_contactHeader_TextView().setVisibility(View.INVISIBLE);
                }

//                setBackgroundColor(Color.WHITE);
//                choose=-1;
//                LayoutInflater.from().inflate(R.layout.fragment_tab_a2,null).findViewById(R.id.home_fraA2_contactHeader_TextView).setVisibility();
                break;
            //default包含了move,down
            default:
//                setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                choose=index;
                if(getHome_fraA2_contactHeader_TextView()!=null){
                    getHome_fraA2_contactHeader_TextView().setText(letters[choose]);
                    getHome_fraA2_contactHeader_TextView().setVisibility(View.VISIBLE);
                }

                invalidate();

                break;


        }

        return true;

    }





    public void setOnStrSelectCallBack(ISideBarSelectCallBack callBack) {
        this.callBack = callBack;
    }

    public interface ISideBarSelectCallBack {
        void onSelectStr(String selectStr);
    }
}