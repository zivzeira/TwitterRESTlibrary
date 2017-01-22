package twitter.ziv.myapplication;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import twitter.base.TwitterSearch;
import twitter.base.api.Tweet;
import twitter.base.api.TweetList;
import twitter.base.utils.MyLogger;

import static org.junit.Assert.assertTrue;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */



@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private HandlerThread handlerThread;
    private Handler handler;
    boolean apiCall;
    boolean assertResult;
    Object lock = new Object();

    @Test(timeout = 30000)
    public void successfulTwitterRestCall() throws Exception {
        // Context of the app under test.
        final Context appContext = InstrumentationRegistry.getTargetContext();
        final TwitterSearch twitterSearch = new TwitterSearch();
        final String searchItem = "shalom";
        MyLogger.info(this, "searchItem: "+searchItem);

        apiCall = true;
        assertResult = true;
        twitterSearch.searchTwitter(appContext, searchItem, getCallback());
        synchronized (lock) {
            while (apiCall) {
                MyLogger.info(this, "sleep start");
                Thread.sleep(1000);
                MyLogger.info(this, "sleep  end");
            }
        }
        assertTrue(assertResult);
    }


    @Test(timeout = 30000)
    public void emptySearch() throws Exception {
        // Context of the app under test.
        final Context appContext = InstrumentationRegistry.getTargetContext();
        final TwitterSearch twitterSearch = new TwitterSearch();
        final String searchItem = "";
        MyLogger.info(this, "searchItem: "+searchItem);

        apiCall = true;
        assertResult = true;

        twitterSearch.searchTwitter(appContext, searchItem, getCallback());

        while ( apiCall) {
            MyLogger.info(this, "sleep start");
            Thread.sleep(1000);
            MyLogger.info(this, "sleep  end");
        }


        assertTrue(!assertResult);
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

                    //assert(tweets.size() > 0);
                    //assert(true);
                  //  assertEquals(true, true);

                    ExampleInstrumentedTest.this.assertResult = true;
                    ExampleInstrumentedTest.this.apiCall = false;

                } catch (Exception e) {
                    MyLogger.error(this, "getTweetList  success exception: "+ e.getMessage(), e);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                MyLogger.info(this, "getTweetList failure error : " + error.getMessage());

                ExampleInstrumentedTest.this.assertResult = false;
                ExampleInstrumentedTest.this.apiCall = false;

            }

        };
    }
}
