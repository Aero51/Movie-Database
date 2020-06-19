package com.aero51.moviedatabase.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aero51.moviedatabase.repository.model.NetworkState;

/**
 * A generic class ported from kotlin that holds a value with its loading status.
 * @param <T>
 */
public class Resource<T> {
    @NonNull
    public final Status status;
    @Nullable
    public final String message;
    @Nullable
    public final T data;
    public final int code;
    @Nullable
    public final Throwable throwable;
    @Nullable
    public final ActionError actionError;

    public Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.code = -1;
        this.throwable = null;
        this.actionError = null;

    }


    public Resource(@NonNull Status status, @Nullable T data, @Nullable String message, int code) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.code = code;
        this.throwable = null;
        this.actionError = null;
    }

    public Resource(@NonNull Status status, @Nullable T data, @Nullable String message, int code, @Nullable Throwable throwable) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.code = code;
        this.throwable = throwable;
        this.actionError = null;

    }

    public Resource(@NonNull Status status, @Nullable T data, @Nullable String message, int code, @Nullable ActionError actionError) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.code = code;
        this.throwable = null;
        this.actionError = actionError;

    }


    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(Status.ERROR, data, msg);
    }

    public static <T> Resource<T> error(String msg, int code, @Nullable T data) {
        return new Resource<>(Status.ERROR, data, msg, code);
    }

    public static <T> Resource<T> error(String msg, int code, Throwable throwable) {
        return new Resource<>(Status.ERROR, null, msg, code, throwable);
    }
    public static <T> Resource<T> error(String msg, int code, @Nullable ActionError actionError) {
        return new Resource<>(Status.ERROR, null, msg, code, actionError);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(Status.LOADING, data, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Resource<?> resource = (Resource<?>) o;

        if (status != resource.status) {
            return false;
        }
        if (message != null ? !message.equals(resource.message) : resource.message != null) {
            return false;
        }
        return data != null ? data.equals(resource.data) : resource.data == null;
    }



    @Override
    public String toString() {
        return "Resource{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
