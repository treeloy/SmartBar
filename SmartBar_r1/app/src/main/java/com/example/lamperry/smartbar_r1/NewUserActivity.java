package com.example.lamperry.smartbar_r1;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


// this class defines the behavior of the new user screen
// namely takes user input to create account, parses through login/password DB to authorize new
// account, and logs user in
// throws user prompt if login already created
public class NewUserActivity extends ActionBarActivity {

    EditText newUsernameText, newPasswordText;
    String usernameString, passwordString;
    ArrayList<String> usernameDB = new ArrayList<>();
    ArrayList<String> passwordDB = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        usernameDB = ((MyApplication)this.getApplication()).getUsernameDB();
        passwordDB = ((MyApplication)this.getApplication()).getPasswordDB();

        newUsernameText = (EditText)findViewById(R.id.type_email);
        newPasswordText = (EditText)findViewById(R.id.type_password);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // makes sure user input is valid; has entered something for username and password
    // and throws user prompt if login already taken
    public void checkUserLogin(View view) {
        usernameString = newUsernameText.getText().toString();
        passwordString = newPasswordText.getText().toString();

        if ((usernameString.equals("")) || (passwordString.equals(""))) {
            Toast.makeText(this, "You must enter a valid login and password", Toast.LENGTH_LONG).show();
            return;
        }

        if (!checkExists(usernameString, passwordString)) {
            newUserToWelcome(view, usernameString, passwordString);
        } else {
            Toast.makeText(this, "Username taken. Please try again.", Toast.LENGTH_LONG).show();
        }
    }

    // parses through DB to check if login account already exists
    private boolean checkExists(String username, String password) {
        int index = 0;
        for ( ; index < usernameDB.size(); index++) {
            if (username.equals(usernameDB.get(index))) {
                return true;
            }
        }
        return false;
    }

    // starts welcome activity and sets all relevant global variables
    public void newUserToWelcome(View view, String username, String password) {
        usernameDB.add(username);
        passwordDB.add((password));
        ((MyApplication)this.getApplication()).setLoggedIn(true);
        ((MyApplication)this.getApplication()).setMyUsername(usernameString);
        ((MyApplication)this.getApplication()).setMyPassword(passwordString);

        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }
}
