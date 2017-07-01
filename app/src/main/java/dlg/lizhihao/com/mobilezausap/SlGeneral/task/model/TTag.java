package dlg.lizhihao.com.mobilezausap.SlGeneral.task.model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.SdkUtils;

/**
 * Created by lizhihao on 16/11/1.
 */

public class TTag {

    /** 整个任务的超时时间 */
    private int timeout;

    /**
     *
     * reload="10" -- 就是一次 15就是1.5次 范围是0-100 默认是10 ，不可小于10
     */
    private int reload = 10;

    /** 需要清除cookie的域名，无需.com后缀，以分号分隔 */
    private List<String> cookieList;

    /** 入口页面 */
    private String start;

    /**
     * 来源地址
     */
    private String refer;
    /**
     * 注入的js
     */
    private String src;

    private String imgUrl;
    private String ticker;
    private String title;
    private String content;
    private String pname;
    /**
     *
     * 在哪种网络情况下才执行该任务（默认情况下，只要有2G网络，就会执行任务）
     */
    private int networkLimit = SdkUtils.NETWORKTYPE_WAP;

    /** t标签下面的p标签集合 */
    private List<PTag> pTagList = new ArrayList<>();

    /** t标签下面的c标签集合 */
    private HashMap<Integer, CTag> cTagMap = new HashMap<>();

    public TTag() {
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getReload() {
        return reload;
    }

    public void setReload(int reload) {
        this.reload = reload;
    }

    public List<String> getCookieList() {
        return cookieList;
    }

    public void setCookieList(List<String> cookieList) {
        this.cookieList = cookieList;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getRefer() {
        return refer;
    }

    public void setRefer(String refer) {
        this.refer = refer;
    }

    public void setNetworkLimit(int networkLimit) {
        this.networkLimit = networkLimit;
    }

    public int getNetworkLimit() {
        return networkLimit;
    }

    public List<PTag> getpTagList() {
        return pTagList;
    }

    public void setpTagList(List<PTag> pTagList) {
        this.pTagList = pTagList;
    }

    public HashMap<Integer, CTag> getcTagMap() {
        return cTagMap;
    }

    public void setcTagMap(HashMap<Integer, CTag> cTagMap) {
        this.cTagMap = cTagMap;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}
