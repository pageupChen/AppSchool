package com.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.appproject.R;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class InitializeActivity extends Activity {
    private Button ad_Btn;
    private ImageView ad_imageView;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private int time=6;
    private Bitmap bitmap;

    private Thread myThread=new Thread(){
        @Override
        public void run() {
            Log.d("isRun","true");
            Message msg=null;
            Bundle bundle=null;
            while((--time)>=0){
                //Log.d("istime",time+"");
                if(time<=0){
                    myThread=null;
                    Intent intent=new Intent(InitializeActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                if(myThread!=null){
                    try{
                        Log.d("istime",time+"");
                        Thread.sleep(1000);
                        msg=new Message();
                        bundle=new Bundle();
                        bundle.putInt("time",time);
                        msg.setData(bundle);

                        //表明Message的身份
                        msg.what=0x123;
                        myHandler.sendMessage(msg);
                    }catch (Exception e){
                        e.getMessage();
                    }
                }
            }
        }
    };

    private Thread adThread=new Thread(){
        @Override
        public void run() {
            super.run();
            try{
//                HttpURLConnection conn=(HttpURLConnection)new URL("http://192.168.1.102:8080/appWeb/adreturn").openConnection();
//                InputStream is=conn.getInputStream();
//                bitmap=BitmapFactory.decodeStream(is);
                OkHttpClient adClient=new OkHttpClient();
                RequestBody requestBody=new FormBody.Builder().build();
                Request request=new Request.Builder()
                        .url("http://192.168.1.101:8080/appWeb/adreturn")
                        .post(requestBody)
                        .build();
                Response response=adClient.newCall(request).execute();
                String returnURL=response.body().string();

                JSONObject URLjson=new JSONObject(returnURL);
                String imageURL=URLjson.getString("returnURL");
                Log.d("imageURL",imageURL);

                URL url=new URL("http://192.168.1.101:8080/schoolapp/dynamicImages/ic_launcher.png");
                InputStream is=url.openStream();
                bitmap=BitmapFactory.decodeStream(is);
                is.close();
                Message msg=new Message();

                //表明Message的身份
                msg.what=0x124;
                myHandler.sendMessage(msg);


            }catch (Exception e){
                e.getMessage();
            }


        }
    };

    private Handler myHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x123:
                    int i=msg.getData().getInt("time");
                    ad_Btn.setText("跳过广告("+i+")");
                    break;
                case 0x124:
                    ad_imageView.setImageBitmap(bitmap);
                    break;
            }
        }
    };

    private View.OnClickListener myListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ad_Btn:
                    myThread=null;
                    Intent intent=new Intent(InitializeActivity.this,MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.ad_imageView:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences=this.getSharedPreferences("Initialize",MODE_PRIVATE);
        if(preferences.getBoolean("isLogin",false)==true){
            //先广告，在MainActivity
            setContentView(R.layout.activity_initialize);
            Log.d("myThread",myThread+"");
            iniVeiw();
            adThread.start();
            myThread.start();

            Log.d("myThread.start()",true+"");


        }else{
            //登录界面
            Log.d("JumpToLogin","true");
            Intent intent=new Intent(InitializeActivity.this,LoginActivity.class);
            startActivity(intent);

        }

    }

    private void iniVeiw(){
        ad_Btn=(Button) this.findViewById(R.id.ad_Btn);
        ad_imageView=(ImageView) this.findViewById(R.id.ad_imageView);

        ad_Btn.setOnClickListener(myListener);
        ad_imageView.setOnClickListener(myListener);
    }



}
