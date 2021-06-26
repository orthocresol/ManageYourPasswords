package com.manageyourpassword;

public class VaultItem {
    private String webName;
    private String url;

    public VaultItem() {}

    public void setWebName(String webName) {
        this.webName = webName;
    }

    public void setUrls(String url) {
        this.url = url;
    }

    public VaultItem(String webName, String url) {
        this.webName = webName;
        this.url = url;
    }

    public String getWebName() {
        return webName;
    }

    public String getUrls() {
        return url;
    }
}
