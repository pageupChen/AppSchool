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
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.app.activity.SchoolMassActivity;
import com.app.activity.SchoolMassDetailsActivity;
import com.app.pojo.Dynamic;
import com.app.pojo.Mass;
import com.app.pojo.Merchant;
import com.example.appproject.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TabA2Fragment extends Fragment {
	private static int startRecordIndex = 0;
	private static boolean firstIn=false;
	private static boolean autoRefresh=true;
	private SmartRefreshLayout smartRefreshLayout;
	private RecyclerView recyclerView;
    private EditText home_fragA2_search_EditView;
	private View view;
	private MyAdapter myAdapter;
	private String preEditText;
//	private Map<Integer,Mass> massHashMap=new HashMap<>();
	//	private LinearLayoutManager linearLayoutManager;
	private StaggeredGridLayoutManager staggeredGridLayoutManager;
//	private String[] massNameStr={"网球协会","极限飞盘协会","羽毛球协会","棋牌协会","定向越野协会","大轮滑协会","武术协会","跆拳道协会","自行车协会","户外俱乐部",
//			"自由者协会","足球协会","乒乓球协会","素质拓展协会","壁虎漫步攀岩协会","女子篮球协会1","女子篮球协会2","泰拳武术格斗协会","跑酷协会","台球协会",};
	private List<Mass> middleMass=new ArrayList<>();
	private List<Mass> massNameList=new ArrayList<>();
	private List<String> searchMassName=new ArrayList<>();

	private TextWatcher myTextWatcher=new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			Log.d("onTextChanged","true");
//			char[] c=home_fragA2_search_EditView.getText().toString().toCharArray();
			if(TextUtils.isEmpty(home_fragA2_search_EditView.getText())){
				searchMassName.clear();
				massNameList.clear();
//				massHashMap.clear();
				for(int i=0;i<middleMass.size();i++){
					Mass mass=new Mass();
					mass.setMassName(middleMass.get(i).getMassName());
					massNameList.add(mass);
					Log.d("massNameList",massNameList.size()+"");
//					massHashMap.put(i,mass);
				}

			}else{
				//如果执行删除操作，则将massNameList全部重新赋值，才能正确筛选（扩大范围）
				//如果执行加字操作，则在第一次筛选出来的结果集中继续筛选
				//如果全部删除，则将massNameList全部重新赋值，重新显示
				if(!TextUtils.isEmpty(preEditText)&&preEditText.length()>home_fragA2_search_EditView.getText().toString().length()){
					massNameList.clear();
					for(int i=0;i<middleMass.size();i++){
						Mass mass=new Mass();
						mass.setMassName(middleMass.get(i).getMassName());
						massNameList.add(mass);
						Log.d("massNameList",massNameList.size()+"");
//					massHashMap.put(i,mass);
					}
				}


				for(int i=0;i<massNameList.size();i++){
					Log.d("formassName",massNameList.get(i).getMassName());
					if(massNameList.get(i).getMassName().equals(home_fragA2_search_EditView.getText().toString())){
						searchMassName.add(massNameList.get(i).getMassName());
						break;
					}else{
						if(massNameList.get(i).getMassName().contains(home_fragA2_search_EditView.getText().toString())){
							searchMassName.add(massNameList.get(i).getMassName());
//							Log.d("searchMassName",searchMassName.get(0));
						}
					}
				}

				preEditText=home_fragA2_search_EditView.getText().toString();


				massNameList.clear();

				for(int j=0;j<searchMassName.size();j++){
					Log.d("fffffff",searchMassName.get(j));
					Mass mass=new Mass();
					mass.setMassName(searchMassName.get(j));
					massNameList.add(mass);
					Log.d("ggggggggg",massNameList.get(j).getMassName());
//					massHashMap.put(j,mass);
				}

				searchMassName.clear();

			}
			Log.d("TextWatcher",massNameList.size()+"");



			myAdapter.notifyDataSetChanged();


		}

		@Override
		public void afterTextChanged(Editable s) {

		}
	};

	private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
//		private Map<Integer,Mass> massMap;
		private Context context;
