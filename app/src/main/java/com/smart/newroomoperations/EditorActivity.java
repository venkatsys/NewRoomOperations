package com.smart.newroomoperations;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


import com.smart.newroomoperations.database.NoteEntity;
import com.smart.newroomoperations.utilities.Constants;
import com.smart.newroomoperations.viewmodel.EditorViewModel;

public class EditorActivity extends AppCompatActivity {

    private EditorViewModel mEditorViewModel;
    private EditText mEditText;
    private boolean mNewNote;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Toolbar toolbar = this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mEditText = this.findViewById(R.id.note_text);
        initViewModel();
    }

    private void initViewModel() {
        mEditorViewModel = ViewModelProviders.of(this).get(EditorViewModel.class);
        mEditorViewModel.mLiveNote.observe(this, new Observer<NoteEntity>() {
            @Override
            public void onChanged(@Nullable NoteEntity noteEntity) {
                if (noteEntity != null) {
                    mEditText.setText(noteEntity.getText());
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setTitle(R.string.new_note);
            mNewNote = true;
        } else {
            setTitle(R.string.edit_note);
            int noteId = extras.getInt(Constants.NOTE_ID_KEY);
            mEditorViewModel.loadData(noteId);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*if (item.getItemId() ==  android.R.id.home) {
            saveAndReturn();
            return true;
        }*/
        switch (item.getItemId()){
            case android.R.id.home:
                saveAndReturn();
                break;
            case R.id.action_delete:
                mEditorViewModel.deleteNote();
                finish();
                break;
            default:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        saveAndReturn();
    }

    private void saveAndReturn() {
        mEditorViewModel.saveNote(mEditText.getText().toString());
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNewNote) {
            getMenuInflater().inflate(R.menu.menu_editor,menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

}
