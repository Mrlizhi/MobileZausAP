package dlg.lizhihao.com.mobilezausap.SlGeneral.task.model;

import java.util.List;

/**
 * Created by sogood on 16/11/1.
 */

public class PTag {

    /**
     *
     * 最少停留时间
     */
    private int stayMin;

    /**
     *
     * 最长停留时间
     */
    private int stayMax;

    /**
     * 页面超时时间
     */
    private int timeOut;

    /**
     *
     * 点击次数
     * */
    private int clickCount;

    /** 对应xml 中c标签的 cid */
    private int cId;

    private List<PTag> pTagList;

    public int getStayMin() {
        return stayMin;
    }

    public void setStayMin(int stayMin) {
        this.stayMin = stayMin;
    }

    public int getStayMax() {
        return stayMax;
    }

    public void setStayMax(int stayMax) {
        this.stayMax = stayMax;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public List<PTag> getpTagList() {
        return pTagList;
    }

    public void setpTagList(List<PTag> pTagList) {
        this.pTagList = pTagList;
    }
}
