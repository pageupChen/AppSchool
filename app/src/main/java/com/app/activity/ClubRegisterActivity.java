package com.app.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.pojo.User;
import com.example.appproject.R;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ClubRegisterActivity extends Activity{
    private EditText club_name;
    private EditText club_commander;
    private EditText club_time;
    private EditText club_sum;
    private Button club_register_Btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_register);

        iniView();
    }

    private void iniView(){
        club_name=(EditText) this.findViewById(R.id.club_name);
        club_commander=(EditText) this.findViewById(R.id.club_commander);
        club_time=(EditText) this.findViewById(R.id.club_time);
        club_sum=(EditText) this.findViewById(R.id.club_sum);

        club_register_Btn=(Button) this.findViewById(R.id.club_register_Btn);
        club_register_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });
    }

    private void sendRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{

                    JSONObject clubJSON=new JSONObject();
                    clubJSON.put("name",club_name.getText().toString());
                    clubJSON.put("commander",club_commander.getText().toString());
                    clubJSON.put("time",club_time.getText().toString());
                    clubJSON.put("sum",club_sum.getText().toString());

//                    usersJSON.put("");
//                    clubJSON.put("users",);


                    User user=(User)ClubRegisterActivity.this.getIntent().getSerializableExtra("user");
                    JSONObject userJSON=new JSONObject();
                    userJSON.put("phone",user.getPhone());
                    userJSON.put("password",user.getPassword());
                    userJSON.put("school",user.getSchool());
                    userJSON.put("major",user.getMajor());
                    userJSON.put("time",user.getTime());
                    userJSON.put("status",user.getStatus());

                    OkHttpClient client=new OkHttpClient();

                    RequestBody requestBody=new FormBody.Builder()
                            .add("usermessage",userJSON.toString())
                            .add("clubmessage",clubJSON.toString())
                            .build();
                    Log.d("click","1");
                    Request request=new Request.Builder()
                            .url("http://192.168.1.101:8080/appWeb/clubregister")
                            .post(requestBody)
                            .build();
                    Response response=client.newCall(request).execute();
                    String returnMessage=response.body().string();

                    JSONObject flag=new JSONObject(returnMessage);

                    Log.d("flag",flag.getString("isReturn"));
                    if(flag.getString("isReturn").equals("true")){
                        Looper.prepare();
                        Toast.makeText(ClubRegisterActivity.this,"注册成功,请登录",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(ClubRegisterActivity.this,LoginActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("user",user);
                        startActivity(intent);
                        Looper.loop();
                    }else{
                        Log.d("LoginError","true");
                        //Toast.makeText(LoginActivity.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
                        Looper.prepare();
                        AlertDialog.Builder LoginError=new AlertDialog.Builder(ClubRegisterActivity.this);
                        LoginError.setTitle("错误提示");
                        LoginError.setMessage("注册失败");
                        LoginError.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        LoginError.show();
                        Looper.loop();
                    }

                }catch (Exception e){
                    e.getMessage();
                }

            }
        }).start();
    }
}
