package cn.edu.sustc.androidclient.view.task.annotationtask;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.base.BaseActivity;
import cn.edu.sustc.androidclient.databinding.ActivityAnnotationTaskBinding;

public class AnnotationTaskActivity extends BaseActivity<AnnotationTaskViewModel, ActivityAnnotationTaskBinding> {
    @BindView(R.id.add_tag)
    Button add_tag;
    @BindView(R.id.next_step)
    Button next_step;
    private AnnotationTaskViewModel viewModel;
    private ActivityAnnotationTaskBinding binding;
    private AnnotateImageView annotateImageView;

    public static void start(Context context) {
        Intent intent = new Intent(context, AnnotationTaskActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected Class<AnnotationTaskViewModel> getViewModel() {
        return AnnotationTaskViewModel.class;
    }

    @Override
    protected void onCreate(Bundle instance, AnnotationTaskViewModel viewModel, ActivityAnnotationTaskBinding binding) {
        this.viewModel = viewModel;
        this.binding = binding;

        annotateImageView = binding.annotateImageView;
        annotateImageView.init(BitmapFactory.decodeResource(getResources(), R.drawable.cover));

        ButterKnife.bind(this);
        add_tag.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                popup_tags();
            }
        });
        next_step.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                // next image
            }
        });

//        binding.undoButton.setOnClickListener(view -> {
//            annotateImageView.undo();
//        });
//        binding.clearButton.setOnClickListener(view -> {
//            annotateImageView.clear();
//        });
//        binding.modeButton.setOnClickListener(view -> {
//            AnnotateImageView.Mode mode =
//                    annotateImageView.getMode() == AnnotateImageView.Mode.EDIT
//                            ? AnnotateImageView.Mode.SELECT : AnnotateImageView.Mode.EDIT;
//            annotateImageView.setMode(mode);
//        });

    }

    private void popup_tags(){
        PopupMenu popup = new PopupMenu(AnnotationTaskActivity.this, add_tag);
        Menu menu = popup.getMenu();

        // 此处应该用for循环加载tag
        // 一个粗暴的test
        menu.add(Menu.NONE, Menu.FIRST + 0, 0, "tag1");
        menu.add(Menu.NONE, Menu.FIRST + 1, 1, "tag2");
        menu.add(Menu.NONE, Menu.FIRST + 2, 2, "tag3");
        menu.add(Menu.NONE, Menu.FIRST + 3, 3, "tag4");
        // menu的item点击事件
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case Menu.FIRST + 0:
                        // 对标签模式的设置（颜色等）
                        break;
                    case Menu.FIRST + 1:
                        break;
                    case Menu.FIRST + 2:
                        break;
                    case Menu.FIRST + 3:
                        break;
                }
                return false;
            }
        });

        popup.show(); //showing popup menu
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_annotation_task;
    }
}
