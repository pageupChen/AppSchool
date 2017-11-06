package com.app.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.pojo.User;
import com.example.appproject.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


//@android:drawable/ic_dialog_alert
public class LoginActivity extends Activity {
    private EditText account_editText;
    private ImageView account_delete;
    private EditText pass_editText;
    private ImageView pass_delete;
    private ImageView pass_visible;
    private boolean isVisible=false;
    private Button login_Btn;
    private TextView register_textView;
    private TextView forgetPass_textView;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private View.OnClickListener myClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.account_delete:
                    if(!TextUtils.isEmpty(account_editText.getText())){
                        account_editText.setText("");
                        account_delete.setVisibility(View.INVISIBLE);
                    }
                    break;
                case R.id.pass_delete:
                    if(!TextUtils.isEmpty(pass_editText.getText())){
                        pass_editText.setText("");
                        pass_delete.setVisibility(View.INVISIBLE);
                    }
                    break;
                case R.id.pass_visible:
                    if(isVisible==false){
                        Log.d("ifs","if");
                        pass_editText.setInputType(InputType.TYPE_CLASS_TEXT);
                        pass_visible.setImageResource(R.mipmap.invisible);       //
                        isVisible=true;
                    }else{
                        Log.d("else","else");
                        Log.d("type",InputType.TYPE_TEXT_VARIATION_PASSWORD+"");
                        pass_editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        pass_visible.setImageResource(R.mipmap.visible);      //眼
                        isVisible=false;
                    }
                    Log.d("isVisible",isVisible+"");
                    break;
                case R.id.register_textView:
                    Intent intentToRegister=new Intent(LoginActivity.this,RegisterActivity.class);
                    startActivity(intentToRegister);
                    break;
                case R.id.login_Btn:
                    sendRequest();
                    //Toast.makeText(LoginActivity.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    } ;

    private TextWatcher myTextWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //Log.d("type",pass_editText.getInputType()+"");
            if(!TextUtils.isEmpty(account_editText.getText())){
                account_delete.setVisibility(View.VISIBLE);
            }else{
                account_delete.setVisibility(View.INVISIBLE);
            }
            if(!TextUtils.isEmpty(pass_editText.getText())){
                pass_delete.setVisibility(View.VISIBLE);
            }else{

                pass_delete.setVisibility(View.INVISIBLE);
            }
            if(TextUtils.isEmpty(pass_editText.getText())){
                pass_editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                pass_visible.setImageResource(R.mipmap.visible);
                //isVisible=false;
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d("loginActivity","loginActivity");

        initView();

    }

    private void initView(){
        preferences=this.getSharedPreferences("Initialize",MODE_PRIVATE);
        editor=preferences.edit();

        account_editText=(EditText)this.findViewById(R.id.account_editText);
        account_delete=(ImageView) this.findViewById(R.id.account_delete);
        pass_editText=(EditText) this.findViewById(R.id.pass_editText);
        pass_delete=(ImageView) this.findViewById(R.id.pass_delete);
        pass_visible=(ImageView) this.findViewById(R.id.pass_visible);
        login_Btn=(Button) this.findViewById(R.id.login_Btn);
        register_textView=(TextView) this.findViewById(R.id.register_textView);
        forgetPass_textView=(TextView) this.findViewById(R.id.forgetPass_textView);

        account_editText.addTextChangedListener(myTextWatcher);
        account_delete.setOnClickListener(myClickListener);
        pass_editText.addTextChangedListener(myTextWatcher);
        pass_delete.setOnClickListener(myClickListener);
        pass_visible.setOnClickListener(myClickListener);
        login_Btn.setOnClickListener(myClickListener);
        register_textView.setOnClickListener(myClickListener);
        forgetPass_textView.setOnClickListener(myClickListener);
    }

    private void sendRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Log.d("sffs","true");
                    JSONObject loginUser=new JSONObject();
                    loginUser.put("phone",account_editText.getText().toString());
                    loginUser.put("password",pass_editText.getText().toString());
                    /**
                     * 连接网络后台所需参数:
                     * 1.url
                     * 2.请求参数      1和2都由request封装
                     * 3.提交方式
                     * */
                    OkHttpClient client=new OkHttpClient();
                    RequestBody requestBody=new FormBody.Builder()
                            .add("loginmessage",loginUser.toString())
                            .build();
                    Log.d("send","true2");
                    Request request=new Request.Builder()
                            .url("http://192.168.1.101:8080/appWeb/login")
                            .post(requestBody)
                            .build();
                    Response response=client.newCall(request).execute();

                    String responseJSON=response.body().string();
                    Log.d("send","true3");
                    Log.d("responseJSON",responseJSON);
//                    Gson returnJSON=new Gson();
//                    User user=returnJSON.fromJson(responseJSON,User.class);
                    JSONObject flag=new JSONObject(responseJSON);
                    //for(int i=0;i<returnJSON.length();i++){
                   //     JSONObject flag=returnJSON.getJSONObject(0);
                    Log.d("logflags",flag.getString("isReturn"));

                    Log.d("logflag",flag.getString("isReturn"));
                    Log.d("statusfrom",flag.getString("status"));
                        if(flag.getString("isReturn").equals("true")){
                            Log.d("ffff","ffff");
                            //Log.d("logflag",flag.getString("isReturn"));
                            editor.putBoolean("isLogin",true);
                            User user=(User)LoginActivity.this.getIntent().getSerializableExtra("user");

//                            Log.d("userstatus",user.getStatus());
                            Log.d("sdsd","111");


                            if(user!=null){
                                Log.d("sdsdsddd","111");
                                if(user.getStatus().equals("学生")){
                                    editor.putString("status","学生");
                                }else{
                                    editor.putString("status","团长");
                                }
                            }else{
                                if(!TextUtils.isEmpty(flag.getString("status"))){
                                    Log.d("sdsdsd","111");
                                    if(flag.getString("status").equals("学生")){
                                        editor.putString("status","学生");
                                    }else{
                                        editor.putString("status","团长");
                                    }
                                }
                            }


                            editor.commit();
                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else{
                            Log.d("LoginError","true");
                            //Toast.makeText(LoginActivity.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
                            Looper.prepare();
                            AlertDialog.Builder LoginError=new AlertDialog.Builder(LoginActivity.this);
                            LoginError.setTitle("错误提示");
                            LoginError.setMessage("账号或密码错误");
                            LoginError.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            LoginError.show();
                            Looper.loop();
                        }
                    //}
                }catch (Exception e){
                    e.getMessage();
                }
            }
        }).start();
    }

}
