package com.example.refuseclassification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.refuseclassification.Database.Knowledge;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity {

    private Toolbar toolbar;
    private EditText editText;
    private RecyclerView recyclerView;
    private List<Knowledge> knowledges = new ArrayList<>();
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // 初始化工具栏
        toolbar = findViewById(R.id.search_toolbar);
        toolbar.setTitle("搜索");
        new setTitleCenter().setTitleCenter(toolbar);

        // 初始化数据列表
        knowledges = LitePal.findAll(Knowledge.class);

        recyclerView = findViewById(R.id.search_recyclerView);
        myAdapter = new MyAdapter(knowledges);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 实例化EditText
        editText = findViewById(R.id.search);
        Intent intent = getIntent();
        String record = intent.getStringExtra("record");
        if (record != null) {
            editText.setText(record);
            filterKnowledgeList(record);
        }

        // 添加文本变化监听器
        editText.addTextChangedListener(new TextWatcher() {
            // 输入文本前的状态
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 不需要进行任何操作
            }

            // 输入文本时的状态
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterKnowledgeList(s.toString());
            }

            // 输入文本之后的状态
            @Override
            public void afterTextChanged(Editable s) {
                // 不需要进行任何操作
            }
        });
    }

    // 过滤知识列表
    private void filterKnowledgeList(String query) {
        knowledges.clear();
        if (!TextUtils.isEmpty(query)) {
            knowledges.addAll(LitePal.where("name like ?", "%" + query + "%").find(Knowledge.class));
        } else {
            knowledges.addAll(LitePal.findAll(Knowledge.class));
        }
        myAdapter.notifyDataSetChanged();
    }

    // 自定义RecyclerView适配器
    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private List<Knowledge> knowledgeList;

        MyAdapter(List<Knowledge> knowledgeList) {
            this.knowledgeList = knowledgeList;
        }

        // 创建ViewHolder
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
            return new MyViewHolder(view);
        }

        // 绑定ViewHolder
        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Knowledge knowledge = knowledgeList.get(position);
            holder.name.setText(knowledge.getName());
            holder.kind.setText(knowledge.getKind());
        }

        // 获取列表项数量
        @Override
        public int getItemCount() {
            return knowledgeList.size();
        }
    }

    // 自定义ViewHolder
    private static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView kind;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            kind = itemView.findViewById(R.id.kind);
        }
    }
}
