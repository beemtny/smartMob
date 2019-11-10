package com.example.smartmob;


import java.io.Serializable;
import java.util.Date;


class ChatMessage{

    private String id;
    private String title;
    private String text;
    private String pin;
    private Date createdAt;

    public ChatMessage(String id, String pin, String title, String text) {
        this(id, pin, title, text, new Date());
    }

    public ChatMessage(String id, String pin, String title, String text, Date createdAt) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.pin = pin;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getPin() {
        return pin;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

//    private void writeObject(ObjectOutputStream out) throws IOException {
//        out.defaultWriteObject();
//    }
//
//    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
//        in.defaultReadObject();
//    }
}
