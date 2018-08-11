package com.smart.newroomoperations;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.smart.newroomoperations.database.NoteEntity;
import com.smart.newroomoperations.ui.NotesAdapter;
import com.smart.newroomoperations.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private MainViewModel mViewModel;
    private NotesAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<NoteEntity> notesData = new ArrayList<>();
    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        initViewModel();
        //notesData.addAll(mViewModel.mNotes);
        //Log.i(TAG, "onCreate: " + mViewModel.mNotes.size());
    }

    private void bindViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = this.findViewById(R.id.recycler_view);
        mFloatingActionButton = this.findViewById(R.id.fab);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration divider = new DividerItemDecoration(
                mRecyclerView.getContext() , layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(divider);
        mFloatingActionButton.setOnClickListener(this);
    }

    private void initViewModel() {
        final Observer<List<NoteEntity>> notesObserver = new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(@Nullable List<NoteEntity> noteEntities) {
                notesData.clear();
                notesData.addAll(noteEntities);
                if (mAdapter == null) {
                    mAdapter = new NotesAdapter(notesData,MainActivity.this);
                    mRecyclerView.setAdapter(mAdapter);
                }else{
                   mAdapter.notifyDataSetChanged();
                }
            }
        };
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mViewModel.mNotes.observe(this,notesObserver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //int id = item.getItemId();

        switch (item.getItemId()){
            case R.id.action_add_sample_data:
                addSampleData();
                break;
            case R.id.action_delete_all:
                deleteAllNotes();
                break;
            default:
                return true;
        }

        /*if(id == R.id.action_add_sample_data){
            addSampleData();
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    private void addSampleData() {
        mViewModel.addSampleData();
    }

    private void deleteAllNotes() {
        mViewModel.deleteAllNotes();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                startActivity(new Intent(MainActivity.this,EditorActivity.class));
            break;
        }
    }
}
