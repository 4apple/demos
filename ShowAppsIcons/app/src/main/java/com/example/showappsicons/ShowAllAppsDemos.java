package com.example.showappsicons;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShowAllAppsDemos extends Activity {
    private ArrayList<AppInfo> mAllAppsInfo=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAllAppsIcon();
        //ImageView iv = new ImageView(this);
//        SharedPreferences sharedPreference = getSharedPreferences("usage",)
        RecyclerView rv = new RecyclerView(this);
        //setContentView(R.layout.activity_show_all_apps_demos);
        setContentView(rv);
        rv.setLayoutManager(new GridLayoutManager(this, 6));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //
        rv.setAdapter(new RecyclerView.Adapter() {
            class ViewHolder extends RecyclerView.ViewHolder {
                private ImageView iv;

                public ViewHolder(ImageView itemView) {
                    super(itemView);
                    iv = itemView;
                    GridLayoutManager.LayoutParams lp = new GridLayoutManager.LayoutParams(180,180);
                    //iv.setLayoutParams(new WindowManager.LayoutParams(180,180,0,0,0));
                    lp.setMargins(20, 30, 20, 10);
                    iv.setLayoutParams(lp);
                }

                public ImageView getIv() {
                    return iv;
                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                return new ViewHolder(new ImageView(viewGroup.getContext()));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
                ViewHolder vh = (ViewHolder) viewHolder;
                final AppInfo appInfo = mAllAppsInfo.get(i);
                vh.getIv().setImageDrawable(appInfo.appIcon);
                vh.getIv().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = getPackageManager().getLaunchIntentForPackage(appInfo.packageName);
                        //intent.setComponent(new ComponentName(appInfo.packageName,appInfo.className));
                        appInfo.usage++;
                        startActivity(intent);
                    }
                });
            }

            @Override
            public int getItemCount() {
                if (mAllAppsInfo != null) {

                    return mAllAppsInfo.size();
                }
                System.out.println("minguo.wan getItemCount()= 0");
                return 0;
            }
        });
//        iv.setImageDrawable(getIcon());
    }

    private void initAllAppsIcon() {
        mAllAppsInfo = new ArrayList<AppInfo>();
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN,null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        mainIntent.setPackage(null);
        List<ResolveInfo> infos = getPackageManager().queryIntentActivities(mainIntent,0);
        Iterator<ResolveInfo> iterator = infos.iterator();

        while(iterator.hasNext()) {
            AppInfo appInfo = new AppInfo();
            ResolveInfo resolveInfo = iterator.next();
            appInfo.appIcon = resolveInfo.loadIcon(getPackageManager());
            appInfo.packageName =resolveInfo.activityInfo.packageName;
            //System.out.println("++class++" + appInfo.packageName.toString());
            appInfo.className = resolveInfo.activityInfo.name;
            //System.out.println("++class++" + appInfo.className.toString());
            appInfo.versionName = "";
            appInfo.versionCode = 0;
            mAllAppsInfo.add(appInfo);
        }
/*
        for(int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            //(mFlags & PackageManager.FLAG_PERMISSION_SYSTEM_FIXED) != 0;
            if((packageInfo.applicationInfo.flags& ApplicationInfo.FLAG_SYSTEM)==0)
            {
                //not system apps
                AppInfo appInfo = new AppInfo();

                appInfo.packageName = packageInfo.packageName;
                appInfo.versionName = packageInfo.versionName;
                appInfo.versionCode = packageInfo.versionCode;
                appInfo.appIcon = packageInfo.applicationInfo.loadIcon(getPackageManager());
                //mAllAppsInfo.add(appInfo);
            }
            else
            {
                //system apps　　　　　
                System.out.println("minguo,system apps:" + packageInfo.packageName);
            }
        }
        */
    }

    private Drawable getIcon(){
        //ArrayList<AppInfo> appList = new ArrayList<AppInfo>();
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
        PackageInfo packageInfo = packages.get(packages.size()-1);
        System.out.println(packageInfo.packageName);
        return packageInfo.applicationInfo.loadIcon(getPackageManager());

//        for(int i=0;i<packages.size();i++) {
//            PackageInfo packageInfo = packages.get(i);
////            AppInfo tmpInfo =new AppInfo();
////            tmpInfo.appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
////            tmpInfo.packageName = packageInfo.packageName;
////            tmpInfo.versionName = packageInfo.versionName;
////            tmpInfo.versionCode = packageInfo.versionCode;
////            tmpInfo.appIcon = packageInfo.applicationInfo.loadIcon(getPackageManager());
////            appList.add(tmpInfo);
//
//        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_show_all_apps_demos, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
