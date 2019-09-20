package com.company;

public class Record {
    final private Long time;
    final private String name;

    public Record(String name, long time){
        this.name = name;
        this.time = time;
    }

    public Long getTime() {
        return time;
    }

    public String getName() {
        return name;
    }
}
