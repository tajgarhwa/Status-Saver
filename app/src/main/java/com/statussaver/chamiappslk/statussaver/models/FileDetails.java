package com.statussaver.chamiappslk.statussaver.models;

public class FileDetails {

    String id;
    String fileName;
    String AbsolutePatheName;

    public FileDetails(String id, String fileName, String absolutePatheName) {
        this.id = id;
        this.fileName = fileName;
        AbsolutePatheName = absolutePatheName;
    }

    public FileDetails() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getAbsolutePatheName() {
        return AbsolutePatheName;
    }

    public void setAbsolutePatheName(String absolutePatheName) {
        AbsolutePatheName = absolutePatheName;
    }
}
