package u.com.runtime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * 创建日期：2019/10/17 on 17:01
 * 描述:
 * 作者: lvzishen
 */
public class ActivityBuilder {

    public static final ActivityBuilder INSTANCE = new ActivityBuilder();

    public void startActivity(Context context, Intent intent) {
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }
}
