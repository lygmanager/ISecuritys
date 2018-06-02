package net.bhtech.lygmanager.database;

/**
 * Created by zhangxinbiao on 2018/5/24.
 */

public class LxzbklinEntity {
    public int pkVal;

    public String JCLIN_NO;//主键（-1，执行保存，其他执行更新）
    public String JCLIN_DSC;//观察者公司
    public String JCLIN_SFNUM;//观察者部门
    public String JCLIN_FXNUM;//观察者姓名
    public String JCLIN_ZDNUM;//观察时间
    public String JCLIN_NANUM;//被观察对象

    public int getPkVal() {
        return pkVal;
    }

    public void setPkVal(int pkVal) {
        this.pkVal = pkVal;
    }

    public String getJCLIN_NO() {
        return JCLIN_NO;
    }

    public void setJCLIN_NO(String JCLIN_NO) {
        this.JCLIN_NO = JCLIN_NO;
    }

    public String getJCLIN_DSC() {
        return JCLIN_DSC;
    }

    public void setJCLIN_DSC(String JCLIN_DSC) {
        this.JCLIN_DSC = JCLIN_DSC;
    }

    public String getJCLIN_SFNUM() {
        return JCLIN_SFNUM;
    }

    public void setJCLIN_SFNUM(String JCLIN_SFNUM) {
        this.JCLIN_SFNUM = JCLIN_SFNUM;
    }

    public String getJCLIN_FXNUM() {
        return JCLIN_FXNUM;
    }

    public void setJCLIN_FXNUM(String JCLIN_FXNUM) {
        this.JCLIN_FXNUM = JCLIN_FXNUM;
    }

    public String getJCLIN_ZDNUM() {
        return JCLIN_ZDNUM;
    }

    public void setJCLIN_ZDNUM(String JCLIN_ZDNUM) {
        this.JCLIN_ZDNUM = JCLIN_ZDNUM;
    }

    public String getJCLIN_NANUM() {
        return JCLIN_NANUM;
    }

    public void setJCLIN_NANUM(String JCLIN_NANUM) {
        this.JCLIN_NANUM = JCLIN_NANUM;
    }
}
