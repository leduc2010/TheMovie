package com.duc.themovie.viewmodel;

import com.duc.themovie.api.res.ImageRes;

import java.util.ArrayList;
import java.util.List;

public class DetailPersonVM extends BaseViewModel {

    public static final String KEY_GET_DETAIL_PERSON = "KEY_GET_DETAIL_PERSON";
    public static final String KEY_GET_PROFILES = "KEY_GET_PROFILES";
    private List<ImageRes.Profile> listProfile =  new ArrayList<>();

    public List<ImageRes.Profile> getListProfile() {
        return listProfile;
    }

    public void getPersonDetail(int personId) {
        getAPI().getDetailPerson(personId).enqueue(initHandleResponse(KEY_GET_DETAIL_PERSON));
    }

    public void getProfiles(int personId) {
        getAPI().getPersonProfiles(personId).enqueue(initHandleResponse(KEY_GET_PROFILES));
    }

    @Override
    protected void handleSuccess(String key, Object body) {
        if (KEY_GET_PROFILES.equals(key)) {
            ImageRes res = (ImageRes) body;
            listProfile.clear();
            listProfile.addAll(res.profiles);
        }
        super.handleSuccess(key, body);
    }
}
