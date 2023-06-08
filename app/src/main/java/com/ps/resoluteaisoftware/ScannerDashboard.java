package com.ps.resoluteaisoftware;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.ps.resoluteaisoftware.databinding.ScannerdashboradBinding;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ScannerDashboard extends AppCompatActivity {
private ScannerdashboradBinding scannerDashboard;


private AppActivity appActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerDashboard=ScannerdashboradBinding.inflate(getLayoutInflater());
        setContentView(scannerDashboard.getRoot());
        appActivity= (AppActivity) getApplication();


        scannerDashboard.scan.setOnClickListener(view -> {

            IntentIntegrator intentIntegrator = new IntentIntegrator(this);
            intentIntegrator.setPrompt("Scan a barcode or QR Code");
            intentIntegrator.setOrientationLocked(false);
            intentIntegrator.initiateScan();

        });


        scannerDashboard.history.setOnClickListener(view -> {

        startActivity(new Intent(this,History.class));

        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        // if the intentResult is null then
        // toast a message as "cancelled"
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                // if the intentResult is not null we'll set
                // the content and format of scan message
              //  messageText.setText(intentResult.getContents());
               // messageFormat.setText(intentResult.getFormatName());

                Toast.makeText(this, intentResult.getContents(), Toast.LENGTH_SHORT).show();
                scannerDashboard.scannerresult.setText(intentResult.getContents());
                ExecutorService executorService= Executors.newSingleThreadExecutor();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        appActivity.getMyRef().child(appActivity.getFirebaseAuth().getUid()).push().setValue(new Model(intentResult.getContents(),intentResult.getFormatName())).addOnCompleteListener(task -> {
                            if (task.isSuccessful()){
                                System.out.println("Dataupdate");
                            }

                        });
                    }
                }); executorService.shutdown();

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
