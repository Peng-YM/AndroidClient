package cn.edu.sustc.androidclient.model.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.UUID;

@Entity(
        foreignKeys = {
                @ForeignKey(entity = Credential.class,
                        parentColumns = "userId",
                        childColumns = "userId"
                )
        },
        indices = {
                @Index(value = {"userId"}, unique = true)
        }
)
public class User implements Serializable {
    public User(@NonNull String userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    @Ignore
    public User(){
        this.userId = UUID.randomUUID().toString();
    }

    @SerializedName("user_id")
    @PrimaryKey
    @NonNull
    public String userId;
    public String username;
    public String email;
    public long phone;
    public String password;
    public int credit;
    public long balance;
    public int level;
    public String avatar;

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
