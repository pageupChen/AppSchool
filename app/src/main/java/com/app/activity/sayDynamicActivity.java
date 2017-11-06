package com.app.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.fragmentA.TabA1Fragment;
import com.app.pojo.Comment;
import com.app.pojo.Dynamic;
import com.app.util.MyImageLoader;
import com.app.view.WrapContentLinearLayoutManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.appproject.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.nostra13.universalimageloader.utils.L;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class sayDynamicActivity extends AppCompatActivity {
    private Toolbar toolBar;
    private SmartRefreshLayout sayDynamicActivity_smartRefreshLayout;
    private RecyclerView sayDynamicActivity_recyclerView;
    private TextView sayDynamicActivity_send_textView;
    private LinearLayout sayDynamicActivity_dynamicLine;
    private EditText sayDynamicActivity_editText;
    private CircleImageView sayDynamicActivity_massLogo_imageHeader;
    private TextView sayDynamicActivity_massName_textView;
    private TextView sayDynamicActivity_sendTime;
    private TextView sayDynamicActivity_school;
    private TextView sayDynamicActivity_recyclerView_textBody;
    private NineGridImageView sayDynamicActivity_ninegridImageView;
    private LinearLayout sayDynamicActivity_sayLine;
    private ImageView sayDynamicActivity_recyclerView_sayImage;
    private TextView sayDynamicActivity_recyclerView_sayText;
    private LinearLayout sayDynamicActivity_goodLine;
    private ImageView sayDynamicActivity_recyclerView_goodImage;
    private TextView sayDynamicActivity_recyclerView_goodText;
    private TextView sayDynamicActivity_tip_textView;
    private ImageView sayDynamicActivity_tip_imageView;
    private LinearLayout sayDynamicActivity_tip_line;
    private List<Bitmap> bitmaps=new ArrayList<>();

    private LinearLayoutManager lm;
    private MyAdapter mAdapter;

    private int startRecordIndex=5;
    private int endRecordIndex=10;
    private boolean isFirstIn;
    private int lastItemPosition;
    private int firstItemPosition;
    private MyImageLoader imageLoader;
    private Dynamic dynamic;
    private List<Comment> commentList=new ArrayList<>();
    public static List<String> commentHeaderURL=new ArrayList<>();
    private static final int SHOW_KEYBOARD=1;
    private static final int CLOSE_KEYBOARD=2;

    //根据Glide缓存来获取头像图片
    private class getLogoImageCacheAsyncTask extends AsyncTask<String,Void,File>{

        @Override
        protected File doInBackground(String... params) {
            String imgUrl=params[0];

//            List<File> files=new ArrayList<>();
            File file=null;
            try{
//                for(int i=0;i<imgUrlList.size();i++) {
//                    Log.d("imgUrlList",imgUrlList.get(i));
                    file=Glide.with(sayDynamicActivity.this)
                            .load(imgUrl)
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get();
//                }
//                Log.d("files",files.size()+"");
                return file;
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(File file) {
            super.onPostExecute(file);




            sayDynamicActivity_massLogo_imageHeader.setImageBitmap(BitmapFactory.decodeFile(file.getPath()));






        }
    }

    private View.OnClickListener myClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.sayDynamicActivity_sayLine:
                    ShowOrCloseKeyboard(SHOW_KEYBOARD);
                    break;
                case R.id.sayDynamicActivity_send_textView:
                    if(TextUtils.isEmpty(sayDynamicActivity_editText.getText())){
                        Toast.makeText(sayDynamicActivity.this,"评论内容不能为空，亲",Toast.LENGTH_SHORT).show();
                    }else{
                        sayDynamicActivity_send_textView.setClickable(false);
//                        Log.d("tipLine",sayDynamicActivity_tip_line.getHeight()+"");
                        sendComment();
                    }
                    break;
                case R.id.sayDynamicActivity_tip_line:

                    ShowOrCloseKeyboard(SHOW_KEYBOARD);
                    break;
            }
        }
    };

    //根据Glide来获取九宫格图片
    private class getImageCacheAsyncTask extends AsyncTask<List<String>,Void,List<File>> {

        @Override
        protected List<File> doInBackground(List<String>... params) {
            List<String> imgUrlList = params[0];

            List<File> files = new ArrayList<>();
            try {
                for (int i = 0; i < imgUrlList.size(); i++) {
                    Log.d("imgUrlList", imgUrlList.get(i));
                    files.add(Glide.with(sayDynamicActivity.this)
                            .load(imgUrlList.get(i))
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get());
                }
                Log.d("files", files.size() + "");
                return files;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<File> files) {
            super.onPostExecute(files);
            if (files.size() != 0) {
                for (int i = 0; i < files.size(); i++) {
                    String path = files.get(i).getPath();

                    bitmaps.add(BitmapFactory.decodeFile(path));

                }
                sayDynamicActivity_ninegridImageView.setImagesData(bitmaps);
            }
        }
    }


    private class MyAdapter extends RecyclerView.Adapter<sayDynamicActivity.MyAdapter.MyViewHolder> {
        public static final int TYPE_HEADER = 0;
        public static final int TYPE_NORMAL = 1;

        private View mHeaderView;
        //		private List<String> mData;
        private Context mContext;

        public void setHeaderView(View headerView) {
            this.mHeaderView = headerView;
        }

        public MyAdapter(Context context,RecyclerView recyclerView) {
            mContext = context;
//            isFirstIn=true;
//            imageLoader=new MyImageLoader(sayDynamicActivity.this,recyclerView);


//			mData = data;
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
        public sayDynamicActivity.MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (mHeaderView != null && viewType == TYPE_HEADER)
                return new sayDynamicActivity.MyAdapter.MyViewHolder(mHeaderView);
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_say_dynamic_item, parent, false);

            return new sayDynamicActivity.MyAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final sayDynamicActivity.MyAdapter.MyViewHolder holder, int position) {
            Log.d("sayDynamicBindView","bindview");
            if (getItemViewType(position) == TYPE_HEADER)
                return;
            final int pos = getRealPosition(holder);
//			final String data = mData.get(pos);
            if (holder instanceof sayDynamicActivity.MyAdapter.MyViewHolder) {
//				holder.getTv_item().setText(data);
//			Glide.with(TabA1Fragment.this.getActivity()).load(BitmapFactory.decodeStream(is)).centerCrop().into(holder.getHome_fragA1_recyclerView_imageHeader());
                holder.setIsRecyclable(false);


                List<Bitmap> bitmapList=new ArrayList<>();
                for(int i=0;i<9;i++){
                    Bitmap bitmap= BitmapFactory.decodeResource(sayDynamicActivity.this.getResources(),R.mipmap.ic_launcher);
                    bitmapList.add(bitmap);
                }

//                Handler handler
//                holder.sayDynamicActivity_header_imageView.setImageBitmap();

//                holder.sayDynamicActivity_header_imageView.setTag(commentList.get(pos).getStuHeader());
//                imageLoader.showCommentHeaderImages(holder.sayDynamicActivity_header_imageView,commentList.get(pos).getStuHeader());
                Glide.with(sayDynamicActivity.this).load(commentList.get(pos).getStuHeader()).into(holder.sayDynamicActivity_header_imageView);

                holder.sayDynamicActivity_stuName_textView.setText(commentList.get(pos).getStuName());
                holder.sayDynamicActivity_floor_textView.setText("\t["+commentList.get(pos).getCommFloor()+"]楼");
                holder.sayDynamicActivity_floor_textView.setTextColor(Color.RED);
                holder.sayDynamicActivity_sendTime_textView.setText(commentList.get(pos).getCommTime());
                holder.sayDynamicActivity_stuSchool_textView.setText(commentList.get(pos).getStuSchool());
                holder.sayDynamicActivity_commentBody_textView.setText(commentList.get(pos).getCommContent());

                holder.sayDynamicActivity_Line.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /**
                         * 根据返回的comment对象拿到该条comment的发送者（发送者id）
                         * 点击该项时，根据id弹出不同的操作项，
                         * 如果id等于自己的账号id就弹出删除
                         * */
                        if(commentList.get(pos).getCommStuId()==1){
                            String[] commTip={"删除"};
                            AlertDialog.Builder commDialog=new AlertDialog.Builder(sayDynamicActivity.this);
                            commDialog.setItems(commTip, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(which==0){
                                        //得到该条comment的id，发送到后台删掉
                                        deleteComment(commentList.get(pos));
                                    }
                                }
                            });
                            commDialog.show();
                        }
                    }
                });

                /**
                 *       holder.getHome_fragA1_ninegridImageView().setImagesData(dymanicMap.get(pos).getDynaPictures());
                 *		 setImagesData需要一个转化后的List<Bitmap>集合
                 *
                 *		 每次加载的时候先在新线程中去后台查询getDynaPictures()里集合的元素，然后转化成List<Bitmap>集合
                 * */

            }

        }

        private int getRealPosition(RecyclerView.ViewHolder holder) {
            int position = holder.getLayoutPosition();
            return mHeaderView == null ? position : position - 1;
        }

        @Override
        public int getItemCount() {
            return mHeaderView == null ? commentList.size() : commentList.size() + 1;
//            return 10;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            LinearLayout sayDynamicActivity_Line;
            CircleImageView sayDynamicActivity_header_imageView;
            TextView sayDynamicActivity_stuName_textView;
            TextView sayDynamicActivity_floor_textView;
            TextView sayDynamicActivity_sendTime_textView;
            TextView sayDynamicActivity_stuSchool_textView;
            TextView sayDynamicActivity_commentBody_textView;



            public MyViewHolder(View itemView) {
                super(itemView);
                if (itemView == mHeaderView)
                    return;
                sayDynamicActivity_Line=(LinearLayout)itemView.findViewById(R.id.sayDynamicActivity_Line);
                sayDynamicActivity_header_imageView=(CircleImageView) itemView.findViewById(R.id.sayDynamicActivity_header_imageView);
                sayDynamicActivity_stuName_textView=(TextView)itemView.findViewById(R.id.sayDynamicActivity_stuName_textView);
                sayDynamicActivity_floor_textView=(TextView)itemView.findViewById(R.id.sayDynamicActivity_floor_textView);
                sayDynamicActivity_sendTime_textView=(TextView)itemView.findViewById(R.id.sayDynamicActivity_sendTime_textView);
                sayDynamicActivity_stuSchool_textView=(TextView)itemView.findViewById(R.id.sayDynamicActivity_stuSchool_textView);
                sayDynamicActivity_commentBody_textView=(TextView)itemView.findViewById(R.id.sayDynamicActivity_commentBody_textView);
            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_say_dynamic);

        initView();

    }

    private void initView(){
        toolBar= (Toolbar) this.findViewById(R.id.sayDynamicActivity_toolBar);
        setSupportActionBar(toolBar);
        //设置返回键可用
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //设置返回键的点击事件
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(sayDynamicActivity.this,MainActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("dynamic",dynamic);
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK,intent);
                sayDynamicActivity.this.finish();
            }
        });
        sayDynamicActivity_smartRefreshLayout=(SmartRefreshLayout)this.findViewById(R.id.sayDynamicActivity_smartRefreshLayout);
        sayDynamicActivity_smartRefreshLayout.autoRefresh();
        sayDynamicActivity_smartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
           //加载事件
            @Override
            public void onLoadmore(final RefreshLayout refreshlayout) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Log.d("onLoadmore","true");
                            startRecordIndex+=5;
                            OkHttpClient okHttpClient=new OkHttpClient();

                            RequestBody requestBody=new FormBody.Builder()
                                    .add("stuId",1+"")
                                    .add("dynamicId",dynamic.getDynaId()+"")
                                    .add("startRecordIndex",startRecordIndex+"")
                                    .build();
                            Request request=new Request.Builder()
                                    .url("http://192.168.1.101:8080/schoolapp/returnComment.action")
                                    .post(requestBody)
                                    .build();

                            Response response=okHttpClient.newCall(request).execute();
                            Log.d("onLoadmore","execute");
                            String responseJSON=response.body().string();

                            Log.d("responseJSON",responseJSON);
                            Gson gson=new Gson();

                            List<Comment> commentListPart=gson.fromJson(responseJSON,new TypeToken<List<Comment>>(){}.getType());
