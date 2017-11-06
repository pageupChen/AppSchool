package com.app.fragmentA;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.activity.MassActivity;
import com.app.activity.MerchantActivity;
import com.app.activity.SchoolMassActivity;
import com.app.adapter.SortAdapter;
import com.app.pojo.Merchant;
import com.app.pojo.SortMode;
import com.app.view.SideBar;
import com.example.appproject.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import net.sourceforge.pinyin4j.PinyinHelper;

/**
 * Case 2：当我们从一个Activity启动了一个Fragment，然后在这个Fragment中又去实例化了一些子Fragment，
 * 在子Fragment中去有返回的启动了另外一个Activity，即通过startActivityForResult方式去启动，
 * 这时候造成的现象会是，子Fragment接收不到OnActivityResult，如果在子Fragment中是以getActivity.startActivityForResult方式启动，
 * 那么只有Activity会接收到OnActivityResult，如果是以getParentFragment.startActivityForResult方式启动，
 * 那么只有父Fragment能接收（此时Activity也能接收），但无论如何子Fragment接收不到OnActivityResult
 * */

public class TabA3Fragment extends Fragment{
	private View view;
	private SideBar sideBar;
	private ListView home_fraA3_ListView;
	private List<SortMode> data;
	private static char[] a_z={'a','b','c','d','e','f','g','h','i','j','k','l','m',
			'n','o','p','q','r','s','t','u','v','w','x','y','z'};
	private SortAdapter sortAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,  Bundle savedInstanceState) {
		initView(inflater);

		return view;
	}


	private void initView(LayoutInflater inflater){
		view=inflater.inflate(R.layout.fragment_tab_a3,null);
		sideBar=(SideBar) view.findViewById(R.id.side_bar);
		sideBar.setHome_fraA2_contactHeader_TextView((TextView) view.findViewById(R.id.home_fraA3_contactHeader_TextView));
		home_fraA3_ListView=(ListView) view.findViewById(R.id.home_fraA3_ListView);
		data=initData(TabA3Fragment.this.getActivity().getResources().getStringArray(R.array.masss_names));
		sortAdapter=new SortAdapter(TabA3Fragment.this.getActivity(),data);
		home_fraA3_ListView.setAdapter(sortAdapter);

		home_fraA3_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//后台时，要把该公司的id传到MerchantActivity,然后在MerchantActivity中把id传到后台，接着放回数据并显示在MerchantActivity中
				Intent intent=new Intent(TabA3Fragment.this.getActivity(), MerchantActivity.class);
				startActivity(intent);
			}
		});

//		home_fraA3_ListView.setSelection(10);

		sideBar.setOnStrSelectCallBack(new SideBar.ISideBarSelectCallBack() {
			@Override
			public void onSelectStr(String selectStr) {
				home_fraA3_ListView.setSelection(sortAdapter.getPositionForSelection(selectStr.toLowerCase().toCharArray()[0])); // 选择到首字母出现的位置
				return;
			}
		});
	}

	//把数据和实体类关联起来
	private List<SortMode> initData(String[] data){
		List<SortMode> mlist=new ArrayList<>();
		char strHeaderPinyin='*';
		for(int i=0;i<data.length;i++){
			SortMode sortMode=new SortMode();
			sortMode.setName(data[i]);
			//得到字符串的第一个字
			char[] strHeader=data[i].substring(0,1).toCharArray();
			//判断该字是否为英文
			if(strHeader[0]>=97&&strHeader[0]<=122){
				for(char c:a_z){
					if(strHeader[0]==c){
						strHeaderPinyin=c;
						break;
					}
				}
			}else {
				//得到该字的拼音
				String[] strPinyin=PinyinHelper.toHanyuPinyinStringArray(strHeader[0]);
				//得到拼音的首字母
				strHeaderPinyin=strPinyin[0].substring(0,1).toCharArray()[0];


			}
			sortMode.setSortLetter(strHeaderPinyin);
			mlist.add(sortMode);

			Log.d("strHeaderPinyin",strHeaderPinyin+"");


		}
		return mlist;
	}



	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

//		bundle.putString("mass_names","");
	}



	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		Log.d("TabA2Fragment", "onDestroy()");

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		Log.d("TabA2Fragment", "onPause()");
	}

}
