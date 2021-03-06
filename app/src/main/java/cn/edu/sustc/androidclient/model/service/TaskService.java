package cn.edu.sustc.androidclient.model.service;


import java.util.List;

import cn.edu.sustc.androidclient.model.MyRequest;
import cn.edu.sustc.androidclient.model.MyResponse;
import cn.edu.sustc.androidclient.model.data.AnnotationCommits;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.model.data.Task.AnnotationTaskFormatter;
import cn.edu.sustc.androidclient.model.data.Transaction;
import cn.edu.sustc.androidclient.model.data.TransactionInfo;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TaskService {
    @GET("api/tasks/")
    Observable<MyResponse<List<Task>>> getTasks(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    @POST("api/tasks/apply/")
    Single<MyResponse<Transaction>> applyTask(@Body MyRequest<TransactionInfo> info);

    @POST("api/tasks/")
    Single<MyResponse<Task>> createTask(@Body MyRequest<Task> newTask);

    @GET("api/tasks/{id}/formatter")
    Single<AnnotationTaskFormatter> getAnnotationTaskFormatter(@Path("id") int taskId);

    @POST("/api/commits/")
    Completable annotationCommit(@Body MyRequest<AnnotationCommits> commits);

}
