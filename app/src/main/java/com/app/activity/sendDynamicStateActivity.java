package com.app.activity;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.internal.http.multipart.MultipartEntity;
import com.bumptech.glide.Glide;
import com.example.appproject.R;
import com.foamtrace.photopicker.ImageCaptureManager;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.PhotoPreviewActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.foamtrace.photopicker.intent.PhotoPreviewIntent;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.http.body.MultipartBody;
import org.xutils.x;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.jar.Manifest;

import id.zelory.compressor.Compressor;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class sendDynamicStateActivity extends AppCompatActivity {
    private int columnWidth;
    private ImageCaptureManager captureManager; // 相机拍照处理类
    private static final int REQUEST_CAMERA_CODE = 11;
    private final static int SEND_IMAGE_PATH=3;
    private EditText text_Dynamic;
    private ImageView sendDynamicState_wait_imageView;
//    private ImageView image_Dynamic;
    private ImageView add_image_Dynamic;
    private ImageView remove_image_Dynamic;
    private Button sendDynamic_Btn;
    private LinearLayout image_linearLayout;
//    private LinearLayout content_linearLayout;
    private LinearLayout image_linearLayout1;
    private LinearLayout image_linearLayout2;
    private LinearLayout image_linearLayout3;
    private LinearLayout add_linearLayout;
    private ArrayList<String> all_image_path=new ArrayList<>();
    private ArrayList<String> back_selectedImage=new ArrayList<>();
    private List<File> CompressorHandlerImage=new ArrayList<>();
//    private int selected_sum=0;
    private GridView dynamic_state_gridView;

    private Uri imageUri;

    private String imagePath=null;

    private String camera;
    private String return_imageType;

    private File outputImage;

    private ArrayList<Integer> delete_image_position=new ArrayList<>();

//    private ArrayList<String> image_Type=new ArrayList<>();

    private Map<String,String> image_Type=new HashMap<>();

    private ArrayList<String> image_Type_List=new ArrayList<>();

    private HashMap<Integer,Integer> position_match_index=new HashMap<>();
    private Toolbar toolBar;

    //选择照片后返回显示的adater
    private BaseAdapter baseAdapter=new BaseAdapter() {
        @Override
        public int getCount() {
            return back_selectedImage.size();
        }

        @Override
        public String getItem(int position) {
            return back_selectedImage.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            convertView =sendDynamicStateActivity.this.getLayoutInflater().inflate(R.layout.selected_image_gridview, null);
            //实例化checkBox
            CheckBox image_remove=(CheckBox) convertView.findViewById(R.id.image_remove);
            //实例化imageView
            ImageView imageView = (ImageView) convertView.findViewById(R.id.select_image);
            //框架里自带glide
            Glide.with(sendDynamicStateActivity.this)
                    .load(new File(getItem(position)))
                    .placeholder(R.mipmap.default_error)
                    .error(R.mipmap.default_error)
                    .centerCrop()
                    .crossFade()
                    .into(imageView);


            //checkBox状态改变事件
            image_remove.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        delete_image_position.add(position);
                        Log.d("select_position",position+"");
                        position_match_index.put(position,delete_image_position.size()-1);
                        Log.d("position_match_index",position_match_index.get(position)+"");
//                        Log.d("delete_image_position",d'/elete_image_position.get(position)+"");
                    }else{
                        Log.d("position_remove",position_match_index.get(position)+"");
                        delete_image_position.remove( (int)position_match_index.get(position));
                        for(int index:position_match_index.keySet()){
                            if(index==position||position_match_index.get(index)<position_match_index.get(position)){

                            }else{
                                position_match_index.put(index,position_match_index.get(index)-1);
                            }
                        }
                        position_match_index.remove(position);

                        for(int j:position_match_index.keySet()){
                            Log.d("admain_position_match",position_match_index.get(j)+"");
                        }

//                        for(int i:delete_image_position){
                            Log.d("delete_index",delete_image_position.size()+"");
//                        }

                    }
                }
            });


            return convertView;
        }
    };


    private View.OnClickListener myListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.text_Dynamic:
                    text_Dynamic.setFocusableInTouchMode(true);
                    break;
                case R.id.add_image_Dynamic:
                    if(back_selectedImage.size()>=9){
                        Toast.makeText(sendDynamicStateActivity.this,"图片已到达上限,亲",Toast.LENGTH_SHORT).show();
                    }else{
                        selectImageFromWhere();
                    }

                    break;
                case R.id.remove_image_Dynamic:
//                    Log.d("back_size()",back_selectedImage.size()+"");
//                    for (Integer i:delete_image_position){
//                        Log.d("delete_image_position",i+"");
//                    }
//                    Collections.sort(delete_image_position);

                    if(back_selectedImage.size()==0||delete_image_position.size()==0){
                        Toast.makeText(sendDynamicStateActivity.this,"您还没有选择图片",Toast.LENGTH_SHORT).show();
                    }else{
                        for (int i = 0; i < delete_image_position.size() - 1; i++) {
                            for (int j = 1; j < delete_image_position.size() - i; j++) {
                                Integer a;
                                if ((delete_image_position.get(j - 1)).compareTo(delete_image_position.get(j)) < 0) {
                                    a = delete_image_position.get(j - 1);
                                    delete_image_position.set((j - 1), delete_image_position.get(j));
                                    delete_image_position.set(j, a);
                                }
                            }
                        }
                        for(int i=0;i<delete_image_position.size();i++){
                            Log.d("delete_image_sort",delete_image_position.get(i)+"");
                        }
                        Log.d("delete_image","1");
                        for(int i:delete_image_position){
                            back_selectedImage.remove(i);

                            Log.d("mmp","2");
                        }
                        Log.d("back_size()",back_selectedImage.size()+"");
                        baseAdapter.notifyDataSetChanged();
                        Log.d("delete_image","3");
                        delete_image_position.clear();
                    }
                    break;
            }
        }
    };

    //Compressor压缩图片方法
    private void CompressorHandler(){
        Log.d("back_select",back_selectedImage.size()+"");
        //遍历选好的图片集合，逐个压缩
        for(int i=0;i<back_selectedImage.size();i++){
            Log.d("back_selected",back_selectedImage.get(i));
            File file=new File(back_selectedImage.get(i));
            File compressorFile=Compressor.getDefault(sendDynamicStateActivity.this).compressToFile(file);
            Log.d("Compressor",compressorFile.getName());
            CompressorHandlerImage.add(compressorFile);
            Log.d("CompressorImage",CompressorHandlerImage.size()+"");
//            Log.d("getName",file.getName());
//            file.renameTo(new File(back_selectedImage.get(i).substring(0,(int)file.getParentFile().length())));
//            Log.d("absolutePath",file.getAbsolutePath());
//            Log.d("parentFile",file.getParentFile()+"");

//            file.renameTo(new File(getExternalCacheDir(),System.currentTimeMillis()+".jpg"));
//            File file1=new File()
//            File file1=new File()
//            file.
            //压缩图片
//            String fileName=back_selectedImage.get(i);
//            Luban.get(sendDynamicStateActivity.this)
//
//                    .load(file)
//                    .putGear(Luban.THIRD_GEAR)
//                    .setCompressListener(new OnCompressListener() {
//                        @Override
//                        public void onStart() {
////                            Toast.makeText(sendDynamicStateActivity.this, "I'm start", Toast.LENGTH_SHORT).show();
////                            Log.d("filePre",file.length()+"");
//                        }
//
//                        @Override
//                        public void onSuccess(File file) {
//                            Log.d("filePre", file.length()+"");
////                                            Glide.with(sendDynamicStateActivity.this).load(file).into(image);
////                                            thumbFileSize.setText(file.length()/ 1024 + "k");
////                                            thumbImageSize.setText(computeSize(file)[0] + "*" + computeSize(file)[1]);
////                            file.renameTo(new File(file.getParent(),System.currentTimeMillis()+".jpg"));
////                            Log.d("reName",file.getName());
//                            LubanHandlerImage.add(file);
//                            Log.d("absolutePath",file.getAbsolutePath());
//                            Log.d("parentFile",file.getParent()+"");
//                            Log.d("getName",file.getName());
//
////                            Log.d("LubanHandlerImage",LubanHandlerImage.size()+"");
//                            if(LubanHandlerImage.size()==back_selectedImage.size()){
//                                sendDynamic();
//                            }
//
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//
//                        }
//                    }).launch();
//            if(LubanHandlerImage.size()==back_selectedImage.size()){
//                sendDynamic();
//            }

        }

        sendDynamic();
    }



    //多图片上传服务器，使用XUtils框架发送图片
    private void sendDynamic(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    String url="http://192.168.1.101:8080/schoolapp/sendDynamic.action";
                    RequestParams params=new RequestParams(url);
                    //以表单的形式
                    params.setMultipart(true);
                    List<KeyValue> list = new ArrayList<KeyValue>();
                    //遍历压缩后的图片集合，并添加到图片框架的参数中
                    for(int i=0;i<CompressorHandlerImage.size();i++){
                        Log.d("sendParams",true+"");
                        list.add(new KeyValue("file",CompressorHandlerImage.get(i)));
                    }
                    list.add(new KeyValue("dyna_content",text_Dynamic.getText().toString()));
                    list.add(new KeyValue("stuId",1+""));
                    //因为要以社团名义发布动态，所以把社团id写进数据库
                    list.add(new KeyValue("dyna_mass_id",1+""));
                    //动态的发送时间
                    Date date=new Date();
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    list.add(new KeyValue("dyna_send_time",simpleDateFormat.format(date)));

                    MultipartBody body=new MultipartBody(list,"UTF-8");
                    params.setRequestBody(body);

                    /**
                     * 以post的方式提交
                     * @params 提交的参数
                     * @Callback 提交动作的回调，实时监听提交事件
                     * */
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        //提交成功的回调方法
                        @Override
                        public void onSuccess(String result) {
                            //提交成功后跳返回到主界面
                            Intent intent=new Intent(sendDynamicStateActivity.this,MainActivity.class);
                            setResult(RESULT_OK,intent);
                            sendDynamicStateActivity.this.finish();
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            Log.d("onSuccess","error");
                        }

                        /**
                         * onCancelled由程序员手动调用，x.http().get()方法返回Cancelable类型的对象，手动调用cancelable.cancel()方法会默认调用onCanceled()
                         * */
                        @Override
                        public void onCancelled(CancelledException cex) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    });