//                            Log.d("dynamicList",dynamicList.size()+"");
                            for(Comment comment:commentListPart){
//                                Log.d("dynamic",comment.getDynaId()+","+comment.getDynaTitle()+","+dynamic.getUserHeader()+","+dynamic.getDynaContent()+","+dynamic.getDynaPictures()+","+dynamic.getDynaSendTime()+","+dynamic.getDynaTalkNumber()+","+dynamic.getDynaStarNumber()+","+dynamic.getDynaStuId()+","+dynamic.getDynaMassId()+","+dynamic.getDynaType());
                            }
                            for(int i=0;i<commentListPart.size();i++){
                                commentList.add(commentListPart.get(i));
                                Log.d("commentList",commentList.get(i).getCommStuId()+"");
//								Log.d("dynamicMap",dymanicMap.get(i).getDynaId()+dymanicMap.get(i).getDynaContent()+dymanicMap.get(i).getDynaStarNumber());
                            }
                            for(int i=0;i<commentList.size();i++){
                                Log.d("dynamicAdd","true");
                                commentHeaderURL.add(commentList.get(i).getStuHeader());
                            }


                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        //结束加载
                        sayDynamicActivity_smartRefreshLayout.finishLoadmore();
                        //加载失败的话3秒后结束加载
                        refreshlayout.finishLoadmore(3000);

                        notifydatachange();
                    }
                }).start();
            }

            //刷新事件
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("autoRefresh()","refresh");
                        Log.d("sayDynamicBindView","refresh");
                        try{
                            //在数据库中重新加载
                            startRecordIndex=0;
                            endRecordIndex=5;

                            //将回复对象的集合全部清空，重新获取，若无人评论，则返回为空
                            commentList.clear();
//                            mAdapter.notifyDataSetChanged();
                            notifydatachange();

                            OkHttpClient okHttpClient=new OkHttpClient();

                            RequestBody requestBody=new FormBody.Builder()
                                    .add("stuId",1+"")
                                    //把该条评论的id发到后台数据库，取出该条评论的所有回复内容
                                    .add("dynamicId",dynamic.getDynaId()+"")
                                    .add("startRecordIndex",startRecordIndex+"")
                                    .build();
                            Request request=new Request.Builder()
                                    .url("http://192.168.1.101:8080/schoolapp/returnComment.action")
                                    .post(requestBody)
                                    .build();
                            Response response=okHttpClient.newCall(request).execute();
                            String responseJSON=response.body().string();

                            Log.d("responseJSON",responseJSON);

                            //将返回的内容为comment的json解析成comment对象集合
                            Gson gson=new Gson();
                            List<Comment> commentListPart=gson.fromJson(responseJSON,new TypeToken<List<Comment>>(){}.getType());


                            for(Comment comment:commentListPart){
//                                Log.d("dynamic",comment.getDynaId()+","+comment.getDynaTitle()+","+dynamic.getUserHeader()+","+dynamic.getDynaContent()+","+dynamic.getDynaPictures()+","+dynamic.getDynaSendTime()+","+dynamic.getDynaTalkNumber()+","+dynamic.getDynaStarNumber()+","+dynamic.getDynaStuId()+","+dynamic.getDynaMassId()+","+dynamic.getDynaType());
                            }
                            //循环遍历将comment对象添加到commentList中
                            for(int i=0;i<commentListPart.size();i++){
                                commentList.add(commentListPart.get(i));
                                Log.d("commentList",commentList.get(i).getCommStuId()+"");
//								Log.d("dynamicMap",dymanicMap.get(i).getDynaId()+dymanicMap.get(i).getDynaContent()+dymanicMap.get(i).getDynaStarNumber());
                            }
                            for(int i=0;i<commentList.size();i++){
                                Log.d("dynamicAdd","true");
                                commentHeaderURL.add(commentList.get(i).getStuHeader());
                                Log.d("commentHeaderURL",commentHeaderURL.size()+"");
                            }

//                            if(isFirstIn){
//                                Log.d("rrr",true+"");
//
//                                imageLoader.loadCommentHeaderImages(firstItemPosition,1);
//
////                                imageLoader.loadNineImages(firstItemPosition,1);
//                                isFirstIn=false;
//                            }

//                            mAdapter.notifyDataSetChanged();


                        }catch (Exception e){
                            e.printStackTrace();
                        }



                        //结束加载
                        sayDynamicActivity_smartRefreshLayout.finishRefresh();
                        //加载失败的话3秒后结束加载
                        refreshlayout.finishRefresh(3000);

                        //编写方法，更新adapter的数据源
                        notifydatachange();

                    }
                }).start();



            }

        });



        sayDynamicActivity_recyclerView=(RecyclerView) this.findViewById(R.id.sayDynamicActivity_recyclerView);


        sayDynamicActivity_editText=(EditText)this.findViewById(R.id.sayDynamicActivity_editText);
        sayDynamicActivity_editText.setSingleLine(false);
