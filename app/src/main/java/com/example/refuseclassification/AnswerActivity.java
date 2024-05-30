package com.example.refuseclassification;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.refuseclassification.Database.Knowledge;

import java.util.ArrayList;
import java.util.List;
/**
 * scc
 */
public class AnswerActivity extends BaseActivity {

    // 定义UI组件
    private Toolbar toolbar;
    private TextView score_view;
    private List<Knowledge> knowledges = new ArrayList<>();
    private int score;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置布局文件
        setContentView(R.layout.activity_answer);

        // 初始化ToolBar
        toolbar = (Toolbar) findViewById(R.id.test_toolbar);
        toolbar.setTitle("考试结果");
        new setTitleCenter().setTitleCenter(toolbar);

        // 初始化分数视图
        score_view = findViewById(R.id.score);

        // 获取传递的数据
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("message");
        knowledges = (List<Knowledge>) bundle.getSerializable("knowledges");
        score = bundle.getInt("score");
        score_view.setText(String.valueOf(score));

        // 设置RecyclerView的适配器
        recyclerView = findViewById(R.id.answer_recyclerView);
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(AnswerActivity.this);
        recyclerView.setLayoutManager(manager);
    }

    // 定义RecyclerView的适配器
    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // 加载item布局文件
            View view = View.inflate(AnswerActivity.this, R.layout.item_answer, null);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            // 绑定数据到视图
            Knowledge knowledge = knowledges.get(position);
            holder.question_done.setText(knowledge.getName());
            holder.right_answer.setText(knowledge.getKind());
            holder.my_answer.setText(knowledge.getAnswer());
        }

        @Override
        public int getItemCount() {
            // 返回数据项的数量
            return knowledges.size();
        }
    }

    // 定义ViewHolder类
    private class MyViewHolder extends RecyclerView.ViewHolder {

        TextView question_done;
        TextView my_answer;
        TextView right_answer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // 绑定视图
            question_done = itemView.findViewById(R.id.question_done);
            my_answer = itemView.findViewById(R.id.my_answer);
            right_answer = itemView.findViewById(R.id.right_answer);
        }
    }
}
