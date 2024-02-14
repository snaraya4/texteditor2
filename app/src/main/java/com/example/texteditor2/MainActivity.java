package com.example.texteditor2;

import android.os.Bundle; //for handling saved instances

//for handling text
import android.text.Editable;
import android.text.TextWatcher;

import android.view.View; //for working with UI components

//for ui widgets like buttons and edit ext components
import android.widget.Button;
import android.widget.EditText;

//for providing compatibility with older android versions
import androidx.appcompat.app.AppCompatActivity;

import java.util.Stack; //for stack

public class MainActivity extends AppCompatActivity {
    //AppcompatActivity is an Android Support class for activities
    private EditText editText;  //input text field reference
    private Button buttonUndo, buttonRedo; //button references
    private Stack<String> undoStack, redoStack; //to maintain history of changes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //using specific id
        editText = findViewById(R.id.editText);
        buttonUndo = findViewById(R.id.buttonUndo);
        buttonRedo = findViewById(R.id.buttonRedo);

        undoStack = new Stack<>();
        redoStack = new Stack<>();

        new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                undoStack.push(text);
                buttonRedo.setEnabled(false);
            }
        };
    }

    public void onUndoClick(View view) {
        if (!undoStack.isEmpty()) {
            redoStack.push(undoStack.pop());
            if (!undoStack.isEmpty()) {
                editText.setText(undoStack.peek());
            } else {
                editText.setText("");
            }
            buttonRedo.setEnabled(true);
        }
    }

    public void onRedoClick(View view) {
        if (!redoStack.isEmpty()) {
            editText.setText(redoStack.pop());
            buttonUndo.setEnabled(true);
        }
    }
}
