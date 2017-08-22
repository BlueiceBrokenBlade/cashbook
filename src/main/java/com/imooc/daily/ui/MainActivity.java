package com.imooc.daily.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.imooc.daily.R;
import com.imooc.daily.adapter.CostListAdapter;
import com.imooc.daily.database.DailyDatabaseManager;
import com.imooc.daily.entity.CostBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView costList;
    private CostListAdapter adapter;
    private DailyDatabaseManager dbManager;
    private List<CostBean> data = new ArrayList<CostBean>();
    public static final int ITEM_DELETE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //实例化数据库管理类
        dbManager = new DailyDatabaseManager(this);
        //加载数据库数据
        data.addAll(dbManager.findAll());

        costList = (ListView) findViewById(R.id.list);

        adapter = new CostListAdapter(this, data);
        costList.setAdapter(adapter);

        //条目长按事件
        costList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                builder.setTitle("添加信息");
                builder.setItems(R.array.item_select, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            //删除一行数据
                            case ITEM_DELETE:
                            dbManager.delete(
                                    data.get(position).getCostTitle(),
                                    data.get(position).getCostData(),
                                    data.get(position).getCostMoney());
                                data.remove(position);
                                adapter.notifyDataSetChanged();
                                break;
                        }
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View viewDialog = inflater.inflate(R.layout.new_cost_data, null);
                final EditText et_cost_title = (EditText) viewDialog.findViewById(R.id.et_cost_title);
                final EditText et_cost_money = (EditText) viewDialog.findViewById(R.id.et_cost_money);
                final DatePicker dp_cost_data = (DatePicker) viewDialog.findViewById(R.id.dp_cost_data);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("添加信息");
                builder.setView(viewDialog);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CostBean costBean = new CostBean(
                                et_cost_title.getText().toString(),
                                dp_cost_data.getYear()+"-"+(dp_cost_data.getMonth()+1)+"-"+dp_cost_data.getDayOfMonth(),
                                et_cost_money.getText().toString());
                        dbManager.insert(costBean);
                        data.add(costBean);
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //生成图片菜单选项
        if (id == R.id.action_charts) {
            Intent intent = new Intent(MainActivity.this,ChartsActivity.class);
            intent.putExtra("cost_list", (Serializable) data);
            startActivity(intent);
            return true;
        }
        //清除所有数据选项
        if (id == R.id.action_clear) {
            dbManager.deleteAll();
            data.clear();
            adapter.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //关闭数据库
        dbManager.closeDb();

    }
}
