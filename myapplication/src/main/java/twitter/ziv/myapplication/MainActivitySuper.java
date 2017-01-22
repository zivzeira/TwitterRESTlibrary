package twitter.ziv.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 7User on 03/01/2017.
 */
abstract public class MainActivitySuper extends AppCompatActivity {
	//public DB db;
	/*public void send(Message message1, long delay) {
	}*/

	abstract public void openSecondFragment(String searchItem);


	public static void navigateTo(Activity activity, final Fragment fragment, int containerId) {
		navigateTo(activity, fragment, null, true, false, false, containerId);
	}

	public static void navigateTo(Activity activity, final Fragment fragment, final boolean addToBackStack, int containerId) {
		navigateTo(activity, fragment, null, addToBackStack, false, false, containerId);
	}

	public static void navigateTo(Activity activity, final Fragment fragment, final FragmentTransaction transaction, final boolean addToBackStack,
								  final boolean allowStateLoss, final boolean useTransition, int containerId) {
		final FragmentManager fm = activity.getFragmentManager();
		final FragmentTransaction ft = transaction == null ? fm.beginTransaction() : transaction;
		ft.setTransition(useTransition ? FragmentTransaction.TRANSIT_FRAGMENT_OPEN : FragmentTransaction.TRANSIT_NONE);

		ft.replace(containerId, fragment, fragment.getClass().getName());
		if (addToBackStack) {
			ft.addToBackStack(fragment.getClass().getName());
		}

		commitTransaction(ft, allowStateLoss);
	}


	private static void commitTransaction(final FragmentTransaction ft, final boolean allowStateLoss) {
		if (allowStateLoss) {
			ft.commitAllowingStateLoss();
		} else {
			ft.commit();
		}
	}





	//abstract public void openChatFragment(int chatId);


}
