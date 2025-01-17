package pickupsports2.ridgewell.pickupsports2.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pickupsports2.ridgewell.pickupsports2.R;
import pickupsports2.ridgewell.pickupsports2.activities.MainActivity;
import pickupsports2.ridgewell.pickupsports2.elements.SportingEventArrayAdapter;
import pickupsports2.ridgewell.pickupsports2.utilities.ServerRequest;
import pickupsports2.ridgewell.pickupsports2.utilities.SwipeRefreshListFragment;
import pickupsports2.ridgewell.pickupsports2.intents.IntentProtocol;
import ridgewell.pickupsports2.common.Event;

/**
 * Created by cameronridgewell on 2/9/15.
 */
public class AllEventsFragment extends SwipeRefreshListFragment implements MainActivity.MainActivityFragment {

    final int CREATE_EVENT_CODE = 111;
    final int SUCCESS_CODE = 1;

    private List<Event> events = new ArrayList<Event>();

    private SportingEventArrayAdapter sportingEventArrayAdapter;
    private ServerRequest svreq = ServerRequest.getInstance();

    View rootView;

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sportingEventArrayAdapter = new SportingEventArrayAdapter(this.getActivity(), events);
        this.setListAdapter(sportingEventArrayAdapter);

        final SwipeRefreshLayout swipeRefresh =
                (SwipeRefreshLayout) rootView.findViewById(R.id.event_list_fragment);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFragment();
                swipeRefresh.setRefreshing(false);
            }
        });

        this.setmSwipeRefreshLayout(swipeRefresh);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_list_view_refresh, container, false);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshFragment();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Event toOpen = events.get(position);
        IntentProtocol.viewEvent(this.getActivity(), toOpen);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_EVENT_CODE) {
            if (resultCode == SUCCESS_CODE) {
                refreshFragment();
            } else {
                Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                        "An error occurred while creating your event", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    public void onActionButtonClick() {

    }

    public void refreshFragment() {
        try{
            Thread.currentThread().sleep(500);
        }catch(Exception e){}
        Log.v("Attempting", "Event Refresh");
        events = svreq.getAllEvents();
        if (events != null) {
            sportingEventArrayAdapter.refreshItems(events);
            Log.v("Completed", "EventRefresh");
        } else {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                    "PickUpSports was unable to connect to server", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}