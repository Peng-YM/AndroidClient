package cn.edu.sustc.androidclient.model.service;

import cn.edu.sustc.androidclient.model.MyResponse;
import cn.edu.sustc.androidclient.model.data.Credential;
import cn.edu.sustc.androidclient.model.data.Session;
import cn.edu.sustc.androidclient.model.data.User;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @POST("sessions")
    Observable<MyResponse<Credential>> login(@Body Session session);

    @POST("users")
    Observable<MyResponse<User>> registration(@Body User user);

    @GET("users/{id}")
    Single<MyResponse<User>> getProfile(@Path("id") String id);

    // fake apis here
    @GET("credentials/1")
    Single<MyResponse<Credential>> fakeLogin();
}
