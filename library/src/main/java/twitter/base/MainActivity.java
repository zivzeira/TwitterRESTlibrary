package twitter.base;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import twitter.base.api.ApiConstants;
import twitter.base.api.Tweet;
import twitter.base.api.TweetList;
import twitter.base.api.TwitterApiService;
import twitter.base.api.TwitterTokenType;
import twitter.base.storage.APIProperties;
import twitter.base.utils.MyLogger;
import twitter.base.utils.ServiceFactory;
import twitter.base.utils.Util;

public class MainActivity extends AppCompatActivity {

    private Handler handler;
    HandlerThread handlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      /*  handlerThread = new HandlerThread("MainHandlerThread");
        handlerThread.start();

        handler = new Handler(handlerThread.getLooper());*/
    }

    @Override
    protected void onStart() {
        super.onStart();

        TwitterSearch twitterSearch = new TwitterSearch();
        twitterSearch.searchTwitter(this, "shalom", getCallback());
        //searchTwitter(this, "shalom");
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

    private void searchTwitter(final Context appContext, final String hashTagSearchStr) {
        try {


            MyLogger.info(this, "hashTagSearchStr : "+ hashTagSearchStr);

            final TwitterApiService twitterServiceApi = ServiceFactory.getApiService();

            String accessToken =  APIProperties.getAccessToken(appContext);

            MyLogger.info(this, "accessToken : "+ accessToken);

            if (accessToken != null){
                getTweetList(hashTagSearchStr, twitterServiceApi, accessToken);
            }
            else {
                try {
                    MyLogger.info(this, "getToken");
                    twitterServiceApi.getToken("Basic " + Util.getBase64String(ApiConstants.BEARER_CREDENTIALS),
                            "client_credentials", new Callback<TwitterTokenType>() {
                                @Override
                                public void success(TwitterTokenType token, Response response) {
                                    MyLogger.info(this, "onGetToken success: "+ token.accessToken);

                                    APIProperties.setAccessToken(appContext, token.accessToken);

                                    getTweetList(hashTagSearchStr, twitterServiceApi, token.accessToken);
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    MyLogger.info(this, "getTweetList failure error : " + error.getMessage());
                                }
                            });
                } catch (Exception e) {
                    MyLogger.error(this, "onGetToken exception: "+ e.getMessage(), e);
                }

            }



        } catch (Exception e) {
            MyLogger.error(this, "Main test exception: "+ e.getMessage(), e);
        }
    }

    private void getTweetList(String hashTagSearchStr, TwitterApiService twitterServiceApi, String accessToken) {
        try {
            MyLogger.info(this, "getTweetList hashTagSearchStr: " + hashTagSearchStr);
            MyLogger.info(this, "getTweetList accessToken: " + accessToken);
          //  mApi.getTweetList("Bearer " + event.twitterToken, event.hashtag, new Callback<TweetList>() {
            twitterServiceApi.getTweetList("Bearer " + accessToken, hashTagSearchStr, getCallback());
        } catch (Exception e) {
            MyLogger.error(this, "getTweetList exception: "+ e.getMessage(), e);
        }
    }


}
