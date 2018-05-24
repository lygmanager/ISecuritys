package net.bhtech.lygmanager.net.rx;

/**
 * Created by zhangxinbiao on 2018/3/18.
 */

public class JsonResult {
    private boolean success = false;// 是否成功
    private String msg = "";// 提示信息
    private Object obj = null;// 其他信息

    private boolean status = false;// 是否成功

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
