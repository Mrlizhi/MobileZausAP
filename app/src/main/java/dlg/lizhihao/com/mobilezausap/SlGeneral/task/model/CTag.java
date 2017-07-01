package dlg.lizhihao.com.mobilezausap.SlGeneral.task.model;

/**
 * Created by sogood on 16/11/1.
 */

public class CTag {

    private Link link = new Link();

    private Javascript js = new Javascript();

    private Slide slide = new Slide();

    private Position position = new Position();

    /**
     * 点击动作ID定义
     */
    public int cId;

    /**
     * 点击等待时间-最少
     */
    public int stayMin;

    /**
     * 点击等待时间-最大
     */
    public int stayMax;

    /**
     * 点击概率, 10/1000，即为1%
     */
    public int rank;

    /**
     * -- 0:链接;1:位置;2:表单;3:脚本;4:跳转, 只支持0,3即可, 2,4为预留
     */
    public int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public Javascript getJs() {
        return js;
    }

    public void setJs(Javascript js) {
        this.js = js;
    }

    public Slide getSlide() {
        return slide;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setSlide(Slide slide) {
        this.slide = slide;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

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

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * 链接点击
     */
    public class Link {

        /**
         * link1中所有属性精确或者模糊筛选的标识集合，即==或=
         */
        public int[] linkFlag;

        /**
         *link中属性的集合
         *
         */
        public String[] linkProperty;

        /**
         * link中规则字段的集合
         */
        public String[] linkRule;

        /**
         * link2中所有属性精确或者模糊筛选的标识集合，即==或=
         */
        public int[] link2Flag;

        /**
         *link2中属性的集合
         *
         */
        public String[] link2Property;

        /**
         * link2中规则字段的集合
         */
        public String[] link2Rule;

        /**
         * link2中所有属性精确或者模糊筛选的标识集合，即==或=
         */
        public int[] rmFlag;

        /**
         *rm中属性的集合
         *
         */
        public String[] rmProperty;

        /**
         * rm中规则字段的集合
         */
        public String[] rmRule;

        /**
         * 判断计算类型   ||  or  &&
         */
        public int operation;

        public int index;

        public int[] getLinkFlag() {
            return linkFlag;
        }

        public void setLinkFlag(int[] linkFlag) {
            this.linkFlag = linkFlag;
        }

        public String[] getLinkProperty() {
            return linkProperty;
        }

        public void setLinkProperty(String[] linkProperty) {
            this.linkProperty = linkProperty;
        }

        public String[] getLinkRule() {
            return linkRule;
        }

        public void setLinkRule(String[] linkRule) {
            this.linkRule = linkRule;
        }

        public int[] getLink2Flag() {
            return link2Flag;
        }

        public void setLink2Flag(int[] link2Flag) {
            this.link2Flag = link2Flag;
        }

        public String[] getLink2Property() {
            return link2Property;
        }

        public void setLink2Property(String[] link2Property) {
            this.link2Property = link2Property;
        }

        public String[] getLink2Rule() {
            return link2Rule;
        }

        public void setLink2Rule(String[] link2Rule) {
            this.link2Rule = link2Rule;
        }

        public int[] getRmFlag() {
            return rmFlag;
        }

        public void setRmFlag(int[] rmFlag) {
            this.rmFlag = rmFlag;
        }

        public String[] getRmProperty() {
            return rmProperty;
        }

        public void setRmProperty(String[] rmProperty) {
            this.rmProperty = rmProperty;
        }

        public String[] getRmRule() {
            return rmRule;
        }

        public void setRmRule(String[] rmRule) {
            this.rmRule = rmRule;
        }

        public int getOperation() {
            return operation;
        }

        public void setOperation(int operation) {
            this.operation = operation;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    /**
     * javascript点击
     */
    public class Javascript {

        /**
         * 以下属性为type=3时生效,js脚本
         */
        public String jsCode;
    }

    /**
     * 滑动
     */
    public class Slide {

        public int slideMin;

        public int slideMax;

    }

    public class Position {

        public int width = 480;

        public int height = 800;

        public int left;

        public int top;

        public int rectWidth;

        public int rectHeight;

        public int msgType;

        public int retryCount;

    }

}
