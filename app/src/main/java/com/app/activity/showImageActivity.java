package com.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appproject.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class showImageActivity extends Activity {
    private ImageView back_activity;
    private Spinner image_type_spinner;
    private Button ok_selected;
    private GridView image_gridView;
//    private RecyclerView recyclerView;
    private ArrayList<String> all_image_path=new ArrayList<>();
    private ArrayList<String> back_selectedImage=new ArrayList<>();
    private ArrayList<String> image_Type_List=new ArrayList<>();
    private ArrayList<String> image_Type=new ArrayList<>();
    private int selected_sum=0;
    private int currentItemPosition;

    private final static int SEND_BIG_IMAGE=1;
    private int start_index,end_index;

//    private MyAdapter myAdapter=new MyAdapter();

    private boolean sIsScrolling=false;

    private HashMap<Integer,Boolean> checkBoxIsChecked=new HashMap<>();

    private int selected_imageSum;

    //不同目录保存的子项的选中状态
    private HashMap<String,HashMap<Integer,Boolean>> image_spinner=new HashMap<>();

    private String image_category;

    //不同目录的真正HashMap<>()
//    private ArrayList<HashMap<Integer,>>

    //当gridview的item出现在屏幕时，getView()就会被执行
    private BaseAdapter baseAdapter=new BaseAdapter() {
        @Override
        public int getCount() {
            return image_Type.size();
        }

        @Override
        public Object getItem(int position) {
            return image_Type.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Log.d("getView()","true");
//            View view;
//            if(convertView==null){
                convertView=showImageActivity.this.getLayoutInflater().inflate(R.layout.show_image_gridview,null);
//            }else{
//                view=convertView;
//            }
            final RelativeLayout image_relativeLayout=(RelativeLayout) convertView.findViewById(R.id.image_relativeLayout);
            final ImageView image=(ImageView) convertView.findViewById(R.id.show_image);
            final CheckBox checkBox=(CheckBox) convertView.findViewById(R.id.image_checkBox);
//            DisplayMetrics dm=new DisplayMetrics();
//            showImageActivity.this.getWindowManager().getDefaultDisplay().getMetrics(dm);
//            int image_width=dm.widthPixels/3;
//
//
//            Bitmap bitmap=BitmapFactory.decodeFile(image_Type.get(position));
//
//            float scaleWidth = ((float) image_width) / bitmap.getWidth();
//            float scaleHeight = ((float) image_width) / bitmap.getHeight();
//            Matrix matrix=new Matrix();
//            matrix.postScale(scaleWidth,scaleHeight);
//
//
//            int bitmapWidth=bitmap.getWidth();
//            int bitmapHeight=bitmap.getHeight();
//            bitmap=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
//            //image.setImageBitmap(bitmap);
//            image.setImageBitmap(bitmap);
            Glide.with(showImageActivity.this).load(image_Type.get(position)).centerCrop().into(image);

//            checkBox.setChecked(true);
//            for(Integer index:checkBoxIsChecked.keySet()){
//                (CheckBox)image_relativeLayout.getChildAt(0)
//            }



            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
//                            Log.d("checkBoxIsChecked",all_image_path.get(position));
                        //勾选照片时，使照片变暗
//                        checkBox.setTag("check");
                        image_spinner.get(image_category).put((Integer) position,isChecked);
//                        for(Integer i:image_spinner.get(image_category).keySet()){
//                            Log.d("ChildHashMap",image_spinner.get(image_category).get(i)+"");
//                        }
                        Log.d("checkBoxId",checkBox.getId()+"");
                        Log.d("checkBoxIsChecked",position+" "+checkBoxIsChecked.get(position)+"");
                        int brightness = -80; //RGB偏移量，变暗为负数
                        ColorMatrix matrix = new ColorMatrix();
                        matrix.set(new float[]{1, 0, 0, 0, brightness, 0, 1, 0, 0, brightness, 0, 0, 1, 0, brightness, 0, 0, 0, 1, 0});
                        ColorMatrixColorFilter cmcf = new ColorMatrixColorFilter(matrix);
                        image.setColorFilter(cmcf); //imageView为显示图片的View。



                        Log.d("select_position",position+"");

                        if(selected_sum>=9){
                            Toast.makeText(showImageActivity.this,"图片已到达上限",Toast.LENGTH_SHORT).show();
                            ok_selected.setText("已选(9/9)");
                            checkBox.setChecked(false);

//                            *checkBox.setEnabled(false);

                            checkBox.setClickable(false);
                            Log.d("Clickable","true");
                        }else{
                            back_selectedImage.add(image_Type.get(position));
//                            selected_imageSum=back_selectedImage.size();
                            ok_selected.setText("已选("+(++selected_sum)+"/9)");
                        }

                    }else{
                        image_spinner.get(image_category).remove(position);
                        int brightness = 0; //RGB偏移量，变暗为负数
                        ColorMatrix matrix = new ColorMatrix();
                        matrix.set(new float[]{1, 0, 0, 0, brightness, 0, 1, 0, 0, brightness, 0, 0, 1, 0, brightness, 0, 0, 0, 1, 0});
                        ColorMatrixColorFilter cmcf = new ColorMatrixColorFilter(matrix);
                        image.setColorFilter(cmcf);
                        back_selectedImage.remove(image_Type.get(position));

                        ok_selected.setText("已选("+(--selected_sum)+"/9)");
                    }
                    if(back_selectedImage.size()==0){
                        ok_selected.setEnabled(false);
                    }else {
                        ok_selected.setEnabled(true);
                    }
                }

            });


