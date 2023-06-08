package com.ps.resoluteaisoftware;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.ps.resoluteaisoftware.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;
    ProgressDialog progressDialog;
    AppActivity appActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        appActivity= (AppActivity) getApplication();

    if (appActivity.getFirebaseAuth().getCurrentUser()!=null){
        startActivity(new Intent(MainActivity.this, ScannerDashboard.class));
        finish();
    }

        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient singhClint= GoogleSignIn.getClient(this,gso);


        mainBinding.googlebtn.setOnClickListener(view -> {

            progressDialog=new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("कृपया लोगिन होने का इन्तजार करे...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            Intent i= singhClint.getSignInIntent();
            startActivityForResult(i,100);

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                ((AppActivity) getApplication()).getFirebaseAuth().signInWithCredential(authCredential)
                        .addOnSuccessListener(MainActivity.this, authResult -> {
                            Toast.makeText(this, "लोगिन सफल", Toast.LENGTH_SHORT).show();

                            FirebaseUser user = authResult.getUser();
                            System.out.println(user);
                            startActivity(new Intent(MainActivity.this, ScannerDashboard.class));
                        });

                progressDialog.dismiss();

            }catch (ApiException e) {
                Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
                e.printStackTrace();

            }
}
    }}