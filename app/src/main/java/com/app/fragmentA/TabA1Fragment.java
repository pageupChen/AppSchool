package com.app.fragmentA;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.activity.MainActivity;
import com.app.activity.sayDynamicActivity;
import com.app.activity.sendDynamicStateActivity;
import com.app.activity.showDynamicImageActivity;
import com.app.activity.showImageActivity;
import com.app.broadcastreceiver.NetworkChangeReceiver;
import com.app.pojo.Dynamic;
import com.app.util.MyImageLoader;
import com.app.view.DynamicImageGridView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.appproject.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.hp.hpl.sparta.Text;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.youth.banner.transformer.ZoomOutTranformer;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * 启动程序进入主界面，后台返回5条dynamic对象的数据，显示在recyclerview中
 * */

public class TabA1Fragment extends Fragment {
	private Callbacks mCallbacks;

	public interface
	Callbacks{
		public MainActivity.autoRefresh isAutoRefresh();
	}

	//	private int i=0;
	private String[] more;
	private MainActivity.autoRefresh autoRefresh;
    private List<String> img=new ArrayList<>();
    private Banner banner;
//	private boolean autoRefresh=true;
	private List<Dynamic> dymanicList=new ArrayList<>();
    public static List<String> dynamicURL=new ArrayList<>();
    public static Map<Integer,List<String>> nineURLMap=new HashMap<>();
	private ImageView home_fragA1_up_imageView;
	private FloatingActionButton floatingActionButton;
	private View view;
	private MyAdapter mAdapter;
	private RecyclerView home_fragA1_recyclerView;
	private SmartRefreshLayout smartRefreshLayout;
	private RelativeLayout home_tab_a1_banner_RelativeLayout;
	private LinearLayoutManager lm;
	private ViewPager home_tab_a1_banner_myViewPager;
	private int[] adImages = {R.mipmap.ading1, R.mipmap.ading2, R.mipmap.ading3,R.mipmap.ading4};
	private List<Bitmap> bitmaps=new ArrayList<>();
	private List<ImageView> adImageView = new ArrayList<ImageView>();
	private LinearLayout home_tab_a1_banner_myLinearLayout;
	private int startRecordIndex=5;
	private int endRecordIndex=10;

	private int lastItemPosition;
	private int firstItemPosition;
	public static MyImageLoader imageLoader;
	private int currentIndex = 900;
    private static final int START_COMMENTACTIVITY=1;
    private static final int START_SENDDYNAMICSTATEACTIVITY=2;
    private static int clickItemPosition;


//    private boolean giveGood=true;

	private boolean waitingShowToUser;
	private IntentFilter intentFilter;
	private NetworkChangeReceiver networkChangeReceiver;

    class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(TabA1Fragment.this.getActivity()).load((String)path).into(imageView);
        }
    }

//	private TabA1Fragment.MyThread myThread;

//	private class MyThread extends Thread {
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//			while (true) {
//                Log.d("ThreadIsRun","true");
//				if (myThread != null) {
//					try {
//						Thread.sleep(2000);
////						Log.d("getCurrentItem", "aaa");
//						if (TabA1Fragment.this.getActivity() == null) {
//
//						} else {
//							TabA1Fragment.this.getActivity().runOnUiThread(new Runnable() {
//								@Override
//								public void run() {
//									Log.d("getCurrentItem", home_tab_a1_banner_myViewPager.getCurrentItem() + "");
//									home_tab_a1_banner_myViewPager.setCurrentItem(home_tab_a1_banner_myViewPager.getCurrentItem() + 1);
//
//									//Log.d("isRun()", "true");
//								}
//							});
//						}
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//
//
//				}else{
//					break;
//				}
//
//			}
//		}
//
//
//	}