//            for(String s:image_spinner.keySet()){
//                Log.d("image_spinner",image_spinner.get(s).get(position)+"");
//            }


            if(image_spinner.get(image_category)!=null&&image_spinner.get(image_category).containsKey(position)){
                checkBox.setChecked(true);


                if(selected_sum>=9){
//                    Toast.makeText(showImageActivity.this,"图片已超过上限",Toast.LENGTH_SHORT).show();
                    ok_selected.setText("已选(9/9)");

//                    checkBox.setEnabled(false);
                    checkBox.setChecked(true);

                    checkBox.setClickable(true);

                }else{
//                    back_selectedImage.clear();
//                    for(String categorykey:image_spinner.keySet()){
//                        if(image_spinner.get(categorykey).keySet()==null){
//
//                        }else{
//                            for (Integer index:image_spinner.get(categorykey).keySet()){
//                                back_selectedImage.add(image_Type.get(index));
//                            }
//                        }
//
//                    }

                    selected_sum=back_selectedImage.size()+selected_imageSum;
                --selected_sum;
//                --selected_imageSum;
                    ok_selected.setText("已选("+(++selected_sum)+"/9)");
                }


//                i=i-selected_sum;
            }else {
                checkBox.setChecked(false);
            }

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(showImageActivity.this,showBigImageActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("image_path",image_Type.get(position));
                    intent.putExtras(bundle);
                    startActivityForResult(intent,SEND_BIG_IMAGE);
                }
            });

            return convertView;
        }
    };






        //以面向对象的思维来操作列表数据
//    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
//
//        //MyAdapter的构造方法
//        public MyAdapter(){}
//
//        //自定义的ViewHolder类，对RecyclerView的每个子项进行类封装（即把每个子项里面的控件作为一个具体对象的属性）
//        class ViewHolder extends RecyclerView.ViewHolder{
//            //RecyclerView中的控件作为类对象的属性
//            public ImageView imageView;
//            public CheckBox checkBox;
//            public ViewHolder(View view){
//                super(view);
//                imageView=(ImageView) view.findViewById(R.id.show_image);
//                checkBox=(CheckBox) view.findViewById(R.id.image_checkBox);
//            }
//        }
//
//        //重写onCreateViewHolder方法，该方法返回子项（一个具体对象）
//        @Override
//        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view=showImageActivity.this.getLayoutInflater().inflate(R.layout.show_image_gridview,null,false);
//            ViewHolder viewHolder=new ViewHolder(view);
//            return viewHolder;
//        }
//
//        //该方法设置对象的属性（即控件的具体要显示的东西）
//        @Override
//        public void onBindViewHolder(final ViewHolder holder, final int position) {
//            Glide.with(showImageActivity.this).load(image_Type.get(position)).centerCrop().into(holder.imageView);
//
//            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if(isChecked){
////                            Log.d("checkBoxIsChecked",all_image_path.get(position));
//                        //勾选照片时，使照片变暗
//                        Log.d("checkBoxIsChecked","true");
//                        int brightness = -80; //RGB偏移量，变暗为负数
//                        ColorMatrix matrix = new ColorMatrix();
//                        matrix.set(new float[]{1, 0, 0, 0, brightness, 0, 1, 0, 0, brightness, 0, 0, 1, 0, brightness, 0, 0, 0, 1, 0});
//                        ColorMatrixColorFilter cmcf = new ColorMatrixColorFilter(matrix);
//                        holder.imageView.setColorFilter(cmcf); //imageView为显示图片的View。
//
//
////                        Log.d("select_position",position+"");
//
//                        if(selected_sum>=9){
//                            Toast.makeText(showImageActivity.this,"图片已超过上限",Toast.LENGTH_SHORT).show();
//
//                            holder.checkBox.setEnabled(false);
//                        }else{
//                            back_selectedImage.add(image_Type.get(position));
//                            ok_selected.setText("已选("+(++selected_sum)+"/9)");
//                        }
//
//                    }else{
//                        int brightness = 0; //RGB偏移量，变暗为负数
//                        ColorMatrix matrix = new ColorMatrix();
//                        matrix.set(new float[]{1, 0, 0, 0, brightness, 0, 1, 0, 0, brightness, 0, 0, 1, 0, brightness, 0, 0, 0, 1, 0});
//                        ColorMatrixColorFilter cmcf = new ColorMatrixColorFilter(matrix);
//                        holder.imageView.setColorFilter(cmcf);
//                        back_selectedImage.remove(image_Type.get(position));
//                        ok_selected.setText("已选("+(--selected_sum)+"/9)");
//                    }
//                    if(back_selectedImage.size()==0){
//                        ok_selected.setEnabled(false);
//                    }else {
//                        ok_selected.setEnabled(true);
//                    }
//                }
//
//            });
//
//            holder.imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent=new Intent(showImageActivity.this,showBigImageActivity.class);
//                    Bundle bundle=new Bundle();
//                    bundle.putString("image_path",image_Type.get(position));
//                    intent.putExtras(bundle);
//                    startActivityForResult(intent,SEND_BIG_IMAGE);
//                }
//            });




