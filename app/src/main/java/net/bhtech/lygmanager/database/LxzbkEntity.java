package net.bhtech.lygmanager.database;

/**
 * Created by zhangxinbiao on 2018/5/24.
 */

public class LxzbkEntity {
    public int pkVal;

    public String JCK_TYP;//主键（-1，执行保存，其他执行更新）
    public String JCK_DTM;//观察者公司
    public String JCKCST_NO;//观察者部门
    public String CRW_NO;//观察者姓名
    public String JCK_ADR;//观察时间
    public String JCK_DSC;//被观察对象
    public String JCKUSR_ID;//观察任务
    public String ORG_NO;//观察区域
    public String JCK_NO;//观察属性
    private String JCK_ID;//观察属性

    public int getPkVal() {
        return pkVal;
    }

    public void setPkVal(int pkVal) {
        this.pkVal = pkVal;
    }

    public String getJCK_TYP() {
        return JCK_TYP;
    }

    public void setJCK_TYP(String JCK_TYP) {
        this.JCK_TYP = JCK_TYP;
    }

    public String getJCK_DTM() {
        return JCK_DTM;
    }

    public void setJCK_DTM(String JCK_DTM) {
        this.JCK_DTM = JCK_DTM;
    }

    public String getJCKCST_NO() {
        return JCKCST_NO;
    }

    public void setJCKCST_NO(String JCKCST_NO) {
        this.JCKCST_NO = JCKCST_NO;
    }

    public String getCRW_NO() {
        return CRW_NO;
    }

    public void setCRW_NO(String CRW_NO) {
        this.CRW_NO = CRW_NO;
    }

    public String getJCK_ADR() {
        return JCK_ADR;
    }

    public void setJCK_ADR(String JCK_ADR) {
        this.JCK_ADR = JCK_ADR;
    }

    public String getJCK_DSC() {
        return JCK_DSC;
    }

    public void setJCK_DSC(String JCK_DSC) {
        this.JCK_DSC = JCK_DSC;
    }

    public String getJCKUSR_ID() {
        return JCKUSR_ID;
    }

    public void setJCKUSR_ID(String JCKUSR_ID) {
        this.JCKUSR_ID = JCKUSR_ID;
    }

    public String getORG_NO() {
        return ORG_NO;
    }

    public void setORG_NO(String ORG_NO) {
        this.ORG_NO = ORG_NO;
    }

    public String getJCK_NO() {
        return JCK_NO;
    }

    public void setJCK_NO(String JCK_NO) {
        this.JCK_NO = JCK_NO;
    }

    public String getJCK_ID() {
        return JCK_ID;
    }

    public void setJCK_ID(String JCK_ID) {
        this.JCK_ID = JCK_ID;
    }
}
