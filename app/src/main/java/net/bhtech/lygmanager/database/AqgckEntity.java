package net.bhtech.lygmanager.database;

/**
 * Created by zhangxinbiao on 2017/3/13.
 * 缺陷的实体类
 */
public class AqgckEntity {

    public  int pkVal;
    public String AQ_NO;//主键（-1，执行保存，其他执行更新）
    public String GC_ORG;//观察者公司
    public String GC_CST;//观察者部门
    public String GC_NAM;//观察者姓名
    public String GC_DTM;//观察时间
    public String BGC_ORG;//被观察对象
    public String GC_RW;//观察任务
    public String GC_QY;//观察区域
    public String GC_SX;//观察属性
    public String GC_TYP;//观察内容类型
    public String WX_SHT;//危险描述
    public String IS_JZ;//是否纠正
    public String JZ_CS;//纠正措施
    public String JZ_XD;//纠正行动
    public String FX_TYP;//风险类型
    public String ZQX_SHT;//正确项描述
    public String ORG_NO;//公司
    public String BZ_NO;//班组
    public String USR_ID;//当前用户
    public String USR_DTM;//当前时间
    private String PICTUREA;

    public int getPkVal() {
        return pkVal;
    }

    public void setPkVal(int pkVal) {
        this.pkVal = pkVal;
    }

    public String getAQ_NO() {
        return AQ_NO;
    }

    public void setAQ_NO(String AQ_NO) {
        this.AQ_NO = AQ_NO;
    }

    public String getGC_ORG() {
        return GC_ORG;
    }

    public void setGC_ORG(String GC_ORG) {
        this.GC_ORG = GC_ORG;
    }

    public String getGC_CST() {
        return GC_CST;
    }

    public void setGC_CST(String GC_CST) {
        this.GC_CST = GC_CST;
    }

    public String getGC_NAM() {
        return GC_NAM;
    }

    public void setGC_NAM(String GC_NAM) {
        this.GC_NAM = GC_NAM;
    }

    public String getGC_DTM() {
        return GC_DTM;
    }

    public void setGC_DTM(String GC_DTM) {
        this.GC_DTM = GC_DTM;
    }

    public String getBGC_ORG() {
        return BGC_ORG;
    }

    public void setBGC_ORG(String BGC_ORG) {
        this.BGC_ORG = BGC_ORG;
    }

    public String getGC_RW() {
        return GC_RW;
    }

    public void setGC_RW(String GC_RW) {
        this.GC_RW = GC_RW;
    }

    public String getGC_QY() {
        return GC_QY;
    }

    public void setGC_QY(String GC_QY) {
        this.GC_QY = GC_QY;
    }

    public String getGC_SX() {
        return GC_SX;
    }

    public void setGC_SX(String GC_SX) {
        this.GC_SX = GC_SX;
    }

    public String getGC_TYP() {
        return GC_TYP;
    }

    public void setGC_TYP(String GC_TYP) {
        this.GC_TYP = GC_TYP;
    }

    public String getWX_SHT() {
        return WX_SHT;
    }

    public void setWX_SHT(String WX_SHT) {
        this.WX_SHT = WX_SHT;
    }

    public String getIS_JZ() {
        return IS_JZ;
    }

    public void setIS_JZ(String IS_JZ) {
        this.IS_JZ = IS_JZ;
    }

    public String getJZ_CS() {
        return JZ_CS;
    }

    public void setJZ_CS(String JZ_CS) {
        this.JZ_CS = JZ_CS;
    }

    public String getJZ_XD() {
        return JZ_XD;
    }

    public void setJZ_XD(String JZ_XD) {
        this.JZ_XD = JZ_XD;
    }

    public String getFX_TYP() {
        return FX_TYP;
    }

    public void setFX_TYP(String FX_TYP) {
        this.FX_TYP = FX_TYP;
    }

    public String getZQX_SHT() {
        return ZQX_SHT;
    }

    public void setZQX_SHT(String ZQX_SHT) {
        this.ZQX_SHT = ZQX_SHT;
    }

    public String getORG_NO() {
        return ORG_NO;
    }

    public void setORG_NO(String ORG_NO) {
        this.ORG_NO = ORG_NO;
    }

    public String getBZ_NO() {
        return BZ_NO;
    }

    public void setBZ_NO(String BZ_NO) {
        this.BZ_NO = BZ_NO;
    }

    public String getUSR_ID() {
        return USR_ID;
    }

    public void setUSR_ID(String USR_ID) {
        this.USR_ID = USR_ID;
    }

    public String getUSR_DTM() {
        return USR_DTM;
    }

    public void setUSR_DTM(String USR_DTM) {
        this.USR_DTM = USR_DTM;
    }

    public String getPICTUREA() {
        return PICTUREA;
    }

    public void setPICTUREA(String PICTUREA) {
        this.PICTUREA = PICTUREA;
    }
}