//	private Handler handler = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			super.handleMessage(msg);
//			if (msg.what == 0x123) {
//				mAdapter.notifyDataSetChanged();
//			}
//		}
//	};

    //返回该界面的回调事件
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if(resultCode== Activity.RESULT_OK){
                if(requestCode==START_COMMENTACTIVITY){
                    Log.d("onActivityResult","true");
                    Dynamic dynamic= (Dynamic) data.getExtras().getSerializable("dynamic");
                    Log.d("ResultDyna",dynamic.toString());

                    Log.d("clickItemPosition",clickItemPosition+"");

                    //根据之前设置的Tag标志，找到item子项，操作子项
                    TextView itemSayText = (TextView) home_fragA1_recyclerView.findViewWithTag(dymanicList.get(clickItemPosition).getDynaId()+"sayText");
                    dymanicList.get(clickItemPosition).setDynaTalkNumber(dynamic.getDynaTalkNumber());
                    itemSayText.setText(dymanicList.get(clickItemPosition).getDynaTalkNumber()+"");

                    TextView itemGoodText= (TextView) home_fragA1_recyclerView.findViewWithTag(dymanicList.get(clickItemPosition).getDynaId()+"goodText");
                    dymanicList.get(clickItemPosition).setDynaStarNumber(dynamic.getDynaStarNumber());
                    itemGoodText.setText(dymanicList.get(clickItemPosition).getDynaStarNumber()+"");

                    ImageView itemGoodImage=(ImageView) home_fragA1_recyclerView.findViewWithTag(dymanicList.get(clickItemPosition).getDynaId()+"goodImage");
                    dymanicList.get(clickItemPosition).setGiveGood(dynamic.isGiveGood());
                    if(dymanicList.get(clickItemPosition).isGiveGood()){
                        itemGoodImage.setImageResource(R.mipmap.givegood);
                    }else{
                        itemGoodImage.setImageResource(R.mipmap.cancelgood);
                    }
                }
                if(requestCode==START_SENDDYNAMICSTATEACTIVITY){
					Log.d("autoR","true");
                    smartRefreshLayout.autoRefresh();
                }
            }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


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
//			isFirstIn=true;

            //实例化MyImageLoader
			imageLoader=new MyImageLoader();


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
		public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			if (mHeaderView != null && viewType == TYPE_HEADER)
				return new MyViewHolder(mHeaderView);
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_tab_a1_recycler_item, parent, false);

			return new MyViewHolder(view);
		}

		@Override
		public void onBindViewHolder(final MyAdapter.MyViewHolder holder, int position) {
			Log.d("onBindViewHolder","onBindViewHolder");
			if (getItemViewType(position) == TYPE_HEADER)
				return;
			final int pos = getRealPosition(holder);

			if (holder instanceof MyViewHolder) {
				holder.setIsRecyclable(false);

                //加载用户头像
                Glide.with(TabA1Fragment.this.getActivity())
						.load(dymanicList.get(pos).getMassLogo())
						.diskCacheStrategy(DiskCacheStrategy.ALL)
						.centerCrop()
						.into(holder.getHome_fragA1_recyclerView_imageHeader());
                //加载社团名（动态的发起者）
				holder.getHome_fragA1_recyclerView_massname_textView().setText(dymanicList.get(pos).getMassName());
				//加载发起动态的时间,与系统当前时间作比较，从而让时间显示更具体
				//获取当前时间
				Date date=new Date();
				//如果等于当前日数，则细分上，中，下，晚上，凌晨
//				if(date.getDay()==Integer.parseInt(dymanicList.get(pos).getDynaSendTime().substring(3,5))){
//
//					if(Integer.parseInt(dymanicList.get(pos).getDynaSendTime().substring(6,8))){
//
//					}
//				}
//				dymanicList.get(pos).getDynaSendTime()
                holder.getHome_fragA1_recyclerView_sendTime().setText(dymanicList.get(pos).getDynaSendTime());
				//加载动态的内容
                holder.getHome_fragA1_recyclerView_textBody().setText(dymanicList.get(pos).getDynaContent());
                //加载评论数
                holder.getHome_fragA1_recyclerView_sayText().setText(dymanicList.get(pos).getDynaTalkNumber()+"");
                //加载点赞数
				holder.getHome_fragA1_recyclerView_goodText().setText(dymanicList.get(pos).getDynaStarNumber()+"");
                //根据用户的是否点赞实时更新界面和后台数据库的数据
				if(dymanicList.get(pos).isGiveGood()){
                    //启动程序进入主界面时，如果后台传过来的数据是已经点赞过，则修改界面为已经点赞
					holder.getHome_fragA1_recyclerView_goodImage().setImageResource(R.mipmap.givegood);
//							mAdapter.notifyItemRangeChanged(pos,1);
				}else{
                    //否则，修改界面为没有点赞
					holder.getHome_fragA1_recyclerView_goodImage().setImageResource(R.mipmap.cancelgood);
				}

				//点击更多事件
				holder.getHome_fragA1_moreLine().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
						//如果是自己发表的动态，则可以删除
						if(dymanicList.get(pos).getDynaStuId()==1){
							more=new String[]{"分享","删除"};
						}else{
							//否则只能分享
							more=new String[]{"分享"};
						}
						AlertDialog.Builder dialog=new AlertDialog.Builder(TabA1Fragment.this.getActivity());
						dialog.setItems(more, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								if(which==0){
									Toast.makeText(TabA1Fragment.this.getActivity(),"分享",Toast.LENGTH_SHORT).show();
								}
								if(which==1){
									Toast.makeText(TabA1Fragment.this.getActivity(),"删除",Toast.LENGTH_SHORT).show();
									//删掉该条动态id对应的后台数据
									deleteDynamic(dymanicList.get(pos));
								}
							}
						});
						dialog.show();
                    }
                });

                //点击点赞事件
                holder.getHome_fragA1_goodLine().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
						Log.d("goodLineClick","true");

						if(dymanicList.get(pos).isGiveGood()){
                            //修改前台界面和数据
                            //如何之前已经是点赞，则该次点击效果为取消点赞。
                            // 根据postion修改该列表项对应的dynamic的dynaStarNumberz的值减一,以便滑动的时候值不出错
                            dymanicList.get(pos).setDynaStarNumber(dymanicList.get(pos).getDynaStarNumber()-1);
                            //将该position对应的dynamic的dynaStarNumber属性值设置给goodText
                            holder.getHome_fragA1_recyclerView_goodText().setText(dymanicList.get(pos).getDynaStarNumber()+"");
                            //设置该positiono对应的列表项的点赞为选中
                            holder.getHome_fragA1_recyclerView_goodImage().setImageResource(R.mipmap.cancelgood);

                        }else{
                            //与上面相反
							dymanicList.get(pos).setDynaStarNumber(dymanicList.get(pos).getDynaStarNumber()+1);
							holder.getHome_fragA1_recyclerView_goodText().setText(dymanicList.get(pos).getDynaStarNumber()+"");
							holder.getHome_fragA1_recyclerView_goodImage().setImageResource(R.mipmap.givegood);

                        }

                        //修改后台的数据库，传入该position对应dynamic对象
						GiveOrCancelGood(dymanicList.get(pos));
                    }
                });

                //点击评论事件，跳转到回复界面
				holder.getHome_fragA1_dynamicLine().setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
                        //将点击到的item项的position赋值给clickItemPosition，供返回到该activity时回调使用，
						clickItemPosition=pos;
                        //用唯一值绑定该item项，便于返回该界面时获取到该项，然后操作该项
						holder.getHome_fragA1_recyclerView_sayText().setTag(dymanicList.get(pos).getDynaId()+"sayText");
						holder.getHome_fragA1_recyclerView_goodText().setTag(dymanicList.get(pos).getDynaId()+"goodText");
						holder.getHome_fragA1_recyclerView_goodImage().setTag(dymanicList.get(pos).getDynaId()+"goodImage");

                        //跳转到回复界面
						Intent intent=new Intent(TabA1Fragment.this.getActivity(),sayDynamicActivity.class);
						Bundle bundle=new Bundle();
                        //把该position对应的dynamic对象传递到回复界面
						bundle.putSerializable("dynamic",dymanicList.get(pos));
						intent.putExtras(bundle);
						startActivityForResult(intent,START_COMMENTACTIVITY);
					}
				});

                //点击评论事件，作用与上同
				holder.getHome_fragA1_sayLine().setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						clickItemPosition=pos;
                        //用唯一值绑定该item项，便于返回该界面时获取到该项，然后操作该项
                        holder.getHome_fragA1_recyclerView_sayText().setTag(dymanicList.get(pos).getDynaId()+"sayText");
                        holder.getHome_fragA1_recyclerView_goodText().setTag(dymanicList.get(pos).getDynaId()+"goodText");
                        holder.getHome_fragA1_recyclerView_goodImage().setTag(dymanicList.get(pos).getDynaId()+"goodImage");

						Intent intent=new Intent(TabA1Fragment.this.getActivity(),sayDynamicActivity.class);
						Bundle bundle=new Bundle();
						bundle.putSerializable("dynamic",dymanicList.get(pos));
						intent.putExtras(bundle);
						startActivityForResult(intent,START_COMMENTACTIVITY);
					}
				});

