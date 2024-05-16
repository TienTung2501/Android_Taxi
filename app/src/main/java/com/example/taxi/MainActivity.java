package com.example.taxi;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_UPDATE_CONTACT = 1;
    private static final int REQUEST_ADD_CONTACT = 2;

    public int position,idTaxi;
    private SqliteDB_1501 myDb;
    private ListView listViewTaxi;
    private ArrayList<Taxi> taxis;
    private ArrayAdapter<Taxi> adapter;
    ImageView addBtn;
    TextInputEditText searchTxt;
    String titleMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponent();
        eventListener();
        registerForContextMenu(listViewTaxi);
    }
    @Override
    protected void onStart() {
        super.onStart();
        myDb.openDb();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myDb.closeDb();
    }

    private void eventListener() {
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,String.valueOf(taxis.size()),Toast.LENGTH_LONG).show();
            }
        });
        listViewTaxi.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {
                idTaxi=taxis.get(i).getId();
                position=i;
                return false;
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, AddTaxiActivity.class);
                startActivityForResult(intent, REQUEST_ADD_CONTACT);
            }
        });
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu,menu);


    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getItemId() ==  R.id.mnEdit) {
            Intent intent=new Intent(MainActivity.this, UpdateActivity.class);
            intent.putExtra("object", (Serializable) taxis.get(position));
            //startActivity(intent);
            startActivityForResult(intent, REQUEST_UPDATE_CONTACT);
            return true;
        }
        else if (item.getItemId() == R.id.mnDelete){
            showDeleteConfirmationDialog();
            return true;
        }
        else {
            return super.onContextItemSelected(item);
        }
    }

    private void initComponent() {
        myDb=new SqliteDB_1501(this);
        taxis=new ArrayList<>();
        listViewTaxi=findViewById(R.id.listViewTaxi);
        addBtn=findViewById(R.id.addBtn);
        searchTxt=findViewById(R.id.searchTxt);
        displayData();

    }
    private void sortContactsByName() {
        Collections.sort(taxis, new Comparator<Taxi>() {
            @Override
            public int compare(Taxi taxi1, Taxi taxi2) {
                return taxi1.getSoXe().compareToIgnoreCase(taxi2.getSoXe());
            }
        });
    }
    private void displayData(){
        taxis.clear();
        fetchData();
        sortContactsByName();
        adapter=new AdapterTaxi(MainActivity.this, R.layout.taxi_item, taxis);
        listViewTaxi.setAdapter(adapter);
        adapter.notifyDataSetChanged(); // Cập nhật dữ liệu trong Adapter
    }
    private  void fetchData(){
        Cursor cursor= myDb.DisplayAll();
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            int id=cursor.getInt(cursor.getColumnIndexOrThrow(SqliteDB_1501.getId()));
            String soxe=cursor.getString(cursor.getColumnIndexOrThrow(SqliteDB_1501.getSoXe()));
            double quangduong=cursor.getDouble(cursor.getColumnIndexOrThrow(SqliteDB_1501.getQuangDuong()));
            double dongia=cursor.getDouble(cursor.getColumnIndexOrThrow(SqliteDB_1501.getDonGia()));
            int phatram=cursor.getInt(cursor.getColumnIndexOrThrow(SqliteDB_1501.getPhanTram()));
            Taxi c=new Taxi(id,soxe,quangduong,dongia,phatram);
            taxis.add(c);
        }

    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(taxis.get(position).getSoXe()+" Are you want to delete?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Xử lý khi người dùng đồng ý xóa
                        deleteContact(idTaxi);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        builder.create().show();
    }

    private void deleteContact(int id) {
        myDb.Delete(id);
        for (int i=0;i<taxis.size();i++){
            if(taxis.get(i).getId()==id){
                taxis.remove(i);
                break;
            }
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_UPDATE_CONTACT && resultCode == RESULT_OK) {
            // Nếu dữ liệu đã được cập nhật trong InformationDetailActivity, làm mới danh sách và hiển thị lại
            displayData();
        }
        if(requestCode == REQUEST_ADD_CONTACT && resultCode == RESULT_OK){
            // If a new contact is added successfully, refresh the contact list
            displayData();
        }
    }
}