//        RelativeLayout.LayoutParams lp= (RelativeLayout.LayoutParams) sayDynamicActivity_editText.getLayoutParams();
//        lp.height=150;
//        sayDynamicActivity_editText.setLayoutParams(lp);
        sayDynamicActivity_send_textView=(TextView)this.findViewById(R.id.sayDynamicActivity_send_textView);

        //获取前一个activyt(MainActivity)传递过来的dynamic对象
        dynamic= (Dynamic) this.getIntent().getExtras().getSerializable("dynamic");

        //实例化线性布局管理器，设置该线性管理器为垂直排列(recyclerview的列表项垂直排列)
        lm = new LinearLayoutManager(sayDynamicActivity.this);
        lm.setOrientation(OrientationHelper.VERTICAL);

        //recyclerview设置布局管理器
        sayDynamicActivity_recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //实例化一个adapter
        mAdapter = new sayDynamicActivity.MyAdapter(sayDynamicActivity.this,sayDynamicActivity_recyclerView);

        //加载recyclerview头部布局
        View viewHeader=LayoutInflater.from(this).inflate(R.layout.activity_say_dynamic_header,null);
        //实例化头部布局的所有控件
        sayDynamicActivity_dynamicLine = (LinearLayout) viewHeader.findViewById(R.id.sayDynamicActivity_dynamicLine);
        sayDynamicActivity_massLogo_imageHeader=(CircleImageView) viewHeader.findViewById(R.id.sayDynamicActivity_massLogo_imageHeader);
        sayDynamicActivity_massName_textView=(TextView) viewHeader.findViewById(R.id.sayDynamicActivity_massName_textView);
        sayDynamicActivity_sendTime=(TextView)viewHeader.findViewById(R.id.sayDynamicActivity_sendTime);
        sayDynamicActivity_school=(TextView) viewHeader.findViewById(R.id.sayDynamicActivity_school);
        sayDynamicActivity_recyclerView_textBody=(TextView) viewHeader.findViewById(R.id.sayDynamicActivity_recyclerView_textBody);
        sayDynamicActivity_ninegridImageView=(NineGridImageView) viewHeader.findViewById(R.id.sayDynamicActivity_ninegridImageView);
        sayDynamicActivity_sayLine=(LinearLayout) viewHeader.findViewById(R.id.sayDynamicActivity_sayLine);
        sayDynamicActivity_recyclerView_sayImage=(ImageView) viewHeader.findViewById(R.id.sayDynamicActivity_recyclerView_sayImage);
        sayDynamicActivity_recyclerView_sayText=(TextView) viewHeader.findViewById(R.id.sayDynamicActivity_recyclerView_sayText) ;
        sayDynamicActivity_goodLine=(LinearLayout) viewHeader.findViewById(R.id.sayDynamicActivity_goodLine);
        sayDynamicActivity_recyclerView_goodImage=(ImageView) viewHeader.findViewById(R.id.sayDynamicActivity_recyclerView_goodImage);
        sayDynamicActivity_recyclerView_goodText=(TextView) viewHeader.findViewById(R.id.sayDynamicActivity_recyclerView_goodText);
        sayDynamicActivity_tip_textView=(TextView) viewHeader.findViewById(R.id.sayDynamicActivity_tip_textView);
        sayDynamicActivity_tip_imageView=(ImageView) viewHeader.findViewById(R.id.sayDynamicActivity_tip_imageView);
        sayDynamicActivity_tip_line=(LinearLayout) viewHeader.findViewById(R.id.sayDynamicActivity_tip_line);

        //根据前一个activity传递过来的dynamic对象设置头部布局的数据
