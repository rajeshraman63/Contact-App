package com.bingo.sqlitecontacts;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper mydb;

    EditText et_name,et_phone,et_id;
    Button btn_create,btn_contacts,btn_update,btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creating the database

        mydb = new DatabaseHelper(this);

        et_name = (EditText)findViewById(R.id.et_name);
        et_phone = (EditText)findViewById(R.id.et_phone);
        et_id = (EditText)findViewById(R.id.et_id);
        btn_create = (Button) findViewById(R.id.btn_create);
        btn_contacts = (Button) findViewById(R.id.btn_contacts);
        btn_update = (Button)findViewById(R.id.btn_update);
        btn_delete = (Button)findViewById(R.id.btn_delete);

        btn_create.setOnClickListener(this);
        btn_contacts.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();

        switch (i){
            case R.id.btn_create:
                createContact();
                break;
            case R.id.btn_contacts:
                showContacts();
                break;
            case R.id.btn_update:
                udpateData();
                break;
            case R.id.btn_delete:
                deleteContact();
                break;
            default:
        }
    }

    private void deleteContact() {
        int no_of_rows_effected = mydb.deleteContact(et_id.getText().toString());

        if(no_of_rows_effected == 0)
            Toast.makeText(this, "No Cotact is Deleted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Contact is deleted", Toast.LENGTH_SHORT).show();
    }

    private void udpateData() {
        boolean isUpdated = mydb.updateContacts(et_id.getText().toString(),et_name.getText().toString(),et_phone.getText().toString());

        if(isUpdated)
            Toast.makeText(this, "Updated Succeffully", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Could not be Updated", Toast.LENGTH_SHORT).show();
    }

    private void showContacts() {
        Cursor contacts = mydb.viewAllContacts();

        if(contacts.getCount()==0){
            showMessage("Error","Empty Table");
            return;
        }

        StringBuffer buffer = new StringBuffer();

        buffer.append("ID    Name     Phone \n\n");

        while (contacts.moveToNext()){
            buffer.append(contacts.getString(0) + " . " + contacts.getString(1) + "  " + contacts.getString(2) + "\n\n");
        }
        showMessage("Contacts",buffer.toString());
    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    private void createContact() {
        boolean isInserted = mydb.insertData(et_name.getText().toString(),et_phone.getText().toString());

        if(isInserted)
            Toast.makeText(this, "Contact Created Successfully", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Contact Creation Failed", Toast.LENGTH_SHORT).show();
    }
}
