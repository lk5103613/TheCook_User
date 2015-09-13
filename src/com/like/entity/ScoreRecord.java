package com.like.entity;

public class ScoreRecord {

    public String add_time;
    public String reason;
    public String score;

    public ScoreRecord(String time, String action, String score) {
        this.add_time = time;
        this.reason = action;
        this.score = score;
    }
}
