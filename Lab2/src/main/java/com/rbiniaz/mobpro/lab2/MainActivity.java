package com.rbiniaz.mobpro.lab2;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView title = (TextView) findViewById(R.id.titleField);
        final TextView note = (TextView) findViewById(R.id.noteField);
        Button save = (Button)findViewById(R.id.saveButton);
        final ListView notes = (ListView) findViewById(R.id.noteList);

        final NotesDbAdapter dbAdapter = new NotesDbAdapter(this);
        dbAdapter.open();

        final NotesListCursorAdapter adapter = new NotesListCursorAdapter(this, dbAdapter.getAllNotes(), dbAdapter);
        notes.setAdapter(adapter);



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileName = title.getText().toString();
                String noteText = note.getText().toString();
                if (fileName != null && noteText != null){
                    dbAdapter.createNote(fileName, noteText);
                    adapter.changeCursor(dbAdapter.getAllNotes());
                    title.setText("");
                    note.setText("");
                }
            }
        });

        save.setFocusable(false);

        notes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Note note  = ((NotesListCursorAdapter.ViewHolder) view.getTag()).note;
                Intent in = new Intent(getApplicationContext(), NoteDetailActivity.class);
                in.putExtra("name", note.getName());
                in.putExtra("contents", note.getContents());
                startActivity(in);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
