package com.dnc.stalkmaster.service.instagram;

import com.dnc.stalkmaster.domain.GenericRestResponse;
import com.dnc.stalkmaster.domain.instagram.GenericInstagramDomain;
import com.dnc.stalkmaster.domain.instagram.InstagramUser;
import com.dnc.stalkmaster.service.BaseService;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Scope("prototype")
public class InstagramUserService extends BaseService {

    public static final String GET_USER_SELF = "/users/self/";
    public static final String GET_USER_BY_ID = "/users/{userId}/";
    public static final String GET_USER_SELF_RECENT_MEDIA = "/users/self/media/recent/";
    public static final String GET_USER_RECENT_MEDIA_BY_ID = "/users/{userId}/media/recent/";
    public static final String GET_USER_SELF_MEDIA_LIKED = "users/self/media/liked/";
    public static final String GET_USER_SEARCH = "users/search/";

    public InstagramUser getUserSelf(){
        String url = getUrl(GET_USER_SELF);

        //TODO: Tratamento de erros aqui
        GenericInstagramDomain<InstagramUser> res = ( GenericInstagramDomain<InstagramUser> )executeGetWithTemplate(url, null, getAccessTokenParam(), new ParameterizedTypeReference<GenericInstagramDomain<InstagramUser>>() {}, true, null);
        return res.getData();
    }

    public byte[] getProfilePictureData(){
        InstagramUser user = getUserSelf();
        String urlPicture = user.getProfile_picture();
        byte[] pictureData = (byte[]) executeGet(urlPicture, byte[].class);
        return pictureData;
    }

    public void getNonFolowers(){

    }

    public void getUserById(){
        String url = getUrl(GET_USER_BY_ID);
    }

    public void getUserSelfRecentMedia(){
        String url = getUrl(GET_USER_SELF_RECENT_MEDIA);
    }

    public void getUserRecentMediaById(){
        String url = getUrl(GET_USER_RECENT_MEDIA_BY_ID);
    }

    public void getUserSelfMediaLiked(){
        String url = getUrl(GET_USER_SELF_MEDIA_LIKED);
    }
    public void getUserSearch(){
        String url = getUrl(GET_USER_SEARCH);
    }

    @Override
    protected HttpHeaders getHeaders() {
        return null;
    }
}
