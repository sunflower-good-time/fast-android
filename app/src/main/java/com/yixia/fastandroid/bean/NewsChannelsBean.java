package com.yixia.fastandroid.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.yixia.network.beans.BaseResponse;

import java.util.List;

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public class NewsChannelsBean extends BaseResponse {
    @SerializedName("result")
    @Expose
    public ShowapiResBody result;


    public void setResult(ShowapiResBody result) {
        this.result = result;
    }

    public ShowapiResBody getResult() {
        return result;
    }

    public class ShowapiResBody {
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("from")
        @Expose
        public String from;

        public void setFrom(String from) {
            this.from = from;
        }

        public String getFrom() {
            return from;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
