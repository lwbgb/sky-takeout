package pers.lwb.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {
    private Integer code;
    private T data;
    private String msg;

    public Result(String msg) {
        this.msg = msg;
    }

    public static <T> Result<T> success() {
        return new Result<>(1, null, "success");
    }

    public static <T> Result<T> success(String msg) {
        return new Result<>(1, null, msg);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(1, data, "sucess");
    }

    public static <T> Result<T> error() {
        return new Result<>(0, null, "error");
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(0, null, msg);
    }
}
