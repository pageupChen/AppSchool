package com.app.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Looper;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.pojo.User;
import com.example.appproject.R;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends Activity {
    private EditText register_account_editText;
    private ImageView register_account_delete;
    private EditText register_pass_editText;
    private ImageView register_pass_delete;
    private ImageView register_pass_visible;

    private Spinner register_school_spinner;
    private Spinner register_major_spinner;
    //private Spinner register_major_spinner;
    private Spinner register_time_spinner;

    private RadioGroup rg;
    private RadioButton status_student;
    private RadioButton status_commander;

    private Button register_Btn;

    private User user=new User();

    private String[] school_spinner={"北京大学","清华大学","复旦大学","浙江大学","上海交通大学","南京大学",
            "中山大学","武汉大学","暨南大学","吉林大学","华中科技大学","四川大学","中国人民大学","山东大学","南开大学",
            "中南大学","北京师范大学","哈尔滨工业大学","西安交通大学","厦门大学","同济大学","东南大学","天津大学","北京航空航天大学",
            "大连理工大学","华东师范大学","中国农业大学","华南理工大学","兰州大学","西北工业大学","湖南大学","天津理工大学","重庆大学",
            "东北大学","华东理工大学","北京科技大学","东北师范大学","华中师范大学","西北大学","中国矿业大学","华中农业大学","东华大学",
            "西南大学","中国海洋大学","南京航空航天大学","电子科技大学","广东工业大学","长安大学","武汉理工大学"};
    private String[] type_spinner={"土木工程系","机械工程系","水利工程系","电力工程系","经济管理系","计算机信息工程系","建筑与环境工程系","自动化工程系","市政工程系","应用外语系"};
    //private String[] major_spinner={""};
    private String[] time_spinner={"2017年","2016年","2015年","2014年","2013年","2012年","2011年","2010年","2009年","2008年","2007年","2006年","2005年","2004年","2003年","2002年","2001年",};

    private AdapterView.OnItemSelectedListener school_myItemSelected=new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            user.setSchool(school_spinner[position]);
            Log.d("user",user.getSchool());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private AdapterView.OnItemSelectedListener type_myItemSelected=new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            user.setMajor(type_spinner[position]);
            Log.d("user",user.getMajor());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private AdapterView.OnItemSelectedListener time_myItemSelected=new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            user.setTime(time_spinner[position]);
            Log.d("user",user.getTime());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        iniView();

        register_school_spinner.setAdapter(new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.simple_list_item_1,school_spinner));
        register_major_spinner.setAdapter(new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.simple_list_item_1,type_spinner));
        register_time_spinner.setAdapter(new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.simple_list_item_1,time_spinner));
    }

    private void iniView(){
        register_account_editText=(EditText) this.findViewById(R.id.register_account_editText);
        register_account_delete=(ImageView) this.findViewById(R.id.register_account_delete);
        register_pass_editText=(EditText) this.findViewById(R.id.register_pass_editText);
        register_pass_delete=(ImageView) this.findViewById(R.id.register_pass_delete);
        register_pass_visible=(ImageView) this.findViewById(R.id.register_pass_visible);
        rg=(RadioGroup) this.findViewById(R.id.rg);
        status_student=(RadioButton)this.findViewById(R.id.status_student);
        status_commander=(RadioButton)this.findViewById(R.id.status_commander);
        register_Btn=(Button) this.findViewById(R.id.register_Btn);

        register_school_spinner=(Spinner) this.findViewById(R.id.register_school_spinner);
        register_major_spinner=(Spinner) this.findViewById(R.id.register_major_spinner);
        register_time_spinner=(Spinner) this.findViewById(R.id.register_time_spinner);

        register_school_spinner.setOnItemSelectedListener(school_myItemSelected);
        register_major_spinner.setOnItemSelectedListener(type_myItemSelected);
        register_time_spinner.setOnItemSelectedListener(time_myItemSelected);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.status_student:
                        user.setStatus("学生");
                        break;
                    case R.id.status_commander:
                        user.setStatus("团长");
                }
            }
        });

        register_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRegister();

            }
        });
    }

    private void sendRegister(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    user.setPhone(register_account_editText.getText().toString());
                    user.setPassword(register_pass_editText.getText().toString());

                    JSONObject registermessage=new JSONObject();
                    registermessage.put("phone",user.getPhone());
                    registermessage.put("password",user.getPassword());
                    registermessage.put("school",user.getSchool());
                    registermessage.put("major",user.getMajor());
                    registermessage.put("time",user.getTime());
                    registermessage.put("status",user.getStatus());
                    if(user.getStatus().equals("学生")){
                        studentRegister(registermessage);
                    }else{
                        Intent clubIntent=new Intent(RegisterActivity.this,ClubRegisterActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("user",user);
                        clubIntent.putExtras(bundle);
                        startActivity(clubIntent);
                    }
                }catch (Exception e){
                    e.getMessage();
                }

            }
        }).start();
    }

    private void studentRegister(JSONObject registermessage){
        try{
            OkHttpClient client=new OkHttpClient();
            RequestBody requestBody=new FormBody.Builder()
                    .add("registermessage",registermessage.toString())
                    .build();
            Log.d("send","true2");
            Request request=new Request.Builder()
                    .url("http://192.168.1.101:8080/appWeb/register")
                    .post(requestBody)
                    .build();
            Response response=client.newCall(request).execute();
            String responseJSON=response.body().string();

            JSONObject flag=new JSONObject(responseJSON);

            Log.d("flag",flag.getString("isReturn"));
            if(flag.getString("isReturn").equals("true")){
                Looper.prepare();
                Toast.makeText(RegisterActivity.this,"注册成功,请登录",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("user",user);
                intent.putExtras(bundle);
                startActivity(intent);
                Looper.loop();
            }else{
                Log.d("LoginError","true");
                //Toast.makeText(LoginActivity.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
                Looper.prepare();
                AlertDialog.Builder LoginError=new AlertDialog.Builder(RegisterActivity.this);
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
}