//                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                        conn.setReadTimeout(TIME_OUT);
//                        conn.setConnectTimeout(TIME_OUT);
//                        conn.setDoInput(true);  //允许输入流
//                        conn.setDoOutput(true); //允许输出流
//                        conn.setUseCaches(false);  //不允许使用缓存
//                        conn.setRequestMethod("POST");  //请求方式
//                        conn.setRequestProperty("Charset", CHARSET);  //设置编码
//                        conn.setRequestProperty("connection", "keep-alive");
//                        conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
//                        conn.connect();
////                        Log.d("sendDynamic",i+"");
//                        File file=LubanHandlerImage.get(i);
//                        if(file!=null){
//                            /**
//                             * 当文件不为空，把文件包装并且上传
//                             */
//
//                            Log.d("isWrite","fileNoNull");
//                            DataOutputStream dos = new DataOutputStream( conn.getOutputStream());
//                            StringBuffer sb = new StringBuffer();
//                            sb.append(PREFIX);
//                            sb.append(BOUNDARY);
//                            sb.append(LINE_END);
//                            /**
//                             * 这里重点注意：
//                             * name里面的值为服务器端需要key   只有这个key 才可以得到对应的文件
//                             * filename是文件的名字，包含后缀名的   比如:abc.png
//                             */
//
//                            sb.append("Content-Disposition: form-data; name=\"img\"; filename=\""+file.getName()+"\""+LINE_END);
//                            sb.append("Content-Type: application/octet-stream; charset="+CHARSET+LINE_END);
//                            sb.append(LINE_END);
//                            dos.write(sb.toString().getBytes());
//                            InputStream is = new FileInputStream(file);
//                            byte[] bytes = new byte[1024];
//                            int len = 0;
//                            while((len=is.read(bytes))!=-1){
//                                dos.write(bytes, 0, len);
//                            }
//                            is.close();
//                            dos.write(LINE_END.getBytes());
//                            Log.d("isWrite","true");
//                            byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
//                            dos.write(end_data);
//                            dos.flush();
//                            /**
//                             * 获取响应码  200=成功
//                             * 当响应成功，获取响应的流
//                             */
//                            int res = conn.getResponseCode();
//                            if(res==200){
//                                Log.d("ResponseCode()","dfs");
//                            }
//
//                        }
//                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
        }

        }).start();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(data!=null){
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri contentUri = Uri.fromFile(outputImage);
                    mediaScanIntent.setData(contentUri);
                    sendBroadcast(mediaScanIntent);
                    back_selectedImage.add(outputImage.getAbsolutePath());
                    baseAdapter.notifyDataSetChanged();
                    break;
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    loadAdpater(data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT));
                    break;
                //浏览照片
                case 22:
                    loadAdpater(data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT));
                    break;

            }

        }


    }

