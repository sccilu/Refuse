package com.example.refuseclassification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.refuseclassification.Database.Knowledge;
import com.example.refuseclassification.R;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * SearchActivity 类用于实现搜索功能。
 */
public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity"; // 日志标签

    private Toolbar toolbar; // 工具栏
    private EditText editText; // 搜索输入框
    private RecyclerView recyclerView; // RecyclerView 用于显示搜索结果
    private TextView emptyView; // 空视图，当搜索结果为空时显示
    private List<Knowledge> knowledges = new ArrayList<>(); // 知识列表
    private MyAdapter myAdapter; // RecyclerView 的适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // 初始化工具栏
        toolbar = findViewById(R.id.search_toolbar);
        toolbar.setTitle("搜索");
        setSupportActionBar(toolbar);

        // 从数据库中获取所有的知识数据
        knowledges = LitePal.findAll(Knowledge.class);

        recyclerView = findViewById(R.id.search_recyclerView);
        emptyView = findViewById(R.id.empty_view);
        myAdapter = new MyAdapter(knowledges);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 初始化 EditText
        editText = findViewById(R.id.search);
        Intent intent = getIntent();
        String record = intent.getStringExtra("record");
        if (record != null) {
            editText.setText(record);
            filterKnowledgeList(record); // 过滤知识列表
        }

        // 添加文本变化监听器
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 不需要进行任何操作
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterKnowledgeList(s.toString()); // 根据输入过滤知识列表
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 不需要进行任何操作
            }
        });
    }

    /**
     * 过滤知识列表并在后台线程中执行
     * @param query 用户输入的查询字符串
     */
    private void filterKnowledgeList(String query) {
        new FilterTask(new AsyncTaskListener<List<Knowledge>>() {
            @Override
            public void onTaskComplete(List<Knowledge> result) {
                // 更新 UI
                knowledges.clear();
                knowledges.addAll(result);
                myAdapter.notifyDataSetChanged();
                updateEmptyView(); // 更新空视图
            }
        }).execute(query);
    }

    /**
     * 更新空视图，当没有搜索结果时显示空视图
     */
    private void updateEmptyView() {
        if (knowledges.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    /**
     * AsyncTask 用于在后台执行过滤操作
     */
    private static class FilterTask extends AsyncTask<String, Void, List<Knowledge>> {

        private AsyncTaskListener<List<Knowledge>> listener;

        FilterTask(AsyncTaskListener<List<Knowledge>> listener) {
            this.listener = listener;
        }

        @Override
        protected List<Knowledge> doInBackground(String... queries) {
            String query = queries[0];
            if (!TextUtils.isEmpty(query)) {
                try {
                    // 修改查询条件，确保参数正确传递
                    return LitePal.where("name LIKE ? OR kind LIKE ?",
                                    "%" + query + "%", "%" + query + "%")
                            .find(Knowledge.class);
                } catch (Exception e) {
                    Log.e(TAG, "查询出错：" + e.getMessage(), e);
                    return new ArrayList<>();
                }
            } else {
                return LitePal.findAll(Knowledge.class);
            }
        }

        @Override
        protected void onPostExecute(List<Knowledge> result) {
            listener.onTaskComplete(result); // 调用监听器方法传递结果
        }
    }

    /**
     * 自定义 RecyclerView 适配器，用于显示知识列表
     */
    private static class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private List<Knowledge> knowledgeList;

        MyAdapter(List<Knowledge> knowledgeList) {
            this.knowledgeList = knowledgeList;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Knowledge knowledge = knowledgeList.get(position);
            holder.name.setText(knowledge.getName());
            holder.kind.setText(knowledge.getKind());
        }

        @Override
        public int getItemCount() {
            return knowledgeList.size();
        }
    }

    /**
     * 自定义 ViewHolder，用于 RecyclerView
     */
    private static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView kind;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            kind = itemView.findViewById(R.id.kind);
        }
    }

    /**
     * 定义一个接口来处理异步任务的结果
     * @param <T> 异步任务的结果类型
     */
    public interface AsyncTaskListener<T> {
        void onTaskComplete(T result);
    }
}
