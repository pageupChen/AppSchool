package com.app.activity;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appproject.R;

public class MassActivity extends Activity {
	private EditText mass_edit;
	private ImageView mass_image;
	private ExpandableListView mass_list;
	private ExpandableListView mass_list2;
	private String[] mass_names={"唱歌","武术","泰拳","象棋","溜冰"};
	private String[][] mass_remark={
			{"一首好歌唱给你听"},
			{"中华传统武术"},
			{"格斗实用性最强的泰国格斗术"},
			{"国际象棋"},
			{"摔死你"}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mass);

		//mass_names=this.getResources().getStringArray(R.array.mass_names);

		mass_edit=(EditText) this.findViewById(R.id.mass_edit);
		mass_image=(ImageView) this.findViewById(R.id.mass_image);
		mass_list=(ExpandableListView) this.findViewById(R.id.mass_list);
		mass_list2=(ExpandableListView) this.findViewById(R.id.mass_list2);

		ExpandableListAdapter expandableListAdapter=new BaseExpandableListAdapter() {

			@Override
			public boolean isChildSelectable(int arg0, int arg1) {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public boolean hasStableIds() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public View getGroupView(int groupPosition, boolean arg1, View arg2, ViewGroup arg3) {
				// TODO Auto-generated method stub
				View view=MassActivity.this.getLayoutInflater().inflate(R.layout.mass_group, null);
				TextView tv=(TextView) view.findViewById(R.id.mass_group_tv);
				tv.setText(mass_names[groupPosition]);

				return view;
			}

			@Override
			public long getGroupId(int groupPosition) {
				// TODO Auto-generated method stub
				return groupPosition;
			}

			@Override
			public int getGroupCount() {
				// TODO Auto-generated method stub
				return mass_names.length;
			}

			@Override
			public Object getGroup(int groupPosition) {
				// TODO Auto-generated method stub
				return mass_names[groupPosition];
			}

			@Override
			public int getChildrenCount(int groupPosition) {
				// TODO Auto-generated method stub
				return mass_remark[groupPosition].length;
			}

			@Override
			public View getChildView(int groupPosition, int childPosition, boolean arg2, View arg3,
									 ViewGroup arg4) {
				// TODO Auto-generated method stub
				View view=MassActivity.this.getLayoutInflater().inflate(R.layout.mass_child, null);
				TextView tv=(TextView) view.findViewById(R.id.mass_child_tv);
				tv.setText(mass_remark[groupPosition][childPosition]);
				return view;
			}

			@Override
			public long getChildId(int arg0, int childPosition) {
				// TODO Auto-generated method stub
				return childPosition;
			}

			@Override
			public Object getChild(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub
				return mass_remark[groupPosition][childPosition];
			}
		};
		mass_list.setAdapter(expandableListAdapter);
		mass_list2.setAdapter(new BaseExpandableListAdapter() {

			@Override
			public boolean isChildSelectable(int arg0, int arg1) {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public boolean hasStableIds() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public View getGroupView(int groupPosition, boolean arg1, View arg2, ViewGroup arg3) {
				// TODO Auto-generated method stub
				View view=MassActivity.this.getLayoutInflater().inflate(R.layout.mass_group, null);
				TextView tv=(TextView) view.findViewById(R.id.mass_group_tv);
				tv.setText(mass_names[groupPosition]);

				return view;
			}

			@Override
			public long getGroupId(int groupPosition) {
				// TODO Auto-generated method stub
				return groupPosition;
			}

			@Override
			public int getGroupCount() {
				// TODO Auto-generated method stub
				return mass_names.length;
			}

			@Override
			public Object getGroup(int groupPosition) {
				// TODO Auto-generated method stub
				return mass_names[groupPosition];
			}

			@Override
			public int getChildrenCount(int groupPosition) {
				// TODO Auto-generated method stub
				return mass_remark[groupPosition].length;
			}

			@Override
			public View getChildView(int groupPosition, int childPosition, boolean arg2, View arg3,
									 ViewGroup arg4) {
				// TODO Auto-generated method stub
				View view=MassActivity.this.getLayoutInflater().inflate(R.layout.mass_child, null);
				TextView tv=(TextView) view.findViewById(R.id.mass_child_tv);
				tv.setText(mass_remark[groupPosition][childPosition]);
				return view;
			}

			@Override
			public long getChildId(int arg0, int childPosition) {
				// TODO Auto-generated method stub
				return childPosition;
			}

			@Override
			public Object getChild(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub
				return mass_remark[groupPosition][childPosition];
			}
		});

		mass_list.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView arg0, View arg1, int groupPosition,
										int arg3, long arg4) {
				// TODO Auto-generated method stub
				//Toast.makeText(MassActivity.this, mass_remark[arg2][arg3], Toast.LENGTH_LONG).show();
				showLoginDialog(groupPosition);

				return true;
			}
		});

		mass_list2.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView arg0, View arg1, int groupPosition,
										int arg3, long arg4) {
				// TODO Auto-generated method stub
				//Toast.makeText(MassActivity.this, mass_remark[arg2][arg3], Toast.LENGTH_LONG).show();
				showLoginDialog(groupPosition);

				return true;
			}
		});

		mass_image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(TextUtils.isEmpty(mass_edit.getText().toString().trim())){
					Toast.makeText(MassActivity.this, "请输入您喜欢的社团名称", Toast.LENGTH_SHORT).show();
				}else{

					//重点
					boolean flag=true;
					for(int i=0;i<mass_names.length;i++){
						if(mass_edit.getText().toString().trim().equals(mass_names[i])){
							showLoginDialog(i);
							flag=false;
							break;
						}
					}
					if(flag){
						Toast.makeText(MassActivity.this, "没有您输入的社团哦,亲", Toast.LENGTH_SHORT).show();
					}

				}
			}
		});


	}

	public void showLoginDialog(final int groupPosition){
		AlertDialog.Builder dialog=new AlertDialog.Builder(MassActivity.this);
		dialog.setTitle(mass_names[groupPosition]+"\n"+"欢迎您,请注册");
		View view=MassActivity.this.getLayoutInflater().inflate(R.layout.mass_login, null);
		final EditText mass_loginEdit=(EditText) view.findViewById(R.id.mass_loginEdit);
		EditText mass_sexEdit=(EditText) view.findViewById(R.id.mass_sexEdit);
		EditText mass_majorEdit=(EditText) view.findViewById(R.id.mass_majorEdit);
		dialog.setView(view);
		dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(MassActivity.this, "欢迎继续浏览", Toast.LENGTH_SHORT).show();
			}
		});
		dialog.setPositiveButton("提交", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(MassActivity.this, "欢迎您加入"+mass_names[groupPosition], Toast.LENGTH_SHORT).show();
				Intent intent=new Intent();
				Bundle bundle=new Bundle();
				bundle.putString("mass_names",mass_names[groupPosition]);
				Log.d("MassActivity_mass",bundle.getString("mass_names"));
				bundle.putBoolean("flag", true);
				Log.d("bundleID",bundle.toString());
				intent.putExtras(bundle);
				setResult(1,intent);
				finish();
			}
		});

		dialog.show();
	}



}
