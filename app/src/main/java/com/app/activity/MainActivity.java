package com.app.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;


import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.broadcastreceiver.NetworkChangeReceiver;
import com.app.fragmentA.TabA1Fragment;
import com.app.fragmentA.TabA4Fragment;
import com.app.fragmentA.TabAFragment;
import com.app.fragmentB.TabBFragment;
import com.app.fragmentC.TabCFragment;
import com.app.fragmentD.TabDFragment;
import com.example.appproject.R;
import com.gigamole.library.ntb.NavigationTabBar;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements TabA1Fragment.Callbacks{
	private MainActivity.autoRefresh autoRefresh;
	private NavigationTabBar mainActivity_navigationTabBar;
	private final static String fragmentATag="1";
	private final static String fragmentBTag="2";
	private final static String fragmentCTag="3";
	boolean flagA=false;
	boolean flagB=true;
	boolean flagC=true;

	@Override
	public MainActivity.autoRefresh isAutoRefresh() {
         return autoRefresh;
	}


	public class autoRefresh{
		public boolean autoRefresh=true;

		public boolean isAutoRefresh() {
			return autoRefresh;
		}

		public void setAutoRefresh(boolean autoRefresh) {
			this.autoRefresh = autoRefresh;
		}
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d("nmb", "MainActivity--->onCreate");

		autoRefresh = new MainActivity.autoRefresh();
		mainActivity_navigationTabBar = (NavigationTabBar) this.findViewById(R.id.mainActivity_navigationTabBar);

		//调出输入法之后不影响原有activity的布局
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

		this.getWindowManager().getDefaultDisplay().getWidth();


		final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();

		models.add(new NavigationTabBar.Model
				.Builder(getResources().getDrawable(R.mipmap.ic_third), getResources().getColor(R.color.colorAccent))
//                .selectedIcon()
				.selectedIcon(getResources().getDrawable(R.mipmap.ic_third))
				.title("HOME")
				.badgeTitle("aaa")
				.build());
		models.add(new NavigationTabBar.Model
				.Builder(getResources().getDrawable(R.mipmap.ic_fourth), getResources().getColor(R.color.colorAccent))
				.title("TEAM")
				.build());
		models.add(new NavigationTabBar.Model
				.Builder(getResources().getDrawable(R.mipmap.ic_seventh), getResources().getColor(R.color.colorAccent))
				.title("ME")
				.build());

		mainActivity_navigationTabBar.setModels(models);
		mainActivity_navigationTabBar.setViewPager(null,0);

		//获取fragment管理器
		FragmentManager fm = getSupportFragmentManager();
		//开启一个事务
		FragmentTransaction ft = fm.beginTransaction();
		//启动程序时先显示TabAFrament
		TabAFragment fragmentA = new TabAFragment();
		//将TabAFragment先添加到帧布局中
		ft.add(R.id.frameLayout, fragmentA, fragmentATag);
		//显示TabAFragment
		ft.show(fragmentA);
		//提交事务
		ft.commit();


		mainActivity_navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
			@Override
			public void onStartTabSelected(NavigationTabBar.Model model, int index) {
//                Toast.makeText(MainActivity.this, index + "", Toast.LENGTH_SHORT).show();
				FragmentManager fm = getSupportFragmentManager();
				//开启一个事务
				FragmentTransaction ft = fm.beginTransaction();
				//fragment布局管理器从Tag中获取fragment
				Fragment fragmentA = fm.findFragmentByTag(fragmentATag);
				Fragment fragmentB = fm.findFragmentByTag(fragmentBTag);
				Fragment fragmentC = fm.findFragmentByTag(fragmentCTag);

				//如果获取到fragment不为空（即已创建并绑定），则全部隐藏，避免重复点击显示该fragment，同时隐藏掉别的fragment
				if (fragmentA != null) {
					ft.hide(fragmentA);
				}
				if (fragmentB != null) {
					ft.hide(fragmentB);
				}
				if (fragmentC != null) {
					ft.hide(fragmentC);
				}

				switch (index) {
					case 0:
						if (fragmentA == null) {
							//若fragmentA从绑定中没有获取到，则新建，并添加到帧布局中
							fragmentA = new TabAFragment();
							ft.add(R.id.frameLayout, fragmentA, fragmentATag);
						}
						//不管是从Tag中获取到，还是新创建的，最终都要show();
						ft.show(fragmentA);
						break;
					case 1:
						if (fragmentB == null) {
							fragmentB = new TabBFragment();
							ft.add(R.id.frameLayout, fragmentB, fragmentBTag);
						}
						ft.show(fragmentB);
						break;
					case 2:
						if (fragmentC == null) {
							fragmentC = new TabCFragment();
							ft.add(R.id.frameLayout, fragmentC, fragmentCTag);
						}
						ft.show(fragmentC);
						break;

					//提交事务
//					ft.commit();
//					return true;
				}
				//提交事务
				ft.commit();
			}

			@Override
			public void onEndTabSelected(NavigationTabBar.Model model, int index) {

			}
		});
	}



