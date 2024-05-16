package com.example.taxi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AdapterTaxi extends ArrayAdapter<Taxi> {
    public AdapterTaxi(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public AdapterTaxi(@NonNull Context context, int resource, @NonNull List<Taxi> objects) {
        super(context, resource, objects);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v= convertView;
        if(v==null){
            LayoutInflater vi=LayoutInflater.from(getContext());
            v=vi.inflate(R.layout.taxi_item,null);// lấy từ layout ta thiết kế

        }
        Taxi sv=getItem(position);
        if(sv!= null){

            TextView soxe= v.findViewById(R.id.SoXeTxt);
            TextView quangduong=v.findViewById(R.id.QuangDuongTxt);
            TextView tongtien=v.findViewById(R.id.TongTienTxt);
            soxe.setText(String.valueOf(sv.getSoXe()));
            quangduong.setText(String.valueOf(sv.getQuangDuong()));
            tongtien.setText(String.valueOf(sv.getQuangDuong()*sv.getDonGia()-(sv.getQuangDuong()*sv.getDonGia()*sv.getPhanTram())/100));
        }
        return v;
    }
}
