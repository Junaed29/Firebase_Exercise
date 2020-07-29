package com.jsc.firebase_exercise;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.tuyenmonkey.mkloader.MKLoader;

import java.util.Date;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class FirstFragment extends Fragment implements NoteRecyclerAdapter.NoteListener {
    private static final String TAG = "FirstFragment";
    NavController navController;

    private RecyclerView recyclerView;

    private MKLoader loader;

    boolean check = true;

    private NoteRecyclerAdapter adapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loader = view.findViewById(R.id.mkLoaderId);
        loader.setVisibility(View.VISIBLE);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            recyclerView = view.findViewById(R.id.recyclerViewId);
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            setupRecyclerView(FirebaseAuth.getInstance().getCurrentUser());

        }

        navController = Navigation.findNavController(view);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                navController.navigate(R.id.firebaseOperationFragment);
                showAlertDialog();
            }
        });

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_thirdFragment);
            }
        });

        loader.setVisibility(View.GONE);
    }

    public void showAlertDialog() {
        Context context = getContext();
        final EditText editText = new EditText(context);

        new AlertDialog.Builder(context)
                .setTitle("Add note")
                .setView(editText)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text = editText.getText().toString();
                        addNote(text);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    public void addNote(String text) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Note note = new Note(text, false, new Timestamp(new Date()), userId);

        if (checkAvailability(text)) {
            Toast.makeText(getContext(), "Already exist", Toast.LENGTH_SHORT).show();
        } else {
            FirebaseFirestore.getInstance().collection("notes")
                    .add(note)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Note Added Successful", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

//    public void addNote(String text) {
//        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        Note note = new Note(text, false, new Timestamp(new Date()), userId);
//
//        if (false) {
//            Toast.makeText(getContext(), "Already exist", Toast.LENGTH_SHORT).show();
//        } else {
//            FirebaseFirestore.getInstance().collection("question")
//                    .document(userId)
//                    .set(note)
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()){
//                                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//        }
//
//    }

    boolean checkAvailability(String text) {

        FirebaseFirestore.getInstance().collection("notes")
                .whereEqualTo("text", text)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null || queryDocumentSnapshots.size() == 0) {
                            check = true;
                        } else {
                            check = false;
                        }
                    }
                });

        return check;
    }

    private void setupRecyclerView(FirebaseUser user) {
        Query query = FirebaseFirestore.getInstance()
                .collection("notes")
                .whereEqualTo("userId", user.getUid())
                .orderBy("isCompleted", Query.Direction.ASCENDING)
                .orderBy("created", Query.Direction.DESCENDING);


        FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query, Note.class)
                .build();

        adapter = new NoteRecyclerAdapter(options, this);
        recyclerView.setAdapter(adapter);


        adapter.startListening();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if (direction == ItemTouchHelper.LEFT) {

                NoteRecyclerAdapter.NoteViewHolder viewHolder1 = (NoteRecyclerAdapter.NoteViewHolder) viewHolder;
                viewHolder1.deleteItem();
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(getContext(), R.color.delete))
                    .addActionIcon(R.drawable.ic_baseline_delete_sweep_24)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };


    @Override
    public void handleCheckChanged(boolean isChecked, DocumentSnapshot snapshot) {
        snapshot.getReference().update("isCompleted", isChecked)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void handleEditNote(final DocumentSnapshot snapshot) {
        final Note note = snapshot.toObject(Note.class);

        Context context = getContext();
        final EditText editText = new EditText(context);
        String oldText = note.getText() + " ";
        editText.setText(oldText);
        //editText.setSelection(oldText.length());

        new AlertDialog.Builder(context)
                .setTitle("Edit note")
                .setView(editText)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newText = editText.getText().toString();
                        note.setText(newText);
                        snapshot.getReference().set(note);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void handleNoteDelete(DocumentSnapshot snapshot) {

        final DocumentReference reference = snapshot.getReference();
        final Note note = snapshot.toObject(Note.class);

        reference.delete();

        Snackbar.make(recyclerView, "Item Deleted", Snackbar.LENGTH_LONG)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reference.set(note);
                    }
                }).show();


    }
}