//		bottomNavigation=(BottomNavigationView) this.findViewById(R.id.buttonNavigation);
//
//		BottomNavigationViewHelper.disableShiftMode(bottomNavigation);

		//获取fragment管理器
//		FragmentManager fm=getSupportFragmentManager();
//		//开启一个事务
//		FragmentTransaction ft=fm.beginTransaction();
//		//启动程序时先显示TabAFrament
//		TabAFragment fragmentA=new TabAFragment();
//		//将TabAFragment先添加到帧布局中
//		ft.add(R.id.frameLayout,fragmentA,fragmentATag);
//		//显示TabAFragment
//		ft.show(fragmentA);
//		//提交事务
//		ft.commit();

//		mainActivity_navigationTabBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//			@Override
//			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				//获取fragment管理器
//				FragmentManager fm=getSupportFragmentManager();
//				//开启一个事务
//				FragmentTransaction ft=fm.beginTransaction();
//				//fragment布局管理器从Tag中获取fragment
//				Fragment fragmentA=fm.findFragmentByTag(fragmentATag);
//				Fragment fragmentB=fm.findFragmentByTag(fragmentBTag);
//				Fragment fragmentC=fm.findFragmentByTag(fragmentCTag);
//
//				//如果获取到fragment不为空（即已创建并绑定），则全部隐藏，避免重复点击显示该fragment，同时隐藏掉别的fragment
//				if(fragmentA!=null){
//					ft.hide(fragmentA);
//				}
//				if(fragmentB!=null){
//					ft.hide(fragmentB);
//				}
//				if(fragmentC!=null){
//					ft.hide(fragmentC);
//				}

//				switch (item.getItemId()){
//					case R.id.item1:
//						bottomNavigation.getMenu().getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_launcher));
////						bottomNavigation.setItemTextColor(Color.BLUE);
////						bottomNavigation.getMenu().getItem(0).getTitle().
//						bottomNavigation.getMenu().getItem(1).setIcon(getResources().getDrawable(R.mipmap.ic_fourth));
//						bottomNavigation.getMenu().getItem(2).setIcon(getResources().getDrawable(R.mipmap.ic_seventh));
//						if(fragmentA==null){
//							//若fragmentA从绑定中没有获取到，则新建，并添加到帧布局中
//							fragmentA=new TabAFragment();
//							ft.add(R.id.frameLayout,fragmentA,fragmentATag);
//						}
//							//不管是从Tag中获取到，还是新创建的，最终都要show();
//							ft.show(fragmentA);
//
//						break;
//					case R.id.item2:
//						bottomNavigation.getMenu().getItem(0).setIcon(getResources().getDrawable(R.mipmap.ic_third));
//						bottomNavigation.getMenu().getItem(1).setIcon(getResources().getDrawable(R.drawable.ic_launcher));
//						bottomNavigation.getMenu().getItem(2).setIcon(getResources().getDrawable(R.mipmap.ic_seventh));
//						if(fragmentB==null){
//							fragmentB=new TabBFragment();
//							ft.add(R.id.frameLayout,fragmentB,fragmentBTag);
//						}
//						ft.show(fragmentB);
//
//						break;
//					case R.id.item3:
//						bottomNavigation.getMenu().getItem(0).setIcon(getResources().getDrawable(R.mipmap.ic_third));
//						bottomNavigation.getMenu().getItem(1).setIcon(getResources().getDrawable(R.mipmap.ic_fourth));
//						bottomNavigation.getMenu().getItem(2).setIcon(getResources().getDrawable(R.drawable.ic_launcher));
//						if(fragmentC==null){
//							fragmentC=new TabCFragment();
//							ft.add(R.id.frameLayout,fragmentC,fragmentCTag);
//						}
//						ft.show(fragmentC);
//
//						break;
//
//
//				}
				//提交事务
//		ft.commit();
//		return true;
//			}
//		});
//
//
//	}

//    static class BottomNavigationViewHelper {
//        public static void disableShiftMode(BottomNavigationView view) {
//            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
//            try {
//                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
//                shiftingMode.setAccessible(true);
//                shiftingMode.setBoolean(menuView, false);
//                shiftingMode.setAccessible(false);
//                for (int i = 0; i < menuView.getChildCount(); i++) {
//                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
//                    //noinspection RestrictedApi
//                    item.setShiftingMode(false);
//                    // set once again checked value, so view will be updated
//                    //noinspection RestrictedApi
//                    item.setChecked(item.getItemData().isChecked());
//                }
//            } catch (NoSuchFieldException e) {
//                Log.e("BNVHelper", "Unable to get shift mode field", e);
//            } catch (IllegalAccessException e) {
//                Log.e("BNVHelper", "Unable to change value of shift mode", e);
//            }
//        }
//    }

    public static void test(){}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d("nmb","MainActivity--->onStart");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
//		unregisterReceiver(networkChangeReceiver);
	}
}
