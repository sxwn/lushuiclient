package com.lushuitv.yewuds.module.response;

/**
 访问返回的response
 */
public class ResultResponse<T> {

    public String has_more;
    public String message;
    public String success; 
    public T data;

    public ResultResponse(String more, String _message, T result) {
        has_more = more;
        message = _message;
        data = result;
    }
}
