package com.dnc.stalkmaster.service.instagram;

import com.dnc.stalkmaster.domain.instagram.GenericInstagramDomain;
import com.dnc.stalkmaster.domain.instagram.InstagramUser;
import com.dnc.stalkmaster.service.BaseService;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.dnc.stalkmaster.service.instagram.InstagramUserService.GET_USER_SELF;

@Service
@Scope("prototype")
public class InstagramRelationshipService extends BaseService {

    public static final String GET_USER_SELF_FOLLOWS = "/users/self/follows/";
    public static final String GET_USER_SELF_FOLLOWED_BY = "/users/self/followed-by/";


    public InstagramUser getUserSelf(){
        String url = getUrl(GET_USER_SELF);

        //TODO: Tratamento de erros aqui
        GenericInstagramDomain<InstagramUser> res = ( GenericInstagramDomain<InstagramUser> )executeGetWithTemplate(url, null, getAccessTokenParam(), new ParameterizedTypeReference<GenericInstagramDomain<InstagramUser>>() {}, true, null);
        return res.getData();
    }

    public List<InstagramUser> getFollows(){
        String url = getUrl(GET_USER_SELF_FOLLOWS);
        GenericInstagramDomain<List<InstagramUser>> follows = ( GenericInstagramDomain<List<InstagramUser>> )executeGetWithTemplate(url, null, getAccessTokenParam(), new ParameterizedTypeReference<GenericInstagramDomain<List<InstagramUser>>>() {}, true, null);
        return  follows.getData();
    }

    public List<InstagramUser> getFollowedBy(){
        String url = getUrl(GET_USER_SELF_FOLLOWED_BY);
        GenericInstagramDomain<List<InstagramUser>> follows = ( GenericInstagramDomain<List<InstagramUser>> )executeGetWithTemplate(url, null, getAccessTokenParam(), new ParameterizedTypeReference<GenericInstagramDomain<List<InstagramUser>>>() {}, true, null);
        return  follows.getData();
    }

    public List<InstagramUser> getNonFollowers(){
        List<InstagramUser> follows = getFollows();
        List<InstagramUser> followedBy = getFollowedBy();
        follows.removeAll(followedBy);
        return  follows;
    }

    @Override
    protected HttpHeaders getHeaders() {
        return null;
    }
}
