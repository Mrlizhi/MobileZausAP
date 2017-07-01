package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.view;

import android.util.Log;

import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.RandomUtil;

/**
 * Created by lizhihao on 2017/3/15.
 */

public class UaHelper {
    //QQ浏览器
    public static String QQ_BROWER_UA="User-Agent, MQQBrowser/26 Mozilla/5.0 (Linux; U; Android 2.3.7; zh-cn; MB200 Build/GRJ22; CyanogenMod-7) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";
    //UC浏览器(标准)
    public static String UC_BROWER_UA="User-Agent, NOKIA5700/ UCWEB7.0.2.37/28/999";
    //UC浏览器（opra）
    public static String UCOPER_BROWER_UA="User-Agent, Mozilla/4.0 (compatible; MSIE 6.0; ) Opera/UCWEB7.0.2.37/28/999";
    //opra浏览器
    public static String OPRA_BROWER_UA="User-Agent, Opera/9.80 (Android 2.3.4; Linux; Opera Mobi/build-1107180945; U; en-GB) Presto/2.8.149 Version/11.10";
    public static  String UAItem="Mozilla/5.0 (Linux; U; Android 4.3; zh-cn; GT-N7100 Build/JSS15J) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.4.4; zh-cn; M355 Build/KTU84P) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.2.2; zh-cn; H30-T10 Build/HuaweiH30-T10) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.1.2; zh-cn; GT-I9300 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.2.1; zh-cn; vivo Y19t Build/JOP40D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.1.2; zh-cn; GT-I8262D Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.2.2; zh-cn; 2013023 Build/HM2013023) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.2.1; zh-CN; 2013022 Build/HM2013022) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.3; zh-cn; ZTE Q802T Build/JLS36C) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.3; zh-cn; Coolpad 8702 Build/JLS36C) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; Android 4.3; zh-cn; SAMSUNG-GT-I9308_TD/1.0 Android/4.3 Release/11.15.2013 Browser/AppleWebKit534.30 Build/JSS15J) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.2.2; zh-cn; vivo X510t Build/JDQ39) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.4.4; zh-cn; MX4 Pro Build/KTU84P) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.2.2; zh-cn; vivo Y20T Build/JDQ39) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.2.2; zh-cn; R831T Build/JDQ39) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.3; zh-cn; X9007 Build/JLS36C) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.0.4; zh-cn; GT-S7562 Build/IMM76I) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.2.1; zh-cn; R815T Build/JOP40D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.2.2; zh-cn; R831T Build/JDQ39) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.2.2; zh-cn; Hol-T00 Build/HUAWEIHol-T00) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.0.3; zh-cn; Coolpad 8076D Build/IML74K Profile/MIDP-2.0 Configuration/CLDC-1.1) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.2.2; zh-cn; H30-U10 Build/HuaweiH30-U10) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.2.1; zh-cn; R819T Build/JOP40D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.1.2; zh-cn; SCH-I739 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.3; zh-cn; R2017 Build/JLS36C) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.3; zh-cn; ZTE Q505T Build/JLS36C) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.3; zh-cn; GT-I9300 Build/JSS15J) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.0.4; zh-cn; R813T Build/IMM76D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.2.1; zh-cn; MI 3 Build/JOP40D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.1.2; zh-cn; SCH-I829 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.1.2; zh-cn; GT-N7100 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.4.4; zh-CN; HM NOTE 1LTE Build/KTU84P) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.3; zh-cn; R7007 Build/JLS36C) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.3; zh-cn; N5117 Build/JLS36C) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.0.4; zh-cn; vivo Y3t Build/IMM76D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.0.3; zh-cn; OPPOX907 Build/IML74K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.3; zh-cn; R8007 Build/JLS36C) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.3; zh-cn; R6007 Build/JLS36C) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.2.1; zh-cn; U707T Build/JOP40D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.1.1; zh-cn; vivo X1 Build/JRO03C) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.2.1; zh-cn; R819T Build/JOP40D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-CN; MX4 Build/KOT49H) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.1.1; zh-CN; MI 2 Build/JRO03L) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.1.2; zh-cn; vivo Y11 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI\n" +
            "Mozilla/5.0 (Linux; U; Android 4.0.4; zh-cn; GT-S7562i Build/IMM76I) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI";

    /**
     * 拿到移动端UA集合
     * @return
     */
    public static String getMoblieUA(){
        String[] UA=new String[100];
        UA=UAItem.split("\n|\r");
        int Random= RandomUtil.getRandom(0,100);
        Log.i("lizhihao---","----改得的UA是："+UA[Random]);
        return  UA[Random];
    }
}