//				holder.getHome_fragA1_goodLine().setOnClickListener(myListener);

//				List<Bitmap> bitmapList=new ArrayList<>();
//				for(int i=0;i<9;i++){
//					Bitmap bitmap= BitmapFactory.decodeResource(TabA1Fragment.this.getActivity().getResources(),R.mipmap.ic_launcher);
//					bitmapList.add(bitmap);
//				}

				//在从网络加载九宫图的时候，就应该把图片加载进缓存
//				new Thread(new Runnable() {
//					@Override
//					public void run() {
//						//dymanicList.get(pos).getDynaPictures()是其中一个dynamic对象的图片（路径）list集合
//						for(int i=0;i<dymanicList.get(pos).getDynaPictures().size();i++){
//							try{
//								Log.d("nineList",dymanicList.get(pos).getDynaPictures().get(i)+"");
////                                Bitmap bitmap=Glide.with(context)
////                                        .load(list.get(i))
////                                        .asBitmap()
////										.into(100,100)
////                                        .get();
//								//dymanicList.get(pos).getDynaPictures().get(i)获取一个dynamic对象图片路径集合（list）中的一个图片路径
//								URL url=new URL(dymanicList.get(pos).getDynaPictures().get(i));
//								HttpURLConnection conn= (HttpURLConnection) url.openConnection();
//								Bitmap bitmap=BitmapFactory.decodeStream(conn.getInputStream());
//								Log.d("bmap",bitmap.toString());
//								bitmaps.add(bitmap);
//							}catch(Exception e){
//								e.printStackTrace();
//							}
//
//						}
//						Log.d("bitmaps",bitmaps.size()+"");
//						//通过传入dynamic对象的图片list集合，和该List集合中每一个路径对应的bitmap对象，保存到LurCache中
//						imageLoader.addBitmapToCacheNine(dymanicList.get(pos).getDynaPictures(),bitmaps);
//
////						MyImageLoader.getBitmapFromCacheNine(dymanicList.get(pos).getDynaPictures());
//
//						Log.d("cacheNine",imageLoader.getBitmapFromCacheNine(dymanicList.get(pos).getDynaPictures()).size()+"");
//
//					}
//				}).start();

                //九宫图加载图片
                holder.getHome_fragA1_ninegridImageView().setAdapter(new NineGridImageViewAdapter<String>() {
					@Override
					protected void onDisplayImage(Context context, ImageView imageView, String imageUrl) {
//						imageView.setImageBitmap(bitmap);
                        //Glide框架加载图片
						Glide.with(context)
                                //imageUrl是网络图片的地址
								.load(imageUrl)
                                //设置默认显示的图片
								.placeholder(R.mipmap.default_error)
								//设置图片缓存
								.diskCacheStrategy(DiskCacheStrategy.ALL)
								.centerCrop()
								.into(imageView);
					}

					//九宫格点击图片事件
					@Override
					protected void onItemImageClick(Context context, final int index, final List<String> list) {
						super.onItemImageClick(context, index, list);

						Intent intent=new Intent(TabA1Fragment.this.getActivity(),showDynamicImageActivity.class);
						Bundle bundle=new Bundle();
                        //将点击到的图片子项的下标传到显示图片界面
						bundle.putInt("bitmapIndex",index);
						//将点击的列表子项对应的dynamic对象传到下一个activity
						bundle.putSerializable("dynamic", dymanicList.get(pos));
						intent.putExtras(bundle);
						startActivity(intent);



					}
				});

                //为九宫格设置图片数据源
				holder.getHome_fragA1_ninegridImageView().setImagesData(dymanicList.get(pos).getDynaPictures());

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
			return mHeaderView == null ? dymanicList.size() : dymanicList.size() + 1;
		}

		public class MyViewHolder extends RecyclerView.ViewHolder {

			private LinearLayout home_fragA1_dynamicLine;
            private CircleImageView home_fragA1_recyclerView_imageHeader;
			private TextView home_fragA1_recyclerView_massname_textView;
            private TextView home_fragA1_recyclerView_sendTime;
			private TextView home_fragA1_recyclerView_school;
            private TextView home_fragA1_recyclerView_textBody;
            private NineGridImageView home_fragA1_ninegridImageView;
            private LinearLayout home_fragA1_moreLine;
			private LinearLayout home_fragA1_sayLine;
			private LinearLayout home_fragA1_goodLine;
            private ImageView home_fragA1_recyclerView_sayImage;
            private TextView home_fragA1_recyclerView_sayText;
            private ImageView home_fragA1_recyclerView_goodImage;
            private TextView home_fragA1_recyclerView_goodText;


			public MyViewHolder(View itemView) {
				super(itemView);
				if (itemView == mHeaderView)
					return;

				home_fragA1_dynamicLine=(LinearLayout)itemView.findViewById(R.id.home_fragA1_dynamicLine);
                home_fragA1_recyclerView_imageHeader = (CircleImageView) itemView.findViewById(R.id.home_fragA1_recyclerView_imageHeader);
				home_fragA1_recyclerView_massname_textView=(TextView) itemView.findViewById(R.id.home_fragA1_recyclerView_massname_textView);
				home_fragA1_recyclerView_sendTime = (TextView) itemView.findViewById(R.id.home_fragA1_recyclerView_sendTime);
				home_fragA1_recyclerView_school = (TextView) itemView.findViewById(R.id.home_fragA1_recyclerView_school);
                home_fragA1_recyclerView_textBody= (TextView) itemView.findViewById(R.id.home_fragA1_recyclerView_textBody);
				home_fragA1_moreLine=(LinearLayout) itemView.findViewById(R.id.home_fragA1_moreLine);
				home_fragA1_sayLine= (LinearLayout) itemView.findViewById(R.id.home_fragA1_sayLine);
				home_fragA1_goodLine= (LinearLayout) itemView.findViewById(R.id.home_fragA1_goodLine);
				home_fragA1_ninegridImageView=(NineGridImageView) itemView.findViewById(R.id.home_fragA1_ninegridImageView);
                home_fragA1_recyclerView_sayImage=(ImageView) itemView.findViewById(R.id.home_fragA1_recyclerView_sayImage);
                home_fragA1_recyclerView_sayText=(TextView) itemView.findViewById(R.id.home_fragA1_recyclerView_sayText);
                home_fragA1_recyclerView_goodImage=(ImageView) itemView.findViewById(R.id.home_fragA1_recyclerView_goodImage);
                home_fragA1_recyclerView_goodText=(TextView) itemView.findViewById(R.id.home_fragA1_recyclerView_goodText);
			}

			public LinearLayout getHome_fragA1_dynamicLine() {
				return home_fragA1_dynamicLine;
			}

			public void setHome_fragA1_dynamicLine(LinearLayout home_fragA1_dynamicLine) {
				this.home_fragA1_dynamicLine = home_fragA1_dynamicLine;
			}

			public CircleImageView getHome_fragA1_recyclerView_imageHeader() {
                return home_fragA1_recyclerView_imageHeader;
            }

			public TextView getHome_fragA1_recyclerView_massname_textView() {
				return home_fragA1_recyclerView_massname_textView;
			}

			public void setHome_fragA1_recyclerView_massname_textView(TextView home_fragA1_recyclerView_massname_textView) {
				this.home_fragA1_recyclerView_massname_textView = home_fragA1_recyclerView_massname_textView;
			}

			public void setHome_fragA1_recyclerView_imageHeader(CircleImageView home_fragA1_recyclerView_imageHeader) {
                this.home_fragA1_recyclerView_imageHeader = home_fragA1_recyclerView_imageHeader;
            }

            public TextView getHome_fragA1_recyclerView_sendTime() {
                return home_fragA1_recyclerView_sendTime;
            }

            public void setHome_fragA1_recyclerView_sendTime(TextView home_fragA1_recyclerView_sendTime) {
                this.home_fragA1_recyclerView_sendTime = home_fragA1_recyclerView_sendTime;
            }

			public TextView getHome_fragA1_recyclerView_school() {
				return home_fragA1_recyclerView_school;
			}

			public void setHome_fragA1_recyclerView_school(TextView home_fragA1_recyclerView_school) {
				this.home_fragA1_recyclerView_school = home_fragA1_recyclerView_school;
			}

			public TextView getHome_fragA1_recyclerView_textBody() {
                return home_fragA1_recyclerView_textBody;
            }

            public void setHome_fragA1_recyclerView_textBody(TextView home_fragA1_recyclerView_textBody) {
                this.home_fragA1_recyclerView_textBody = home_fragA1_recyclerView_textBody;
            }

            public LinearLayout getHome_fragA1_moreLine() {
                return home_fragA1_moreLine;
            }

            public void setHome_fragA1_moreLine(LinearLayout home_fragA1_moreLine) {
                this.home_fragA1_moreLine = home_fragA1_moreLine;
            }

            public LinearLayout getHome_fragA1_sayLine() {
				return home_fragA1_sayLine;
			}

			public void setHome_fragA1_sayLine(LinearLayout home_fragA1_sayLine) {
				this.home_fragA1_sayLine = home_fragA1_sayLine;
			}

			public LinearLayout getHome_fragA1_goodLine() {
				return home_fragA1_goodLine;
			}

			public void setHome_fragA1_goodLine(LinearLayout home_fragA1_goodLine) {
				this.home_fragA1_goodLine = home_fragA1_goodLine;
			}

			public NineGridImageView getHome_fragA1_ninegridImageView() {
				return home_fragA1_ninegridImageView;
			}

			public void setHome_fragA1_ninegridImageView(NineGridImageView home_fragA1_ninegridImageView) {
				this.home_fragA1_ninegridImageView = home_fragA1_ninegridImageView;
			}

			public ImageView getHome_fragA1_recyclerView_sayImage() {
                return home_fragA1_recyclerView_sayImage;
            }

            public void setHome_fragA1_recyclerView_sayImage(ImageView home_fragA1_recyclerView_sayImage) {
                this.home_fragA1_recyclerView_sayImage = home_fragA1_recyclerView_sayImage;
            }

            public TextView getHome_fragA1_recyclerView_sayText() {
                return home_fragA1_recyclerView_sayText;
            }

            public void setHome_fragA1_recyclerView_sayText(TextView home_fragA1_recyclerView_sayText) {
                this.home_fragA1_recyclerView_sayText = home_fragA1_recyclerView_sayText;
            }

            public ImageView getHome_fragA1_recyclerView_goodImage() {
                return home_fragA1_recyclerView_goodImage;
            }

            public void setHome_fragA1_recyclerView_goodImage(ImageView home_fragA1_recyclerView_goodImage) {
                this.home_fragA1_recyclerView_goodImage = home_fragA1_recyclerView_goodImage;
            }

            public TextView getHome_fragA1_recyclerView_goodText() {
                return home_fragA1_recyclerView_goodText;
            }

            public void setHome_fragA1_recyclerView_goodText(TextView home_fragA1_recyclerView_goodText) {
                this.home_fragA1_recyclerView_goodText = home_fragA1_recyclerView_goodText;
            }
        }
	}


	//浮动按钮的点击事件
	private View.OnClickListener myListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.home_fragA1_floatingActionButton:
                    Intent intent = new Intent();
                    intent.setClass(TabA1Fragment.this.getActivity(), sendDynamicStateActivity.class);

                    startActivityForResult(intent,START_SENDDYNAMICSTATEACTIVITY);
					break;
			}
		}
	};

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
//		Log.d("autoRefresh",autoRefresh+"");
		Log.d("getCurrentItem","onAttach()");
		Log.d("TabA1Fragment","onAttach()");
