package edu.iastate.goalfriends.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import edu.iastate.goalfriends.R;
import edu.iastate.goalfriends.threads.TestAsyncTask;

public class FriendsActivity extends AppCompatActivity {
    public static ListView friendsListView;
    public static ListAdapter friendsListAdapter;
    public static List<String> friends = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        friendsListAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, friends);

        friendsListView = findViewById(R.id.list);

        TestAsyncTask testAsyncTask = new TestAsyncTask(FriendsActivity.this, MainActivity.token);

        AsyncTask<String, Void, List<String>> result = testAsyncTask.execute();

        friendsListView.setAdapter(friendsListAdapter);

        //friendsListAdapter.notify();
    }
}
