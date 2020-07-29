package com.jsc.firebase_exercise;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;

public class Note {

    @DocumentId
    private String documentId;
    private String text;
    private boolean isCompleted;
    private Timestamp created;
    private String userId;

    public Note() {
    }

    public Note(String text, boolean completed, Timestamp created, String userId) {
        this.text = text;
        this.isCompleted = completed;
        this.created = created;
        this.userId = userId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getText() {
        return text;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public Timestamp getCreated() {
        return created;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Note{" +
                "documentId='" + documentId + '\'' +
                ", text='" + text + '\'' +
                ", completed=" + isCompleted +
                ", created=" + created +
                ", userId='" + userId + '\'' +
                '}';
    }
}
