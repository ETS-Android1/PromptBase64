package com.pleiades.pleione.base64.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pleiades.pleione.base64.R;

import java.util.ArrayList;

import static com.pleiades.pleione.base64.ui.Configs.KEY_CLIPBOARD;
import static com.pleiades.pleione.base64.ui.Configs.KEY_LINK;
import static com.pleiades.pleione.base64.ui.Configs.SETTING_TYPE_CLIPBOARD;
import static com.pleiades.pleione.base64.ui.Configs.SETTING_TYPE_LINK;

public class SettingsActivity extends AppCompatActivity {
    private ArrayList<Integer> settingTypeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Context context = SettingsActivity.this;

        View appbar = findViewById(R.id.appbar_settings);
        Toolbar toolbar = appbar.findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        settingTypeList = new ArrayList<>();
        settingTypeList.add(SETTING_TYPE_CLIPBOARD);
        settingTypeList.add(SETTING_TYPE_LINK);
        RecyclerView settingRecyclerView = findViewById(R.id.recycler_view);
        settingRecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        settingRecyclerView.setLayoutManager(linearLayoutManager);
        SettingRecyclerAdapter settingAdapter = new SettingRecyclerAdapter();
        settingRecyclerView.setAdapter(settingAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home)
            onBackPressed();

        return super.onOptionsItemSelected(item);
    }

    private class SettingRecyclerAdapter extends RecyclerView.Adapter<SettingRecyclerAdapter.SettingViewHolder> {
        private boolean isCheckLocked;

        class SettingViewHolder extends RecyclerView.ViewHolder {
            TextView titleTextView, contentsTextView;
            SwitchCompat switchCompat;

            SettingViewHolder(View view) {
                super(view);

                titleTextView = view.findViewById(R.id.title_setting);
                contentsTextView = view.findViewById(R.id.contents_setting);
                switchCompat = view.findViewById(R.id.switch_setting);
            }
        }

        @NonNull
        @Override
        public SettingViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_setting, viewGroup, false);
            return new SettingViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SettingViewHolder holder, final int position) {
            SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            int settingType = settingTypeList.get(position);

            if (settingType == SETTING_TYPE_CLIPBOARD) {
                holder.titleTextView.setText(R.string.setting_title_clipboard);
                holder.contentsTextView.setText(R.string.setting_contents_clipboard);
            } else if (settingType == SETTING_TYPE_LINK) {
                holder.titleTextView.setText(R.string.setting_title_link);
                holder.contentsTextView.setText(R.string.setting_contents_link);
            }

            holder.switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isCheckLocked)
                    return;

                if (settingType == SETTING_TYPE_CLIPBOARD) editor.putBoolean(KEY_CLIPBOARD, isChecked);
                else if (settingType == SETTING_TYPE_LINK) editor.putBoolean(KEY_LINK, isChecked);

                editor.apply();
            });

            // set checked
            isCheckLocked = true;
            if (settingType == SETTING_TYPE_CLIPBOARD) holder.switchCompat.setChecked(prefs.getBoolean(KEY_CLIPBOARD, true));
            else if (settingType == SETTING_TYPE_LINK) holder.switchCompat.setChecked(prefs.getBoolean(KEY_LINK, true));
            isCheckLocked = false;
        }

        @Override
        public int getItemCount() {
            return settingTypeList.size();
        }
    }
}