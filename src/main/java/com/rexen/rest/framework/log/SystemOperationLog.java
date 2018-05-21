package com.rexen.rest.framework.log;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统操作日志
 * </p>
 *
 * @author GavinHacker
 * @since 2018-03-02
 */

public class SystemOperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 用户主键
     */
    private String userId;
    /**
     * 操作时间
     */
    private Date operationTime;
    /**
     * 客户端IP
     */
    private String clientIp;
    /**
     * 功能模块
     */
    private String module;
    /**
     * 操作类型
     */
    private String operationType;

    /**
     * 操作参数
     * */
    private SystemOperationParam operationParam;

    /**
     * 用户名称
     */
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public SystemOperationParam getOperationParam() {
        return operationParam;
    }

    public void setOperationParam(SystemOperationParam operationParam) {
        this.operationParam = operationParam;
    }

    @Override
    public String toString() {
        return "SystemOperationLog{" +
        ", id=" + id +
        ", userId=" + userId +
        ", operationTime=" + operationTime +
        ", clientIp=" + clientIp +
        ", module=" + module +
        ", operationType=" + operationType +
        "}";
    }
}
