package pickupsports2.ridgewell.pickupsports2.activites;

import com.software.shell.fab.FloatingActionButton;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import pickupsports2.ridgewell.pickupsports2.R;
import pickupsports2.ridgewell.pickupsports2.utilities.ServerRequest;

public class MainActivity extends ActionBarActivity {
    final int CREATE_EVENT_CODE = 1;

    EventFragment eventList;

    private ServerRequest svreq = new ServerRequest();

    FloatingActionButton create_event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Games");
        setContentView(R.layout.activity_main_view_screen);

        FragmentManager fm = getFragmentManager();

        if (fm.findFragmentById(android.R.id.content) == null) {
            eventList = new EventFragment();
            fm.beginTransaction().add(android.R.id.content, eventList).commit();
        }

        createEventClickListener();
    }

    private void createEventClickListener() {
        create_event = (FloatingActionButton) findViewById(R.id.create_event);
        create_event.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent launch_new_event = new Intent(MainActivity.this, CreateEventActivity.class);
                startActivityForResult(launch_new_event, CREATE_EVENT_CODE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_view_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.create_new_event) {
            Intent launch_new_event = new Intent(MainActivity.this, CreateEventActivity.class);
            startActivityForResult(launch_new_event, CREATE_EVENT_CODE);
        }
        return super.onOptionsItemSelected(item);
    }
}
