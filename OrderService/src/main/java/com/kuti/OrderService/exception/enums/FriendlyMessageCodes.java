package com.kuti.OrderService.exception.enums;

public enum FriendlyMessageCodes implements IFriendlyMessageCode{

    OK(1000),
    ERROR(1001),
    SUCCESS(1002);

    private final int value;
    FriendlyMessageCodes(int value) {
        this.value = value;
    }
    @Override
    public int getFriendlyMessageCode() {
        return value;
    }
}
