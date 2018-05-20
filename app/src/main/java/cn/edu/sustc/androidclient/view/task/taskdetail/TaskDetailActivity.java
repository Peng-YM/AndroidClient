package cn.edu.sustc.androidclient.view.task.taskdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.base.BaseActivity;
import cn.edu.sustc.androidclient.databinding.ActivityTaskDetailBinding;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.view.task.TaskViewModel;

public class TaskDetailActivity extends BaseActivity<TaskViewModel, ActivityTaskDetailBinding> {
    // injected modules
    private ActivityTaskDetailBinding binding;
    private TaskViewModel viewModel;
    private Task task;

    public static void start(Context context, Task task) {
        Intent intent = new Intent(context, TaskDetailActivity.class);
        intent.putExtra("task", task);
        context.startActivity(intent);
    }

    @Override
    protected Class<TaskViewModel> getViewModel() {
        return TaskViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, TaskViewModel viewModel, ActivityTaskDetailBinding binding) {
        this.viewModel = viewModel;
        this.binding = binding;

        Intent intent = getIntent();
        task = (Task) intent.getSerializableExtra("task");
        viewModel.setTask(task);

        binding.setViewModel(viewModel);
        //TODO: change to task description!
        binding.taskDescriptions.loadUrl("file:///android_asset/about.html");
        binding.takeTaskBtn.setOnClickListener(view -> viewModel.takeTask());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_task_detail;
    }
}