//        sayDynamicActivity_recyclerView_imageHeader.setImageBitmap();
//        sayDynamicActivity_recyclerView_textHeader.setText(dynamic.getDynaTitle());
//        sayDynamicActivity_recyclerView_textTime.setText(dynamic.getDynaSendTime());
//        sayDynamicActivity_massLogo_imageHeader.setImageBitmap();
        new getLogoImageCacheAsyncTask().execute(dynamic.getMassLogo());
        sayDynamicActivity_massName_textView.setText(dynamic.getMassName());
        sayDynamicActivity_sendTime.setText(dynamic.getDynaSendTime());
        sayDynamicActivity_recyclerView_textBody.setText(dynamic.getDynaContent());

        //新开线程加载glide缓存中的九宫格图片
        new getImageCacheAsyncTask().execute(dynamic.getDynaPictures());

        //九宫格设置图片适配器
        sayDynamicActivity_ninegridImageView.setAdapter(new NineGridImageViewAdapter<Bitmap>() {
            @Override
            protected void onDisplayImage(Context context, ImageView imageView, Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
//                Glide.with(sayDynamicActivity.this)
//                        .load(bitmap)
//                        .centerCrop()
//                        .into(imageView);
            }

            @Override
            protected void onItemImageClick(Context context, int index, List<Bitmap> list) {
                super.onItemImageClick(context, index, list);
                Intent intent=new Intent(sayDynamicActivity.this,showDynamicImageActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("bitmapIndex",index);
                bundle.putSerializable("dynamic", dynamic);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });



        sayDynamicActivity_recyclerView_sayText.setText(dynamic.getDynaTalkNumber()+"");
        sayDynamicActivity_recyclerView_goodText.setText(dynamic.getDynaStarNumber()+"");
        //根据是否点赞过来设置点赞的图片
        if(dynamic.isGiveGood()){
            sayDynamicActivity_recyclerView_goodImage.setImageResource(R.mipmap.givegood);
        }else{
            sayDynamicActivity_recyclerView_goodImage.setImageResource(R.mipmap.cancelgood);
        }

        sayDynamicActivity_goodLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("goodLineClick","true");

                if(dynamic.isGiveGood()){
                    dynamic.setDynaStarNumber(dynamic.getDynaStarNumber()-1);
                    sayDynamicActivity_recyclerView_goodText.setText(dynamic.getDynaStarNumber()+"");
                    sayDynamicActivity_recyclerView_goodImage.setImageResource(R.mipmap.cancelgood);
//							mAdapter.notifyItemRangeChanged(pos,1);
                }else{
                    dynamic.setDynaStarNumber(dynamic.getDynaStarNumber()+1);
                    sayDynamicActivity_recyclerView_goodText.setText(dynamic.getDynaStarNumber()+"");
                    sayDynamicActivity_recyclerView_goodImage.setImageResource(R.mipmap.givegood);
//							mAdapter.notifyItemRangeChanged(pos,1);
                }

                GiveOrCancelGood(dynamic);
            }
        });


        //点击评论数事件
        sayDynamicActivity_sayLine.setOnClickListener(myClickListener);
        sayDynamicActivity_tip_line.setOnClickListener(myClickListener);

        //发送事件
        sayDynamicActivity_send_textView.setOnClickListener(myClickListener);

