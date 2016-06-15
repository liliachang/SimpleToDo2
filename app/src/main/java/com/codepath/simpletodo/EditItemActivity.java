package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;


public class EditItemActivity extends AppCompatActivity {
    private final int REQUEST_CODE = 20;
    int position = 0;
   // ArrayList<String> newItem;
   // ArrayAdapter<String> newItemsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String text = getIntent().getStringExtra("oldtext");
        position = getIntent().getIntExtra("location", -1);
        EditText editText = (EditText) findViewById(R.id.editText);
        assert editText != null;
        //editText.append("");
        editText.append(text);
    }

    public void onSubmit(View v) {
        EditText editText = (EditText) findViewById(R.id.editText);
        Intent data = new Intent();
        position = getIntent().getIntExtra("location", -1);
        data.putExtra("newtext", editText.getText().toString());
        data.putExtra("location", position);
        setResult(RESULT_OK, data);
        this.finish();
    }


}
