package com.itwashard.jetrubyviewer.data.remote;

import com.itwashard.jetrubyviewer.data.model.Comment;
import com.itwashard.jetrubyviewer.data.model.Shot;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Single;

/**
 * Created by stanislavzakharov on 22.12.16.
 */

public interface JetrubyService {

    /**
     * Retrieve a list of shots
     */
    @GET("shots")
    Single<List<Shot>> getShots(@Query("access_token") String accessToken,
                                @Query("per_page") int perPage,
                                @Query("page") int page);


    /**
     * Retrieve a list of comments for a given shot
     */
    @GET("shots/{shot_id}/comments")
    Single<List<Comment>> getComments(@Path("shot_id") int shotId,
                                      @Query("access_token") String accessToken,
                                      @Query("per_page") int perPage,
                                      @Query("page") int page);

}