//    private void handleImageBeforeKitKat(Intent data){
//        Uri uri=data.getData();
//        imagePath=getImagePath(uri,null);
//        displayImage(imagePath);
//    }

    /**
     * 打开相册选取一张图片
     * 相册将返回图片的URI(根据android版本不同，有些返回真实的路径URI，而有些是经过包装的URI
     * 但无论哪种都会包含图片的id值，然后根据id值去查数据库的DATA列，即路径字符串)
     * */
//    @TargetApi(19)
//    private void handleImageOnKitKat(Intent data){
////        String imagePath=null;
//        Uri uri=data.getData();
////        String[] filePathColumn = { MediaStore.Images.Media.DATA };
////        Log.d("filePathColumn",filePathColumn[0]);
////        Log.d("filePathColumn",filePathColumn[1]);
////        Log.d("filePathColumn",filePathColumn[2]);
//        try{
//            if(DocumentsContract.isDocumentUri(this,uri)){
//                String docId=DocumentsContract.getDocumentId(uri);
//                if("com.android.providers.media.documents".equals(uri.getAuthority())){
//                    String id=docId.split(":")[1];
//                    String selection=MediaStore.Images.Media._ID+"="+id;
//                    Log.d("mdia",selection);
//                    imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
//                    Log.d("EXTERNAL_CONTENT_URI",MediaStore.Images.Media.EXTERNAL_CONTENT_URI+"");
//                }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
//                    Uri contentUri= ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
//                    imagePath=getImagePath(contentUri,null);
//                }
//            }else if(uri.getScheme().equals("content")){
//                Log.d("uri.getScheme()",uri.getScheme());
//                imagePath=getImagePath(uri,null);
//            }else if("file".equalsIgnoreCase(uri.getScheme())){
//                imagePath=uri.getPath();
//            }
//            Log.d("ImagePath2",imagePath);
//            displayImage(imagePath);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }

