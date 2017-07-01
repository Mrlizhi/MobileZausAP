package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.operation;

import android.os.Handler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.exception.WebElementNotFound;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium.By;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium.SgSolo;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium.WebElement;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.LogUtil;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.OperationSleeper;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.RandomUtil;
import dlg.lizhihao.com.mobilezausap.SlGeneral.task.model.CTagCompress;

/**
 * Created by sogood on 16/11/7.
 */

public class RandClickByConditionOperation extends BaseOperation {

    private static final String TAG = RandClickByConditionOperation.class.getSimpleName();

    private WebView mWebView;
    private int mLinkOperatorType;
    private int mRand;
    private String[] mAttrNames;
    private String[] mAttrValues;
    private int[] mMatchTypes;
    private String[] mAttrNames2;
    private String[] mAttrValues2;
    private int[] mMatchTypes2;
    private String[] mRmAttrNames;
    private String[] mRmAttrValues;
    private int[] mRmMatchTypes;
    private int stayMin;
    private int stayMax;
    private int index;
    private String src;

    private boolean mJumpFinish;

    public RandClickByConditionOperation(WebView webView, int rand, int stayMin, int stayMax, String[] attrNames, String[] attrValues, int[] matchType,
                              String[] attrNames2, String[] attrValues2, int[] matchType2, String[] rmAttrNames,
                              String[] rmAttrValue, int[] rmMatchType, int linkOperatorType, int index,String src) {
        this.mWebView = webView;
        this.mLinkOperatorType = linkOperatorType;
        this.mRand = rand;
        this.mAttrNames = attrNames;
        this.mAttrValues = attrValues;
        this.mMatchTypes = matchType;
        this.mAttrNames2 = attrNames2;
        this.mAttrValues2 = attrValues2;
        this.mMatchTypes2 = matchType2;
        this.mRmAttrNames = rmAttrNames;
        this.mRmAttrValues = rmAttrValue;
        this.mRmMatchTypes = rmMatchType;
        this.stayMin = stayMin;
        this.stayMax = stayMax;
        this.index = index;
        this.src=src;
    }
    @Override
    public int doExecute(Handler handler, final WebView webView, final SgSolo solo) {
        LogUtil.i(TAG, "advertising 点击概率: " + mRand);
        if (!RandomUtil.isHit(mRand, 1000)) {
            LogUtil.i(TAG, "advertising 没中，不用进行点击");
            return JUMP;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                mWebView.setWebViewClient(new RandClickByConditionOperation.MyWebViewClient());
            }
        });

        //查找第一页中是否能找到符合条件的元素
        final List<WebElement> rslList = new ArrayList<>();
        List<WebElement> webElementList = solo.getCurrentWebElements();
        rslList.addAll(filt(webElementList));
        List<WebElement> lastWebElementList = webElementList;
        int pageCount = 0;
        //找不到符合条件的元素则翻页查找
        while(rslList.size() == 0 && pageCount < 45 && mAttrNames != null) {
            pageCount ++;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    LogUtil.i(TAG, "advertising 翻页查找");
                    webView.pageDown(true);
                }
            });
            OperationSleeper.sleep(RandomUtil.getRandom(5000, 10000));
            webElementList = solo.getCurrentWebElements();
            rslList.addAll(filt(webElementList));

            if (isSame(lastWebElementList, webElementList)) {
                LogUtil.i(TAG, "advertising 已经到达底页");
                break;
            }
            lastWebElementList = webElementList;
        }

        int sleepTime = RandomUtil.getRandom(stayMin, stayMax);
        LogUtil.i(TAG, "advertising 睡眠" + sleepTime + "s后再点击");
        OperationSleeper.sleep(sleepTime * 1000);

        WebElement clickElement = null;
        //点击筛选出来的元素
        if (0 == index && rslList.size() > 1) {
            clickElement = rslList.get(RandomUtil.getRandom(0, rslList.size()));
        } else if (index > 0 && rslList.size() >= index) {
            clickElement = rslList.get(index - 1);
        } else if (0 == index && rslList.size() == 1) {
            clickElement = rslList.get(0);
        }

        if (clickElement == null) { //如果为空,则随机点击一个a标签
            List<WebElement> aWebElementList = new ArrayList<>();
            for (WebElement e : webElementList) {
                if (e.getTagName().equalsIgnoreCase("a")) {
                    aWebElementList.add(e);
                }
            }
            if (0 < aWebElementList.size()) {
                clickElement = aWebElementList.get(RandomUtil.getRandom(0, aWebElementList.size() - 1));
            }
        }
        if (null != clickElement) {
            LogUtil.i(TAG, "advertising index = " + index +", 点击的元素" + clickElement.getTagName() + " " + clickElement.getAttributes());
            try {

//                <Books>
//                <book author="John" year="2009" language="En">Hello</book>
//                <book author="John" year="2010" language="En">Bye</book>
//                <book author="John" year="2009" language="Chs">Good</book>
//                <book author="Mike" year="2009" language="En">Thanks</book>
//                </Books>
//                Books/book[@author='John' and @year='2009' and @language='En']

//                String xpath = "//%s[@%s='%s'";
//                // index = 1, 点击的元素A {href=update_log.html}
//                Set<Map.Entry<String, String>> entrySet = clickElement.getAttributes().entrySet();
//                Iterator itor = entrySet.iterator();
//                boolean isFirst = true;
//                while (itor.hasNext()) {
//                    Map.Entry<String, String> attrEntry = (Map.Entry<String, String>) itor.next();
//                    String attrName = attrEntry.getKey();
//                    String attrValue = attrEntry.getValue();
//                    if (isFirst) {
//                        xpath = String.format(xpath, clickElement.getTagName(), attrName, attrValue);
//                        isFirst = false;
//                    } else {
//                        xpath += " and @" + attrName + "='" + attrValue + "'";
//                    }
//                }
//                xpath += "]";
//                LogUtil.i(TAG, xpath);
//                solo.clickOnWebElement(By.xpath(xpath));

                LogUtil.i(TAG, "advertising solo.clickOnWebElement(By.index(" + webElementList.indexOf(clickElement) + "))");
                solo.clickOnWebElement(By.index(webElementList.indexOf(clickElement)));

                //判断点击后是否有跳转成功,暂时定为有跳转成功该点击操作才算成功
                int loopTimes = 0;
                while (!mJumpFinish && loopTimes < (LOAD_WEB_TIMEOUT / OperationSleeper.sleepMiniDuration)) {
                    OperationSleeper.sleepMini();
                    loopTimes ++;
                    LogUtil.i(TAG, "advertising load web finish = " + mJumpFinish + ", loopTimes = " + loopTimes);
                }
            } catch (WebElementNotFound webElementNotFound) {
                webElementNotFound.printStackTrace();
                return FAIL;
            }
        }
        return SUCC;
    }
    private List<WebElement> filt(List<WebElement> webElementList) {
        LogUtil.i(TAG, "advertising webElementList.size = " + webElementList.size());
        List<WebElement> rslList = new ArrayList<>();
        if (mAttrNames != null && mAttrNames .length > 0) {
            for (int i = 0; i <  mAttrNames.length; i ++ ) {
                LogUtil.i(TAG, "advertising mAttrNames:" + i + " = " + mAttrNames[i]);
                LogUtil.i(TAG, "advertising mAttrValues:" + i + " = " + mAttrValues[i]);
            }
            rslList = filt(webElementList, mAttrNames, mAttrValues, mMatchTypes, mLinkOperatorType);
            LogUtil.i(TAG, "advertising 第一次过滤后，剩余的元素： " + rslList.size());
            for (int i = 0; i < rslList.size(); i++) {
                LogUtil.i(TAG, rslList.get(i).getTagName() + " " + rslList.get(i).getAttributes());
            }
        }

        if (mAttrNames2 != null && mAttrNames2.length > 0) {
            for (int i = 0; i <  mAttrNames2.length; i ++ ) {
                LogUtil.i(TAG, "advertising mAttrNames2:" + i + " = " + mAttrNames2[i]);
                LogUtil.i(TAG, "advertising mAttrValues2:" + i + " = " + mAttrValues2[i]);
            }
            rslList = filt(rslList, mAttrNames2, mAttrValues2, mMatchTypes2, mLinkOperatorType);
            LogUtil.i(TAG, "advertising 第二次过滤后，剩余的元素： " + rslList.size());
            for (int i = 0; i < rslList.size(); i++) {
                LogUtil.i(TAG, rslList.get(i).getTagName() + " " + rslList.get(i).getAttributes());
            }
        }
        if (mRmAttrNames != null && mRmAttrNames.length > 0) {
            for (int i = 0; i <  mRmAttrNames.length; i ++ ) {
                LogUtil.i(TAG, "advertising mRmAttrNames:" + i + " = " + mRmAttrNames[i]);
                LogUtil.i(TAG, "advertising mRmAttrValues:" + i + " = " + mRmAttrValues[i]);
            }
            List<WebElement> rmList = filt(rslList, mRmAttrNames, mRmAttrValues, mRmMatchTypes, mLinkOperatorType);
            rslList.removeAll(rmList);
            LogUtil.i(TAG, "advertising 第三次过滤后，剩余的元素： " + rslList.size());
            for (int i = 0; i < rslList.size(); i++) {
                LogUtil.i(TAG, rslList.get(i).getTagName() + " " + rslList.get(i).getAttributes());
            }
        }
        return rslList;
    }
    /**
     * 过滤出符合条件的Web元素
     * @param webElementList
     * @param attrNames
     * @param attrValues
     * @param matchTypes
     * @param linkOperatorType
     * @return
     */
    private List<WebElement> filt(List<WebElement> webElementList, String[] attrNames, String[] attrValues, int[] matchTypes, int linkOperatorType) {
        List<WebElement> firstFilterList = new ArrayList<>();
        for (WebElement element : webElementList) {
            boolean rsl = false;
            Hashtable attributeTable = element.getAttributes();
            Set<Map.Entry<String, String>> entrySet = attributeTable.entrySet();
            Iterator<Map.Entry<String, String>> itor = entrySet.iterator();
            //遍历所有html元素中的属性，筛选需要点击的 Webelement
            while (itor.hasNext()) {
                Map.Entry<String, String> entry = itor.next();
                String key = entry.getKey();
                String value = entry.getValue();
                switch (linkOperatorType) {
                    case CTagCompress.LinkOperatorType.AND:
                        if (null != attrNames && null != matchTypes && null != attrValues) {
                            boolean allOk = true;
                            for (int i = 0; i < attrNames.length; i ++) {
                                if (key.equals(attrNames[i])) {
                                    if (matchTypes[i] == CTagCompress.MatchType.BLURRY) {
                                        if (!value.contains(attrValues[i])) {
                                            allOk = false;
                                        }
                                    } else if (matchTypes[i] == CTagCompress.MatchType.EXACT) {
                                        if (!value.equals(attrValues[i])) {
                                            allOk = false;
                                        }
                                    }
                                }
                            }
                            rsl = allOk;
                        }
                        break;
                    case CTagCompress.LinkOperatorType.OR:
                    case CTagCompress.LinkOperatorType.NULL:
                        if (null != attrNames && null != matchTypes && null != attrValues) {
                            for (int i = 0; i < attrNames.length; i ++) {
                                if (key.equals(attrNames[i])) {
                                    if (matchTypes[i] == CTagCompress.MatchType.BLURRY) {
                                        if (value.contains(attrValues[i])) {
                                            rsl = true;
                                        }
                                    } else if (matchTypes[i] == CTagCompress.MatchType.EXACT) {
                                        if (value.equals(attrValues[i])) {
                                            rsl = true;
                                        }
                                    }
                                }
                            }
                        }
                        break;
                }
            }
            if (rsl) {
                firstFilterList.add(element);
            }
        }
        return firstFilterList;
    }
    private boolean isSame(List<WebElement> webElementList, List<WebElement> webElementList2) {
        if (webElementList.size() != webElementList2.size()) {
            return false;
        }
        for (int i = 0; i < webElementList.size(); i++) {
            if (!webElementList.get(i).getTagName().equals(webElementList2.get(i).getTagName())
                    || !(webElementList.get(i).getAttributes().toString().equals(webElementList2.get(i).getAttributes().toString()))) {
                return false;
            }
        }
        return true;
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            /**
            if(src!=null){
                String js = "var newscript = document.createElement(\"script\");";
                js += "newscript.src=\""+src+"\";";
                js += "document.body.appendChild(newscript);";
                mWebView.loadUrl("javascript:"+js);
            }
             **/
            //聚告
            //mWebView.loadUrl("javascript:var obj=document.createElement('div'); obj.id='chubeiwang_320_50_c'; obj.style='width:320px;height:50px;position:fixed;top:0;z-index:99999';document.body.appendChild(obj);var scriptObj = document.createElement('script');scriptObj.src = 'http://pmp.adinall.com/chubeiwang_320_50_c.js'; obj.appendChild(scriptObj);");
/**
            mWebView.loadUrl("javascript:function testsd() {\n" +
                    "    var s = '_' + Math.random().toString(36).slice(2);\n" +
                    "    document.write('<div id=' + s + '></div>');\n" +
                    "    (window.slotbydup=window.slotbydup || []).push({\n" +
                    "        id: '3164463',\n" +
                    "        container: s,\n" +
                    "        size: '0,0',\n" +
                    "        display: 'inlay-fix'\n" +
                    "    });\n" +
                    "}testsd();var scriptObj = document.createElement('script');scriptObj.src = 'http://dup.baidustatic.com/js/om.js'; document.body.appendChild(scriptObj);");
 **/
            mWebView.loadUrl("javascript:var scriptObj = document.createElement('script');scriptObj.src = '//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js';document.body.appendChild(scriptObj);var divobj=document.createElement('div');divobj.setAttribute('style','position: fixed; z-index: 10; bottom: 0px; left: 50%; width: 412px; height: 100px; margin: 0px 0px 0px -206px; padding: 0px; border: 0px;');document.body.appendChild(divobj);var Obj = document.createElement('ins');Obj.setAttribute('class','adsbygoogle'); Obj.setAttribute('style','display:block;z-index:9999'); Obj.setAttribute('data-ad-client','ca-pub-8006791032730788');Obj.setAttribute('data-ad-slot','3397612355');Obj.setAttribute('data-ad-format','auto');divobj.appendChild(Obj);var scrip = document.createElement('script');scrip.innerHTML='(adsbygoogle = window.adsbygoogle || []).push({});';document.body.appendChild(scrip);");
          //  mWebView.loadUrl("javascript:var scriptObj = document.createElement('script');scriptObj.src = 'http://union.star-media.cn/smu0/o.js';scriptObj.setAttribute('smua','d=m&s=l&u=u2976385&h=20:8'); document.body.appendChild(scriptObj);");
        }
    }
}
