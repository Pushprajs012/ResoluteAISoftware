package com.ps.resoluteaisoftware;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ps.resoluteaisoftware.databinding.HistoryBinding;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class History extends AppCompatActivity {
private HistoryBinding historyBinding;
private RecyclerView recyclerView;
private AppActivity appActivity;
private ArrayList<Model> arrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        historyBinding=HistoryBinding.inflate(getLayoutInflater());
        setContentView(historyBinding.getRoot());
        appActivity= (AppActivity) getApplication();
        arrayList=new ArrayList<Model>();


        Adapter adapter=new Adapter(arrayList);
        historyBinding.rv.setAdapter(adapter);
        historyBinding.rv.setLayoutManager(new LinearLayoutManager(History.this));

        ExecutorService executorService= Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                appActivity.getMyRef().child(appActivity.getFirebaseAuth().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                            Model model=dataSnapshot.getValue(Model.class);
                            System.out.println("hiii"+model);
                            arrayList.add(model);

                        }
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println(error);
                    }
                });
            }
        });


    }
}
