package com.app.fragmentB;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.appproject.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link TabBFragment.OnFragmentInteractionListener} interface to handle
 * interaction events. Use the {@link TabBFragment#newInstance} factory method
 * to create an instance of this fragment.
 *
 */
public class TabBFragment extends Fragment {
	private View view;
	private EditText advertising_edit;
	private ImageView advertising_image;
	private ViewPager myViewPager;
	private LinearLayout myLinearLayout;
	private ListView discover_list;
	private String[] discover_arrays={"全国热门社团","拉赞助","借物资","吃喝玩乐","约活动","线上活动"};
	private List<ImageView> adImageView=new ArrayList<ImageView>();
	private int[] adImages={R.mipmap.adverting11,R.mipmap.adverting22,R.mipmap.adverting33};
	private int currentIndex=900;
	private boolean isRun=true;
	private MyThread myThread;

	private class MyThread extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub


			while (true) {
				if(myThread!=null){
					try {
						Thread.sleep(2000);
						Log.d("getCurrentItem", "aaa");
						if(TabBFragment.this.getActivity()==null){

						}else{
							TabBFragment.this.getActivity().runOnUiThread(new Runnable() {
								@Override
								public void run() {
									Log.d("getCurrentItem", myViewPager.getCurrentItem()+"");
									myViewPager.setCurrentItem(myViewPager.getCurrentItem() + 1);

									//Log.d("isRun()", "true");
								}
							});
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}



				}

			}
		}


	}



	public TabBFragment() {
		// Required empty public constructor
	}

	//fragment每次被调到前台都会执行onCreateView()方法
	//fragment每次被调到前台或后台都会从新开始生命周期和销毁掉
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		Log.d("TabBFragment","onCreateView()");
		view=inflater.inflate(R.layout.fragment_tab_b, null);

		advertising_edit=(EditText) view.findViewById(R.id.advertising_edit);
		advertising_image=(ImageView) view.findViewById(R.id.advertising_image);


		iniView();
		MyThread myThread=new MyThread();
		myThread.start();
		return view;

	}

	private void iniView(){
		myViewPager=(ViewPager) view.findViewById(R.id.myViewPager);
		myLinearLayout=(LinearLayout) view.findViewById(R.id.myLinearLayout);
		discover_list=(ListView) view.findViewById(R.id.discover_list);


		for(int i=0;i<adImages.length;i++){
			//动态添加小圆点
			ImageView imageView_dot=new ImageView(TabBFragment.this.getActivity());
			imageView_dot.setBackgroundColor(Color.WHITE);
			imageView_dot.setPadding(15, 5, 15, 5);

			myLinearLayout.addView(imageView_dot);

			//动态添加广告的滚动项
			ImageView adImageItem=new ImageView(TabBFragment.this.getActivity());
			adImageItem.setImageResource(adImages[i]);
			adImageView.add(adImageItem);
		}

		//为viewPager添加适配器
		myViewPager.setAdapter(new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0==arg1;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return Integer.MAX_VALUE;
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				// TODO Auto-generated method stub
				//return super.instantiateItem(container, position);
				int index=position%adImages.length;
				//((ViewPager)container).addView(adImageView.get(index));
				View view = adImageView.get(index);
				//如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
				ViewParent vp =view.getParent();
				if (vp!=null){
					ViewGroup parent = (ViewGroup)vp;
					if(parent.equals(myViewPager)){
						Log.d("parent",true+"");
					}

					parent.removeView(view);
				}
				container.addView(view);
				return view;
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
									Object object) {
				// TODO Auto-generated method stub
				//super.destroyItem(container, position, object);
				//int index=position%adImages.length;
				//((ViewPager)container).removeView((View)object);
			}
		});

		//动态同步小圆点与广告的位置
		myViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				int index=position%adImages.length;
				setDotsBackGroundColor(index);

			}

			private void setDotsBackGroundColor(int index){
				for(int i=0;i<myLinearLayout.getChildCount();i++){
					ImageView imageView_select=(ImageView) myLinearLayout.getChildAt(i);
					if(i==index){
						imageView_select.setBackgroundColor(Color.BLACK);
					}else{
						imageView_select.setBackgroundColor(Color.WHITE);
					}
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

		myViewPager.setCurrentItem(currentIndex);
		ShowDiscoverList();
	}

	//为左侧导航（listView）添加适配器,
	private void ShowDiscoverList(){
		discover_list.setAdapter(new BaseAdapter() {

			@Override
			public View getView(int position, View arg1, ViewGroup arg2) {
				// TODO Auto-generated method stub
				//LinearLayout l=new LinearLayout(TabBFragment.this.getActivity());
				//LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(width, height, 1);
				TextView tv=new TextView(TabBFragment.this.getActivity());
				tv.setText(discover_arrays[position]);
				tv.setTextSize(30);
//				/l.addView(tv);
				return tv;
			}

			@Override
			public long getItemId(int arg0) {
				// TODO Auto-generated method stub
				return arg0;
			}

			@Override
			public Object getItem(int arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return 6;
			}
		});

		//为左侧导航（listView）添加点击事件
		discover_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
									long arg3) {
				// TODO Auto-generated method stub
				Bundle bundle=new Bundle();
				bundle.putInt("id",position);
				DiscoverContentFragment discoverContentFragment=new DiscoverContentFragment();
				discoverContentFragment.setArguments(bundle);
				getChildFragmentManager().beginTransaction().replace(R.id.discover_content, discoverContentFragment).commit();
			}
		});
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//myThread=null;
//		isRun=false;
//		Log.d("isRun", isRun+"");
		myThread=null;
		Log.d("TabBFragment","onPause()");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		myThread=new MyThread();
		/**
		 * 线程调用start方法两次，就会有两个run方法在运行
		 * */
		//myThread.start();
		//myViewPager.setCurrentItem(currentIndex);
		Log.d("TabBFragment","onResume()");
	}

	//每切换掉一个fragment时,改fragment都会被销毁
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("TabBFragment", "onDestroy已执行");
	}



}
