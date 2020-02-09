package com.deadlyllama.journal.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deadlyllama.journal.R;
import com.deadlyllama.journal.adapter.EntryAdapter;
import com.deadlyllama.journal.entity.Entry;
import com.deadlyllama.journal.viewmodel.EntryViewModel;

import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    private Boolean initialised = false;

    private EntryViewModel entryViewModel;
    private EntryAdapter entryAdapter;
    private RecyclerView entryRecyclerView;

    private final String TAG = "com.deadlyllama.journal.DashboardFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        if (!initialised) {
            Log.d(TAG, "onCreateView: Finding recyclerview home");
            entryRecyclerView = root.findViewById(R.id.recyclerview_home);
            Log.d(TAG, "onCreateView: Creating linear layout manager");
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                    getActivity().getBaseContext()
            );

            Log.d(TAG, "onCreateView: Attaching linear layout manager to recyclerview");
            entryRecyclerView.setLayoutManager(linearLayoutManager);

            Log.d(TAG, "onCreateView: Instantiating adapter");
            entryAdapter = new EntryAdapter(this.getContext());
            Log.d(TAG, "onCreateView: Retrieving view model");
            entryViewModel = ViewModelProviders.of(getActivity()).get(EntryViewModel.class);

            Log.d(TAG, "onCreateView: Setting adapter to recyclerview");
            entryRecyclerView.setAdapter(entryAdapter);

            initialised = true;
        }

        Log.d(TAG, "onCreateView: Getting entries");
        entryViewModel.getAllEntries().observe(this, new Observer<List<Entry>>() {
            @Override
            public void onChanged(List<Entry> entries) {
                Log.d(TAG, "onChanged: Entries changed! list now has: " + entries.size() + " items");
                entryAdapter.setAllEntries(entries);
                entryAdapter.notifyDataSetChanged();
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onStart() {
        super.onStart();

        entryViewModel.getAllEntries().observe(this, new Observer<List<Entry>>() {
            @Override
            public void onChanged(List<Entry> entries) {
                entryAdapter.setAllEntries(entries);
            }
        });

        Log.d("DASHBOARDFRAGMENT", "onStart: started!");
    }
}