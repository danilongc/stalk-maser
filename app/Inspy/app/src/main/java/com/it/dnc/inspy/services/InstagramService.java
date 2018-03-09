package com.it.dnc.inspy.services;

import com.it.dnc.inspy.domain.InstagramUser;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by dnc on 07/03/18.
 */

public interface InstagramService {

    @GET("/test/user-profile-picture")
    Call<ResponseBody> getProfilePicture();

    @GET("/test/user-info")
    Call<InstagramUser> getUserInfo();

    @GET("/insta/non-folowers")
    Call<List<InstagramUser>> getNonFollowers();
}
