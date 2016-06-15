package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final int REQUEST_CODE = 20;
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        readItems();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.lvItems);
        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
        setupEditViewListener();
    }

    private void setupEditViewListener() {
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        launchEditView(position);
                        writeItems();
                    }
                }
        );
    }
    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }

                });
    }

    @Override
    protected void onActivityResult(int request, int result, Intent data) {
        if (result == RESULT_OK && request == REQUEST_CODE) {
            String name = data.getExtras().getString("newtext");
            int position = data.getExtras().getInt("position");
            items.set(position, name);
            //writeItems();
            itemsAdapter.notifyDataSetChanged();
            writeItems();
        }
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        itemsAdapter.notifyDataSetChanged();
        writeItems();
    }

    public void readItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e){
            items = new ArrayList<String>();
        }
    }

    public void writeItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(file, items);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void launchEditView(int position) {
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        //startActivityForResult(i, REQUEST_CODE);
        String text = items.get(position);
        i.putExtra("oldtext", text);
        i.putExtra("location", position);
        startActivityForResult(i, REQUEST_CODE);
    }

    public void onSubmit() {
        this.finish();
    }


}