//		myThread=new MyThread();
//		myThread.start();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		//View view = inflater.inflate(R.layout.fragment_tab1, null);
		Log.d("nmb", "TabA1Fragment--->onCreateView");

		iniView(inflater);
		if(autoRefresh.isAutoRefresh()){
			smartRefreshLayout.autoRefresh();
//			i++;
			autoRefresh.setAutoRefresh(false);
		}
//		//将动态信息包装成HashMap
//		for(int i=0;i<5;i++){
//			//构造一个Dymanic对象
//			Dynamic dynamic=new Dynamic();
//			//填充属性
//			dynamic.setImageHeader(R.mipmap.ic_launcher);
//			dynamic.setTextHeader("PAGE UP");
//			dynamic.setTextTime("09-21 12:24");
//			dynamic.setTextBody("陈澧《东塾读书记·小学》：“声者象乎意而宣之者也，声不能传於异地，留於异时，於是乎书之为文字。”\n" + "　　鲁迅《汉文学史纲要》第一篇：“要之文字成就，所当绵历岁时，且由众手");
//			List<Integer> listImageBody=new ArrayList<>();
//			for(int j=0;j<9;j++){
//				listImageBody.add(R.mipmap.ic_launcher);
//
//			}
//			dynamic.setImageBody(listImageBody);
//			dynamic.setSayText("5");
//			dynamic.setGoodText("8");
//			Log.d("listImageBody",listImageBody.size()+"");
//			//将Dymanic对象抛进HashMap
//			dymanicMap.put((Integer)i,dynamic);
//		}







