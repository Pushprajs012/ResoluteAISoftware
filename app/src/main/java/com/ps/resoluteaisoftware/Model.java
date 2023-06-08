package com.ps.resoluteaisoftware;

public class Model {
    public Model() {
    }

    public Model(String contents, String formatName) {
        Contents = contents;
        FormatName = formatName;
    }

    public String getContents() {
        return Contents;
    }

    public void setContents(String contents) {
        Contents = contents;
    }

    public String getFormatName() {
        return FormatName;
    }

    public void setFormatName(String formatName) {
        FormatName = formatName;
    }

    String Contents,FormatName;
}
