package net.bhtech.lygmanager.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhangxinbiao on 2017/11/26.
 * 上岗到位的实体类
 */
@Entity(nameInDb = "mount_profile")
public class MountEntity {

    @Id(autoincrement = true)
    private Long pkVal;
    private String MOUNT_NO;
    private String ORG_NO;
    private String MOUNT_DSC;
    private String ELC_ID;
    private String MOUNT_IP;
    private String MOUNT_DTM;
    private String USR_NAM;

    @Generated(hash = 636159073)
    public MountEntity(Long pkVal, String MOUNT_NO, String ORG_NO, String MOUNT_DSC,
            String ELC_ID, String MOUNT_IP, String MOUNT_DTM, String USR_NAM) {
        this.pkVal = pkVal;
        this.MOUNT_NO = MOUNT_NO;
        this.ORG_NO = ORG_NO;
        this.MOUNT_DSC = MOUNT_DSC;
        this.ELC_ID = ELC_ID;
        this.MOUNT_IP = MOUNT_IP;
        this.MOUNT_DTM = MOUNT_DTM;
        this.USR_NAM = USR_NAM;
    }

    @Generated(hash = 209370344)
    public MountEntity() {
    }

    public String getMOUNT_NO() {
        return MOUNT_NO;
    }

    public void setMOUNT_NO(String MOUNT_NO) {
        this.MOUNT_NO = MOUNT_NO;
    }

    public String getMOUNT_DSC() {
        return MOUNT_DSC;
    }

    public void setMOUNT_DSC(String MOUNT_DSC) {
        this.MOUNT_DSC = MOUNT_DSC;
    }

    public String getELC_ID() {
        return ELC_ID;
    }

    public void setELC_ID(String ELC_ID) {
        this.ELC_ID = ELC_ID;
    }

    public String getMOUNT_IP() {
        return MOUNT_IP;
    }

    public void setMOUNT_IP(String MOUNT_IP) {
        this.MOUNT_IP = MOUNT_IP;
    }

    public String getMOUNT_DTM() {
        return MOUNT_DTM;
    }

    public void setMOUNT_DTM(String MOUNT_DTM) {
        this.MOUNT_DTM = MOUNT_DTM;
    }

    public String getUSR_NAM() {
        return USR_NAM;
    }

    public void setUSR_NAM(String USR_NAM) {
        this.USR_NAM = USR_NAM;
    }

    public String getORG_NO() {
        return ORG_NO;
    }

    public void setORG_NO(String ORG_NO) {
        this.ORG_NO = ORG_NO;
    }

    public Long getPkVal() {
        return pkVal;
    }

    public void setPkVal(Long pkVal) {
        this.pkVal = pkVal;
    }
}
