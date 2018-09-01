package com.example.siva.aznews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by siva on 15/8/18.
 */

public class BitcoinFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<techList>>, SwipeRefreshLayout.OnRefreshListener {
    public static final String LOG_TAG = BitcoinFragment.class.getName();
    /** URL for earthquake data from the USGS dataset */
    private static final String USGS_REQUEST_URL =
            "https://newsapi.org/v2/everything?q=bitcoin&sortBy=publishedAt&apiKey=47ada2986be0434699996aaf4902169b";

    /** Adapter for the list of earthquakes */
    private techAdapter mAdapter;

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;


    private SwipeRefreshLayout mSwipeRefreshLayout;

    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int EARTHQUAKE_LOADER_ID = 1;
    @Override
    public Loader<List<techList>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        mAdapter.clear();



        return new techLoader(getActivity(), USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<techList>> loader, List<techList> bitcoin) {


        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (bitcoin != null && !bitcoin.isEmpty()) {
            mAdapter.addAll(bitcoin);

        }

    }
    @Override
    public void onLoaderReset(Loader<List<techList>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    public BitcoinFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);


        View rootView = inflater.inflate(R.layout.fragment_bitcoin, container, false);

        int count = getActivity().getSupportFragmentManager().getBackStackEntryCount();
        if (count > 0) {
            getActivity().getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        // Find a reference to the {@link ListView} in the layout
        final ListView businessListView = (ListView) rootView.findViewById(R.id.list_bitcoin);
        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new techAdapter(getActivity(), new ArrayList<techList>());
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);


        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        businessListView.setAdapter(mAdapter);

        ViewCompat.setNestedScrollingEnabled(businessListView, true);




        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected earthquake.
        businessListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                // Find the current earthquake that was clicked on

                Intent intent = new Intent(getActivity(), detailActivity.class);
                techList currentEarthquake = mAdapter.getItem(position);
                //intent.putExtra("image", currentEarthquake.getmImage()); //you can name the keys whatever you like
                intent.putExtra("title", currentEarthquake.getmTitle()); //note that all these values have to be primitive (i.e boolean, int, double, String, etc.)
                intent.putExtra("author", currentEarthquake.getmAuthor());
                intent.putExtra("description", currentEarthquake.getmDescription());
                intent.putExtra("url", currentEarthquake.getmUrl());
                intent.putExtra("image",currentEarthquake.getmImage());
                startActivity(intent);


            }
        });
        // Start the AsyncTask to fetch the earthquake data
        BitcoinFragment.BitcoinAsyncTask task = new BitcoinFragment.BitcoinAsyncTask();
        task.execute(USGS_REQUEST_URL);


        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default_img data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getActivity().getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);

        }
        else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            //View loadingIndicator = rootView.findViewById(R.id.loading_indicator);
            //loadingIndicator.setVisibility(View.GONE);

            Context context = getActivity().getApplicationContext();
            CharSequence text = "No Internet Connection";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            //mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

        final ProgressBar pbar = (ProgressBar) rootView.findViewById(R.id.loading_indicator); // Final so we can access it from the other thread
        pbar.setVisibility(View.VISIBLE);

// Create a Handler instance on the main thread
        final Handler handler = new Handler();

// Create and start a new Thread
        new Thread(new Runnable() {
            public void run() {
                try{
                    Thread.sleep(1000);
                }
                catch (Exception e) { } // Just catch the InterruptedException

                // Now we use the Handler to post back to the main thread
                handler.post(new Runnable() {
                    public void run() {
                        // Set the View's visibility back on the main UI Thread
                        pbar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();



        mEmptyStateTextView = (TextView) rootView.findViewById(R.id.empty_view);
        businessListView.setEmptyView(mEmptyStateTextView);






        return rootView;
    }
    @Override
    public void onRefresh() {
        BitcoinFragment.BitcoinAsyncTask task = new BitcoinFragment.BitcoinAsyncTask();
        task.execute(USGS_REQUEST_URL);

    }

    private class BitcoinAsyncTask extends AsyncTask<String, Void, List<techList>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mAdapter.clear();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            //ProgressBar pbar = (ProgressBar) getActivity().findViewById(R.id.loading_indicator); // Final so we can access it from the other thread
            //pbar.setVisibility(View.GONE);
        }

        /**
         * This method runs on a background thread and performs the network request.
         * We should not update the UI from a background thread, so we return a list of
         * {@link techList}s as the result.
         */
        @Override
        protected List<techList> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<techList> result = TechQueryUtils.fetchTechData(urls[0]);
            return result;
        }

        /**
         * This method runs on the main UI thread after the background work has been
         * completed. This method receives as input, the return value from the doInBackground()
         * method. First we clear out the adapter, to get rid of earthquake data from a previous
         * query to USGS. Then we update the adapter with the new list of earthquakes,
         * which will trigger the ListView to re-populate its list items.
         */
        @Override
        protected void onPostExecute(List<techList> data) {
            // Clear the adapter of previous earthquake data
            mAdapter.clear();
            if(mSwipeRefreshLayout.isRefreshing())
            {
                mSwipeRefreshLayout.setRefreshing(false);
            }

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
                //ProgressBar pbar = (ProgressBar) getActivity().findViewById(R.id.loading_indicator); // Final so we can access it from the other thread
                //pbar.setVisibility(View.GONE);
            }
        }
    }
}

