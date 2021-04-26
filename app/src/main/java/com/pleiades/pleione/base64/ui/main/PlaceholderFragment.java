package com.pleiades.pleione.base64.ui.main;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.pleiades.pleione.base64.R;

import java.util.Base64;

import static android.content.Context.CLIPBOARD_SERVICE;

public class PlaceholderFragment extends Fragment {
    private Context context;

    private static final String ARG_SECTION_NUMBER = "section_number";
    private int index;

    private PageViewModel pageViewModel;
    private EditText inputEditText;
    private TextView outputTextView;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
            pageViewModel.setHint(getString(index == 0 ? R.string.hint_encode : R.string.hint_decode));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        inputEditText = root.findViewById(R.id.input);
        outputTextView = root.findViewById(R.id.output);

        pageViewModel.getHint().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                inputEditText.setHint(s);
            }
        });
        pageViewModel.getOutput().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                outputTextView.setText(s);
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        inputEditText.requestFocus();
    }

    public void convert() {
        String input = inputEditText.getText().toString();
        String output = index == 0 ? encode(input) : decode(input);
        pageViewModel.setOutput(output);

        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("output", output);
        clipboardManager.setPrimaryClip(clipData);
    }

    private String encode(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    private String decode(String input) {
        return new String(Base64.getDecoder().decode(input));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}