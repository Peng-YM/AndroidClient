package cn.edu.sustc.androidclient.model.service;


import java.util.List;

import cn.edu.sustc.androidclient.model.MyResponse;
import cn.edu.sustc.androidclient.model.data.Task;
import cn.edu.sustc.androidclient.model.data.Transaction;
import cn.edu.sustc.androidclient.model.data.TransactionInfo;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TaskService {
    @GET("tasks")
    Observable<MyResponse<Task>> getTasks(@Query("offset") int offset, @Query("limit") int limit);

    // TODO: remove fake api
    @GET("tasks")
    Observable<MyResponse<List<Task>>> fakeGetTasks();

    @GET("tasks/apply/")
    Single<MyResponse<Transaction>> applyTask(@Body TransactionInfo info);

}
