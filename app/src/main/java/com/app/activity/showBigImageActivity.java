package com.app.activity;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.appproject.R;

public class showBigImageActivity extends Activity {
    private ImageView big_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_big_image);

        initView();
        showBigImage();
    }

    private void initView(){
        big_image=(ImageView) this.findViewById(R.id.big_image);
    }

    private void showBigImage(){
        String image_path=this.getIntent().getExtras().getString("image_path");
        big_image.setImageBitmap(BitmapFactory.decodeFile(image_path));
    }
}
