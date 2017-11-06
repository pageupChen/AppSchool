package com.app.fragmentD;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appproject.R;

/**
 * A simple {@link Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link TabDFragment.OnFragmentInteractionListener} interface to handle
 * interaction events. Use the {@link TabDFragment#newInstance} factory method
 * to create an instance of this fragment.
 * 
 */
public class TabDFragment extends Fragment {



	public TabDFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_tab_d, container, false);
	}

	
}
