package pickupsports2.ridgewell.pickupsports2.activities;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.FileInputStream;

import pickupsports2.ridgewell.pickupsports2.R;
import pickupsports2.ridgewell.pickupsports2.elements.EditUserDialog;
import pickupsports2.ridgewell.pickupsports2.utilities.ServerRequest;
import ridgewell.pickupsports2.common.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements MainActivity.MainActivityFragment{

    private static ServerRequest svreq = ServerRequest.getInstance();

    User user = null;

    View rootView = null;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String user_id = "";
        try {
            FileInputStream fis = getActivity().openFileInput(
                    getResources().getString(R.string.user_storage_file));
            int ch;
            StringBuffer fileContent = new StringBuffer("");
            while( (ch = fis.read()) != -1) {
                fileContent.append((char) ch);
            }
            user_id = fileContent.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user_id.equals("")) {
            Log.e("Storage Error", "User id could not be read from internal storage");
        } else {
            user = svreq.getUser(user_id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        setTexts();

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void onActionButtonClick() {
        EditUserDialog editUserDialog = new EditUserDialog();
        Bundle args = new Bundle();
        args.putParcelable("user",user);
        editUserDialog.setArguments(args);
        editUserDialog.show(getFragmentManager(), "creating user at login");
    }

    public void refreshFragment() {
        Log.v("Calling","GetUser");
        user = svreq.getUser(user.get_id());
        Log.v("Called","GetUser");
        setTexts();
    }

    private void setTexts() {
        TextView userName = (TextView) rootView.findViewById(R.id.user_name);
        userName.setText(user.getFirstname() + " " + user.getLastname());

        TextView joinDate = (TextView) rootView.findViewById(R.id.user_join_date);
        joinDate.setText(user.getJoinTime().toString("MMMM d, yyyy"));

        TextView location = (TextView) rootView.findViewById(R.id.user_location);
        location.setText(user.getLocation().getLocation());
    }
}
