package com.rexen.rest.framework.log;

import java.io.Serializable;

/**
 * <p>
 * 系统操作参数记录
 * </p>
 *
 * @author GavinHacker
 * @since 2018-03-02
 */
public class SystemOperationParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 日志主键
     */
    private String logId;
    /**
     * 请求参数
     */
    private String requestParam;
    /**
     * 响应结果
     */
    private String responseParam;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    public String getResponseParam() {
        return responseParam;
    }

    public void setResponseParam(String responseParam) {
        this.responseParam = responseParam;
    }

    @Override
    public String toString() {
        return "SystemOperationParam{" +
        ", id=" + id +
        ", logId=" + logId +
        ", requestParam=" + requestParam +
        ", responseParam=" + responseParam +
        "}";
    }
}
