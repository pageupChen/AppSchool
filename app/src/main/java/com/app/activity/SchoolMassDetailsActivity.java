package com.app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.appproject.R;

public class SchoolMassDetailsActivity extends AppCompatActivity {
    private ListView SchoolMassDetailsActivity_listView;

    private BaseAdapter baseAdapter=new BaseAdapter() {
        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder=null;
            if(viewHolder==null){
                viewHolder=new ViewHolder();
                convertView= LayoutInflater.from(SchoolMassDetailsActivity.this).inflate(R.layout.schoolmassdetailsactivity_list_item,parent,false);
                viewHolder.schoolmassdetailsActivity_goodsName_textView= (TextView) convertView.findViewById(R.id.schoolmassdetailsActivity_goodsName_textView);
                viewHolder.schoolmassdetailsActivity_sum_textView= (TextView) convertView.findViewById(R.id.schoolmassdetailsActivity_sum_textView);
                viewHolder.schoolmassdetailsActivity_reduce_imageView= (ImageView) convertView.findViewById(R.id.schoolmassdetailsActivity_reduce_imageView);
                viewHolder.schoolmassdetailsActivity_needCounts_editText= (EditText) convertView.findViewById(R.id.schoolmassdetailsActivity_needCounts_editText);
                viewHolder.schoolmassdetailsActivity_add_imageView= (ImageView) convertView.findViewById(R.id.schoolmassdetailsActivity_add_imageView);
                convertView.setTag(viewHolder);
            }
            viewHolder= (ViewHolder) convertView.getTag();


            return convertView;
        }

        class ViewHolder{
             TextView schoolmassdetailsActivity_goodsName_textView;
             TextView schoolmassdetailsActivity_sum_textView;
             ImageView schoolmassdetailsActivity_reduce_imageView;
             EditText schoolmassdetailsActivity_needCounts_editText;
             ImageView schoolmassdetailsActivity_add_imageView;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_mass_details);

        initView();
    }

    private void initView(){
        SchoolMassDetailsActivity_listView= (ListView) this.findViewById(R.id.SchoolMassDetailsActivity_listView);
        SchoolMassDetailsActivity_listView.setAdapter(baseAdapter);
    }
}
