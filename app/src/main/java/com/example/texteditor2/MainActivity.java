package com.example.texteditor2;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button buttonUndo, buttonRedo;
    private Stack<String> undoStack, redoStack;
    private boolean isUndoOrRedo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        buttonUndo = findViewById(R.id.buttonUndo);
        buttonRedo = findViewById(R.id.buttonRedo);

        undoStack = new Stack<>();
        redoStack = new Stack<>();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (!isUndoOrRedo) {
                    String text = editable.toString();
                    undoStack.push(text);
                    buttonRedo.setEnabled(false);
                }
            }
        });
    }

    public void onUndoClick(View view) {
        if (!undoStack.isEmpty()) {
            isUndoOrRedo = true;
            // Save the cursor position
            int cursorPosition = editText.getSelectionStart();

            redoStack.push(undoStack.pop());
            String undoText = undoStack.isEmpty() ? "" : undoStack.peek();
            editText.setText(undoText);

            // Restore the cursor position
            if (cursorPosition > undoText.length()) {
                cursorPosition = undoText.length();
            }
            editText.setSelection(cursorPosition);

            buttonRedo.setEnabled(true);
            isUndoOrRedo = false;
        }
    }


    public void onRedoClick(View view) {
        if (!redoStack.isEmpty()) {
            isUndoOrRedo = true;
            String redoText = redoStack.pop();
            editText.setText(redoText);
            undoStack.push(redoText);

            // Set cursor position to the end of the text
            editText.setSelection(redoText.length());

            buttonUndo.setEnabled(true);
            isUndoOrRedo = false;
        }
    }



}
