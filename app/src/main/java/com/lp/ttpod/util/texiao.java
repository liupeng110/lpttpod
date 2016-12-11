//package com.lp.ttpod.util;
//
///**
// * 717219917@qq.com
// * 2016/12/9  0:49
// */
//
//import android.app.ActivityGroup;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.widget.SlidingPaneLayout;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ExpandableListView;
//import android.widget.FrameLayout;
//import android.widget.TextView;
//
//import com.lanyes.adapter.LeftMenuExAdapter;
//import com.lanyes.adapter.LeftMenuExAdapter.MyItemListener;
//import com.lanyes.config.Config;
//import com.lanyes.config.MyApp;
//import com.lanyes.db.DbManager0;
//import com.lanyes.fragment.entity.ExceptionOrderEntity;
//import com.lanyes.fragment.entity.ExceptionSheetnoIsinsert;
//import com.lanyes.sundan.bean.DB_AliQRcode;
//import com.lanyes.widget.InfoDialog;
//import com.lp.ttpod.R;
//
//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//import org.greenrobot.eventbus.ThreadMode;
//import org.xutils.DbManager;
//import org.xutils.x;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
//
//package com.lanyes.sundan;
//
//@SuppressWarnings("deprecation")
//public class HostActivity extends ActivityGroup implements MyItemListener,
//        OnClickListener {
//    private ExpandableListView exListView;
//    private TextView tvName, tvBranch;
//    private SlidingPaneLayout pane;
//    private FrameLayout content; //更换的是这个布局
//    private View headView;
//    private InfoDialog msgDialog;
//
//    private int activityId = 3;
//    private int fragmentId = 0;
//
//    static DbManager db_xutils;
//
//    static DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
//            .setDbName("test.db")
//            // 不设置dbDir时, 默认存储在app的私有目录.
//            // .setDbDir(new File("/sdcard")) // "sdcard"的写法并非最佳实践, 这里为了简单,
//            // 先这样写了.
//            .setDbVersion(2).setDbOpenListener(new DbManager.DbOpenListener() {
//                @Override
//                public void onDbOpened(DbManager db) {
//                    // 开启WAL, 对写入加速提升巨大
//                    db.getDatabase().enableWriteAheadLogging();
//                }
//            }).setDbUpgradeListener(new DbManager.DbUpgradeListener() {
//                @Override
//                public void onUpgrade(DbManager db, int oldVersion,
//                                      int newVersion) {
//                    // TODO: ...
//                    // db.addColumn(...);
//                    // db.dropTable(...);
//                    // ...
//                    // or
//                    // db.dropDb();
//                }
//            });
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home_page);
//        EventBus.getDefault().register(this);
//        getIntentInfo();
//        initView();
//        content.requestFocus();
//        initLeftMenu();
//        DbManager0.getMaxGropID();
//
//    }
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        return super.dispatchTouchEvent(ev);
//    }
//
//    private void getIntentInfo() {
//        activityId = getIntent().getIntExtra("activityId", 3);
//        fragmentId = getIntent().getIntExtra("fragmentId", 0);
//    }
//
//    private void initView() {
//        msgDialog = new InfoDialog(HostActivity.this, R.style.myDialog);
//        pane = (SlidingPaneLayout) findViewById(R.id.sp);
//        content = (FrameLayout) findViewById(R.id.rl_content);
//    }
//
//    private void initLeftMenu() {
//        exListView = (ExpandableListView) findViewById(R.id.lv_left);
//        LeftMenuExAdapter exAdapter = new LeftMenuExAdapter(HostActivity.this,
//                this);
//        headView = LayoutInflater.from(HostActivity.this).inflate(
//                R.layout.layout_listview_top, null);
//        tvName = (TextView) headView.findViewById(R.id.str_name);
//        tvBranch = (TextView) headView.findViewById(R.id.str_branch_no);
//        headView.setOnClickListener(this);
//        exListView.addHeaderView(headView);
//
//        exListView.setAdapter(exAdapter);
//        int groupCount = exAdapter.getGroupCount();
//        for (int i = 0; i < groupCount; i++) {
//            exListView.expandGroup(i);
//        }
//        showActivity(activityId);
//
//        if (MyApp.getmInstance().getOperInfo() != null) {
//            tvName.setText(MyApp.getmInstance().getOperInfo().getCASHIER_NAME());
//        }
//        tvBranch.setText(MyApp.getmInstance().getShopCode());
//        if (pane.isOpen()) {
//            pane.closePane();
//        } else {
//            pane.openPane();
//        }
//    }
//
//    /****/
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void recfromOtherActivity(String str) {
//        L.i("car", "hostctvity中收到eventbus的消息:" + str);
//        if (str.equals("退出应用")) {
//            back();
//        }else if(str.contains("开单页的position")){
//            fragmentId=Integer.parseInt(str.substring(12,str.length()));
//        }
//
//    }
//
//    /** 菜单列表点击事件 */
//    @Override
//    public void onItemClick(int position, boolean isExpanded) {
//
//        // MyApp.getmInstance().ShowToast(position + "");
//
//        if (position == 3) {
//            if (isExpanded) {
//                exListView.collapseGroup(position);
//            } else {
//                exListView.expandGroup(position);
//            }
//        } else {
//            showActivity(position);
//        }
//    }
//
//    @Override
//    public void onClick(View arg0) {
//        // TODO Auto-generated method stub
//        showActivity(3);
//    }
//
//    private void showMsgNoPermission() {
//        if (!msgDialog.isShowing()) {
//            msgDialog.show();
//        }
//        msgDialog.showMsgNocancelBtn("没有权限");
//    }
//
//    private void showActivity(int position) {
//        HashMap<String, String> funMap = MyApp.getmInstance().getmFuncMap();
//        Intent intent = null;
//        switch (position) {
//            case Config.presaleselect_activity:// 预开单查询
//                if (funMap.get("预开单查询") != null
//                        && funMap.get("预开单查询").equals(Config.HASPERMISSION)) {
//                    intent = new Intent(HostActivity.this,
//                            PreSaleSelectActivity.class);
//                    setTab("tab01", intent);
//                } else {
//                    // 没有权限
//                    showMsgNoPermission();
//                }
//                break;
//            case Config.billing_activity:// 开单
//                if (funMap.get("开单") != null&& funMap.get("开单").equals(Config.HASPERMISSION)) {
//                    intent = new Intent(HostActivity.this,BillingActivity.class);
////				intent = new Intent(HostActivity.this,com.lanyes.sundan.car.Car_Activity_Billing.class);
//                    intent.putExtra("position", fragmentId);//
//                    setTab("tab02", intent);
//                } else {
//                    // 没有权限
//                    showMsgNoPermission();
//                }
//                break;
//            case Config.changesn_activity:// 更改串码
//                if (funMap.get("更改串码") != null
//                        && funMap.get("更改串码").equals(Config.HASPERMISSION)) {
//                    intent = new Intent(HostActivity.this, ChangeSnActivity.class);
//                    setTab("tab03", intent);
//                } else {
//                    // 没有权限
//                    showMsgNoPermission();
//                }
//                break;
//            case Config.person_activity://
//                intent = new Intent(HostActivity.this, PersonActivity.class);
//                setTab("tab04", intent);
//                break;
//            case Config.feedback_activity:// 顾客需求登记
//                if (funMap.get("顾客建议登记") != null
//                        && funMap.get("顾客建议登记").equals(Config.HASPERMISSION)) {
//
//                    intent = new Intent(HostActivity.this, FeedBackActivity.class);
//                    setTab("tab05", intent);
//                } else {
//                    // 没有权限
//                    showMsgNoPermission();
//                }
//
//                break;
//            case Config.jdetakestock_activity:// JDE盘点
//                if (funMap.get("JDE盘点") != null
//                        && funMap.get("JDE盘点").equals(Config.HASPERMISSION)) {
//                    intent = new Intent(HostActivity.this,
//                            JDETakeStockActivity.class);
//                    setTab("tab06", intent);
//                } else {
//                    // 没有权限
//                    showMsgNoPermission();
//                }
//                break;
//
//            case Config.codetakestock_activity:// 串码盘点
//                if (funMap.get("串码盘点") != null
//                        && funMap.get("串码盘点").equals(Config.HASPERMISSION)) {
//                    intent = new Intent(HostActivity.this,
//                            CodeTakeStockActivity.class);
//                    setTab("tab07", intent);
//                } else {
//                    // 没有权限
//                    showMsgNoPermission();
//                }
//                break;
//            case Config.setting_avctivity:// 系统设置
//                intent = new Intent(HostActivity.this, activity8.class);
//                setTab("tab08", intent);
//                break;
//            case Config.warehousestock_activity:// 总仓库存查询
//                if (funMap.get("总仓库存查询接口") != null
//                        && funMap.get("总仓库存查询接口").equals(Config.HASPERMISSION)) {
//                    intent = new Intent(HostActivity.this,
//                            WareHouseStockActivity.class);
//                    setTab("tab09", intent);
//                } else {
//                    // 没有权限
//                    showMsgNoPermission();
//                }
//                break;
//            case Config.branchstock_activity:// 门店库存查询
//                if (funMap.get("门店库存查询接口") != null
//                        && funMap.get("门店库存查询接口").equals(Config.HASPERMISSION)) {
//                    intent = new Intent(HostActivity.this,
//                            BranchStockActivity.class);
//                    setTab("tab010", intent);
//                } else {
//                    // 没有权限
//                    showMsgNoPermission();
//                }
//                break;
//            case Config.ledgerstock_activity:// 分类账查询
//                if (funMap.get("分类账查询接口") != null
//                        && funMap.get("分类账查询接口").equals(Config.HASPERMISSION)) {
//                    intent = new Intent(HostActivity.this,
//                            LedgerStockActivity.class);
//                    setTab("tab11", intent);
//                } else {
//                    // 没有权限
//                    showMsgNoPermission();
//                }
//                break;
//            case Config.itm_activity:// 货品搜索
//                if (funMap.get("货品查询接口") != null
//                        && funMap.get("货品查询接口").equals(Config.HASPERMISSION)) {
//                    intent = new Intent(HostActivity.this, ItmActivity.class);
//                    setTab("tab12", intent);
//                } else {
//                    // 没有权限
//                    showMsgNoPermission();
//                }
//                break;
//            case Config.saleorder_activity:// 销售单查询
//                if (funMap.get("销售单查询接口") != null
//                        && funMap.get("销售单查询接口").equals(Config.HASPERMISSION)) {
//                    intent = new Intent(HostActivity.this, SaleOrderActivity.class);
//                    setTab("tab13", intent);
//                } else {
//                    // 没有权限
//                    showMsgNoPermission();
//                }
//                break;
//            case Config.orderlist_activity:// 销售清单查询
//                if (funMap.get("销售清单查询接口") != null
//                        && funMap.get("销售清单查询接口").equals(Config.HASPERMISSION)) {
//                    intent = new Intent(HostActivity.this, OrderListActivity.class);
//                    setTab("tab14", intent);
//                } else {
//                    // 没有权限
//                    showMsgNoPermission();
//                }
//                break;
//            case Config.purchase_activity:// 采购单查询
//                if (funMap.get("采购单查询接口") != null
//                        && funMap.get("采购单查询接口").equals(Config.HASPERMISSION)) {
//                    intent = new Intent(HostActivity.this, PurchaseActivity.class);
//                    setTab("tab15", intent);
//                } else {
//                    // 没有权限
//                    showMsgNoPermission();
//                }
//                break;
//            case Config.purchaselist_activity:// 采购清单查询
//                if (funMap.get("采购清单查询接口") != null
//                        && funMap.get("采购清单查询接口").equals(Config.HASPERMISSION)) {
//                    intent = new Intent(HostActivity.this,
//                            PurChaseListActivity.class);
//                    setTab("tab16", intent);
//                } else {
//                    // 没有权限
//                    showMsgNoPermission();
//                }
//                break;
//            case Config.transfer_activity:// 调拨单查询
//                if (funMap.get("调拨单查询接口") != null
//                        && funMap.get("调拨单查询接口").equals(Config.HASPERMISSION)) {
//                    intent = new Intent(HostActivity.this, TransferActivity.class);
//                    setTab("tab17", intent);
//                } else {
//                    // 没有权限
//                    showMsgNoPermission();
//                }
//                break;
//            case Config.mintenancestock_activity:// 维修库存查询
//                if (funMap.get("维修库存查询接口") != null
//                        && funMap.get("维修库存查询接口").equals(Config.HASPERMISSION)) {
//                    intent = new Intent(HostActivity.this,
//                            MaintenanceStockActivity.class);
//                    setTab("tab18", intent);
//                } else {
//                    // 没有权限
//                    showMsgNoPermission();
//                }
//                break;
//            case Config.Exception_activity:// 异常维修
//                intent = new Intent(HostActivity.this, ExceptionActivity.class);
//                setTab("tab19", intent);
//                break;
//            case Config.Extras_activity:// 附加功能
//                L.i("进入附加功能:" + Config.Extras_activity);
//                intent = new Intent(HostActivity.this, ExceptionOrderActivity.class);
//                setTab("tab20", intent);
//                // startActivity(intent);
//                break;
//        }
//        pane.closePane();
//    }
//
//    private void setTab(String id, Intent intent) {
//        Log.i("content", content + "");
//        content.removeAllViews();
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        content.addView(getLocalActivityManager().startActivity(id, intent).getDecorView());
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK&& event.getAction() == KeyEvent.ACTION_DOWN) {
//            // exitBy2Click();
//            back();
//            return true;
//        }
//        else {
//            try{
//                return false;
////			return super.dispatchKeyEvent(event);
//            }catch
//                    (Exception e){
//                e.printStackTrace();
//                return false;
//            }
//
//        }
//    }
//
//
////	// 重写按键监听事件
////	@Override
////	public boolean onKeyDown(int keyCode, KeyEvent event) {
////		String topclazz = "";
////		topclazz = this.getLocalActivityManager().getCurrentActivity().getClass().getName();
//////		topclazz = topclazz.substring(topclazz.lastIndexOf(".") + 1,topclazz.length());
////		L.i("car", "HostActivity按键中,当前最上层类名:" + topclazz);
////		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {// 返回键
////			if (topclazz.equals("HostActivity")) {        // 本类中,消费
////				back();
////				return true;
////			} else {// 其他类在顶层,进行分发
////				if (pane.isOpen()) {//打开会先闭合
////					pane.closePane();
////					return true;
////				}
////				Key_Event keyevent = new Key_Event();
////				keyevent.setClazz(topclazz);
////				keyevent.setEvent(event);
////				keyevent.setKeyCode(keyCode);
////				EventBus.getDefault().post(keyevent);//进行按键事件分发 到各个activity
////
////
////				return false;
////			}
////		} else {// 其他键不处理
////			return false;
////		}
////
////	}
//
////	@Override
////	public boolean onKeyDown(int keyCode, KeyEvent event) {
////		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
////				&& event.getAction() == KeyEvent.ACTION_DOWN) {
////			// exitBy2Click();
////			back();
////			return true;
////		}  else {
////
////			return false;
//////			return super.dispatchKeyEvent(event);
////		}
////	}
//
//
//
////	 // 重写返回键监听
////	 @Override
////	 public boolean onKeyDown(int keyCode, KeyEvent event) {
////	 L.i("进入onkeydown()");
////	 L.i("car","当前最上层类名:"+this.getLocalActivityManager().getCurrentActivity().getClass().getName());
////
////	 isForeground(MyApp.getmInstance(),"com.lanyes.sundan.car.Car_Billing_Activity");
////
////	 if (event.getKeyCode() == KeyEvent.KEYCODE_BACK&& event.getAction() ==
////	 KeyEvent.ACTION_DOWN) {
////	 // exitBy2Click();//注释
////	 // back(); //在用
////	 // return true; //在用
////	 if(isForeground(MyApp.getmInstance(),"Car_Billing_Activity.class")){
////	 L.i("car","billingactivity在最上层");
////	 return false ;
////	 }else {
////
////	 back();
////	 return true;
////	 }
////
////
////	 // return super.dispatchKeyEvent(event);
////	 } else {
////	 try{
////
////	 return false;//
////	 // return super.dispatchKeyEvent(event);
////	 }catch(Exception e){
////	 e.printStackTrace();
////	 return false;//
////	 }
////
////	 // return false;//
////
////	 }
////
////	 }
//
//    private InfoDialog mInfoDialog;
//
//    private void back() {
//        L.i("进入back()");
//        if (mInfoDialog == null) {
//            mInfoDialog = new InfoDialog(HostActivity.this, R.style.myDialog);
//
//        }
//        mInfoDialog.show();
//        mInfoDialog.showMsg("是否要退出智能收银系统！", new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//                exit();
//            }
//        });
//    }
//
//    public static void exit() {
//        // Intent startMain = new Intent(Intent.ACTION_MAIN);
//        // startMain.addCategory(Intent.CATEGORY_HOME);
//        // startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        // startActivity(startMain);
//
//        db_xutils = x.getDb(daoConfig);
//
//        try {
//
//            // 需要删除 多余的 DB_AliQRcode ExceptionOrderEntity
//            // ExceptionSheetnoIsinsert
//            ArrayList<DB_AliQRcode> qrlist = (ArrayList<DB_AliQRcode>) db_xutils
//                    .selector(DB_AliQRcode.class).findAll();
//
//            if (qrlist.size() > 1) {// 必须有数据
//
//                for (int a = 1; a < qrlist.size(); a++) {// 从第二个开始
//                    // for(int a=qrlist.size();a>1;a--){
//                    db_xutils.delete(qrlist.get(a - 1));
//
//                }
//
//            }
//
//            // db_xutils.delete();
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//        }
//
//        try {
//
//            // 需要删除 多余的 DB_AliQRcode ExceptionOrderEntity
//            // ExceptionSheetnoIsinsert
//            ArrayList<ExceptionOrderEntity> excorderenlist = (ArrayList<ExceptionOrderEntity>) db_xutils
//                    .selector(ExceptionOrderEntity.class).findAll();
//            if (excorderenlist.size() > 1) {// 必须有数据
//
//                for (int a = 1; a < excorderenlist.size(); a++) {// 从第二个开始
//                    // for(int a=excorderenlist.size();a>1;a--){
//                    db_xutils.delete(excorderenlist.get(a - 1));
//
//                }
//
//            }
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//        }
//
//        try {
//
//            // 需要删除 多余的 DB_AliQRcode ExceptionOrderEntity
//            // ExceptionSheetnoIsinsert
//            ArrayList<ExceptionSheetnoIsinsert> qrlist = (ArrayList<ExceptionSheetnoIsinsert>) db_xutils
//                    .selector(ExceptionSheetnoIsinsert.class).findAll();
//            if (qrlist.size() > 1) {// 必须有数据
//
//                for (int a = 1; a < qrlist.size(); a++) {// 从第二个开始
//                    // for(int a=qrlist.size();a>1;a--){
//                    db_xutils.delete(qrlist.get(a - 1));
//
//                }
//
//            }
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//        }
//
//        try {
//
//            L.i("productinfo删除结果:" + DbManager0.deleteProductinfo());
//
//        } catch (Exception e) {
//
//            L.i("productinfo删除异常");
//            e.printStackTrace();
//
//        }
//
//        try {
//
//            DbManager0.deletePromotion();
//
//        } catch (Exception e) {
//
//            L.i("promotion删除异常");
//            e.printStackTrace();
//
//        }
//
//        try {// 这里清空insurance 延保信息表
//
//            L.i("HOST中INSURANCE删除结果:" + DbManager0.deleteInsurance());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////		finish();
//        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(0);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1) {
//            Intent intent = new Intent(HostActivity.this, BillingActivity.class);
//            intent.putExtra("position", Config.SALE_SHOPCAR);
//            setTab("tab02", intent);
//        }
//    }
//
//}
