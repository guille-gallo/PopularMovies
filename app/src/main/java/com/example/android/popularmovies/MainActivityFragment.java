package com.example.android.popularmovies;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A fragment containing the list view of Android versions.
 */
public class MainActivityFragment extends Fragment {

    private AndroidFlavorAdapter flavorAdapter;
    private ArrayList<AndroidFlavor> flavorList;



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null || !savedInstanceState.containsKey("flavors")) {

            flavorList = new ArrayList();
        }
        else {
            flavorList = savedInstanceState.getParcelableArrayList("flavors");
            System.out.println("LA LISTAAAA: " + flavorList);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovie();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("flavors", flavorList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        flavorAdapter = new AndroidFlavorAdapter(getActivity(), flavorList);

        // Get a reference to the ListView, and attach this adapter to it.
        GridView gridView = (GridView) rootView.findViewById(R.id.listview_flavor);
        gridView.setAdapter(flavorAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AndroidFlavor flavorClick = flavorAdapter.getItem(i);
                flavorClick.versionName += ":)";
                flavorAdapter.notifyDataSetChanged();
            }
        });

        return rootView;
    }

    private void updateMovie(){
        FetchMoviesTask movieTask = new FetchMoviesTask(flavorAdapter);
        movieTask.execute(); // executes http request and updates adapter
    }


}