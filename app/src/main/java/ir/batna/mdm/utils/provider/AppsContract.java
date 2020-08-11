package ir.batna.mdm.utils.provider;

import static ir.batna.mdm.utils.provider.MyProvider.AUTHORITY;

public class AppsContract {



    /**
     * The Uri to access the Apps.
     */
    public static final String APPS = "apps";
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + APPS;
    static final String MESSAGING_APP_TAG = "messaging";
    static final String CLOUD_APP_TAG = "cloud";




}



