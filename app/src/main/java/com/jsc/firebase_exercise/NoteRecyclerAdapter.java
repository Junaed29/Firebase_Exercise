package com.jsc.firebase_exercise;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class NoteRecyclerAdapter  extends FirestoreRecyclerAdapter<Note, NoteRecyclerAdapter.NoteViewHolder> {

    private static final String TAG = "NoteRecyclerAdapter";

    private NoteListener listener;

    public NoteRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Note> options, NoteListener listener) {
        super(options);
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull Note note) {
        holder.noteTextView.setText(note.getText());
        CharSequence charSequence = DateFormat.format("EEEE, MMM  d,  yyyy h:mm:ss a", note.getCreated().toDate());
        holder.dateTextView.setText(charSequence);
        Log.d(TAG, "onBindViewHolder: "+note.getIsCompleted());
        holder.checkBox.setChecked(note.getIsCompleted());
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.note_row, parent, false);
        return new NoteViewHolder(view);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView noteTextView;
        TextView dateTextView;
        CheckBox checkBox;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            noteTextView = itemView.findViewById(R.id.note_textView);
            dateTextView = itemView.findViewById(R.id.date_textView);
            checkBox = itemView.findViewById(R.id.checkBox);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    DocumentSnapshot snapshot = getSnapshots().getSnapshot(getAdapterPosition());

                    Note note = getItem(getAdapterPosition());
                    if (note.getIsCompleted() != isChecked){
                        listener.handleCheckChanged(isChecked,snapshot);
                    }

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.handleEditNote(getSnapshots().getSnapshot(getAdapterPosition()));

                }
            });
        }

        public void deleteItem(){
            Log.d(TAG, "deleteItem: "+getAdapterPosition());
            listener.handleNoteDelete(getSnapshots().getSnapshot(getAdapterPosition()));
        }
    }

    interface NoteListener{
        public void handleCheckChanged(boolean isChecked, DocumentSnapshot snapshot);
        public void handleEditNote(DocumentSnapshot snapshot);
        public void handleNoteDelete(DocumentSnapshot snapshot);
    }
}
