package com.duc.themovie.viewmodel;

import com.duc.themovie.api.res.PeopleRes;

import java.util.ArrayList;
import java.util.List;

public class PeopleVM extends BaseViewModel {
    public static final String KEY_GET_PEOPLE = "KEY_GET_PEOPLE";
    private List<PeopleRes.Result> people = new ArrayList<>();
    private int page = 1;

    public List<PeopleRes.Result> getPeople() {
        return people;
    }

    public void getPopularPeople() {
        getAPI().getPopularpeople(page).enqueue(initHandleResponse(KEY_GET_PEOPLE));
    }

    @Override
    protected void handleSuccess(String key, Object body) {
        if (key.equals(KEY_GET_PEOPLE)) {
            PeopleRes peopleRes = (PeopleRes) body;
            people.addAll(peopleRes.results);
            page++;
        }
        super.handleSuccess(key, body);
    }
}
