package com.imooc.daily.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.imooc.daily.R;
import com.imooc.daily.entity.CostBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.Chart;
import lecho.lib.hellocharts.view.LineChartView;

public class ChartsActivity extends Activity {
    List<CostBean> data;
    private LineChartView mChart;
    private Map<String, Integer> table = new TreeMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        //获得图标控件
        mChart = (LineChartView) findViewById(R.id.chart);

        //获得图标所需list数据
        Intent intent = getIntent();
        data = (List<CostBean>) intent.getSerializableExtra("cost_list");

//        for (int i = 0; i < data.size(); i++) {
//            CostBean c = data.get(i);
//            Log.e("DATA:","data-"+c.getCostData() + "money-" +c.getCostMoney());
//        }

        //处理原始数据
        generateValidValue(data);

        //为图标控件设置数据
        mChart.setLineChartData(generateData());
    }

    /**
     * 生成图标数据
     * @return
     */
    private LineChartData generateData() {
        //数据点集合
        List<PointValue> values = new ArrayList<PointValue>();
        int index = 0;
        for(Integer value : table.values()){
            values.add(new PointValue(index, value));
            Log.e("DATA:","index-"+index+" value-"+value);
            index++;
        }

        //数据线集合
        Line line = new Line(values).setColor(Color.BLUE).setCubic(true);
        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        //图标数据
        LineChartData data = new LineChartData();
        data.setLines(lines);

        Axis axisX = new Axis().setTextSize(15).setTextColor(Color.BLACK);
        Axis axisY = new Axis().setHasLines(true).setTextSize(15).setTextColor(Color.BLACK);
        axisX.setName("消费日期");
        axisY.setName("消费金额");
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);

        data.setBaseValue(Float.NEGATIVE_INFINITY);

        return data;
    }

    /**
     * 处理原始数据，比如合并同一天的消费金额
     * @param data
     */
    private void generateValidValue(List<CostBean> data) {
        if(data != null){
            for (int i = 0; i < data.size(); i++) {
                CostBean c = data.get(i);
                String costData = c.getCostData();
                int costMoney = Integer.parseInt(c.getCostMoney());
                if(!table.containsKey(costData)){
                    table.put(costData, costMoney);
                }else{
                    int oldMoney = table.get(costData);
                    table.put(costData, oldMoney + costMoney);
                }
            }
        }
    }
}
