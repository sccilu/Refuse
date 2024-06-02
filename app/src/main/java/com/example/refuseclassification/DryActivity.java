package com.example.refuseclassification;
//解裔地
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.refuseclassification.Database.Knowledge;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class DryActivity extends BaseActivity {

    private Toolbar toolbar; // 工具栏
    RecyclerView recyclerView; // RecyclerView 列表视图
    List<Knowledge> knowledges = new ArrayList<>(); // 数据列表
    DryActivity.MyAdapter myAdapter; // 适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dry);

        // 初始化工具栏并设置标题
        toolbar = (Toolbar) findViewById(R.id.dry_toolbar);
        toolbar.setTitle("干垃圾");
        new setTitleCenter().setTitleCenter(toolbar);

        // 初始化RecyclerView
        recyclerView = findViewById(R.id.dry_recyclerView);
        knowledges = LitePal.where("kind = ?", "干垃圾").find(Knowledge.class); // 查询数据库中所有 "干垃圾" 类型的知识项
        myAdapter = new DryActivity.MyAdapter();
        recyclerView.setAdapter(myAdapter); // 设置适配器
        LinearLayoutManager manager = new LinearLayoutManager(DryActivity.this); // 设置布局管理器
        recyclerView.setLayoutManager(manager);
    }

    // 自定义RecyclerView适配器
    class MyAdapter extends RecyclerView.Adapter<DryActivity.MyViewHolder> {

        @NonNull
        @Override
        public DryActivity.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // 创建并返回自定义的ViewHolder
            View view = View.inflate(DryActivity.this, R.layout.item_recyclerview, null);
            DryActivity.MyViewHolder myViewHolder = new DryActivity.MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull DryActivity.MyViewHolder holder, int position) {
            // 绑定数据到ViewHolder
            Knowledge knowledge = knowledges.get(position);
            holder.name.setText(knowledge.getName()); // 设置名称
            // holder.kind.setText(knowledge.getKind()); // 设置种类（此处被注释掉了）
        }

        @Override
        public int getItemCount() {
            // 返回列表项的总数
            return knowledges.size();
        }
    }

    // 自定义ViewHolder
    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name; // 名称TextView
        TextView kind; // 种类TextView

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name); // 初始化名称TextView
            kind = itemView.findViewById(R.id.kind); // 初始化种类TextView
        }
    }
}