//        sayDynamicActivity_editText.setSelection(6);

        //评论框事件
        sayDynamicActivity_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //监听editText内容的改变，从而做不同的处理
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //根据editText是否为空，来设置发送按钮的颜色
                if(TextUtils.isEmpty(sayDynamicActivity_editText.getText())){
                    sayDynamicActivity_send_textView.setTextColor(sayDynamicActivity.this.getResources().getColor(R.color.colorFont2));
                }else{
//                    sayDynamicActivity_send_textView.setClickable(true);
                    sayDynamicActivity_send_textView.setTextColor(sayDynamicActivity.this.getResources().getColor(R.color.colorPrimary));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        mAdapter.setHeaderView(sayDynamicActivity_dynamicLine);
        sayDynamicActivity_recyclerView.setAdapter(mAdapter);
    }

    //发送评论方法
    private void sendComment(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                /**
                 * 回复的内容，发表回复的学生id（本账号id），回复那条评论的id，发表回复的时间
                 * */
                try{
                    //创建一个评论对象
                    Comment comment=new Comment();
                    //将输入框的内容设置到评论对象中
                    comment.setCommContent(sayDynamicActivity_editText.getText().toString());
                    //得到本账号的id
                    comment.setCommStuId(Integer.parseInt("1"));
                    //得到该条动态的id
                    comment.setCommDynaId(dynamic.getDynaId());
                    //new一个时间
                    Date date=new Date();
                    //设置时间为年月日，24小时制的时分秒的格式
                    SimpleDateFormat bartDateFormat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    //将发送的时间设置到评论对象中
                    comment.setCommTime(bartDateFormat.format(date));

                    //创建一个josn对象，将comment对象封装成json对象
                    JSONObject commentJSON=new JSONObject();
                    commentJSON.put("commContent",comment.getCommContent());
                    commentJSON.put("commStuId",comment.getCommStuId());
                    commentJSON.put("commDynaId",comment.getCommDynaId());
                    commentJSON.put("commTime",comment.getCommTime());

                    OkHttpClient client=new OkHttpClient();
                    RequestBody requestBody=new FormBody.Builder()
                            .add("comment",commentJSON.toString())
                            .build();
                    Request request=new Request.Builder()
                            .url("http://192.168.1.101:8080/schoolapp/sendComment.action")
                            .post(requestBody)
                            .build();

                    Response response=client.newCall(request).execute();

                    /**
                     * 针对所回复的评论（即拿到所回复评论的id），然后更新该条评论的评论数（评论数++）
                     * */

                    //发送后自动刷新
                    sayDynamicActivity_smartRefreshLayout.autoRefresh();
                    //调用发送后的方法
                    showSendTip();

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).start();
    }

    //点击发送后的处理
    private void showSendTip(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //将点击按钮设置为不可点击
                sayDynamicActivity_send_textView.setClickable(true);
                if(commentList.size()!=0){
                    Log.d("isShowSendTip","true");
//                    sayDynamicActivity_recyclerView_sayText.setText(commentList.get(0).getCommFloor()+"");
                }

//                sayDynamicActivity_send_textView.setClickable(false);
                //提示正在发送
                Toast.makeText(sayDynamicActivity.this, "正在发送", Toast.LENGTH_SHORT).show();
                //清空输入框内容
                sayDynamicActivity_editText.setText("");
                //关闭键盘
                ShowOrCloseKeyboard(CLOSE_KEYBOARD);
            }
        });
    }

    //显示或关闭键盘的方法
    private void ShowOrCloseKeyboard(int keyboardState){
        if(keyboardState==SHOW_KEYBOARD){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
        }else if(keyboardState==CLOSE_KEYBOARD){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }

    //点赞或取消点赞事件
    private void GiveOrCancelGood(final Dynamic dynamic){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    RequestBody requestBody=null;
                    if(dynamic.isGiveGood()){
                        //如果当前的状态为已经点赞，则发通知到后台取消点赞
                        requestBody=new FormBody.Builder()
                                .add("giveOrCancel","cancelGood")
                                .add("stuId",1+"")
                                .add("DynaId",dynamic.getDynaId()+"")
                                .build();

                        dynamic.setGiveGood(false);
                    }else{
                        requestBody=new FormBody.Builder()
                                .add("giveOrCancel","giveGood")
                                .add("stuId",1+"")
                                .add("DynaId",dynamic.getDynaId()+"")
                                .build();
                        dynamic.setGiveGood(true);
                    }
                    Request request=new Request.Builder()
                            .url("http://192.168.1.101:8080/schoolapp/giveOrCancelGood.action")
                            .post(requestBody)
                            .build();
                    Response response=client.newCall(request).execute();
                    Log.d("giveOrCancelGood","true");
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).start();
    }

    //点击手机物理键事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            //如果点击了返回键，则向前一个activity传递数据，并结束当前activity
            Log.d("onKeyDown","true");
            Intent intent=new Intent(sayDynamicActivity.this,MainActivity.class);
            Bundle bundle=new Bundle();
            bundle.putSerializable("dynamic",dynamic);
            intent.putExtras(bundle);
            setResult(Activity.RESULT_OK,intent);
            this.finish();
        }

        return super.onKeyDown(keyCode, event);

    }

    //删除回复事件
    public void deleteComment(final Comment comment){
        //访问网络，新开线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
//                    JSONObject commentObject=new JSONObject();
//                    commentObject.put("")
                    OkHttpClient client=new OkHttpClient();
                    RequestBody requestBody=new FormBody.Builder()
                            .add("commId",comment.getCommId()+"")
                            .add("commDynaId",comment.getCommDynaId()+"")
                            .build();
                    Log.d("commDynaId",comment.getCommDynaId()+"");
                    Request request=new Request.Builder()
                            .post(requestBody)
                            .url("http://192.168.1.101:8080/schoolapp/deleteComment.action")
                            .build();
                    Response response=client.newCall(request).execute();
                    //弹出删除的提示
                    refreshComment();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }


    //通知主线程修改组件的状态和数据
    public void notifydatachange(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
                if(commentList.size()>0){
                    dynamic.setDynaTalkNumber(commentList.get(0).getCommFloor());
                    sayDynamicActivity_recyclerView_sayText.setText(commentList.get(0).getCommFloor()+"");
                    //在发送回复的时候，把回复总数写进dynamic对象

                }else if(commentList.size()==0){
                    dynamic.setDynaTalkNumber(0);
                    sayDynamicActivity_recyclerView_sayText.setText("0");
                }

                sayDynamicActivity_tip_textView.setVisibility(View.VISIBLE);
                if(commentList.size()!=0){
                    sayDynamicActivity_tip_textView.setText("评论\t"+commentList.get(0).getCommFloor()+"\t条");
                    sayDynamicActivity_tip_line.setVisibility(View.GONE);
                }else{
                    Log.d("tip_line","true");
                    sayDynamicActivity_tip_textView.setText("评论\t0\t条");
                    sayDynamicActivity_tip_line.setVisibility(View.VISIBLE);
                }

//                        if(commentList.get(0).getCommFloor()==0){
//                            sayDynamicActivity_tip_imageView.setVisibility(View.VISIBLE);
//                        }else{
//                            sayDynamicActivity_tip_imageView.setVisibility(View.INVISIBLE);
//                        }
            }
        });
    }

    //发送评论后，重新从后台获取数据，刷新页面
    public void refreshComment(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("autoRefresh()","refresh");
                Log.d("sayDynamicBindView","refresh");
                try{
                    startRecordIndex=0;
                    endRecordIndex=5;
                    commentList.clear();
//                            dynamicURL.clear();
//                            nineURLMap.clear();
                    OkHttpClient okHttpClient=new OkHttpClient();

                    RequestBody requestBody=new FormBody.Builder()
                            .add("stuId",1+"")
                            .add("dynamicId",dynamic.getDynaId()+"")
                            .add("startRecordIndex",startRecordIndex+"")
                            .build();
                    Request request=new Request.Builder()
                            .url("http://192.168.1.101:8080/schoolapp/returnComment.action")
                            .post(requestBody)
                            .build();
                    Response response=okHttpClient.newCall(request).execute();
                    String responseJSON=response.body().string();

                    Log.d("responseJSON",responseJSON);
                    Gson gson=new Gson();

                    List<Comment> commentListPart=gson.fromJson(responseJSON,new TypeToken<List<Comment>>(){}.getType());
//                            Log.d("dynamicList",dynamicList.size()+"");
                    for(Comment comment:commentListPart){
//                                Log.d("dynamic",comment.getDynaId()+","+comment.getDynaTitle()+","+dynamic.getUserHeader()+","+dynamic.getDynaContent()+","+dynamic.getDynaPictures()+","+dynamic.getDynaSendTime()+","+dynamic.getDynaTalkNumber()+","+dynamic.getDynaStarNumber()+","+dynamic.getDynaStuId()+","+dynamic.getDynaMassId()+","+dynamic.getDynaType());
                    }
                    for(int i=0;i<commentListPart.size();i++){
                        commentList.add(commentListPart.get(i));
                        Log.d("commentList",commentList.get(i).getCommStuId()+"");
//								Log.d("dynamicMap",dymanicMap.get(i).getDynaId()+dymanicMap.get(i).getDynaContent()+dymanicMap.get(i).getDynaStarNumber());
                    }
                    for(int i=0;i<commentList.size();i++){
                        Log.d("dynamicAdd","true");
                        commentHeaderURL.add(commentList.get(i).getStuHeader());
                        Log.d("commentHeaderURL",commentHeaderURL.size()+"");
                    }

//                    if(isFirstIn){
//                        Log.d("rrr",true+"");
//
//                        imageLoader.loadCommentHeaderImages(firstItemPosition,1);
//
////                                imageLoader.loadNineImages(firstItemPosition,1);
//                        isFirstIn=false;
//                    }

//                            mAdapter.notifyDataSetChanged();


                }catch (Exception e){
                    e.printStackTrace();
                }



                //结束加载
//                sayDynamicActivity_smartRefreshLayout.finishRefresh();
//                //加载失败的话3秒后结束加载
//                refreshlayout.finishRefresh(3000);

                //从后台获取到数据后，刷新界面
                notifydatachange();

            }
        }).start();
    }
}
