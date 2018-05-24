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
    private String position;
    private String result;
    private String cstName;
    private String orgNo;
    private String usrNam;
    private String positionNo;
    private String userId;
    private String orgName;
    private String cstNo;
    private String photo;
    private String crwNo;
    private String plaNo;

    @Generated(hash = 1287676212)
    public UtusrEntity(long pkVal, String position, String result, String cstName,
            String orgNo, String usrNam, String positionNo, String userId,
            String orgName, String cstNo, String photo, String crwNo,
            String plaNo) {
        this.pkVal = pkVal;
        this.position = position;
        this.result = result;
        this.cstName = cstName;
        this.orgNo = orgNo;
        this.usrNam = usrNam;
        this.positionNo = positionNo;
        this.userId = userId;
        this.orgName = orgName;
        this.cstNo = cstNo;
        this.photo = photo;
        this.crwNo = crwNo;
        this.plaNo = plaNo;
    }

    @Generated(hash = 372498128)
    public UtusrEntity() {
    }

    public long getPkVal() {
        return pkVal;
    }

    public void setPkVal(long pkVal) {
        this.pkVal = pkVal;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCstName() {
        return cstName;
    }

    public void setCstName(String cstName) {
        this.cstName = cstName;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getUsrNam() {
        return usrNam;
    }

    public void setUsrNam(String usrNam) {
        this.usrNam = usrNam;
    }

    public String getPositionNo() {
        return positionNo;
    }

    public void setPositionNo(String positionNo) {
        this.positionNo = positionNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getCstNo() {
        return cstNo;
    }

    public void setCstNo(String cstNo) {
        this.cstNo = cstNo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCrwNo() {
        return crwNo;
    }

    public void setCrwNo(String crwNo) {
        this.crwNo = crwNo;
    }

    public String getPlaNo() {
        return plaNo;
    }

    public void setPlaNo(String plaNo) {
        this.plaNo = plaNo;
    }
}
