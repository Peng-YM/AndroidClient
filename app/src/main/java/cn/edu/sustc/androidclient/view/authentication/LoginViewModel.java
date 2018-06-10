package cn.edu.sustc.androidclient.view.authentication;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.orhanobut.logger.Logger;

import java.util.UUID;

import cn.edu.sustc.androidclient.model.MyResource;
import cn.edu.sustc.androidclient.model.data.Credential;
import cn.edu.sustc.androidclient.model.data.Session;
import cn.edu.sustc.androidclient.model.data.User;
import cn.edu.sustc.androidclient.model.repository.UserRepository;

public class LoginViewModel extends ViewModel {
    // injected modules
    private UserRepository userRepository;
    // data
    private MutableLiveData<MyResource<Credential>> credential;
    private MutableLiveData<MyResource<User>> createdUser;

    public LoginViewModel(UserRepository repository) {
        this.userRepository = repository;
    }

    public void login(Session session) {
        Logger.d("Attempted to Login: Email: %s, Password: %s", session.email, session.password);
        credential = userRepository.login(session);
    }

    public void registration(User user) {
        user.userId = UUID.randomUUID().toString();
        this.credential = userRepository.registration(user);
        Logger.d("Attempted to registration: %s", user);
    }

    public MutableLiveData<MyResource<Credential>> getCredential() {
        return credential;
    }

    /**
     * the mutable data should be clear when the ViewModel is destroyed.
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        userRepository.onClear();
    }
}