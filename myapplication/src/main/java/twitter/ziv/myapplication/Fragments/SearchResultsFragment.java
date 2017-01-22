package twitter.ziv.myapplication.Fragments;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import twitter.base.TwitterSearch;
import twitter.base.api.Tweet;
import twitter.base.api.TweetList;
import twitter.base.utils.MyLogger;
import twitter.ziv.myapplication.Adapter.TweetAdapter;
import twitter.ziv.myapplication.MainActivitySuper;
import twitter.ziv.myapplication.R;

public class SearchResultsFragment extends ListFragment {



	private String searchItem;

	private TweetAdapter brandAdapter;

	private TextView textResult;

	public static final String ARG_SEARCH_REQUEST = "searchItem";
	private TwitterSearch twitterSearch;
	private MainActivitySuper mainActivity;
	private ProgressDialog progress;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		MyLogger.info(this, "onCreateView");
		mainActivity = (MainActivitySuper) getActivity();
		View rootView = inflater.inflate(R.layout.fragment_list_tweets, container, false);
		brandAdapter = new TweetAdapter(getActivity(), new TweetList());
		setListAdapter(brandAdapter);
		searchItem = getArguments().getString(ARG_SEARCH_REQUEST);
		MyLogger.info(this, "searchItem" + searchItem);
		twitterSearch = new TwitterSearch();
		
		return rootView;
	}

	@Override
	public void onStart() {
		super.onStart();

		MyLogger.info(this, "onStart searchItem: "+searchItem);
		progress = new ProgressDialog(mainActivity);
		progress.setMessage("Twitter Search");
		progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progress.setIndeterminate(true);
		progress.setProgress(0);
		progress.show();
		twitterSearch.searchTwitter(mainActivity, searchItem, getCallback());

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

					brandAdapter.setTweetList(tweetList);
					brandAdapter.notifyDataSetChanged();

					progress.dismiss();
					progress = null;


				} catch (Exception e) {
					MyLogger.error(this, "getTweetList  success exception: "+ e.getMessage(), e);
				}
			}

			@Override
			public void failure(RetrofitError error) {
				MyLogger.info(this, "getTweetList failure error : " + error.getMessage());
				MyLogger.info(this, "getTweetList getUrl : " +   error.getUrl());

				if (progress != null)
					progress.dismiss();
				progress = null;

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						mainActivity);

				// set title
				alertDialogBuilder.setTitle("Failed: "+ error.getMessage());

				// set dialog message
				alertDialogBuilder
						.setCancelable(false)
						.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {

							}
						});

				AlertDialog alertDialog = alertDialogBuilder.create();


				alertDialog.show();
			}
		};
	}


	@Override
	public void onStop() {
		super.onStop();
	}

}
