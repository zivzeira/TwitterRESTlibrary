package twitter.base.utils;

import retrofit.RestAdapter;
import twitter.base.api.ApiConstants;
import twitter.base.api.TwitterApiService;

/**
 * Created by 7User on 17/01/2017.
 */


public class ServiceFactory {
    private static volatile TwitterApiService _instance;
    //volatile variable
    public static TwitterApiService getApiService()
    {
        if(_instance == null)
        {
            synchronized(ServiceFactory.class)
            {
                if(_instance == null)
                _instance = buildApi();
            }
        }
        return _instance;
    }

    private static TwitterApiService buildApi() {
        MyLogger.info(ServiceFactory.class, "buildApi TWITTER_BASE_URL: "+ ApiConstants.TWITTER_BASE_URL);
        return new RestAdapter.Builder()
                .setEndpoint(ApiConstants.TWITTER_BASE_URL)
                .build()
                .create(TwitterApiService.class);
    }

   /* public enum MySingleton {
        INSTANCE;
        private MySingleton() {
            System.out.println("Here");
        }
    }

    public static void main(String[] args) {
    System.out.println(MySingleton.INSTANCE);
}
    but keep in mind that is not a lazy loaded ServiceFactory

        Read more: http://javarevisited.blogspot.com/2012/07/why-enum-singleton-are-better-in-java.html#ixzz4W1fhJKWM

*/
}

//Read more: http://javarevisited.blogspot.com/2011/06/volatile-keyword-java-example-tutorial.html#ixzz4VyZ0IKLF
