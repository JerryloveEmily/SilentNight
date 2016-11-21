package com.jerry.silentnight.util;

import android.view.View;
import android.widget.AdapterView;

/**
 * 避免双击的监听工具
 * Created by Jerry on 2016/5/11.
 */
public class AvoidDoubleClickUtil {

    private AvoidDoubleClickUtil(){}

    public static abstract class OnClickListener implements View.OnClickListener{

        @Override
        public final void onClick(View v) {
            if (isDoubleClick()){
                onDoubleClick(v);
                return;
            }
            onSingleClick(v);
        }

        public abstract void onSingleClick(View v);
        public void onDoubleClick(View v){
        }
    }

    public static abstract class OnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public final void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (isDoubleClick()){
                onItemDoubleClick(parent, view, position, id);
                return;
            }
            onItemSingleClick(parent, view, position, id);
        }

        public abstract void onItemSingleClick(AdapterView<?> parent, View view, int position, long id);
        public void onItemDoubleClick(AdapterView<?> parent, View view, int position, long id){

        }
    }

    public static final long SPACE_TIME = 500;
    private static long lastClickTime;
    private static boolean isDoubleClick(){
        boolean flag = false;
        long delt = System.currentTimeMillis() - lastClickTime;
        if (SPACE_TIME > delt){
            flag = true;
        }
        lastClickTime = System.currentTimeMillis();
        return flag;
    }
}