//    private String getImagePath(Uri uri,String selection){
////        String path=null;
//        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
//        if(cursor!=null){
//            Log.d("cursor",cursor+"");
//            if(cursor.moveToFirst()){
//                imagePath=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//
//                Log.d("rrrrr",cursor.getLong(0)+" "+cursor.getColumnName(0)+" "+cursor.getColumnName(1)+" "+cursor.getColumnName(2));
//            }
//            cursor.close();
//        }
//
//        return imagePath;
//    }

    private void displayImage(final ArrayList<String> back_Images){
//        back_selectedImage.add(back_selectedImage);
//        back_selectedImage.removeAll();
////        for(int i=0;i<back_Images.size();i++){
////            back_selectedImage.add(back_Images.get(i));
////        }
        //back_selectedImage.clear();
        for(String s:back_Images){
            back_selectedImage.add(s);
        }


        baseAdapter.notifyDataSetChanged();


//        dynamic_state_gridView.setAdapter(null);

    }


    private void selectImageFromWhere(){
        AlertDialog.Builder dialog=new AlertDialog.Builder(sendDynamicStateActivity.this);
        dialog.setItems(new String[]{"拍摄一张", "从相册选择"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("Items",which+"");
                switch (which){
                    case 0:
                        //拍照并返回照片
                        selectImageFromCamera();
                        break;
                    case 1:
                        //从相册返回图片
                        selectImageFromAlbum();
                        break;
                }
            }
        });
        dialog.show();

    }

    private void sendImagePathToActivity(){
//        for(String s:all_image_path){
//            String[] str=s.split("/");
////            Log.d("str",str[str.length-2]);
//            image_Type.add(str[str.length-2]);
//        }

//        image_Type.


        Intent intent=new Intent(sendDynamicStateActivity.this,showImageActivity.class);
        Bundle bundle=new Bundle();
        bundle.putStringArrayList("imagePaths",all_image_path);
        bundle.putStringArrayList("image_Type_List",image_Type_List);
        bundle.putInt("selected_sum",back_selectedImage.size());
        intent.putExtras(bundle);
        startActivityForResult(intent,SEND_IMAGE_PATH);
    }

    //打开相机拍照并返回照片
    private void selectImageFromCamera(){
        /**
         * Environmetn.getEexternalStorageDirectory()获取外部存储卡的目录
         * getAbsolutePath()获取目录下的绝对路径
         * */
        //在该应用的默认存储下新建一个jpg文件，以系统当前时间命名
        outputImage=new File(getExternalCacheDir(),System.currentTimeMillis()+".jpg");

        try{
            if(outputImage.exists()){
                //如果该文件存在，则删掉
                outputImage.delete();
            }
            //否则，新建
            outputImage.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        Log.d("mycamera","is");
        /**
         * 进行系统版本的判断:
         * 将File对象转化为Uri对象
         * */
        //如果SDK版本大于24
        if(Build.VERSION.SDK_INT>=24){
            Log.d("Build>=24","true");
            imageUri= FileProvider.getUriForFile(sendDynamicStateActivity.this,"com.app.activity.fileprovider",outputImage);
        }else{
            imageUri=Uri.fromFile(outputImage);
        }


        //打开系统照相机程序
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,1);
    }

    //检查是否有读写内存的权限
    private void selectImageFromAlbum(){
        //没有，向用户提出申请
        if(ContextCompat.checkSelfPermission(sendDynamicStateActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(sendDynamicStateActivity.this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else{
            //若有，执行打开相册的方法
            openAlbum();
        }
    }

    //设置图片数据源，并更新adapter
    private void loadAdpater(ArrayList<String> paths){
        if( back_selectedImage== null){
            back_selectedImage = new ArrayList<>();
        }
        back_selectedImage.clear();
        back_selectedImage.addAll(paths);
        if(baseAdapter == null){
//            baseAdapter = new GridAdapter(back_selectedImage);
            dynamic_state_gridView.setAdapter(baseAdapter);
        }else {
            baseAdapter.notifyDataSetChanged();
        }
    }

    //打开相册选取图片
    private void openAlbum(){

        PhotoPickerIntent intent1 = new PhotoPickerIntent(sendDynamicStateActivity.this);
        intent1.setSelectModel(SelectModel.MULTI);
//        intent1.setShowCarema(true); // 是否显示拍照
        intent1.setMaxTotal(9); // 最多选择照片数量，默认为9
        intent1.setSelectedPaths(back_selectedImage); // 已选中的照片地址， 用于回显选中状态
        startActivityForResult(intent1, REQUEST_CAMERA_CODE);

    }

    //权限申请回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //若该条权限申请成功，执行打开相册的方法
                    openAlbum();
                }else{
                    //否则，给出提示，权限获取失败
                    Toast.makeText(sendDynamicStateActivity.this,"权限获取失败",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_dynamic_state);

        //调出输入法之后不影响原有activity的布局
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        iniView();
    }

    private void iniView(){
        //实例化ToolBar
        toolBar= (Toolbar) this.findViewById(R.id.sendDynamicState_toolbar);
        setSupportActionBar(toolBar);
        //设置返回键可用
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //设置返回键的点击事件
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(sendDynamicStateActivity.this);
                dialog.setTitle("是否退出编辑?");
                dialog.setPositiveButton("退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                sendDynamicStateActivity.this.finish();
                            }
                        });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();

            }
        });

        sendDynamicState_wait_imageView=(ImageView) this.findViewById(R.id.sendDynamicState_wait_imageView);
        text_Dynamic=(EditText) this.findViewById(R.id.text_Dynamic);
