This Android application performs a simple twitter search using Twitter’s REST API.
This project contains:

.\library

	The library module that gets a search term (string of hashtag) and return array of tweets from twitter.

	The library makes a request by the Retrofit POST oauth2 / token endpoint to exchange these credentials for a bearer token and save it in the application shared preferences.
	When accessing the REST API, the application uses the bearer token to authenticate and by Retrofit Get to a suitable GSON objects.


	search preformed by:

		TwitterSearch.searchTwitter(Context context, String hashTagSearchStr,
                              final Callback<TweetList> callback)


.\myapplication

	This android application module:
		1. provides a simple android UI for using the library
		2. provides a basic Android JUnit4 test for the library.
