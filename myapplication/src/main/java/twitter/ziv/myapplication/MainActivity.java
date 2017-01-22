package twitter.ziv.myapplication;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import twitter.base.api.Tweet;
import twitter.base.api.TweetList;
import twitter.base.utils.MyLogger;
import twitter.ziv.myapplication.Fragments.SearchResultsFragment;
import twitter.ziv.myapplication.Fragments.WelcomeFragment;

public class MainActivity extends MainActivitySuper
        implements FragmentManager.OnBackStackChangedListener {

    private WelcomeFragment welcomeFragment;
    private SearchResultsFragment searchResultsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyLogger.info(this, "onCreate");
        setContentView(R.layout.activity_main);
        getFragmentManager().addOnBackStackChangedListener(this);

        welcomeFragment = new WelcomeFragment();

        navigateTo(this, welcomeFragment, false, R.id.container_main);
       // shouldDisplayHomeUp();
    }

    @Override
    public void openSecondFragment(String searchItem) {
        searchResultsFragment = new SearchResultsFragment();
        Bundle args = new Bundle();
        args.putString(SearchResultsFragment.ARG_SEARCH_REQUEST, searchItem);
        searchResultsFragment.setArguments(args);
        navigateTo(this, searchResultsFragment, R.id.container_main);
    }

    /*public void shouldDisplayHomeUp() {

     //   boolean canback = getFragmentManager().getBackStackEntryCount() > 0;
       // getActionBar().setDisplayHomeAsUpEnabled(canback);
    }*/

    @Override
    public boolean onNavigateUp() {
        getFragmentManager().popBackStack();
        return true;
    }



    @Override
    public void onBackStackChanged() {
       // boolean canback = getFragmentManager().getBackStackEntryCount() > 0;
       // getActionBar().setDisplayHomeAsUpEnabled(canback);
    }



    @Override
    protected void onStart() {
        super.onStart();
        MyLogger.info(this, "onStart");
        //TwitterSearch twitterSearch = new TwitterSearch();
       // String searchItem = "shalom";
       // MyLogger.info(this, "searchItem: "+searchItem);
       // twitterSearch.searchTwitter(this, searchItem, getCallback());
    }


    @NonNull
    private Callback<TweetList> getCallback() {
        return new Callback<TweetList>() {
            @Override
            public void success(TweetList tweetList, Response response) {
                try {
                    MyLogger.info(this, "getTweetList success");
                    ArrayList<Tweet> tweets = tweetList.tweets;
                    MyLogger.info(this, "getTweetList size:"+ tweets.size());

                    for(Tweet tweet : tweets){
                        MyLogger.info(this, "tweet : "+ tweet.toString());
                    }
                } catch (Exception e) {
                    MyLogger.error(this, "getTweetList  success exception: "+ e.getMessage(), e);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                MyLogger.info(this, "getTweetList failure error : " + error.getMessage());
                MyLogger.info(this, "getTweetList getUrl : " +   error.getUrl());
            }
        };
    }



}
