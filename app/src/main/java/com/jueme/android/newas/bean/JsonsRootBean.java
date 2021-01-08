package com.jueme.android.newas.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Jueme on 2020/12/18
 **/
public class JsonsRootBean {

    @SerializedName("showapi_res_error")
    private String showapiResError;
    @SerializedName("showapi_res_id")
    private String showapiResId;
    @SerializedName("showapi_res_code")
    private int showapiResCode;
    @SerializedName("showapi_res_body")
    private ShowapiResBody showapiResBody;

    public void setShowapiResError(String showapiResError) {
        this.showapiResError = showapiResError;
    }
    public String getShowapiResError() {
        return showapiResError;
    }

    public void setShowapiResId(String showapiResId) {
        this.showapiResId = showapiResId;
    }
    public String getShowapiResId() {
        return showapiResId;
    }

    public void setShowapiResCode(int showapiResCode) {
        this.showapiResCode = showapiResCode;
    }
    public int getShowapiResCode() {
        return showapiResCode;
    }

    public void setShowapiResBody(ShowapiResBody showapiResBody) {
        this.showapiResBody = showapiResBody;
    }
    public ShowapiResBody getShowapiResBody() {
        return showapiResBody;
    }
}
