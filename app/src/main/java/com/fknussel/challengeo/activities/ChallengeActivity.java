package com.fknussel.challengeo.activities;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.fknussel.challengeo.fragments.ChallengeFragment;
import com.fknussel.challengeo.R;


public class ChallengeActivity extends ActionBarActivity {

    private int streak;
    private int high;
    private int wrongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        
        setContentView(R.layout.activity_challenge);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.challenge_accepted);
        actionBar.setSubtitle("What's this flag?");

        if (savedInstanceState == null) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            this.streak = preferences.getInt("streak", 0);
            this.high = preferences.getInt("high", 0);
            this.wrongs = preferences.getInt("wrongs", 0);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ChallengeFragment())
                    .commit();
        } else {
            this.streak = savedInstanceState.getInt("streak");
            this.high = savedInstanceState.getInt("high");
            this.wrongs = savedInstanceState.getInt("wrongs");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("streak", this.streak);
        outState.putInt("high", this.high);
        outState.putInt("wrongs", this.wrongs);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_challenge, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    public int getStreak() {
        return this.streak;
    }

    public int getHigh() {
        return this.high;
    }

    public int getWrongs() {
        return this.wrongs;
    }

    public void persistScore() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("streak", this.streak);
        editor.putInt("high", this.high);
        editor.putInt("wrongs", this.wrongs);
        editor.apply();
    }

    public void keepScore(boolean correct) {
        if (correct) {
            this.streak++;      // increment rights

            // is new highest score?
            if (this.streak > this.high) {
                this.high = this.streak;
            }
        } else {
            this.wrongs++;      // increment wrongs
            this.streak = 0;    // reset streak
        }

        this.persistScore();
    }
}
