package com.imooc.daily.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.imooc.daily.entity.CostBean;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xhx12366 on 2017-08-18.
 */

public class DailyDatabaseManager {
    public static final String COST_TITLE = "costTitle";
    public static final String COST_DATA = "costData";
    public static final String COST_MONEY = "costMoney";
    private DailyDatabaseHelper helper;
    private SQLiteDatabase db;

    public DailyDatabaseManager(Context context) {
        helper = new DailyDatabaseHelper(context);

        db = helper.getWritableDatabase();
    }

    /**
     * 插入一条信息
     * @param costBean
     */
    public void insert(CostBean costBean){
        db.execSQL("insert into cost_info(costTitle, costData, costMoney) values(?,?,?)",
                new Object[]{costBean.getCostTitle(), costBean.getCostData(),
                costBean.getCostMoney()});
    }

    /**
     * 删除一条信息
     * @param title
     * @param data
     * @param money
     */
    public void delete(String title,String data, String money){
        db.execSQL("delete from cost_info where costTitle = ? and costData = ? and costMoney = ?",
                new String[]{title, data, money});
    }

    /**
     * 删除所有数据库数据
     */
    public void deleteAll(){
        db.execSQL("delete from cost_info");
    }

    public void updata(){

    }

    public CostBean findByTitle(String costTitle){
        CostBean costBean = new CostBean();
        Cursor cursor = db.rawQuery("select * from cost_info where costTitle = ?",new String[]{costTitle});
        cursor.moveToNext();
        costBean.setCostTitle(cursor.getString(cursor.getColumnIndex(COST_TITLE)));
        costBean.setCostData(cursor.getString(cursor.getColumnIndex(COST_DATA)));
        costBean.setCostMoney(cursor.getString(cursor.getColumnIndex(COST_MONEY)));
        cursor.close();
        return costBean;
    }

    /**
     *查询所有数据
     * @return
     */
    public List<CostBean> findAll(){
        List<CostBean> list = new ArrayList<CostBean>();
        Cursor cursor = db.rawQuery("select * from cost_info", null);
        while(cursor.moveToNext()){
            CostBean costBean = new CostBean();
            costBean.setCostTitle(cursor.getString(cursor.getColumnIndex(COST_TITLE)));
            costBean.setCostData(cursor.getString(cursor.getColumnIndex(COST_DATA)));
            costBean.setCostMoney(cursor.getString(cursor.getColumnIndex(COST_MONEY)));
            list.add(costBean);
        }
        cursor.close();
        return list;
    }

    /**
     * 关闭数据库
     */
    public void closeDb(){
        db.close();
    }

}
