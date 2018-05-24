package net.bhtech.lygmanager.net.rx;

import java.util.List;

/**
 * Created by zhangxinbiao on 2018/5/22.
 */

public class LiemsResult {


    private String result;// 是否成功
    private String total ;// 提示信息
    private Object rows = null;// 其他信息
    private String count ;// 提示信息
    private String msg ;// 提示信息
    private String pkValue ;// 提示信息

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Object getRows() {
        return rows;
    }

    public void setRows(Object rows) {
        this.rows = rows;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPkValue() {
        return pkValue;
    }

    public void setPkValue(String pkValue) {
        this.pkValue = pkValue;
    }
}
