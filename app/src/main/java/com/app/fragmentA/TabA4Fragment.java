package com.app.fragmentA;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.activity.SchoolMassDetailsActivity;
import com.app.pojo.Goods;
import com.app.pojo.Merchant;
import com.example.appproject.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabA4Fragment extends Fragment {
    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView recyclerView;
    private View view;
    private Map<Integer,Goods> goodsHashMap=new HashMap<>();
    private LinearLayoutManager linearLayoutManager;

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
        private Map<Integer,Goods> goodsMap;
        private Context context;

        public MyAdapter(Map<Integer,Goods> goodsMap,Context context){
            this.goodsMap=goodsMap;
            this.context=context;
        }



        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(context).inflate(R.layout.home_frag_a4_recycleritem,parent,false);
            LinearLayout line= (LinearLayout) view.findViewById(R.id.SchoolMassActivity_line);
            line.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(TabA4Fragment.this.getActivity(),SchoolMassDetailsActivity.class);
                    startActivity(intent);
                }
            });
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.getGoodsName().setText(goodsMap.get(position).getName());

        }

        @Override
        public int getItemCount() {
            return goodsMap.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            private ImageView goodsHeader;
            private TextView goodsName;
//            private Button goodsHelp;

            public MyViewHolder(View view){
                super(view);
                goodsHeader=(ImageView) view.findViewById(R.id.goodsHeader_imageView);
                goodsName=(TextView) view.findViewById(R.id.goodsName_textView);
//                goodsHelp=(Button) view.findViewById(R.id.goodsHelp_button);
            }

            public ImageView getGoodsHeader() {
                return goodsHeader;
            }

            public void setGoodsHeader(ImageView goodsHeader) {
                this.goodsHeader = goodsHeader;
            }

            public TextView getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(TextView goodsName) {
                this.goodsName = goodsName;
            }

//            public Button getGoodsHelp() {
//                return goodsHelp;
//            }
//
//            public void setGoodsHelp(Button goodsHelp) {
//                this.goodsHelp = goodsHelp;
//            }
        }


    }

    private class MyItemDecoration extends RecyclerView.ItemDecoration {
        private Paint mPaint;
        private int mDividerHeight =15;//分割线高度，默认为1px
        private int mOrientation;//列表的方向：LinearLayoutManager.VERTICAL或LinearLayoutManager.HORIZONTAL

        /**
         * 自定义分割线
         *
         * @param orientation   列表方向
         * @param dividerHeight 分割线高度
         * @param dividerColor  分割线颜色
         */
        public MyItemDecoration(int orientation, int dividerHeight, int dividerColor) {
            this.mOrientation=orientation;
            this.mDividerHeight = dividerHeight;
            this.mPaint = new Paint();
            this.mPaint.setColor(dividerColor);
            Log.d("mPaintColor",mPaint.getColor()+"");
//            this.mPaint.setStyle(Paint.Style.FILL);
        }


        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            //设定底部边距为1px
            outRect.set(0, 0, 0, mDividerHeight);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
            if(mOrientation==LinearLayoutManager.VERTICAL){
                drawHorizontal(c, parent);
            }

        }

        //绘制横向 item 分割线
        private void drawHorizontal(Canvas canvas, RecyclerView parent) {
//            final int left = parent.getPaddingLeft();
            final int left = 0;
            final int right = parent.getMeasuredWidth();
            final int childSize = parent.getChildCount();
            for (int i = 0; i < childSize; i++) {
                final View child = parent.getChildAt(i);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int top = child.getBottom() + layoutParams.bottomMargin;
                final int bottom = top + mDividerHeight;
//				if (mDivider != null) {
//					mDivider.setBounds(left, top, right, bottom);
//					mDivider.draw(canvas);
//				}
                if (mPaint != null) {
                    canvas.drawRect(left, top, right, bottom, mPaint);
                }
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        /**
         * 伪造数据
         * */
        for(int i=0;i<20;i++){
            Goods goods=new Goods();
            goods.setName("社团"+(i+1));


            goodsHashMap.put(i,goods);
        }
        initView(inflater,container);

        return view;
    }

    private void initView(LayoutInflater inflater,ViewGroup container){
        view = inflater.inflate(R.layout.fragment_tab_a4, container,false);
        smartRefreshLayout=(SmartRefreshLayout) view.findViewById(R.id.home_fragA4_smartRefreshLayout);
        recyclerView=(RecyclerView) view.findViewById(R.id.home_fragA4_recyclerView);
        linearLayoutManager=new LinearLayoutManager(TabA4Fragment.this.getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setAdapter(new MyAdapter(goodsHashMap,TabA4Fragment.this.getActivity()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new MyItemDecoration(linearLayoutManager.getOrientation(),15,TabA4Fragment.this.getActivity().getResources().getColor(R.color.background)));
    }


    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.d("TabA3Fragment", "onDestroy()");
    }

}