//		changeBitmap();


//		for (int i = 0; i < adImages.length; i++) {
//			//动态添加小圆点
//			ImageView imageView_dot = new ImageView(TabA1Fragment.this.getActivity());
//			imageView_dot.setBackgroundColor(Color.WHITE);
//			imageView_dot.setPadding(15, 5, 15, 5);
//
//			home_tab_a1_banner_myLinearLayout.addView(imageView_dot);
//
//			//动态添加广告的滚动项
//			ImageView adImageItem = new ImageView(TabA1Fragment.this.getActivity());
//			adImageItem.setImageResource(adImages[i]);
//			adImageView.add(adImageItem);
//		}

//		shoolList.setAdapter(new BaseAdapter() {
//
//			@Override
//			public View getView(int position, View arg1, ViewGroup arg2) {
//				// TODO Auto-generated method stub
//				View view=TabA1Fragment.this.getActivity().getLayoutInflater().inflate(R.layout.tab_a1_list, null);
//				TextView tv1=(TextView) view.findViewById(R.id.tab_a1_list_tv1);
//				tv1.setText(school_atti[position]);
//				TextView tv2=(TextView) view.findViewById(R.id.tab_a1_list_tv2);
//				tv2.setText(">");
//				return view;
//			}
//
//			@Override
//			public long getItemId(int arg0) {
//				// TODO Auto-generated method stub
//				return arg0;
//			}
//
//			@Override
//			public Object getItem(int arg0) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public int getCount() {
//				// TODO Auto-generated method stub
//				return 5;
//			}
//		});

		return view;
	}

	private void iniView(LayoutInflater inflater) {

		view = inflater.inflate(R.layout.fragment_tab_a1, null);
//		autoRefresh=((MainActivity)getActivity());
		mCallbacks=(Callbacks)((MainActivity) getActivity());
		autoRefresh=mCallbacks.isAutoRefresh();

		home_fragA1_recyclerView = (RecyclerView) view.findViewById(R.id.home_fragA1_recyclerView);
		smartRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.home_fragA1_smartRefreshLayout);
		/**
		 * 下拉刷新，全部重新加载，（之前上拉加载过多的会被消掉，重新开始）
		 * */
		smartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {


			@Override
			public void onLoadmore(RefreshLayout refreshlayout) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							startRecordIndex += 5;
//							endRecordIndex+=5;
							OkHttpClient okHttpClient = new OkHttpClient();

							RequestBody requestBody = new FormBody.Builder()
									.add("stuId", 1 + "")
									.add("startRecordIndex", startRecordIndex + "")
									.build();
							Request request = new Request.Builder()
									.url("http://192.168.1.101:8080/schoolapp/returnDynamic.action")
									.post(requestBody)
									.build();
							Response response = okHttpClient.newCall(request).execute();
							String responseJSON = response.body().string();

							Gson gson = new Gson();

							List<Dynamic> dynamicListPart = gson.fromJson(responseJSON, new TypeToken<List<Dynamic>>() {
							}.getType());
							Log.d("dynamicList", dynamicListPart.size() + "");
							for (Dynamic dynamic : dynamicListPart) {
								Log.d("isGiveGood", dynamic.getDynaId() + "," + dynamic.isGiveGood());
							}
							for (int i = 0; i < dynamicListPart.size(); i++) {
								dymanicList.add(dynamicListPart.get(i));
//								Log.d("dynamicMap",dymanicMap.get(i).getDynaId()+dymanicMap.get(i).getDynaContent()+dymanicMap.get(i).getDynaStarNumber());
							}
							for (int i = 0; i < dymanicList.size(); i++) {
								Log.d("dynamicAdd", "true");
								dynamicURL.add(dymanicList.get(i).getUserHeader());
							}
							for (int j = 0; j < dymanicList.size(); j++) {
								nineURLMap.put((Integer) j, dymanicList.get(j).getDynaPictures());
							}
//							mAdapter.notifyDataSetChanged();
						} catch (Exception e) {
							e.printStackTrace();
						}
						notifyDatas();
					}
				}).start();


				//结束加载
				smartRefreshLayout.finishLoadmore();
				//加载失败的话3秒后结束加载
				refreshlayout.finishLoadmore(1000);


			}

			@Override
			public void onRefresh(final RefreshLayout refreshlayout) {
				//注册广播接收器
				intentFilter = new IntentFilter();
				intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
				networkChangeReceiver = new NetworkChangeReceiver(TabA1Fragment.this.getActivity());
				TabA1Fragment.this.getActivity().registerReceiver(networkChangeReceiver, intentFilter);
				new Thread(new Runnable() {
					@Override
					public void run() {
						Log.d("autoRefresh()", "refresh");
						Log.d("onBindViewHolder", "refresh");
						try {
							startRecordIndex = 0;
							endRecordIndex = 5;
							dymanicList.clear();
                            notifyDatas();
							dynamicURL.clear();
							nineURLMap.clear();
							OkHttpClient okHttpClient = new OkHttpClient();

							RequestBody requestBody = new FormBody.Builder()
									.add("stuId", 1 + "")
									.add("startRecordIndex", startRecordIndex + "")
									.build();
							Request request = new Request.Builder()
									.url("http://192.168.1.101:8080/schoolapp/returnDynamic.action")
									.post(requestBody)
									.build();
							Response response = okHttpClient.newCall(request).execute();
							String responseJSON = response.body().string();

							Log.d("responseJSON", responseJSON);
							Gson gson = new Gson();

							List<Dynamic> dynamicList = gson.fromJson(responseJSON, new TypeToken<List<Dynamic>>() {
							}.getType());
							Log.d("dynamicList", dynamicList.size() + "");
							for (Dynamic dynamic : dynamicList) {
								Log.d("isGiveGood", dynamic.getDynaId() + "," + dynamic.isGiveGood());
							}
							for (int i = 0; i < dynamicList.size(); i++) {
								dymanicList.add(dynamicList.get(i));
								Log.d("dynamicMap", dymanicList.get(i).getDynaId() + dymanicList.get(i).getDynaContent() + dymanicList.get(i).getDynaPictures());
							}
							for (int i = 0; i < dymanicList.size(); i++) {
								Log.d("dynamicAdd", "true");
								dynamicURL.add(dymanicList.get(i).getUserHeader());
							}
							for (int j = 0; j < dymanicList.size(); j++) {
								nineURLMap.put((Integer) j, dymanicList.get(j).getDynaPictures());
							}
//							if(isFirstIn){
//								Log.d("rrr",true+"");
//								imageLoader.loadHeaderImages(firstItemPosition,1);
//                                imageLoader.loadNineImages(firstItemPosition,1);
//								isFirstIn=false;
//							}

						} catch (Exception e) {
							e.printStackTrace();
						}


						//结束加载
						smartRefreshLayout.finishRefresh();
						//加载失败的话3秒后结束加载
						refreshlayout.finishRefresh(3000);
//						mAdapter.notifyDataSetChanged();

					}
				}).start();
			}
		});
		floatingActionButton = (FloatingActionButton) view.findViewById(R.id.home_fragA1_floatingActionButton);
		home_fragA1_up_imageView = (ImageView) view.findViewById(R.id.home_fragA1_up_imageView);
		home_fragA1_up_imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				home_fragA1_recyclerView.smoothScrollToPosition(0);
			}
		});
		floatingActionButton.setOnClickListener(myListener);


