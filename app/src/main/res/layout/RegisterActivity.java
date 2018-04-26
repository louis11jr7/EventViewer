package utpb.team8.eventviewer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameField;
    private EditText emailField;
    private EditText passField;
    private EditText userField;
    private EditText phoneField;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameField = (EditText) findViewById(R.id.nameField);
        passField = (EditText) findViewById(R.id.passField);
        emailField = (EditText) findViewById(R.id.emailField);
        userField = (EditText) findViewById(R.id.userField);
        phoneField = (EditText) findViewById(R.id.phoneField);

        mAuth=FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
    }


    public void registerClick(View view){
        final String name = nameField.getText().toString().trim();
        String pass = passField.getText().toString().trim();
        String email = emailField.getText().toString().trim();
        final String user = userField.getText().toString().trim();
        final String phone = phoneField.getText().toString().trim();


        if(!TextUtils.isEmpty(name)&& !TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){
            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        String userID=mAuth.getCurrentUser().getUid();
                        DatabaseReference current_userDB= mDatabase.child(userID);
                        current_userDB.child("Name").setValue(name);
                        current_userDB.child("ProfilePicture").setValue("default");
                        current_userDB.child("Username").setValue(user);
                        current_userDB.child("PhoneNumber").setValue(phone);

                        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                    }
                }
            });
        }
    }
}
