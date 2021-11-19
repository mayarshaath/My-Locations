package com.example.mylocations;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import android.os.Bundle;

public class DbUpdate extends AppCompatActivity {

    EditText Address, Latitude, Longitude, updateold, updatenew, delete;
    DatabaseAdapter helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        Address = (EditText) findViewById(R.id.editAddress);
        Latitude = (EditText) findViewById(R.id.editLatitude);
        Longitude = (EditText) findViewById(R.id.editLongitude);
        updateold = (EditText) findViewById(R.id.editText3);
        updatenew = (EditText) findViewById(R.id.editText5);
        delete = (EditText) findViewById(R.id.editText6);

        helper = new DatabaseAdapter(this);
    }

    public void addAddress(View view) {
        String t1 = Address.getText().toString();
        String t2 = Latitude.getText().toString();
        String t3 = Longitude.getText().toString();
        if (t1.isEmpty() || t2.isEmpty() || t3.isEmpty()) {
            Message.message(getApplicationContext(), "Enter Address, Latitude, and Longitude");
        } else {
            long id = helper.insertData(t1, t2, t3);
            if (id <= 0) {
                Message.message(getApplicationContext(), "Insertion Unsuccessful");
                Address.setText("");
                Latitude.setText("");
                Longitude.setText("");
            } else {
                Message.message(getApplicationContext(), "Insertion Successful");
                Address.setText("");
                Latitude.setText("");
                Longitude.setText("");
            }
        }
    }

    public void viewdata(View view) {
        String data = helper.getData();
        Message.message(this, data);
    }

    public void update(View view) {
        String a1 = updateold.getText().toString();
        String a2 = updatenew.getText().toString();
        if (a1.isEmpty() || a2.isEmpty()) {
            Message.message(getApplicationContext(), "Enter Data");
        } else {
            int a = helper.updateAddress(a1, a2);
            if (a <= 0) {
                Message.message(getApplicationContext(), "Unsuccessful");
                updateold.setText("");
                updatenew.setText("");
            } else {
                Message.message(getApplicationContext(), "Updated");
                updateold.setText("");
                updatenew.setText("");
            }
        }

    }

    public void delete(View view) {
        String address = delete.getText().toString();
        if (address.isEmpty()) {
            Message.message(getApplicationContext(), "Enter Data");
        } else {
            int a = helper.delete(address);
            if (a <= 0) {
                Message.message(getApplicationContext(), "Unsuccessful");
                delete.setText("");
            } else {
                Message.message(this, "DELETED");
                delete.setText("");
            }
        }
    }
}