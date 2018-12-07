package com.example.hp.smartstudy;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by HP on 17-02-2017.
 */

public class MaterialAdapter extends BaseAdapter {

    Activity a;
    ArrayList<HashMap<String, String>> arrayList;
    LayoutInflater layoutInflater;

    MaterialAdapter(Activity a, ArrayList<HashMap<String, String>> arrayList) {
        this.a = a;
        this.arrayList = arrayList;
        layoutInflater = a.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.raw_material, null);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.ivShowDetails);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(a);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_materials);

                TextView text = (TextView) dialog.findViewById(R.id.tvMaterialTitle);
                TextView desc = (TextView) dialog.findViewById(R.id.tvMaterialDesc);
                text.setText(arrayList.get(position).get(MaterialActivity.TAG_MATERIAL_TITLE));
                desc.setText(arrayList.get(position).get(MaterialActivity.TAG_MATERIAL_DESCRIPTION));
                dialog.show();
                Button btnConfirm = (Button) dialog.findViewById(R.id.btnDownload);
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Close dialog
                        DownloadFile.downloadFile(a, arrayList.get(position).get(MaterialActivity.TAG_MATERIAL_TITLE_URL).replace(" ", "%20"));
                        dialog.dismiss();
                    }
                });
            }
        });
        TextView tvmaterial = (TextView) convertView.findViewById(R.id.tvMaterialTitle);
        final HashMap<String, String> hashMap = arrayList.get(position);
        tvmaterial.setText(hashMap.get(MaterialActivity.TAG_MATERIAL_TITLE));
        Button btnDownload = (Button) convertView.findViewById(R.id.btnDownload);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadFile.downloadFile(a, hashMap.get(MaterialActivity.TAG_MATERIAL_TITLE_URL).replace(" ", "%20"));
            }
        });
        return convertView;
    }
}