//        image_Dynamic=(ImageView)this.findViewById(R.id.image_Dynamic);
        add_image_Dynamic=(ImageView)this.findViewById(R.id.add_image_Dynamic);
        remove_image_Dynamic=(ImageView) this.findViewById(R.id.remove_image_Dynamic);
//        sendDynamic_Btn=(Button)this.findViewById(R.id.sendDynamic_Btn);
        image_linearLayout=(LinearLayout) this.findViewById(R.id.image_linearLayout);
//        content_linearLayout=(LinearLayout) this.findViewById(R.id.content_linearLayout);

        dynamic_state_gridView=(GridView) this.findViewById(R.id.dynamic_state_gridView);

        image_linearLayout1=new LinearLayout(sendDynamicStateActivity.this);
        image_linearLayout2=new LinearLayout(sendDynamicStateActivity.this);
        image_linearLayout3=new LinearLayout(sendDynamicStateActivity.this);
        image_linearLayout.addView(image_linearLayout1);
        image_linearLayout.addView(image_linearLayout2);
        image_linearLayout.addView(image_linearLayout3);

        dynamic_state_gridView.setAdapter(baseAdapter);
        //GridView item点击事件（浏览照片）
        dynamic_state_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhotoPreviewIntent intent = new PhotoPreviewIntent(sendDynamicStateActivity.this);
                intent.setCurrentItem(position);
                intent.setPhotoPaths(back_selectedImage);
                startActivityForResult(intent, 22);
            }
        });

        add_linearLayout=(LinearLayout) this.findViewById(R.id.add_linearLayout);

//        text_Dynamic.setFocusable(false);
//        text_Dynamic.setOnClickListener(myListener);
//        image_Dynamic.setOnClickListener(myListener);
        text_Dynamic.setOnClickListener(myListener);
        add_image_Dynamic.setOnClickListener(myListener);
        remove_image_Dynamic.setOnClickListener(myListener);
//        sendDynamic_Btn.setOnClickListener(myListener);


    }

    @Override
    protected void onPause() {
        super.onPause();
        image_Type_List.clear();
        all_image_path.clear();
        delete_image_position.clear();
        position_match_index.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.send_dynamic_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //点击发送时的事件
        if(item.getItemId()==R.id.sendDynamic_Btn){
            //如果动态的内容和图片都为空，则给出提示
            if(TextUtils.isEmpty(text_Dynamic.getText())&&back_selectedImage.size()==0) {
                Toast.makeText(sendDynamicStateActivity.this, "发表内容不能为空", Toast.LENGTH_SHORT).show();
            }else{
                //反之，可以发送，并设置发送为不可点击，防止多次发送，最后调用Compressor方法发送
                item.setEnabled(false);
                sendDynamicState_wait_imageView.setVisibility(View.VISIBLE);
                CompressorHandler();
            }
        }
        return true;
    }
}
