package net.bhtech.lygmanager.net.appupdate;

/**
 * Created by zhangxinbiao on 2018/5/25.
 */

public class AppVersionEntity {
    private String appName;
    private String serverVersion;
    private String serverFlag;
    private String lastForce;
    private String updateUrl;
    private String upgradeInfo;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(String serverVersion) {
        this.serverVersion = serverVersion;
    }

    public String getServerFlag() {
        return serverFlag;
    }

    public void setServerFlag(String serverFlag) {
        this.serverFlag = serverFlag;
    }

    public String getLastForce() {
        return lastForce;
    }

    public void setLastForce(String lastForce) {
        this.lastForce = lastForce;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public String getUpgradeInfo() {
        return upgradeInfo;
    }

    public void setUpgradeInfo(String upgradeInfo) {
        this.upgradeInfo = upgradeInfo;
    }
}
