package com.example.siva.aznews;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by siva on 16/7/18.
 */

public class techLoader extends AsyncTaskLoader<List<techList>> {

    /** Tag for log messages */
    private static final String LOG_TAG = techLoader.class.getName();

    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link techLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public techLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<techList> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<techList> book = TechQueryUtils.fetchTechData(mUrl);
        return book;
    }
}
