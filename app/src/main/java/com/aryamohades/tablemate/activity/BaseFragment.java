package com.aryamohades.tablemate.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import com.aryamohades.tablemate.R;
import com.aryamohades.tablemate.model.Table;
import com.aryamohades.tablemate.model.User;
import com.aryamohades.tablemate.utils.PreferencesManager;

public class BaseFragment extends Fragment {
    protected PreferencesManager prefs;
    protected User user;
    protected String token;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        prefs = new PreferencesManager(context);
        user = prefs.getUser();
        if (user != null) {
            token = user.token();
        }
    }

    protected void setupToolbar(String title, int resId) {
        final ImageView actionBarIcon = (ImageView) getActivity().findViewById(R.id.action_bar_icon);
        final TextView titleView = (TextView) getActivity().findViewById(R.id.title);
        actionBarIcon.setImageResource(resId);
        titleView.setText(title);
    }

    protected String getRestaurantId() {
        return prefs.getRestaurantId();
    }

    protected Table getTable() {
        return prefs.getTable();
    }

    protected String getCurrentRestaurantId() {
        final Table t =  prefs.getTable();
        if (t != null) {
            return t.getRestaurant().getId();
        }
        return null;
    }
}
