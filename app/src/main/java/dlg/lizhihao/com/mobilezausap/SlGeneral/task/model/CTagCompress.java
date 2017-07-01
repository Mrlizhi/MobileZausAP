package dlg.lizhihao.com.mobilezausap.SlGeneral.task.model;


import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.SdkUtils;

/**
 * Created by sogood on 16/11/1.
 */

public class CTagCompress implements CompressVO<CTag> {

//    c节点
//    链接点击
//    <c a="cid|minSleep,MaxSleep|rank|type|idx" l="href=img" l2="href=detail" rm="href=stop"/>
//
//    坐标点击
//    <c a="cid|minSleep,MaxSleep|rank|type" r="width,height|left,top,rectwidth,rectheight|msgType|RetryCount"/>
//
//    js点击
//    <c a="cid|minSleep,MaxSleep|rank|type" c="OpenUrl('http://gn.zhanyilu.com/test/ck29/imglink.html', 0)"/>
//
//    滑动
//    <c a="cid|minSleep,MaxSleep|rank|type" of="500,800"/>


    private String a;

    private String l;

    private String l2;

    private String rm;

    private String c;

    private String r;

    private String of;

    int[] flag = null;//临时存储==或者=的判断标识
    String[] Property = null;//临时存储分割的属性名
    String[] Rule = null;//临时存储分割的属性规则字段
    String[] childLink = null;//临时存储由解析出的link，rm进行分割后的子字段

    @Override
    public CTag decompress() {
        CTag cTag = new CTag();
        //解析a 属性
        if (!SdkUtils.isEmpty(a)) {
            String[] aArrayStr = a.split("\\|");
            int cId = (Integer.parseInt(aArrayStr[0]));
            int stayMin = (Integer.parseInt(aArrayStr[1].split(",")[0]));
            int stayMax = (Integer.parseInt(aArrayStr[1].split(",")[1]));
            int rank = (Integer.parseInt(aArrayStr[2]));
            int type = (Integer.parseInt(aArrayStr[3]));
            cTag.setcId(cId);
            cTag.setStayMin(stayMin);
            cTag.setStayMax(stayMax);
            cTag.setRank(rank);
            cTag.setType(type);
            switch (type) { //判断点击类型
                case CTagType.LINK:
                    if (!SdkUtils.isEmpty(l)) {
                        splitInfo(l,cTag);              //l: "href=update_log.html"
                        //进行link字段的信息保存
                        cTag.getLink().setLinkFlag(flag);  //flag: 2
                        cTag.getLink().setLinkProperty(Property);   // {"href"}
                        cTag.getLink().setLinkRule(Rule);   //{"update_log.html"}
                        int index = (Integer.parseInt(aArrayStr[4]));
                        cTag.getLink().setIndex(index);
                    }
                    if(!SdkUtils.isEmpty(l2)){
                        //进行link2字段的信息保存
                        splitInfo(l2,cTag);
                        cTag.getLink().setLink2Flag(flag);
                        cTag.getLink().setLink2Property(Property);
                        cTag.getLink().setLink2Rule(Rule);
                    }
                    if(!SdkUtils.isEmpty(rm)){
                        //进行rm字段的信息保存
                        splitInfo(rm,cTag);
                        cTag.getLink().setRmFlag(flag);
                        cTag.getLink().setRmProperty(Property);
                        cTag.getLink().setRmRule(Rule);
                    }
                    break;
                case CTagType.SCRIPT:
                    if (!SdkUtils.isEmpty(c)){
                        cTag.getJs().jsCode = c;
                    }
                    break;
                case CTagType.SLIDE:
                    if (!SdkUtils.isEmpty(of)) {
                        cTag.getSlide().slideMin = Integer.parseInt(of.split(",")[0]);
                        cTag.getSlide().slideMax = Integer.parseInt(of.split(",")[1]);
                    }
                    break;

                case CTagType.POSITION:
                    if (!SdkUtils.isEmpty(r)) {
                        //<c a="cid|minSleep,MaxSleep|rank|type" r="width,height|left,top,rectwidth,rectheight|msgType|RetryCount"/>
                        String[] paramStrArray = r.split("\\|");
                        cTag.getPosition().width = Integer.parseInt(paramStrArray[0].split(",")[0]);
                        cTag.getPosition().height = Integer.parseInt(paramStrArray[0].split(",")[1]);
                        cTag.getPosition().left = Integer.parseInt(paramStrArray[1].split(",")[0]);
                        cTag.getPosition().top = Integer.parseInt(paramStrArray[1].split(",")[1]);
                        cTag.getPosition().rectWidth = Integer.parseInt(paramStrArray[1].split(",")[2]);
                        cTag.getPosition().rectHeight = Integer.parseInt(paramStrArray[1].split(",")[3]);
                        cTag.getPosition().msgType = Integer.parseInt(paramStrArray[2]);
                        cTag.getPosition().retryCount = Integer.parseInt(paramStrArray[3]);
                    }
                    break;
            }
        }
        return cTag;
    }

