package com.wisdom.project.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import cn.jpush.android.service.PushService;

/**
 * @author HanXueFeng
 * @ProjectName project： DaidaHaiProject
 * @class package：com.wisdom.project.base
 * @class describe：
 * @time 2019/2/14 11:48
 * @change
 */
public class BoardcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent pushintent = new Intent(context, PushService.class);
        //启动极光推送的服务
        context.startService(pushintent);
    }
}