//		data.add("1");
//		data.add("2");
//		data.add("3");

		View headView = inflater.inflate(R.layout.home_tab_a1_banner, null);
//        mBanner=(Banner) header.findViewById(R.id.banner);

		lm = new LinearLayoutManager(TabA1Fragment.this.getActivity());
		lm.setOrientation(OrientationHelper.VERTICAL);
		home_fragA1_recyclerView.setLayoutManager(lm);
		mAdapter = new MyAdapter(TabA1Fragment.this.getActivity(), home_fragA1_recyclerView);
//		home_tab_a1_banner_frameLayout = (FrameLayout) headView.findViewById(R.id.home_tab_a1_banner_frameLayout);
//		home_tab_a1_banner_myViewPager = (ViewPager) headView.findViewById(R.id.home_tab_a1_banner_myViewPager);
//		home_tab_a1_banner_myLinearLayout = (LinearLayout) headView.findViewById(R.id.home_tab_a1_banner_myLinearLayout);
		home_tab_a1_banner_RelativeLayout = (RelativeLayout) headView.findViewById(R.id.home_tab_a1_banner_RelativeLayout);
		banner = (Banner) headView.findViewById(R.id.home_tab_a1_Banner);
		img.add("http://192.168.1.101:8080/schoolapp/BannerImages/banner1.jpg");
		img.add("http://192.168.1.101:8080/schoolapp/BannerImages/banner2.jpg");
		img.add("http://192.168.1.101:8080/schoolapp/BannerImages/banner3.jpg");
		img.add("http://192.168.1.101:8080/schoolapp/BannerImages/banner4.jpg");

		//设置Banner参数
		//设置广告图片源
		banner.setImages(img)
				//设置Banner的滑动动画
				.setBannerAnimation(ZoomOutTranformer.class)
				//设置指示器的样式
				.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
				//设置Banner的指示器的显示位置
				.setIndicatorGravity(BannerConfig.CENTER)