    public void splitInfo(String lk, CTag tag) {
        String link[] = null;
        if (lk.contains("||")) {
            link = lk.split("\\|\\|");
            tag.getLink().setOperation(LinkOperatorType.OR);//为1的时候是||
            getLinkInfo(link);
        } else if (lk.contains("$$")) {
            link = lk.split("$$");
            tag.getLink().setOperation(LinkOperatorType.AND);//为2的时候是$$
            getLinkInfo(link);
        } else {
            flag = new int[1];
            //当条件唯一时，直接用解析的字符串进行分割赋值
            if (lk.contains("==")) {
                link = lk.split("==");
                flag[0] = MatchType.EXACT; //精确查找
            } else {
                link = lk.split("=");
                flag[0] = MatchType.BLURRY; //模糊查找
            }
            tag.getLink().setOperation(LinkOperatorType.NULL);
            Property = new String[1];
            Rule = new String[1];
            Property[0] = link[0];
            Rule[0] = link[1];
        }
    }

    /**
     * 保存link信息
     *
     * @param link
     * @param
     */
    public void getLinkInfo(String[] link) {
        if (link!= null) {
            flag = new int[link.length];
            Property = new String[link.length];
            Rule = new String[link.length];
            for (int i = 0; i < link.length; i++) {
                if (link[i].contains("==")) {
                    childLink = link[i].split("==");
                    flag[i] = MatchType.EXACT;
                    Property[i] = childLink[0];
                    Rule[i] = childLink[1];
                } else {
                    childLink = link[i].split("=");
                    flag[i] = MatchType.BLURRY;
                    Property[i] = childLink[0];
                    Rule[i] = childLink[1];
                }
            }
        } else {

        }

    }

    public void setA(String a) {
        this.a = a;
    }

    public void setL(String l) {
        this.l = l;
    }

    public void setL2(String l2) {
        this.l2 = l2;
    }

    public void setRm(String rm) {
        this.rm = rm;
    }

    public void setC(String c) {
        this.c = c;
    }

    public void setR(String r) {
        this.r = r;
    }

    public void setOf(String of) {
        this.of = of;
    }

    public static class CTagType {
        public static final int LINK = 0;
        public static final int POSITION = 1;
        public static final int FORM = 2;
        public static final int SCRIPT = 3;
        public static final int JUMP = 4;
        public static final int SLIDE = 6;
    }

    /**
     * 条件连接符
     */
    public static class LinkOperatorType {
        public static final int NULL = 0; //无
        public static final int OR = 1;   //为1的时候是||
        public static final int AND = 2;  //为2的时候是$$
    }

    public static class MatchType {
        public static final int EXACT = 1;  //规则中精确筛选，即==
        public static final int BLURRY = 2; //规则中模糊筛选，即=
    }

}
