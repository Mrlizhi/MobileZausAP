package dlg.lizhihao.com.mobilezausap.SlGeneral.task;

import android.util.Xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.LogUtil;
import dlg.lizhihao.com.mobilezausap.SlGeneral.task.model.CTagCompress;
import dlg.lizhihao.com.mobilezausap.SlGeneral.task.model.PTagCompress;
import dlg.lizhihao.com.mobilezausap.SlGeneral.task.model.TTag;
import dlg.lizhihao.com.mobilezausap.SlGeneral.task.model.TTagCompress;

/**
 * Created by lizhihao on 16/11/1.
 */

public class TaskXmlParser {

    private static final String TAG = TaskXmlParser.class.getSimpleName();

    private static TaskXmlParser mInstance;

    private TaskXmlParser() {}

    public static TaskXmlParser getInstance() {
        if (mInstance == null) {
            mInstance = new TaskXmlParser();
        }
        return mInstance;
    }

    public List<TTag> parseTask(String xml) {

        List<TTag> tTagList = new ArrayList<>();

        try {
            List<TTagCompress> tTagCompressesList = parseCompressTaskByDom4j(xml);
            for (TTagCompress tTagCompress : tTagCompressesList) {
                TTag tTag = tTagCompress.decompress();
                tTagList.add(tTag);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, "解析任务时出现错误~!!!");
        }
        return tTagList;
    }

    @Deprecated
    private List<TTagCompress> parseCompressTask(String text) throws XmlPullParserException, IOException {

        List<TTagCompress> tTagCompressList = new ArrayList<>();

        TTagCompress tTag = null;
        List<PTagCompress> pTagCompressList = null;
        List<CTagCompress> cTagCompressList = null;

        // ========创建XmlPullParser,有两种方式=======
        // 方式一:使用工厂类XmlPullParserFactory
        XmlPullParser parser = Xml.newPullParser();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(text.getBytes());
        parser.setInput(byteArrayInputStream, "utf-8");
        // 产生第一个事件
        int eventType = parser.getEventType();
        // 只要不是文档结束事件，就一直循环
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                // 触发开始文档事件
                case XmlPullParser.START_DOCUMENT:
                    break;
                // 触发开始元素事件
                case XmlPullParser.START_TAG:
                    // 获取解析器当前指向的元素的名称
                    String name = parser.getName();
                    if ("t".equals(name)) {
                        tTag = new TTagCompress();
                        pTagCompressList = new ArrayList<>();
                        cTagCompressList = new ArrayList<>();
                        tTag.setA(parser.getAttributeValue(null, "a"));
                        tTag.setS(parser.getAttributeValue(null, "s"));
                        tTag.setRf(parser.getAttributeValue(null, "rf"));
                        tTag.setN(parser.getAttributeValue(null, "n"));
                        tTag.setSrc(parser.getAttributeValue(null,"src"));
                    }

                    if ("p".equals(name) && pTagCompressList != null) {
                        PTagCompress pTag = new PTagCompress();
                        pTag.setA(parser.getAttributeValue(null, "a"));
                        pTagCompressList.add(pTag);
                    }
                    if ("c".equals(name)) {
                        CTagCompress cTagCompress = new CTagCompress();
                        cTagCompress.setA(parser.getAttributeValue(null, "a"));
                        cTagCompress.setC(parser.getAttributeValue(null, "c"));
                        cTagCompress.setR(parser.getAttributeValue(null, "r"));
                        cTagCompress.setL(parser.getAttributeValue(null, "l"));
                        cTagCompress.setL2(parser.getAttributeValue(null, "l2"));
                        cTagCompress.setRm(parser.getAttributeValue(null, "rm"));
                        cTagCompress.setOf(parser.getAttributeValue(null, "of"));
                        cTagCompressList.add(cTagCompress);
                    }
                    break;
                // 触发结束元素事件
                case XmlPullParser.END_TAG:
                    //
                    if ("t".equals(parser.getName())) {
                        if (pTagCompressList != null && tTag != null) {
                            tTag.setpTagCompressVoList(pTagCompressList);
                        }
                        if (cTagCompressList != null && tTag != null) {
                            tTag.setcTagCompressVoList(cTagCompressList);
                        }
                        if (tTag != null) {
                            tTagCompressList.add(tTag);
                        }
                        cTagCompressList = null;
                        pTagCompressList = null;
                        tTag = null;
                    }
                    break;
                default:
                    break;
            }
            eventType = parser.next();
        }
        return tTagCompressList;
    }
    private static List<TTagCompress> parseCompressTaskByDom4j(String text)
            throws DocumentException {
        Document document = DocumentHelper.parseText(text);
        List<TTagCompress> tTagCompressList = new ArrayList<>();
        Element node = document.getRootElement();
        tTagCompressList.add(parseTTagCompress(node));
        return tTagCompressList;
    }
    private static TTagCompress parseTTagCompress(Element node) {
        TTagCompress tTag = new TTagCompress();
        List<PTagCompress> pTagCompressList = new ArrayList<>();
        List<CTagCompress> cTagCompressList = new ArrayList<>();
        tTag.setA(node.attributeValue("a", null));
        tTag.setS(node.attributeValue("s", null));
        tTag.setRf(node.attributeValue("rf", null));
        tTag.setN(node.attributeValue("n", null));
        tTag.setSrc(node.attributeValue("src",null));
        tTag.setImgUrl(node.attributeValue("imgUrl",null));
        tTag.setContent(node.attributeValue("content",null));
        tTag.setTitle(node.attributeValue("title",null));
        tTag.setTicker(node.attributeValue("ticker",null));
        tTag.setPname(node.attributeValue("pname",null));
        tTag.setpTagCompressVoList(pTagCompressList);
        tTag.setcTagCompressVoList(cTagCompressList);
        Iterator<Element> iterator = node.elementIterator();
        while (iterator.hasNext()) {
            Element childNode = iterator.next();
            if ("p".equals(childNode.getName())) {
                pTagCompressList.add(parsePTagCompress(childNode));
            } else if ("c".equals(childNode.getName())) {
                cTagCompressList.add(parseCTagCompress(childNode));
            }
        }
        return tTag;
    }
    private static PTagCompress parsePTagCompress(Element node) {
        PTagCompress pTag = new PTagCompress();
        List<PTagCompress> pTagCompressList = new ArrayList<PTagCompress>();
        pTag.setA(node.attributeValue("a", null));
        pTag.setpTagCompressList(pTagCompressList);
        Iterator<Element> iterator = node.elementIterator();
        while (iterator.hasNext()) {
            Element childNode = iterator.next();
            pTagCompressList.add(parsePTagCompress(childNode));
        }
        return pTag;
    }

    private static CTagCompress parseCTagCompress(Element node) {
        CTagCompress cTagCompress = new CTagCompress();
        cTagCompress.setA(node.attributeValue("a", null));
        cTagCompress.setC(node.attributeValue("c", null));
        cTagCompress.setR(node.attributeValue("r", null));
        cTagCompress.setL(node.attributeValue("l", null));
        cTagCompress.setL2(node.attributeValue("l2", null));
        cTagCompress.setRm(node.attributeValue("rm", null));
        cTagCompress.setOf(node.attributeValue("of", null));
        return cTagCompress;
    }

}
