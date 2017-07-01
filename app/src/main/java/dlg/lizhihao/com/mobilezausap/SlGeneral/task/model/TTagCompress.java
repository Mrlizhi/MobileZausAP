package dlg.lizhihao.com.mobilezausap.SlGeneral.task.model;

import java.util.Arrays;
import java.util.List;

import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.SdkUtils;

/**
 * Created by sogood on 16/11/1.
 */

public class TTagCompress implements CompressVO<TTag> {

    //<t a="timeout|reload|cookie1;cookie2" s="start" rf="referurl" />

    private String a;

    private String s;

    private String rf;

    private String n;

    private  String src;

    private String ticker;
    private String title;
    private String content;
    private String imgUrl;
    private String pname;

    private List<CTagCompress> cTagCompressVoList;

    private List<PTagCompress> pTagCompressVoList;

    public TTagCompress() {
    }

    public void setA(String a) {
        this.a = a;
    }

    public void setS(String s) {
        this.s = s;
    }

    public void setRf(String rf) {
        this.rf = rf;
    }

    public void setN(String n) {
        this.n = n;
    }
    public void setSrc(String src) {
        this.src = src;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setcTagCompressVoList(List<CTagCompress> cTagCompressVoList) {
        this.cTagCompressVoList = cTagCompressVoList;
    }
    public void setPname(String pname) {
        this.pname = pname;
    }
    public void setpTagCompressVoList(List<PTagCompress> pTagCompressVoList) {
        this.pTagCompressVoList = pTagCompressVoList;
    }

    @Override
    public TTag decompress() {
        TTag tTag = new TTag();
        if (!SdkUtils.isEmpty(a)) {
            String[] aArrayStr = a.split("\\|");
            if (!SdkUtils.isEmpty(aArrayStr[0])) {
                tTag.setTimeout(Integer.parseInt(aArrayStr[0]));
            }
            if (aArrayStr.length > 1 && !SdkUtils.isEmpty(aArrayStr[1])) {
                tTag.setReload(Integer.parseInt(aArrayStr[1]));
            }
            if (aArrayStr.length > 2 && !SdkUtils.isEmpty(aArrayStr[2])) {
                tTag.setCookieList(Arrays.asList(aArrayStr[2].split(",")));
            }
        }
        if (!SdkUtils.isEmpty(s)) {
            tTag.setStart(s);
        }
        if (!SdkUtils.isEmpty(rf)) {
            tTag.setRefer(rf);
        }
        if (!SdkUtils.isEmpty(n)) {
            tTag.setNetworkLimit(Integer.parseInt(n));
        }
        if(!SdkUtils.isEmpty(src)){
            tTag.setSrc(src);
        }
        if(!SdkUtils.isEmpty(ticker)){
            tTag.setTicker(ticker);
        }
        if(!SdkUtils.isEmpty(title)){
            tTag.setTitle(title);
        }
        if(!SdkUtils.isEmpty(content)){
            tTag.setContent(content);
        }
        if(!SdkUtils.isEmpty(imgUrl)){
            tTag.setImgUrl(imgUrl);
        }
        if(!SdkUtils.isEmpty(pname)){
            tTag.setPname(pname);
        }
        if (null != cTagCompressVoList) {
            for (CTagCompress c : cTagCompressVoList) {
                CTag cTag = c.decompress();
                tTag.getcTagMap().put(cTag.getcId(), cTag);
            }
        }
        if (null != pTagCompressVoList) {
            for (PTagCompress p : pTagCompressVoList) {
                PTag pTag = p.decompress();
                tTag.getpTagList().add(pTag);
            }
        }
        return tTag;
    }


}
