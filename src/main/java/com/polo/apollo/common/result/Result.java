package com.polo.apollo.common.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author baoqianyong
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {

    private ResultCode code;

    private Object data;

    private String msg;

    Result(ResultCode code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static Result success(Object data, String msg) {
        return new Result(ResultCode.SUCCESS, data, msg);
    }

    public static Result success(Object data) {
        return Result.success(data, null);
    }

    public static Result success() {
        return Result.success(null, null);
    }

    public static Result error(String msg) {
        return new Result(ResultCode.ERROR, null, msg);
    }

    public static Result error(Object data, String msg) {
        return Result.error(data, msg);
    }

    public static Result error() {
        return Result.error(null, null);
    }
}
