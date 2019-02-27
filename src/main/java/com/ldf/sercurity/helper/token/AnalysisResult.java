package com.ldf.sercurity.helper.token;

import lombok.Data;

/**
 * 解析结果
 * @author lidefu
 * @date 2018/12/11 12:13
 */
@Data
public class AnalysisResult<T> {

    /**
     * 是否成功
     */
    private boolean isSuccess;

    /**
     * 错误码
     */
    private int errorCode;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 解析结果
     */
    private T result;

    @Override
    public String toString(){
        return "{" +
                "isSuccess=" + this.isSuccess +
                ",errorCode=" + this.errorMsg +
                ",errorMsg=" + this.errorMsg +
                ",result=" + this.result +
                "}";
    }

    public static<T> AnalysisResult ok(T t){
        AnalysisResult result = new AnalysisResult();
        result.setSuccess(true);
        result.setResult(t);
        return result;
    }

    public static AnalysisResult fail(int errorCode, String msg){
        AnalysisResult result = new AnalysisResult();
        result.setSuccess(false);
        result.setErrorCode(errorCode);
        result.setErrorMsg(msg);
        return result;
    }

}