//				.setIn
				//设置Banner用Glide框架加载图片
				.setImageLoader(new GlideImageLoader())
				//设置多久滑动一次
				.setDelayTime(2000)
				//开始轮播
				.start();
		//设置Banner的点击事件
		banner.setOnBannerListener(new OnBannerListener() {
			@Override
			public void OnBannerClick(int position) {
				switch (position){
					case 0:
						Toast.makeText(TabA1Fragment.this.getActivity(),"广告1",Toast.LENGTH_SHORT).show();
						break;
					case 1:
						Toast.makeText(TabA1Fragment.this.getActivity(),"广告2",Toast.LENGTH_SHORT).show();
						break;
					case 2:
						Toast.makeText(TabA1Fragment.this.getActivity(),"广告3",Toast.LENGTH_SHORT).show();
						break;
					case 3:
						Toast.makeText(TabA1Fragment.this.getActivity(),"广告4",Toast.LENGTH_SHORT).show();
						break;
				}
			}
		});

		mAdapter.setHeaderView(home_tab_a1_banner_RelativeLayout);
		home_fragA1_recyclerView.setAdapter(mAdapter);
	}


	//删除动态,要先删除该动态对应的评论和点赞
	public void deleteDynamic(final Dynamic dynamic) {
		//访问网络，新开线程
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					OkHttpClient client=new OkHttpClient();
					RequestBody requestBody=new FormBody.Builder()
							.add("dynaId",dynamic.getDynaId()+"")
							.build();
					Request request=new Request.Builder()
							.url("http://192.168.1.101:8080/schoolapp/deleteDynamic.action")
							.post(requestBody)
							.build();
					Response response=client.newCall(request).execute();

					refreshDynamic();
				}catch (Exception e){
					e.printStackTrace();
				}

			}
		}).start();
	}

	public void notifyDatas(){
		TabA1Fragment.this.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mAdapter.notifyDataSetChanged();

			}
		});
	}

	public void refreshDynamic(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				Log.d("autoRefresh()", "refresh");
				Log.d("onBindViewHolder", "refresh");
				try {
					startRecordIndex = 0;
					endRecordIndex = 5;
					dymanicList.clear();
					dynamicURL.clear();
					nineURLMap.clear();
					OkHttpClient okHttpClient = new OkHttpClient();

					RequestBody requestBody = new FormBody.Builder()
							.add("stuId", 1 + "")
							.add("startRecordIndex", startRecordIndex + "")
							.build();
					Request request = new Request.Builder()
							.url("http://192.168.1.101:8080/schoolapp/returnDynamic.action")
							.post(requestBody)
							.build();
					Response response = okHttpClient.newCall(request).execute();
					String responseJSON = response.body().string();

					Log.d("responseJSON", responseJSON);
					Gson gson = new Gson();

					List<Dynamic> dynamicList = gson.fromJson(responseJSON, new TypeToken<List<Dynamic>>() {
					}.getType());
					Log.d("dynamicList", dynamicList.size() + "");
					for (Dynamic dynamic : dynamicList) {
						Log.d("isGiveGood", dynamic.getDynaId() + "," + dynamic.isGiveGood());
					}
					for (int i = 0; i < dynamicList.size(); i++) {
						dymanicList.add(dynamicList.get(i));
						Log.d("dynamicMap", dymanicList.get(i).getDynaId() + dymanicList.get(i).getDynaContent() + dymanicList.get(i).getDynaPictures());
					}
					for (int i = 0; i < dymanicList.size(); i++) {
						Log.d("dynamicAdd", "true");
						dynamicURL.add(dymanicList.get(i).getUserHeader());
					}
					for (int j = 0; j < dymanicList.size(); j++) {
						nineURLMap.put((Integer) j, dymanicList.get(j).getDynaPictures());
					}
//							if(isFirstIn){
//								Log.d("rrr",true+"");
//								imageLoader.loadHeaderImages(firstItemPosition,1);
//                                imageLoader.loadNineImages(firstItemPosition,1);
//								isFirstIn=false;
//							}

				} catch (Exception e) {
					e.printStackTrace();
				}

				notifyDatas();


			}
		}).start();
	}




