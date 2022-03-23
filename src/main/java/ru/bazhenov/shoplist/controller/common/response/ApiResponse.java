package ru.bazhenov.shoplist.controller.common.response;

public class ApiResponse<T> {

    private final T data;
    private final ApiError error;

    public ApiResponse(T data, ApiError error) {
        this.error = error;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public ApiError getError() {
        return error;
    }
}
