package com.duc.themovie.viewmodel;

import com.duc.themovie.api.res.CombinedCreditsRes;
import com.duc.themovie.api.res.ImageRes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailPersonVM extends BaseViewModel {

    public static final String KEY_GET_DETAIL_PERSON = "KEY_GET_DETAIL_PERSON";
    public static final String KEY_GET_PROFILES = "KEY_GET_PROFILES";
    public static final String KEY_GET_COMBINED_CREDITS = "KEY_GET_COMBINED_CREDITS";
    private final List<ImageRes.Profile> listProfile = new ArrayList<>();

    public List<ImageRes.Profile> getListProfile() {
        return listProfile;
    }

    public Map<String, Integer> mapCombinedCred = new HashMap<>();
    public String[] depts = {"Acting", "Directing", "Writing", "Production", "Creator", "Editing", "Art", "Camera", "Sound", "Crew", "Lighting", "Visual"};
    String[] importantJobs = {"Director", "Executive Producer", "Producer", "Writer", "Screenplay", "Creator", "Co-Producer", "Director of Photography", "Editor", "Original Music Composer", "Characters", "Story", "Comic Book"};


    public void getPersonDetail(int personId) {
        getAPI().getDetailPerson(personId).enqueue(initHandleResponse(KEY_GET_DETAIL_PERSON));
    }

    public void getProfiles(int personId) {
        getAPI().getPersonProfiles(personId).enqueue(initHandleResponse(KEY_GET_PROFILES));
    }

    public void getCombinedCredits(int personId) {
        getAPI().getCombinedCredits(personId).enqueue(initHandleResponse(KEY_GET_COMBINED_CREDITS));
    }

    @Override
    protected void handleSuccess(String key, Object body) {
        if (key.equals(KEY_GET_PROFILES)) {
            ImageRes res = (ImageRes) body;
            listProfile.clear();
            listProfile.addAll(res.profiles);

        } else if (key.equals(KEY_GET_COMBINED_CREDITS)) {
            CombinedCreditsRes res = (CombinedCreditsRes) body;

            // === CAST → ACTING ===
            for (CombinedCreditsRes.Cast c : res.listCast) {
                String type = "movie".equals(c.mediaType) ? "Movie" : "TV";
                increment("Acting_" + type);
            }

            // === CREW ===
            for (CombinedCreditsRes.Crew c : res.listCrew) {
                String type = "movie".equals(c.mediaType) ? "Movie" : "TV";

                // Creator đặc biệt
                if ("Creator".equals(c.job)) {
                    increment("Creator_TV");
                    incrementJob("Creator", "TV");
                    continue;
                }

                // Xác định department
                String deptKey = switch (c.department) {
                    case "Directing" -> "Directing";
                    case "Production" -> "Production";
                    case "Writing" -> "Writing";
                    case "Creator" -> "Creator";
                    case "Editing" -> "Editing";
                    case "Art" -> "Art";
                    case "Camera" -> "Camera";
                    case "Sound" -> "Sound";
                    case "Lighting" -> "Lighting";
                    case "Crew" -> "Crew";
                    case "Visual Effects" -> "Visual";
                    default -> "Crew"; // Các department khác gộp vào Crew
                };

                // Đếm department
                increment(deptKey + "_" + type);

                // Đếm job quan trọng
                for (String job : importantJobs) {
                    if (job.equals(c.job)) {
                        incrementJob(job, type);
                        break;
                    }
                }
            }
        }
        super.handleSuccess(key, body);
    }

    private void increment(String key) {
        mapCombinedCred.put(key, mapCombinedCred.getOrDefault(key, 0) + 1);
    }

    private void incrementJob(String job, String type) {
        String key = job + "_" + type;
        mapCombinedCred.put(key, mapCombinedCred.getOrDefault(key, 0) + 1);
    }
}