package dlg.lizhihao.com.mobilezausap.SlGeneral.task.model;
import java.util.ArrayList;
import java.util.List;

import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.SdkUtils;


/**
 * Created by lizhihao on 16/11/1.
 */

public class PTagCompress implements CompressVO<PTag> {

    //p节点 <p a="minStay,MaxStay|click_cnt|clickid|timeout">

    private String a;

    private List<PTagCompress> pTagCompressList = new ArrayList<>();

    @Override
    public PTag decompress() {
        PTag pTag = new PTag();
        if (!SdkUtils.isEmpty(a)) {
            String[] paramArray = a.split("\\|");
            pTag.setStayMin(Integer.parseInt(paramArray[0].split(",")[0]));
            pTag.setStayMax(Integer.parseInt(paramArray[0].split(",")[1]));
            pTag.setClickCount(Integer.parseInt(paramArray[1]));
            pTag.setcId(Integer.parseInt(paramArray[2]));
            pTag.setTimeOut(Integer.parseInt(paramArray[3]));
        }
        List<PTag> pTagList = new ArrayList<PTag>();
        if (pTagCompressList != null && pTagCompressList.size() > 0) {
            for (PTagCompress pTagCompress : pTagCompressList) {
                pTagList.add(pTagCompress.decompress());
            }
        }
        pTag.setpTagList(pTagList);
        return pTag;
    }

    public void setA(String a) {
        this.a = a;
    }

    public void setpTagCompressList(List<PTagCompress> pTagCompressList) {
        this.pTagCompressList = pTagCompressList;
    }

}
