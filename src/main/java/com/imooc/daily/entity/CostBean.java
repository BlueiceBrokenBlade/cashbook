package com.imooc.daily.entity;

import java.io.Serializable;

/**
 * Created by xhx12366 on 2017-08-18.
 */

public class CostBean implements Serializable{
    private String costTitle = "无";
    private String costData = "无";
    private String costMoney = "0";

    public CostBean() {
    }

    public CostBean(String costTitle, String costData, String costMoney) {
        if(!costTitle.equals("")){
            this.costTitle = costTitle;
        }
        if(!costData.equals("")){
            this.costData = costData;
        }
        if(!costMoney.equals("")){
            this.costMoney = costMoney;
        }


    }

    public String getCostData() {
        return costData;
    }

    public void setCostData(String costData) {
        this.costData = costData;
    }

    public String getCostMoney() {
        return costMoney;
    }

    public void setCostMoney(String costMoney) {
        this.costMoney = costMoney;
    }

    public String getCostTitle() {
        return costTitle;
    }

    public void setCostTitle(String costTitle) {
        this.costTitle = costTitle;
    }
}
