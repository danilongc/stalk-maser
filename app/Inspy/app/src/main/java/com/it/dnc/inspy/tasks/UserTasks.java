package com.it.dnc.inspy.tasks;

import android.os.AsyncTask;
import android.widget.ImageView;

import com.it.dnc.inspy.domain.InstagramUser;
import com.it.dnc.inspy.services.InstagramService;
import com.it.dnc.inspy.util.ApiCreator;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dnc on 07/03/18.
 */

public class UserTasks extends AsyncTask<String, Void, byte[]> {

    private InstagramService instagramService;
    private Exception exception;
    private ImageView profilePic;

    public UserTasks(ImageView profilePic){
        this.profilePic = profilePic;
    }

    @Override
    protected byte[] doInBackground(String... strings) {

        try {
            createAPI();
            Call<InstagramUser> profilePicture = instagramService.getUserInfo();

            profilePicture.enqueue(new Callback<InstagramUser>() {
                @Override
                public void onResponse(Call<InstagramUser> call, Response<InstagramUser> response) {
                    String profile_picture = response.body().getProfile_picture();
                    RequestCreator load = Picasso.get().load(profile_picture);
                    load.into(profilePic);
                }

                @Override
                public void onFailure(Call<InstagramUser> call, Throwable t) {

                }
            });

        }catch (Exception e){
            this.exception = e;
            return null;
        }

        return null;
    }


    private void createAPI(){
        instagramService = ApiCreator.getRetrofitInstance().create(InstagramService.class);
    }
}
