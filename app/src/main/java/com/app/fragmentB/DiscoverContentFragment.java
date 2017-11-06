package com.app.fragmentB;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link DiscoverContentFragment.OnFragmentInteractionListener} interface to
 * handle interaction events. Use the
 * {@link DiscoverContentFragment#newInstance} factory method to create an
 * instance of this fragment.
 * 
 */
public class DiscoverContentFragment extends Fragment {
	

	public DiscoverContentFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		int id=this.getArguments().getInt("id");
		Log.d("myid", id+"");
		TextView textView=new TextView(this.getActivity());
		switch (id) {
			case 0:
				//存放具体内容
				textView.setText("全国热门社团");
				break;

			case 1:
				textView.setText("拉赞助");
				break;
			case 2:
				textView.setText("借物资");
				break;
			case 3:
				textView.setText("吃喝玩乐");
				break;
			case 4:
				textView.setText("约运动");
				break;
			case 5:
				textView.setText("线上活动");
				break;
		}
		return textView;
	}



}
