package com.app.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.fragmentA.TabA1Fragment;
import com.app.pojo.Dynamic;
import com.app.util.MyImageLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.example.appproject.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class showDynamicImageActivity extends AppCompatActivity {
    private CircleImageView showDynamicImageActivity_back_circleImageView;
    private CircleImageView showDynamicImageActivity_download_circleImageView;
    private ViewPager showDynamicImageActivity_viewPager;
    private List<ImageView> imageViews=new ArrayList<>();
//    private List<>
//    private List<Bitmap> bitmapList=new ArrayList<>();
    private Dynamic dynamic;
    private int bitmapIndex;
    private int currentIndex;

    //另开线程的时候，如果程序有变量需要等该线程完成时才能获取值的时候，
    // 一定要等该变量在新线程中获得值才能使用（即要保证用到该变量的逻辑代码在新线程运行完后才能运行）
    private class getImageCacheAsyncTask extends AsyncTask<List<String>,Void,List<File>>{

        @Override
        protected List<File> doInBackground(List<String>... params) {
            List<String> imgUrlList=params[0];

            List<File> files=new ArrayList<>();
            try{
                for(int i=0;i<imgUrlList.size();i++) {
                    Log.d("imgUrlList",imgUrlList.get(i));
                    files.add(Glide.with(showDynamicImageActivity.this)
                            .load(imgUrlList.get(i))
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get());
                }
                Log.d("files",files.size()+"");
                return files;
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<File> files) {
            super.onPostExecute(files);

            for(int i=0;i<dynamic.getDynaPictures().size();i++){
                ImageView imageView=new ImageView(showDynamicImageActivity.this);
                imageView.setImageResource(R.mipmap.default_error);
                imageViews.add(imageView);
            }
           if(files.size()!=0){
                for(int i=0;i<files.size();i++){
                    String path=files.get(i).getPath();
                    Log.d("path",path);

                    imageViews.get(i).setImageBitmap(BitmapFactory.decodeFile(path));


                }

                Log.d("imageViews",imageViews.size()+"");
                showDynamicImageActivity_viewPager.setAdapter(new PagerAdapter() {
                    @Override
                    public int getCount() {
                        return dynamic.getDynaPictures().size();
                    }

                    @Override
                    public boolean isViewFromObject(View view, Object object) {
                        return view==object;
                    }

                    @Override
                    public Object instantiateItem(ViewGroup container, int position) {
                        Log.d("imageViews",imageViews.size()+"");
                        View view=imageViews.get(position);
                        container.addView(view);
                        return view;

                    }

                    @Override
                    public void destroyItem(ViewGroup container, int position, Object object) {
//                super.destroyItem(container, position, object);
                        container.removeView(imageViews.get(position));
                    }
                });

                showDynamicImageActivity_viewPager.setCurrentItem(bitmapIndex);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_dynamic_image);

        initView();
    }

    private void initView(){
        showDynamicImageActivity_back_circleImageView=(CircleImageView)this.findViewById(R.id.showDynamicImageActivity_back_circleImageView);
        showDynamicImageActivity_download_circleImageView=(CircleImageView)this.findViewById(R.id.showDynamicImageActivity_download_circleImageView);
        showDynamicImageActivity_viewPager= (ViewPager) this.findViewById(R.id.showDynamicImageActivity_viewPager);

        //获取前一个activity传递过来的dynamic对象
        dynamic= (Dynamic) showDynamicImageActivity.this.getIntent().getExtras().get("dynamic");
        //获取前一个activity传递过来的当前图片的下标
        bitmapIndex= (int) showDynamicImageActivity.this.getIntent().getExtras().get("bitmapIndex");

        //viewPager的滑动监听事件
        showDynamicImageActivity_viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("PagerPosition",position+"");
                //获取当前的图片的position
                currentIndex=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //点击返回事件
        showDynamicImageActivity_back_circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //结束当前activity
                showDynamicImageActivity.this.finish();
            }
        });

        //点击下载按钮事件
        showDynamicImageActivity_download_circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("ImagePath", Environment.getExternalStorageDirectory().getPath()+"/PAGE UP");
                //图片下载类
                SDFileHelper helper=new SDFileHelper(showDynamicImageActivity.this);
                helper.savePicture(System.currentTimeMillis()+".jpg",dynamic.getDynaPictures().get(currentIndex));
            }
        });

        new getImageCacheAsyncTask().execute(dynamic.getDynaPictures());




    }

    public class SDFileHelper{
        private Context context;

        public SDFileHelper(){}
        public SDFileHelper(Context context){
            super();
            this.context=context;
        }

        //Glide保存图片
        public void savePicture(final String fileName,String url){
            Glide.with(context).load(url).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
                @Override
                public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                    try{
                        saveFileToSD(fileName,bytes);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });
        }

        //往SD卡写入文件的方法
        public void saveFileToSD(String filename,byte[] bytes) throws Exception{
            //如果手机已插入SD卡，并且具有读写SD卡的权限
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                //获取图片缓存目录
                String filePath=Environment.getExternalStorageDirectory().getPath()+"/PAGE UP";
                File dir=new File(filePath);
                //如果该目录不存在则创建
                if(!dir.exists()){
                    dir.mkdirs();
                }
                //指定图片的路径
                filename=filePath+"/"+filename;
                FileOutputStream output=new FileOutputStream(filename);
                output.write(bytes);
                output.close();
                Toast.makeText(context,"图片已成功保存到"+filePath,Toast.LENGTH_SHORT).show();
                insertImageToSystemGallery(context,filename);
            }else{
                Toast.makeText(context,"SD卡不存在或没有读取权限",Toast.LENGTH_SHORT).show();
            }
        }




    }

    //发送广播到系统图库通知更新
    public static void insertImageToSystemGallery(Context context,String filePath){
        Intent intent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri=Uri.fromFile(new File(filePath));
        intent.setData(uri);
        context.sendBroadcast(intent);
    }

}
