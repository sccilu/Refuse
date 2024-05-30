package com.example.refuseclassification;

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

public class RecyclableActivity extends BaseActivity {

    private Toolbar toolbar; // 工具栏
    private RecyclerView recyclerView; // RecyclerView 列表视图
    private List<Knowledge> knowledges = new ArrayList<>(); // 数据列表
    private MyAdapter myAdapter; // 适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclable);

        // 初始化工具栏并设置标题
        toolbar = findViewById(R.id.recyclable_toolbar);
        toolbar.setTitle("可回收垃圾");
        new setTitleCenter().setTitleCenter(toolbar); // 设置标题居中

        // 初始化RecyclerView
        recyclerView = findViewById(R.id.recyclable_recyclerView);
        knowledges = LitePal.where("kind = ?", "可回收物").find(Knowledge.class); // 查询数据库中所有 "可回收物" 类型的知识项
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter); // 设置适配器
        LinearLayoutManager manager = new LinearLayoutManager(RecyclableActivity.this); // 设置布局管理器
        recyclerView.setLayoutManager(manager);
    }

    // 自定义RecyclerView适配器
    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // 创建并返回自定义的ViewHolder
            View view = View.inflate(RecyclableActivity.this, R.layout.item_recyclerview, null);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
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
    private class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name; // 名称TextView
        TextView kind; // 种类TextView

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name); // 初始化名称TextView
            kind = itemView.findViewById(R.id.kind); // 初始化种类TextView（此处没有实际使用）
        }
    }
}
