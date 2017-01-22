package twitter.ziv.myapplication.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import twitter.base.utils.MyLogger;
import twitter.ziv.myapplication.MainActivitySuper;
import twitter.ziv.myapplication.R;


public class WelcomeFragment extends Fragment {

	private  MainActivitySuper mainActivity;
	private EditText editSearch;




	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);
		MyLogger.info(this, "onCreateView");

		editSearch = (EditText) rootView.findViewById(R.id.edit_search_tag);
		mainActivity = (MainActivitySuper) getActivity();

		Button buttonSearch = (Button) rootView.findViewById(R.id.button_search);
		buttonSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String searchItem = editSearch.getText().toString();
				MyLogger.info(this, "searchItem" + searchItem);
				if (!TextUtils.isEmpty(editSearch.getText())) {

					mainActivity.openSecondFragment(searchItem);

				} else {
					MyLogger.info(this, "onCreateView setOnClickListener empty search");
					//makeToast(getActivity(), "Please input a valid word or hashtag to search");
				}
			}
		});
		return rootView;
	}

}
