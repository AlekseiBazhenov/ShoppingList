package ru.bazhenov.shoplist.response;

public class SuccessResultData {
    private final boolean success;

    public SuccessResultData(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