//	@Override
//	public void setUserVisibleHint(boolean isVisibleToUser) {
//
//		super.setUserVisibleHint(isVisibleToUser);
//		if(isVisibleToUser){
//			Log.d("TabA1FragsetUserVisi",isVisibleToUser+"");
//			myThread = new MyThread();
//			myThread.start();
//			Log.d("ThreadSum",Thread.activeCount()+"");
//		}else if(isVisibleToUser==false){
//			Log.d("TabA1FragsetUserVisi",isVisibleToUser+"");
////			myThread=null;
//			Log.d("ThreadSum",Thread.activeCount()+"");
//		}
//
//	}




	/**
	 * Fragment虽然有onResume和onPause的，但是这两个方法是Activity的方法，调用时机也是与Activity相同
	 * fragment中的onPause()和onResume()方法和所处于的Activity的调用时机相同
	 * */
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		myThread=null;
//		isRun=false;
//		Log.d("isRun", isRun+"");
//		myThread=null;
//		autoRefresh=false;
		img.clear();
//		Log.d("autoRefresh",autoRefresh+"");
		Log.d("TabA1Fragment","onPause()");
	}



	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		myThread=new MyThread();
		/**
		 * 线程调用start方法两次，就会有两个run方法在运行
		 * */
		//myThread.start();
		//myViewPager.setCurrentItem(currentIndex);
//		home_fragA1_recyclerView.findViewWithTag()
		Log.d("TabA1Fragment","onResume()");
	}

	//每切换掉一个fragment时,改fragment都会被销毁
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		TabA1Fragment.this.getActivity().unregisterReceiver(networkChangeReceiver);
//		myThread=null;
		Log.d("TabA1Fragment", "onDestroy已执行");
	}

    //点赞时修改后台数据的方法
	private void GiveOrCancelGood(final Dynamic dynamic){
        //访问网络很耗时，即新开线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    //post请求
                    RequestBody requestBody=null;

                    if(dynamic.isGiveGood()){
                        //如果之前的数据是点赞过的，则这次点赞时后台的点赞数减一
                        requestBody=new FormBody.Builder()
                                .add("giveOrCancel","cancelGood")
								.add("stuId",1+"")
                                .add("DynaId",dynamic.getDynaId()+"")
                                .build();

                        //设置动态的点赞为取消
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

}
