package com.jsc.firebase_exercise;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseOperationFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "FirebaseOperation";
    FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();

    public FirebaseOperationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_firebase_operation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.createbuttonId).setOnClickListener(this);
        view.findViewById(R.id.readbuttonId).setOnClickListener(this);
        view.findViewById(R.id.updatebuttonId).setOnClickListener(this);
        view.findViewById(R.id.deletebuttonId).setOnClickListener(this);
        view.findViewById(R.id.listbuttonId).setOnClickListener(this);
        view.findViewById(R.id.realbuttonId).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createbuttonId:

//                Toast.makeText(getContext(),"createbuttonId" , Toast.LENGTH_SHORT).show();
//                Map<String, Object>  map = new HashMap<>();
//                map.put("text","i wanna be a software engineering");
//                map.put("isCompleted",false);
//                map.put("created", new Timestamp(new Date()));
//
//                firestoreDB.collection("notes")
//                        .add(map)
//                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                            @Override
//                            public void onSuccess(DocumentReference documentReference) {
//                                Log.d(TAG, "onSuccess: task was successful");
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.d(TAG, "onFailure: task was not successful");
//                            }
//                        });
//
//                Map<String, Object>  map = new HashMap<>();
//                map.put("name","iPhone 11");
//                map.put("price",699);
//                map.put("isAvailable", true);
//
//                firestoreDB.collection("products")
//                        .add(map)
//                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                            @Override
//                            public void onSuccess(DocumentReference documentReference) {
//                                Log.d(TAG, "onSuccess: task was successful");
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.e(TAG, "onFailure: ", e);
//                            }
//                        });


                //Create a new writer Batch
                WriteBatch batch = firestoreDB.batch();

                DocumentReference reference = firestoreDB.collection("products").document();
                batch.set(reference, new products("iMac 27", true, (long) 2197));

                DocumentReference reference1 = firestoreDB.collection("products").document();
                batch.set(reference1, new products("iMac Pro", true, (long) 4815));

                DocumentReference reference2 = firestoreDB.collection("products").document();
                batch.set(reference2, new products("iPad Air 10.5", true, (long) 794));

                DocumentReference reference3 = firestoreDB.collection("products").document();
                batch.set(reference3, new products("iPad Pro 11", true, (long) 941));

                DocumentReference reference4 = firestoreDB.collection("products").document();
                batch.set(reference4, new products("iPad Pro 12.9", true, (long) 1150));

                DocumentReference reference5 = firestoreDB.collection("products").document();
                batch.set(reference5, new products("iPad Wi-Fi Only", false, (long) 417));

                DocumentReference reference6 = firestoreDB.collection("products").document();
                batch.set(reference6, new products("iPhone 11 Pro", true, (long) 999));

                DocumentReference reference7 = firestoreDB.collection("products").document();
                batch.set(reference7, new products("iPhone X", false, (long) 400));

                DocumentReference reference8 = firestoreDB.collection("products").document();
                batch.set(reference8, new products("iPhone 7", false, (long) 150));


                batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: isSuccessful");
                        } else {
                            Log.e(TAG, "onComplete: is not Successful", task.getException());
                        }
                    }
                });

                break;


            case R.id.readbuttonId:
//
//                firestoreDB.collection("products")
//                        .get()
//                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                            @Override
//                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                                List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
//                                for (DocumentSnapshot snapshot : documentSnapshots){
//                                    Log.d(TAG, "onSuccess: "+snapshot.getData());
//                                }
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//
//                            }
//                        });


//                firestoreDB.collection("products")
////                        .whereLessThan("price", 1000)
//                        .whereEqualTo("available", false)
//                        .get()
//                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                            @Override
//                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                                List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
//                                for (DocumentSnapshot snapshot : documentSnapshots){
//                                    Log.d(TAG, "onSuccess: "+snapshot.getData());
//                                }
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//
//                            }
//                        });


//                firestoreDB.collection("products")
//                        .document("D3u5Z5Di0xVaXeLZqpJh")
//                        .get()
//                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                            @Override
//                            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                Log.d(TAG, "onSuccess: "+documentSnapshot.getData());
//                                Log.d(TAG, "onSuccess: "+documentSnapshot.getString("name"));
//                                Log.d(TAG, "onSuccess: "+documentSnapshot.getBoolean("available"));
//                                Log.d(TAG, "onSuccess: "+documentSnapshot.getLong("price"));
//                            }
//                        });

                firestoreDB.collection("products")
//                        .orderBy("price")
                        .orderBy("price", Query.Direction.DESCENDING)
                        .limit(5)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<products> productsList = queryDocumentSnapshots.toObjects(products.class);
                                for (products p : productsList) {
                                    Log.d(TAG, "onSuccess: " + p.toString());
                                }
                            }
                        });


                break;
            case R.id.updatebuttonId:
                final DocumentReference documentReference = firestoreDB.collection("products")
                                                                 .document("nvn76WUEonXp3p2JZUxV");

                Map<String,Object> map = new HashMap<>();
                map.put("name", "MacBook 12 best");
               // map.put("price", FieldValue.delete());
               // map.put("price", FieldValue.increment(50));
               // map.put("price", FieldValue.increment(-50));


                documentReference.update(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Log.d(TAG, "onComplete: Complete");
                                }
                            }
                        });

                break;
            case R.id.deletebuttonId:

//                firestoreDB.collection("products")
//                        .document("123")
//                        .delete()
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()){
//                                    Log.d(TAG, "onComplete: Deleted Success");
//                                }
//                            }
//                        });


                firestoreDB.collection("products")
                        .whereEqualTo("name","junaed")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){

                                    WriteBatch batch1 = firestoreDB.batch();

                                    List<DocumentSnapshot> documentSnapshotList = task.getResult().getDocuments();

                                    for (DocumentSnapshot documentSnapshot : documentSnapshotList){
                                        batch1.delete(documentSnapshot.getReference());
                                    }

                                    batch1.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "onComplete: Batch Delete success");
                                            }
                                        }
                                    });

                                }
                            }
                        });

                break;
            case R.id.listbuttonId:
                Toast.makeText(getContext(), "listbuttonId", Toast.LENGTH_SHORT).show();
                break;
            case R.id.realbuttonId:
                firestoreDB.collection("products")
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                if (e != null) {
                                    return;
                                }
                                if (queryDocumentSnapshots != null) {
                                    Log.d(TAG, "onEvent: ---------------------------------------");

//                                    // Return all list contain the update instance
//                                    List<DocumentSnapshot> documentSnapshotList = queryDocumentSnapshots.getDocuments();
//                                    for (DocumentSnapshot snapshot : documentSnapshotList){
//                                        Log.d(TAG, "onEvent: "+snapshot.getData());
//                                    }
//
                                    // Return only the update instance
                                    List<DocumentChange> documentChangeList = queryDocumentSnapshots.getDocumentChanges();
                                    for (DocumentChange documentChange : documentChangeList){
                                        Log.d(TAG, "onEvent: "+documentChange.getDocument().getData());
                                    }


                                } else {
                                    Toast.makeText(getContext(), "queryDocumentSnapshots is null", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
        }
    }
}