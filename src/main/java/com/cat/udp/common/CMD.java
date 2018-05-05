package com.cat.udp.common;


public enum CMD {

    Login(1000),
    Logout(1001),
    AddTask(2000),
    PassToWorkFlow(2001);

    private int value;

    private CMD(int value) {
        this.value = value;
    }

    public int value() {
        return value;

    }
}
