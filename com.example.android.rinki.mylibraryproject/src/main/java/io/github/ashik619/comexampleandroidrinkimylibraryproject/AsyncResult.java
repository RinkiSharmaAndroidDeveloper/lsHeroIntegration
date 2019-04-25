package io.github.ashik619.comexampleandroidrinkimylibraryproject;

/**
 * Created by dilip on 20/4/19.
 */

public interface AsyncResult<TData> {
    void success(TData data);
    void error(String error);



}