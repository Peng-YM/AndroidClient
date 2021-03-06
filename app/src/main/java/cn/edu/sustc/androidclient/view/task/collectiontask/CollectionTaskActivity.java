package cn.edu.sustc.androidclient.view.task.collectiontask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.util.ArrayList;

import javax.inject.Inject;

import cn.edu.sustc.androidclient.R;
import cn.edu.sustc.androidclient.common.Constants;
import cn.edu.sustc.androidclient.common.utils.ScreenUtils;
import cn.edu.sustc.androidclient.databinding.ActivityCollectionTaskBinding;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.model.data.Transaction;
import cn.edu.sustc.androidclient.view.base.BaseActivity;

public class CollectionTaskActivity extends BaseActivity<CollectionTaskViewModel, ActivityCollectionTaskBinding> {
    @Inject
    CollectionTaskViewModel viewModel;

    private ActivityCollectionTaskBinding binding;
    private Task task;
    private Transaction transaction;

    private AlbumAdapter adapter;
    private ArrayList<AlbumFile> albumFiles;

    public static void start(Context context, Task task, Transaction transaction) {
        Intent intent = new Intent(context, CollectionTaskActivity.class);
        intent.putExtra("task", task);
        intent.putExtra("transaction", transaction);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getBinding();
        this.task = (Task) getIntent().getSerializableExtra("task");
        this.transaction = (Transaction) getIntent().getSerializableExtra("transaction");
        RecyclerView recyclerView = binding.albumView;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        binding.taskTitleTv.setText(task.name);
        binding.fab.setOnClickListener(view -> selectAlbum());
        binding.commitBtn.setOnClickListener(view -> {
            String URL =Constants.BASE_URL +
                    String.format("api/commits/pictures/%d/", transaction.transactionId);
            if (albumFiles != null && albumFiles.size() == transaction.size) {
                viewModel.uploadImages(albumFiles, URL).observe(this, resource -> {
                    switch (resource.status) {
                        case LOADING:
                            binding.commitBtn.setText(String.format("%.1f%%", resource.data));
                            break;
                        case SUCCESS:
                            binding.commitBtn.setText(getString(R.string.commit_success));
                            showAlertDialog(getString(R.string.info), getString(R.string.commit_success));
                            viewModel.commit(transaction.transactionId);
                            break;
                        case ERROR:
                            showAlertDialog(getString(R.string.alert), resource.message);
                        default:
                            break;
                    }
                });
            } else {
                showAlertDialog(getString(R.string.alert),
                        String.format(getString(R.string.alert_wrong_selection), transaction.size));
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_collection_task;
    }

    private void selectAlbum() {
        if (adapter == null) {
            int itemSize = ScreenUtils.getScreenWidth(this) / 3;
            adapter = new AlbumAdapter(this, itemSize, (view, position) -> previewAlbum(position));
            binding.albumView.setAdapter(adapter);
        }
        Logger.d("Select Images from album");
        Album.album(this)
                .multipleChoice()
                .columnCount(3)
                .cameraVideoQuality(1) // Video quality, [0, 1].
                .cameraVideoLimitDuration(Long.MAX_VALUE) // The longest duration of the video is in milliseconds.
                .cameraVideoLimitBytes(Long.MAX_VALUE) // Maximum size of the video, in bytes.
                .selectCount(transaction.size)
                .camera(true)
                .checkedList(albumFiles)
                .onResult((requestCode, result) -> {
                    albumFiles = result;
                    adapter.notifyDataSetChanged(albumFiles);
                })
                .start();
    }

    private void previewAlbum(int position) {
        if (albumFiles == null || albumFiles.size() == 0) {
            Toast.makeText(this, "You selected nothing", Toast.LENGTH_LONG).show();
        } else {
            Album.galleryAlbum(this)
                    .checkable(true)
                    .checkedList(albumFiles)
                    .currentPosition(position)
                    .widget(
                            Widget.newDarkBuilder(this)
                                    .title("选择图片")
                                    .build()
                    )
                    .onResult((requestCode, result) -> {
                        albumFiles = result;
                        adapter.notifyDataSetChanged(albumFiles);
                    })
                    .start();
        }
    }
}
