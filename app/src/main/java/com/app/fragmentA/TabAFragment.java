package com.app.fragmentA;

import java.util.ArrayList;
import java.util.List;


import android.graphics.Color;
import android.net.LinkAddress;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.fragmentB.TabBFragment;
import com.app.adapter.FragmentAdapter;
import com.app.view.ViewPagerIndicate;
import com.example.appproject.R;


public class TabAFragment extends Fragment{
	private View view;
	private ViewPager home_fraA_viewPager;
	private ViewPagerIndicate home_fragA_indicate;
	private int[] mTextColors = {0xFFA0A0A0, 0xFF000000};
	private int mUnderlineColor = 0xFF168EFF;
//	private String[] mTitles = new String[] {
//			"实时动态", "本校社团", "索求赞助","借取物资"};
private String[] mTitles = new String[] {
		"实时动态", "本校社团", "寻求赞助"};
	private ArrayList<TextView> mTextViews;
//	private boolean waitingShowToUser;

	public TabAFragment(){}

//	@Override
//	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//		super.onActivityCreated(savedInstanceState);
//// 如果自己是显示状态，但父Fragment却是隐藏状态，就把自己也改为隐藏状态，并且设置一个等待显示标记
//		if (getUserVisibleHint()) {
//			Fragment parentFragment = getParentFragment();
//			if (parentFragment != null && !parentFragment.getUserVisibleHint()) {
//				waitingShowToUser = true;
//				super.setUserVisibleHint(false);
//			}
//		}
//
//
//	}


		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			//调出输入法之后不影响原有activity的布局
			Log.d("TabAFragment","onCreateView()");
			view=inflater.inflate(R.layout.fragment_tab_a,null);
			initViewPgaer();
			initViewPagerIndicate();



		
		return view;
	}

	private void initViewPgaer() {
		mTextViews = new ArrayList<TextView>();
		for (int i = 0; i < mTitles.length; i++) {
			TextView tv = new TextView(TabAFragment.this.getActivity());
			tv.setGravity(Gravity.CENTER);
			tv.setText(mTitles[i]);
			mTextViews.add(tv);
		}
		home_fraA_viewPager = (ViewPager) view.findViewById(R.id.home_fraA_viewPager);
		ArrayList<Fragment> fragmentsList = new ArrayList<Fragment>();

		Fragment fragmentA1 = new TabA1Fragment();
		fragmentsList.add(fragmentA1);
		Fragment fragmentA2 = new TabA2Fragment();
		fragmentsList.add(fragmentA2);
		Fragment fragmentA3 = new TabA3Fragment();
		fragmentsList.add(fragmentA3);
//		Fragment fragmentA4 = new TabA4Fragment();
//		fragmentsList.add(fragmentA4);

		home_fraA_viewPager.setAdapter(new FragmentAdapter(getChildFragmentManager(), fragmentsList));
		home_fraA_viewPager.setCurrentItem(0);
	}

	private void initViewPagerIndicate() {
		home_fragA_indicate = (ViewPagerIndicate)view.findViewById(R.id.home_fragA_indicate);
//		home_fragA_indicate.setmContext(TabAFragment.this.getActivity());
		home_fragA_indicate.setScreenWidth(TabAFragment.this.getActivity().getWindowManager().getDefaultDisplay().getWidth());
		//设置标签样式、文本和颜色
		home_fragA_indicate.resetText(R.layout.layout_text_indicate, mTitles, mTextColors);
		//设置下划线粗细和颜色
		home_fragA_indicate.resetUnderline(4, mUnderlineColor);
		//设置ViewPager
		home_fragA_indicate.resetViewPager(home_fraA_viewPager);
		//设置初始化完成
		home_fragA_indicate.setOk();
	}

