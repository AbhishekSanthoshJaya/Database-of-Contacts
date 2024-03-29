package com.aby.contactdb_abhishek_c0769778_android.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.aby.contactdb_abhishek_c0769778_android.R;
import com.aby.contactdb_abhishek_c0769778_android.database.ContactDB;
import com.aby.contactdb_abhishek_c0769778_android.model.Contact;

public class AddContactActivity extends AppCompatActivity {

    EditText fname, lname, address, phone, email;
    Button addPerson;
    Contact editPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_contact);

        //Getting values from the text fields
        fname = findViewById( R.id.text_fname );
        lname = findViewById( R.id.text_lname );
        address = findViewById( R.id.text_address );
        phone = findViewById( R.id.text_phone );
        addPerson = findViewById( R.id.button_submit );
        email = findViewById(R.id.text_email);

        //Getting contact parcel
        editPerson = getIntent().getParcelableExtra( "contact" );

        //Adding the new contact
        addPerson.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Certain fields are mandatory and alert lets the user know
                if(fname.getText().toString().isEmpty() || address.getText().toString().isEmpty() || phone.getText().toString().isEmpty())
                {
                    new AlertDialog.Builder(AddContactActivity.this)
                            .setTitle("Fields missing")
                            .setMessage("First Name, Number and Address are required")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, null)
                            .show();
                    return;
                }
                String fnameString = fname.getText().toString();
                String lnameString = lname.getText().toString();
                String addressString = address.getText().toString();
                String phoneString = phone.getText().toString();
                String emailString = email.getText().toString();
                ContactDB personDB = ContactDB.getInstance( v.getContext() );

                if (editPerson != null)
                {
                    editPerson.setFirstname( fnameString );
                    editPerson.setLastname( lnameString );
                    editPerson.setAddress( addressString );
                    editPerson.setPhone( phoneString );
                    editPerson.setEmail(emailString);
                    personDB.daoObject().update( editPerson );

                }
                else {
                    //Creating contact object
                    Contact contact = new Contact( fnameString, lnameString, addressString, phoneString, emailString);
                    personDB.daoObject().insert( contact );
                }

                finish();
            }
        } );

        if (editPerson != null)
        {
            fname.setText( editPerson.getFirstname() );
            lname.setText( editPerson.getLastname() );
            address.setText( editPerson.getAddress() );
            phone.setText( editPerson.getPhone() );
            email.setText(editPerson.getEmail());
        }

    }
    //Method to dismiss keyboard on clicking outside text field
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}