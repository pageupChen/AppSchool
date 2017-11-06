package com.app.activity;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.fragmentA.TabA1Fragment;
import com.app.fragmentA.TabA4Fragment;
import com.app.pojo.Goods;
import com.app.pojo.Mass;
import com.example.appproject.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchoolMassActivity extends AppCompatActivity {
    private SmartRefreshLayout SchoolMassActivity_smartRefreshLayout;
    private RecyclerView SchoolMassActivity_recyclerView;
    private LinearLayout recyclerviewHeader_linearLayout;
    private RecyclerView recyclerviewJob_recyclerView;
    private TextView recyclerviewContent_textView;
    private ItemAdapter itemAdapter;
    private HeaderAdapter headerAdapter;
    private Map<Integer,Mass> schoolMassMap=new HashMap<>();
    private LinearLayoutManager itemLinearLayoutManager;
    private LinearLayoutManager headerLinearLayoutManager;
    private List<Integer> image=new ArrayList<>();
    private List<String> jobName=new ArrayList<>();

    private class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
        public static final int TYPE_HEADER = 0;
        public static final int TYPE_NORMAL = 1;

        private View mHeaderView;
        private Map<Integer,Mass> massMap;
        private Context mContext;

        public void setHeaderView(View headerView) {
            this.mHeaderView = headerView;
        }

        public ItemAdapter(Context context) {
            mContext = context;
            massMap = schoolMassMap;
        }


        @Override
        public int getItemViewType(int position) {
            if (mHeaderView == null)
                return TYPE_NORMAL;
            if (position == 0)
                return TYPE_HEADER;
            return TYPE_NORMAL;
        }

        @Override
        public ItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (mHeaderView != null && viewType == TYPE_HEADER)
                return new ItemAdapter.MyViewHolder(mHeaderView);
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schoolmassactivity_recyclerview_item, parent, false);

            return new ItemAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ItemAdapter.MyViewHolder holder, int position) {
            if (getItemViewType(position) == TYPE_HEADER)
                return;
            final int pos = getRealPosition(holder);
//			final String data = mData.get(pos);
            if (holder instanceof ItemAdapter.MyViewHolder) {
//				holder.getTv_item().setText(data);
                holder.setIsRecyclable(false);
//                holder.getProjectHeader().setImageResource(massMap.get((Integer)pos).getHeader());
//                holder.getProjectName().setText(massMap.get(pos).getName());

            }

        }

        private int getRealPosition(RecyclerView.ViewHolder holder) {
            int position = holder.getLayoutPosition();
            return mHeaderView == null ? position : position - 1;
        }

        @Override
        public int getItemCount() {
            return mHeaderView == null ? massMap.size() : massMap.size() + 1;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            private ImageView projectHeader;
            private TextView projectName;

            public MyViewHolder(View itemView) {
                super(itemView);
                if (itemView == mHeaderView)
                    return;
//				tv_item=(TextView) itemView.findViewById(R.id.tv_item);
                projectHeader= (ImageView) itemView.findViewById(R.id.projectHeader_imageView);
                projectName= (TextView) itemView.findViewById(R.id.projectName_textView);
            }

            public ImageView getProjectHeader() {
                return projectHeader;
            }

            public void setProjectHeader(ImageView projectHeader) {
                this.projectHeader = projectHeader;
            }

            public TextView getProjectName() {
                return projectName;
            }

            public void setProjectName(TextView projectName) {
                this.projectName = projectName;
            }
        }
    }

    private class HeaderAdapter extends RecyclerView.Adapter<HeaderAdapter.MyViewHolder> {


            private Context context;

            public HeaderAdapter(Context context){

                this.context=context;
            }



            @Override
            public HeaderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(context).inflate(R.layout.schoolmassactivity_recyclerview_job,null);
                return new HeaderAdapter.MyViewHolder(view);
            }

            @Override
            public void onBindViewHolder(HeaderAdapter.MyViewHolder holder, int position) {
                holder.getJobHeader_imageView().setImageResource(image.get(position));
                holder.getJobName_textView().setText(jobName.get(position));
            }

            @Override
            public int getItemCount() {
                return image.size();
            }

            public class MyViewHolder extends RecyclerView.ViewHolder{
                private ImageView jobHeader_imageView;
                private TextView jobName_textView;


                public MyViewHolder(View view){
                    super(view);
                    jobHeader_imageView=(ImageView) view.findViewById(R.id.jobHeader_imageView);
                    jobName_textView=(TextView) view.findViewById(R.id.jobName_textView);

                }

                public ImageView getJobHeader_imageView() {
                    return jobHeader_imageView;
                }

                public void setJobHeader_imageView(ImageView jobHeader_imageView) {
                    this.jobHeader_imageView = jobHeader_imageView;
                }

                public TextView getJobName_textView() {
                    return jobName_textView;
                }

                public void setJobName_textView(TextView jobName_textView) {
                    this.jobName_textView = jobName_textView;
                }
            }




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_mass);

        initView();
    }

    private void initView(){
        for(int i=0;i<20;i++){
            Mass mass=new Mass();
//            mass.setMassLogo(R.mipmap.ic_launcher);
            mass.setMassName("社团项目"+(i+1));

            image.add(R.mipmap.ic_launcher);
            jobName.add("社长"+(i+1));

            schoolMassMap.put((Integer)i,mass);
        }

        SchoolMassActivity_smartRefreshLayout= (SmartRefreshLayout) this.findViewById(R.id.SchoolMassActivity_smartRefreshLayout);
        SchoolMassActivity_recyclerView= (RecyclerView) this.findViewById(R.id.SchoolMassActivity_recyclerView);

        itemLinearLayoutManager=new LinearLayoutManager(this);
        itemLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        headerLinearLayoutManager=new LinearLayoutManager(this);
        headerLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);


        View viewHeader= LayoutInflater.from(this).inflate(R.layout.schoolmassactivity_recyclerview_header,null);
        recyclerviewHeader_linearLayout= (LinearLayout) viewHeader.findViewById(R.id.recyclerviewHeader_linearLayout);
        recyclerviewContent_textView= (TextView) viewHeader.findViewById(R.id.recyclerviewContent_textView);
        recyclerviewJob_recyclerView= (RecyclerView) viewHeader.findViewById(R.id.recyclerviewJob_recyclerView);

        itemAdapter=new ItemAdapter(SchoolMassActivity.this);
        itemAdapter.setHeaderView(recyclerviewHeader_linearLayout);
        SchoolMassActivity_recyclerView.setAdapter(itemAdapter);
        SchoolMassActivity_recyclerView.setLayoutManager(itemLinearLayoutManager);

        headerAdapter=new HeaderAdapter(this);
        recyclerviewJob_recyclerView.setAdapter(headerAdapter);
        recyclerviewJob_recyclerView.setLayoutManager(headerLinearLayoutManager);

        SchoolMassActivity_smartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("autoRefresh()","true");

                    }
                }).start();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("autoRefresh()","true");

                    }
                }).start();
            }
        });

    }
}
