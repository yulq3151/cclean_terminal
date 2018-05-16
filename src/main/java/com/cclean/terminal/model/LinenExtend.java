package com.cclean.terminal.model;

import java.util.List;

/**
 * Created by hubin on 2018/4/8.
 * Sku扩展信息
 */
public class LinenExtend {

    private List<Linen> linenList; //布草信息

    private List<String> unregisteredList;//未登记布草rfid

    private List<String> registeredList;//已登记布草rfid

    public List<Linen> getLinenList() {
        return linenList;
    }

    public void setLinenList(List<Linen> linenList) {
        this.linenList = linenList;
    }

    public List<String> getUnregisteredList() {
        return unregisteredList;
    }

    public void setUnregisteredList(List<String> unregisteredList) {
        this.unregisteredList = unregisteredList;
    }

    public List<String> getRegisteredList() {
        return registeredList;
    }

    public void setRegisteredList(List<String> registeredList) {
        this.registeredList = registeredList;
    }

    @Override
    public String toString() {
        return "LinenExtend{" +
                "linenList=" + linenList +
                ", unregisteredList=" + unregisteredList +
                ", registeredList=" + registeredList +
                '}';
    }
}
