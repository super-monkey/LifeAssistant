
package com.supermonkey.lifeassistant.bridge;

import android.content.Context;

import com.supermonkey.lifeassistant.bridge.cache.localstorage.LocalFileStorageManager;
import com.supermonkey.lifeassistant.bridge.cache.sharePref.EBSharedPrefManager;
import com.supermonkey.lifeassistant.bridge.security.SecurityManager;
import com.supermonkey.lifeassistant.bridge.http.OkHttpManager;

import java.util.HashMap;

/**
 * @author supermonkey
 * @version 1.0
 * @date 创建时间：2017/4/20
 * @Description <中间连接层>
 */
public class BridgeFactory {

    private static BridgeFactory model;

    private HashMap<String, Object> mBridges;

    private BridgeFactory() {
        mBridges = new HashMap<String, Object>();
    }

    public static void init(Context context) {
        model = new BridgeFactory();
        model.iniLocalFileStorageManager();
        model.initPreferenceManager();
        model.initSecurityManager();
        model.initUserSession();
        model.initCoreServiceManager(context);
        model.initOkHttpManager();
    }

    public static void destroy() {
        model.mBridges = null;
        model = null;
    }

    /**
     * 初始化本地存储路径管理类
     */
    private void iniLocalFileStorageManager() {
        LocalFileStorageManager localFileStorageManager = new LocalFileStorageManager();
        model.mBridges.put(Bridges.LOCAL_FILE_STORAGE, localFileStorageManager);
        BridgeLifeCycleSetKeeper.getInstance().trustBridgeLifeCycle(localFileStorageManager);
    }

    /**
     * 初始化SharedPreference管理类
     */
    private void initPreferenceManager() {
        EBSharedPrefManager ebSharedPrefManager = new EBSharedPrefManager();
        model.mBridges.put(Bridges.SHARED_PREFERENCE, ebSharedPrefManager);
        BridgeLifeCycleSetKeeper.getInstance().trustBridgeLifeCycle(ebSharedPrefManager);
    }

    /**
     * 网络请求管理类
     */
    private void initOkHttpManager() {
        OkHttpManager mOkHttpManager = new OkHttpManager();
        model.mBridges.put(Bridges.HTTP, mOkHttpManager);
        BridgeLifeCycleSetKeeper.getInstance().trustBridgeLifeCycle(mOkHttpManager);
    }

    /**
     * 初始化安全模块
     */
    private void initSecurityManager() {
        SecurityManager securityManager = new SecurityManager();
        model.mBridges.put(Bridges.SECURITY, securityManager);
        BridgeLifeCycleSetKeeper.getInstance().trustBridgeLifeCycle(securityManager);
    }

    /**
     * 初始化用户信息模块
     */
    private void initUserSession() {
    }

    /**
     * 初始化Tcp服务
     *
     * @param context
     */
    private void initCoreServiceManager(Context context) {
    }


    private void initDBManager() {
    }

    /**
     * 通过bridgeKey {@link Bridges}来获取对应的Bridge模块
     *
     * @param bridgeKey {@link Bridges}
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <V extends Object> V getBridge(String bridgeKey) {
        final Object bridge = model.mBridges.get(bridgeKey);
        if (bridge == null) {
            throw new NullPointerException("-no defined bridge-");
        }
        return (V) bridge;
    }
}
