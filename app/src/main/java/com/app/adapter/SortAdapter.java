package com.app.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.pojo.SortMode;
import com.example.appproject.R;


import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.zip.Inflater;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/10/5.
 */

public class SortAdapter extends BaseAdapter {
    private Context context;
    private List<SortMode> modes;
    private LayoutInflater inflater;

    public SortAdapter(Context context,List<SortMode> modes){
        this.context=context;
        this.modes=modes;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return modes.size();
    }

    @Override
    public Object getItem(int position) {
        return modes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
                try {
                    //声明变量时，系统不会为该变量分配内存，new的时候才会分配内存
                    ViewHolder viewHolder=null;
                    /**
                     * 当第一次调用getView()绘制item时,convertView为空，此时会新建一个convertView和viewHolder，该convertView和viewHolder会被保存在内存中
                     * 第二次调用getView()时，该convertView和viewHolder还未被系统回收，因此可以继续调用该convertView和viewHolder
                     *
                     * viewHolder对象是convertView布局的实际view
                     * getView()返回的是convertView，即具体的item
                     * */
                    if(viewHolder==null){
                        viewHolder=new ViewHolder();
                        convertView=inflater.inflate(R.layout.home_tab_a2_listview_item,null);
                        viewHolder.tvLatter=(TextView) convertView.findViewById(R.id.home_fragA2_catalog_TextView);
                        viewHolder.home_fragA2_logo_ImageView=(ImageView) convertView.findViewById(R.id.home_fragA2_logo_ImageView);
                        viewHolder.tvName=(TextView) convertView.findViewById(R.id.home_fragA2_title_TextView);
                        convertView.setTag(viewHolder);
                    }else{
                        viewHolder=(ViewHolder)convertView.getTag();
                    }
                    SortMode sortMode=modes.get(position);

                    //获取首字母的Ascoll值
                    int selection=sortMode.getSortLetter();
                    //返回第一次出现的positionView
                    int positionForSelection=getPositionForSelection(selection);
                    if(positionForSelection==position){
                        viewHolder.tvLatter.setVisibility(View.VISIBLE);
                        String headerString=sortMode.getSortLetter()+"";
                        viewHolder.tvLatter.setText(headerString.substring(0,1).toUpperCase());
                    }else{
                        viewHolder.tvLatter.setVisibility(View.GONE);
                    }
                    viewHolder.tvName.setText(sortMode.getName());


//                    URL url=new URL("http://192.168.1.101:8080/schoolapp/dynamicImages/ic_launcher.png");
////								InputStream is=url.openStream();
//                    OkHttpClient client=new OkHttpClient();
//                    Request request=new Request.Builder().url(url).build();
//                    Response response=client.newCall(request).execute();
//                    InputStream is=response.body().byteStream();
//                    viewHolder.home_fragA2_logo_ImageView.setImageBitmap(BitmapFactory.decodeStream(is));

                }catch (Exception e){
                    e.printStackTrace();
                }
//
//            }
//        }).start();

        return convertView;
    }

    public int getPositionForSelection(int selection){
        for(int i=0;i<getCount();i++){
            char sortName=modes.get(i).getSortLetter();
            if(sortName==selection){
                //该i永远是相同的首字母排在第一位的下标，而postion则是当前项的下标
                return i;
            }
        }
        return -1;
    }

    private class ViewHolder{
        public TextView tvLatter;
        public ImageView home_fragA2_logo_ImageView;
        public TextView tvName;
    }
}
