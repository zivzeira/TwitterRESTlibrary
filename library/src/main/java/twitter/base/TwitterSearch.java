package twitter.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import twitter.base.api.ApiConstants;
import twitter.base.api.TweetList;
import twitter.base.api.TwitterApiService;
import twitter.base.api.TwitterTokenType;
import twitter.base.storage.APIProperties;
import twitter.base.utils.MyLogger;
import twitter.base.utils.ServiceFactory;
import twitter.base.utils.Util;

/**
 * Created by 7User on 19/01/2017.
 */

public class TwitterSearch {



    public void searchTwitter(final Context context, final String hashTagSearchStr,
                              final Callback<TweetList> callback) {
        try {

            MyLogger.info(this, "hashTagSearchStr : "+ hashTagSearchStr);

            if ((hashTagSearchStr == null) || (hashTagSearchStr.length() == 0)) {
                // throw new Exception("HashTagSearch parameter should not be empty");
                String errorMsg = "HashTagSearch parameter should not be empty";
                RetrofitError error = getRetrofitError("HashTagSearch parameter should not be empty!");
                callback.failure(error);
                return;
            }

            if (callback == null) {
                //throw new Exception("Callback parameter should not be null");
                callback.failure(getRetrofitError("Callback parameter should not be null!"));
                return;
            }

            if (!isConnectingToInternet(context)) {
               // throw new Exception("No available network");
                callback.failure(getRetrofitError("No available network!"));
                return;
            }



            final TwitterApiService twitterServiceApi = ServiceFactory.getApiService();

            String accessToken =  APIProperties.getAccessToken(context);

            MyLogger.info(this, "accessToken : "+ accessToken);

            if (accessToken != null){
                getTweetList(hashTagSearchStr, twitterServiceApi, accessToken, callback);
            }
            else {
                try {
                    MyLogger.info(this, "getToken");
                    twitterServiceApi.getToken("Basic " + Util.getBase64String(ApiConstants.BEARER_CREDENTIALS),
                            "client_credentials", new Callback<TwitterTokenType>() {
                                @Override
                                public void success(TwitterTokenType token, Response response) {
                                    MyLogger.info(this, "onGetToken success: "+ token.accessToken);

                                    APIProperties.setAccessToken(context, token.accessToken);

                                    getTweetList(hashTagSearchStr, twitterServiceApi, token.accessToken, callback);
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    MyLogger.info(this, "getTweetList failure error : " + error.getMessage());
                                    callback.failure(error);
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

    @NonNull
    private RetrofitError getRetrofitError(String errorMsg) {
        Exception e = new Exception(errorMsg);
        MyLogger.error(this, "getRetrofitError: "+ e.getMessage(), e);
        return RetrofitError.unexpectedError(errorMsg, e);
    }

    private boolean isConnectingToInternet(Context context) {

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    private void getTweetList(String hashTagSearchStr, TwitterApiService twitterServiceApi,
                              String accessToken, Callback<TweetList> callback) {
        try {
            MyLogger.info(this, "getTweetList hashTagSearchStr: " + hashTagSearchStr);
            MyLogger.info(this, "getTweetList accessToken: " + accessToken);

            twitterServiceApi.getTweetList("Bearer " + accessToken, hashTagSearchStr, callback);
        } catch (Exception e) {
            MyLogger.error(this, "getTweetList exception: "+ e.getMessage(), e);
        }
    }
}