//		private List<String> massNameList;

		public MyAdapter(Context context){
//			this.massNameList=massNameList;
			this.context=context;
//			massMap=new HashMap<>();
			/**
			 * 伪造数据
			 * */
//			for(int i=0;i<20;i++){
//				Mass mass=new Mass();
//
//				mass.setName(massNameList.get(i));
//				massHashMap.put(i,mass);
//			}

		}



		@Override
		public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view=LayoutInflater.from(context).inflate(R.layout.home_frag_a2_recycleritem,parent,false);
			LinearLayout line=(LinearLayout) view.findViewById(R.id.home_frag_a2_line);
			line.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(TabA2Fragment.this.getActivity(), SchoolMassActivity.class);
					startActivity(intent);
				}
			});
			return new MyViewHolder(view);
		}

		@Override
		public void onBindViewHolder(MyViewHolder holder, int position) {
//			holder.getMerchantName().setText(merchantMap.get(position).getName());
			holder.getMerchantContent().setText(massNameList.get(position).getMassName());
			holder.getMerchantBorrow().setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(TabA2Fragment.this.getActivity(),SchoolMassDetailsActivity.class);
					startActivity(intent);
				}
			});
		}

		@Override
		public int getItemCount() {
			return massNameList.size();
		}

		public class MyViewHolder extends RecyclerView.ViewHolder{
			private ImageView merchantHeader;
			private TextView merchantContent;
			private Button merchantBorrow;

			public MyViewHolder(View view){
				super(view);
				merchantHeader=(ImageView) view.findViewById(R.id.merchantHeader_imageView);
				merchantContent=(TextView) view.findViewById(R.id.merchantContent_textView);
				merchantBorrow=(Button) view.findViewById(R.id.merchantBorrow_button);
			}

			public ImageView getMerchantHeader() {
				return merchantHeader;
			}

			public void setMerchantHeader(ImageView merchantHeader) {
				this.merchantHeader = merchantHeader;
			}

			public TextView getMerchantContent() {
				return merchantContent;
			}

			public void setMerchantContent(TextView merchantContent) {
				this.merchantContent = merchantContent;
			}

			public Button getMerchantBorrow() {
				return merchantBorrow;
			}

			public void setMerchantBorrow(Button merchantBorrow) {
				this.merchantBorrow = merchantBorrow;
			}
		}


	}

	private class MyItemDecoration extends RecyclerView.ItemDecoration {
		private Paint mPaint;
		private int mDividerHeight = 2;//分割线高度，默认为1px
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
			this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			this.mPaint.setColor(dividerColor);
			this.mPaint.setStyle(Paint.Style.FILL);
		}


		@Override
		public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
			//设定底部边距为1px
			outRect.set(0, 0, 0, mDividerHeight);
		}

		@Override
		public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
			super.onDraw(c, parent, state);
			if(mOrientation== LinearLayoutManager.VERTICAL){
				drawHorizontal(c, parent);
			}

		}

		//绘制横向 item 分割线
		private void drawHorizontal(Canvas canvas, RecyclerView parent) {
			final int left = parent.getPaddingLeft();
			final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
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
		Log.d("TabA2Fragment","onCreateView");
//
//		/**
//		 * 伪造数据
//		 * */
//		for(int i=0;i<middleMass.size();i++){
//			Mass mass=new Mass();
//
//			mass.setMassName(middleMass.get(i).getMassName());
//			massNameList.add(mass);
////			massHashMap.put(i,mass);
//		}
		initView(inflater);

		return view;
	}

	private void initView(LayoutInflater inflater){
		view = inflater.inflate(R.layout.fragment_tab_a2, null);
		smartRefreshLayout=(SmartRefreshLayout) view.findViewById(R.id.home_fragA2_smartRefreshLayout);
		recyclerView=(RecyclerView) view.findViewById(R.id.home_fragA2_recyclerView);
		myAdapter=new MyAdapter(TabA2Fragment.this.getActivity());
		home_fragA2_search_EditView= (EditText) view.findViewById(R.id.home_fragA2_search_EditView);
		home_fragA2_search_EditView.addTextChangedListener(myTextWatcher);
        staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
//		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

		recyclerView.setAdapter(myAdapter);
		recyclerView.setLayoutManager(staggeredGridLayoutManager);
//		recyclerView.addItemDecoration(new MyItemDecoration(linearLayoutManager.getOrientation(),2,R.color.background));


		smartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {

			@Override
			public void onLoadmore(final RefreshLayout refreshlayout) {
				new Thread(new Runnable() {
					@Override
					public void run() {


						//结束加载
						smartRefreshLayout.finishLoadmore();
						//加载失败的话3秒后结束加载
						refreshlayout.finishLoadmore(1000);
					}
				}).start();
			}

			@Override
			public void onRefresh(final RefreshLayout refreshlayout) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try{
							startRecordIndex=0;
							middleMass.clear();
							massNameList.clear();
							notifyDatachange();

							OkHttpClient client=new OkHttpClient();

							RequestBody requestBody=new FormBody.Builder()
									//发送学生id到后台，先查到学校，再查本学校所有社团
									.add("stuId",1+"")
									.add("startRecordIndex",startRecordIndex+"")
									.build();

							Request request=new Request.Builder()
									.url("http://192.168.1.101:8080/schoolapp/returnMass.action")
									.post(requestBody)
									.build();

							Response response=client.newCall(request).execute();

							String massResponseJSON=response.body().string();
							Log.d("massResponseJSON",massResponseJSON);
							Gson gson=new Gson();
							List<Mass> massList = gson.fromJson(massResponseJSON, new TypeToken<List<Mass>>() {
							}.getType());

							for(int i=0;i<massList.size();i++){
								middleMass.add(massList.get(i));
								massNameList.add(massList.get(i));
							}



						}catch (Exception e){
							e.printStackTrace();
						}

						notifyDatachange();
						//结束刷新
						smartRefreshLayout.finishRefresh();
						//加载失败的话3秒后结束刷新
						refreshlayout.finishRefresh(1000);
					}
				}).start();
			}
		});
	}

	public void notifyDatachange(){
		TabA2Fragment.this.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				myAdapter.notifyDataSetChanged();
			}
		});
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		//fragment对用户可见
		if(isVisibleToUser){
			//如果autoRefresh为真，则自动刷新
			if(autoRefresh){
				smartRefreshLayout.autoRefresh();
			}

		}else{
			//因为程序刚进入时，TabA2Fragment一样为不可见，所以再增加一个变量firstIn作为是否是真正第一次进入
			if(firstIn){
				autoRefresh=false;
			}
			firstIn=true;

			Log.d("userVisibleHint","false");
		}
	}

	@Override
	public void onPause() {
		super.onPause();

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("TabA2Fragment", "onDestroy()");
	}

}