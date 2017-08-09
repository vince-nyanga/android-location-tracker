package com.vinarah.locationtracker.vo;

import static com.vinarah.locationtracker.vo.Status.ERROR;
import static com.vinarah.locationtracker.vo.Status.LOADING;
import static com.vinarah.locationtracker.vo.Status.SUCCESS;

/**
 * @author Vincent
 * @since 2017/08/08
 */

public class Resource<T> {
    public final T value;
    public final String message;
    public final Status status;

    public Resource(T value, Status status, String message) {
        this.value = value;
        this.message = message;
        this.status = status;
    }

    public static <T> Resource<T> success(T value){
        return new Resource<>(value, SUCCESS, null);
    }

    public static <T> Resource<T> loading(){
        return new Resource<>(null, LOADING, null);
    }

    public static <T> Resource<T> error(String message){
        return  new Resource<>(null, ERROR, message);
    }
}
