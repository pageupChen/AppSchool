package com.app.fragmentC;
import android.os.Bundle;


import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appproject.R;

/**
 * A simple {@link Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link TabCFragment.OnFragmentInteractionListener} interface to handle
 * interaction events. Use the {@link TabCFragment#newInstance} factory method
 * to create an instance of this fragment.
 * 
 */
public class TabCFragment extends Fragment {
	private NavigationView navigationView;
	private View view;

	public TabCFragment() {
		// Required empty public constructor
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment

		initView(inflater,container);
		return view;
	}

	private void initView(LayoutInflater inflater, ViewGroup container){
		view=inflater.inflate(R.layout.fragment_tab_c,container,false);
		navigationView=(NavigationView) view.findViewById(R.id.tab_c_navigationView);
		navigationView.setItemIconTintList(null);
//		navigationView.setItem
	}
	

}
