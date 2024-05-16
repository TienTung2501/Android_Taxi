package com.example.taxi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class UpdateActivity extends AppCompatActivity {

    private Taxi object;
    TextInputEditText soxe,quangduong,dongia,khuyenmai;
    Button btnAdd,btnBack;
    private static final int REQUEST_UPDATE_CONTACT = 1;
    SqliteDB_1501 mydb;
    @Override
    protected void onStart() {
        super.onStart();
        mydb.openDb();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mydb.closeDb();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getIntentExtra();
        initComponent();
        eventListener();
    }

    private void eventListener() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=object.getId();
                String _soxe= soxe.getText().toString();
                double _quangduong= Double.parseDouble(quangduong.getText().toString());
                double _dongia= Double.parseDouble(dongia.getText().toString());
                int _phantram= Integer.parseInt(khuyenmai.getText().toString());
                mydb.Update(id,_soxe,_quangduong,_dongia,_phantram);
                Toast.makeText(UpdateActivity.this,"Update Suceess",Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initComponent() {
        mydb=new SqliteDB_1501(this);
        btnAdd=findViewById(R.id.updateBtn);
        btnBack=findViewById(R.id.backBtn);
        soxe=findViewById(R.id.SoXeTxt);
        quangduong=findViewById(R.id.QuangDuongTxt);
        dongia=findViewById(R.id.DonGiaTxt);
        khuyenmai=findViewById(R.id.KhuyenMaiTxt);
        soxe.setText(object.getSoXe());
        quangduong.setText(String.valueOf(object.getQuangDuong()));
        dongia.setText(String.valueOf(object.getDonGia()));
        khuyenmai.setText(String.valueOf(object.getPhanTram()));
    }

    private void getIntentExtra() {
        Intent intent = getIntent();// cần khai báo biến object ở trên
        object=(Taxi) intent.getSerializableExtra("object");
    }

}