//		home_fraA_viewPager.setAdapter(new FragmentAdapter() {
//			@Override
//			public Object instantiateItem(ViewGroup container, int position) {
//				TextView tv = mTextViews.get(position);
//				container.addView(tv);
//				return tv;
//			}
//
//			@Override
//			public void destroyItem(ViewGroup container, int position,
//									Object object) {
//				container.removeView(mTextViews.get(position));
//			}
//
//			@Override
//			public boolean isViewFromObject(View arg0, Object arg1) {
//				return arg0 == arg1;
//			}
//
//			@Override
//			public int getCount() {
//				return mTextViews.size();
//			}
//		});

	private void initView(){


//		//为ad_viewPager(广告轮播器)添加适配器
//		home_fragA_advert_viewPager.setAdapter(new PagerAdapter() {
//
//			@Override
//			public boolean isViewFromObject(View arg0, Object arg1) {
//				// TODO Auto-generated method stub
//				return arg0 == arg1;
//			}
//
//			@Override
//			public int getCount() {
//				// TODO Auto-generated method stub
//				return Integer.MAX_VALUE;
//			}
//
//			@Override
//			public Object instantiateItem(ViewGroup container, int position) {
//				// TODO Auto-generated method stub
//				//return super.instantiateItem(container, position);
//				int index = position % adImages.length;
//				//((ViewPager)container).addView(adImageView.get(index));
//				View view = adImageView.get(index);
//				//如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
//				ViewParent vp = view.getParent();
//				if (vp != null) {
//					ViewGroup parent = (ViewGroup) vp;
//					if (parent.equals(home_fragA_advert_viewPager)) {
//						Log.d("parent", true + "");
//					}
//
//					parent.removeView(view);
//				}
//				container.addView(view);
//				return view;
//			}
//			@Override
//			public void destroyItem(ViewGroup container, int position,
//									Object object) {
//				// TODO Auto-generated method stub
//				//super.destroyItem(container, position, object);
//				//int index=position%adImages.length;
//				//((ViewPager)container).removeView((View)object);
//			}
//		});
//
//		//动态同步小圆点与广告的位置
//		home_fragA_advert_viewPager.setOnPageChangeListener(new OnPageChangeListener() {
//
//			@Override
//			public void onPageSelected(int position) {
//				// TODO Auto-generated method stub
//				int index=position%adImages.length;
//				setDotsBackGroundColor(index);
//
//			}
//
//			private void setDotsBackGroundColor(int index){
//				for(int i=0;i<home_fragA_advert_line.getChildCount();i++){
//					ImageView imageView_select=(ImageView) home_fragA_advert_line.getChildAt(i);
//					if(i==index){
//						imageView_select.setBackgroundColor(Color.BLACK);
//					}else{
//						imageView_select.setBackgroundColor(Color.WHITE);
//					}
//				}
//			}
//
//			@Override
//			public void onPageScrolled(int arg0, float arg1, int arg2) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onPageScrollStateChanged(int arg0) {
//				// TODO Auto-generated method stub
//
//			}
//		});
//
//		home_fragA_advert_viewPager.setCurrentItem(currentIndex);
//	}
//
//	private void createDots(){
//		for(int i=0;i<adImages.length;i++){
//			//动态添加小圆点
//			ImageView imageView_dot=new ImageView(TabAFragment.this.getActivity());
//			imageView_dot.setBackgroundColor(Color.WHITE);
//			imageView_dot.setPadding(15, 5, 15, 5);
//
//			home_fragA_advert_line.addView(imageView_dot);
//
//			//动态添加广告的滚动项
//			ImageView adImageItem=new ImageView(TabAFragment.this.getActivity());
//			adImageItem.setImageResource(adImages[i]);
//			adImageView.add(adImageItem);
//		}
	}

//	@Override
//	public void setUserVisibleHint(boolean isVisibleToUser) {
//		Log.d("isVisibleToUser", "is");
//		super.setUserVisibleHint(isVisibleToUser);
//		// 父Fragment还没显示，你着什么急
//		if (isVisibleToUser) {
//			Fragment parentFragment = getParentFragment();
//			if (parentFragment != null && !parentFragment.getUserVisibleHint()) {
//				waitingShowToUser = true;
//				super.setUserVisibleHint(false);
//				return;
//			}
//		}
//
//		if (getActivity() != null) {
//			List<Fragment> childFragmentList = getChildFragmentManager().getFragments();
//			if (isVisibleToUser) {
//				// 将所有正等待显示的子Fragment设置为显示状态，并取消等待显示标记
//				if (childFragmentList != null && childFragmentList.size() > 0) {
//					for (Fragment childFragment : childFragmentList) {
//						if (childFragment instanceof TabAFragment) {
//							TabAFragment childBaseFragment = (TabAFragment) childFragment;
//							if (childBaseFragment.isWaitingShowToUser()) {
//								childBaseFragment.setWaitingShowToUser(false);
//								childFragment.setUserVisibleHint(true);
//							}
//						}
//					}
//				}
////				myThread=new TabA1Fragment.MyThread();
//			} else {
//				// 将所有正在显示的子Fragment设置为隐藏状态，并设置一个等待显示标记
//				if (childFragmentList != null && childFragmentList.size() > 0) {
//					for (Fragment childFragment : childFragmentList) {
//						if (childFragment instanceof TabAFragment) {
//							TabAFragment childBaseFragment = (TabAFragment) childFragment;
//							if (childFragment.getUserVisibleHint()) {
//								childBaseFragment.setWaitingShowToUser(true);
//								childFragment.setUserVisibleHint(false);
//							}
//						}
//					}
//				}
////				myThread=null;
//			}
//		}
//	}
//
//	public boolean isWaitingShowToUser() {
//		return waitingShowToUser;
//	}
//
//	public void setWaitingShowToUser(boolean waitingShowToUser) {
//		this.waitingShowToUser = waitingShowToUser;
//	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//myThread=null;
//		isRun=false;
//		Log.d("isRun", isRun+"");
//		myThread=null;
		Log.d("TabAFragment","onPause()");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		myThread=new TabAFragment.MyThread();
		/**
		 * 线程调用start方法两次，就会有两个run方法在运行
		 * */
		//myThread.start();
		//myViewPager.setCurrentItem(currentIndex);
		Log.d("TabAFragment","onResume()");
	}

	//每切换掉一个fragment时,改fragment都会被销毁
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("TabAFragment", "onDestroy已执行");
	}


}