//        }

//        @Override
//        public int getItemCount() {
//            return image_Type.size();
//        }
//
//
//    }


    //不同类别图片的切换
    private AdapterView.OnItemSelectedListener myOnItemSelectedListener=new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            for(int i=0;i<image_Type.size();i++){
//                image_Type.remove(i);
//            }
//            Log.d("spinnerSelected","true");
            image_Type.clear();
            Log.d("spinnerSelected1",image_Type.size()+"");
            //HashMap插入相同的值会覆盖之前的值
            image_spinner.put(image_category,image_spinner.get(image_category));
//            image_spinner.get(image_category)
            image_category=image_Type_List.get(position);
            Log.d("image_category",image_category+"");
            for(String image_Path:all_image_path){
                if(image_Path.contains(image_Type_List.get(position))){
                    image_Type.add(image_Path);
                    Log.d("select_position_image",image_Path);
                }

            }
            Log.d("spinnerSelected2",image_Type.size()+"");
            baseAdapter.notifyDataSetChanged();
//            image_spinner.put(image_Type_List.get(position),);
//            checkBoxIsChecked.clear();
//            checkBoxIsChecked.clear();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    //
    private View.OnClickListener myOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.back_activity:
                    break;
                //发送图片到下一个activity中显示
                case R.id.ok_selected:
                    if(back_selectedImage.size()==0){
//                        ok_selected.setEnabled(false);
                    }else{
//                        ok_selected.setEnabled(true);
                        Intent intent=new Intent();
                        Bundle bundle=new Bundle();
                        bundle.putStringArrayList("back_selectedImage",back_selectedImage);
//                        bundle.putInt("selected_sum",selected_sum);
                        intent.putExtras(bundle);
                        setResult(RESULT_OK,intent);
                        finish();
                    }

                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        Log.d("onCreate","true");
        initView();


    }



    private void initView(){
        Log.d("image_Type_sum",image_Type.size()+"");
        back_activity=(ImageView) this.findViewById(R.id.back_activity);
        image_type_spinner=(Spinner) this.findViewById(R.id.image_type_spinner);
        ok_selected=(Button) this.findViewById(R.id.ok_selected);
        image_gridView=(GridView) this.findViewById(R.id.gridview);
//        recyclerView=(RecyclerView) this.findViewById(R.id.recyclerView);

        back_activity.setOnClickListener(myOnClickListener);
        image_type_spinner.setOnItemSelectedListener(myOnItemSelectedListener);
        ok_selected.setOnClickListener(myOnClickListener);

//        image_gridView.setOnScrollListener(myOnScrollListener);

        ok_selected.setEnabled(false);
        all_image_path=this.getIntent().getExtras().getStringArrayList("imagePaths");
        selected_sum=this.getIntent().getExtras().getInt("selected_sum");
        selected_imageSum=selected_sum;
        /**
         * 从上一个Activity传过来的图片的目录（WeiXin,Camera等）
         * */
        image_Type_List=this.getIntent().getExtras().getStringArrayList("image_Type_List");

        for(String s:image_Type_List){
            image_spinner.put(s,new HashMap<Integer, Boolean>());
        }

        Log.d("selected_sum",selected_sum+"");
        ok_selected.setText("已选("+(selected_sum)+"/9)");

        //图片类别的适配器
        image_type_spinner.setAdapter(new ArrayAdapter<String>(showImageActivity.this,android.R.layout.simple_spinner_item,image_Type_List));
        image_gridView.setAdapter(baseAdapter);

//        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        /**
         *      定义各个子项之间的排序方式，RecyclerView把该功能交给布局管理器来管理
         *      而ListView,GridVeiw则由自身来管理（android:numCounts属性）
         */

//        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,3);
//        recyclerView.setLayoutManager(gridLayoutManager);
//        //该类别下具体的图片适配器设置
//        recyclerView.setAdapter(myAdapter);
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if(newState==RecyclerView.SCROLL_STATE_DRAGGING||newState==RecyclerView.SCROLL_STATE_SETTLING){
//                    sIsScrolling=true;
//
//
//                    Glide.with(showImageActivity.this).pauseRequests();
//                }else if(newState==RecyclerView.SCROLL_STATE_IDLE){
//                    if(sIsScrolling==true){
//                        Glide.with(showImageActivity.this).resumeRequests();
//                    }
//                    sIsScrolling=false;
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//            }
//        });

    }

//    private void showImage(){
//        image_gridView.setAdapter(baseAdapter);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("showImageOnDestroy","true");
//        Glide.with(this).pauseRequests();

    }
}
