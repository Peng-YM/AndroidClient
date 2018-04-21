package cn.edu.sustc.androidclient.view.task;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yanzhenjie.album.Album;

import cn.edu.sustc.androidclient.R;

public class CollectionTaskActivity extends AppCompatActivity {
    public static void start(Context context){
        Intent intent = new Intent(context, CollectionTaskActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_task);
        Button startBtn = findViewById(R.id.start_collection_task);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Album.image(CollectionTaskActivity.this)
                        .multipleChoice()
                        .camera(true)
                        .columnCount(3)
                        .selectCount(3)
                        .start();
            }
        });
    }

    private void selectAlbum(){

    }
}
