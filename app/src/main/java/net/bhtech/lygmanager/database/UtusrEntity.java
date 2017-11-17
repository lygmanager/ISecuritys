package net.bhtech.lygmanager.database;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhangxinbiao on 2017/3/18.
 */
@Entity(nameInDb = "user_profile")
public class UtusrEntity {
    @Id(autoincrement = true)
    private long pkVal;
    private String USR_ID;
    private String USR_NAM;
    private String CST_NAM;
    private String POS_NAM;
    private String CHECK;
    private String ORG_NO;

    @Generated(hash = 1479986116)
    public UtusrEntity(long pkVal, String USR_ID, String USR_NAM, String CST_NAM,
            String POS_NAM, String CHECK, String ORG_NO) {
        this.pkVal = pkVal;
        this.USR_ID = USR_ID;
        this.USR_NAM = USR_NAM;
        this.CST_NAM = CST_NAM;
        this.POS_NAM = POS_NAM;
        this.CHECK = CHECK;
        this.ORG_NO = ORG_NO;
    }
    @Generated(hash = 372498128)
    public UtusrEntity() {
    }

    public String getORG_NO()
    {
        return this.ORG_NO;
    }
    public void setORG_NO(String org_no) {
        this.ORG_NO = org_no;
    }
    public String getUSR_NAM() {
        return this.USR_NAM;
    }
    public void setUSR_NAM(String usr_nam) {
        this.USR_NAM = usr_nam;
    }
    public String getCST_NAM() {
        return this.CST_NAM;
    }
    public void setCST_NAM(String cst_nam) {
        this.CST_NAM = cst_nam;
    }
    public String getPOS_NAM() {
        return this.POS_NAM;
    }
    public void setPOS_NAM(String pos_nam) {
        this.POS_NAM = pos_nam;
    }
    public String getCHECK() {
        return this.CHECK;
    }
    public void setCHECK(String check) {
        this.CHECK = check;
    }

    public long getPkVal() {
        return pkVal;
    }

    public void setPkVal(long pkVal) {
        this.pkVal = pkVal;
    }

    public String getUSR_ID() {
        return USR_ID;
    }

    public void setUSR_ID(String USR_ID) {
        this.USR_ID = USR_ID;
    }
}
