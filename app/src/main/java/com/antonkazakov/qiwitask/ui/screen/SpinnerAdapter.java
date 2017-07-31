package com.antonkazakov.qiwitask.ui.screen;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.antonkazakov.qiwitask.R;
import com.antonkazakov.qiwitask.data.beans.Widget;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anton Kazakov
 * @date 26.07.17.
 */

public class SpinnerAdapter extends ArrayAdapter<Widget.Choice> {

    private Context context;
    private List<Widget.Choice> choices = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private OnPleaseClickListener pleaseClickListener;

    public SpinnerAdapter(@NonNull Context context,
                          @LayoutRes int resource,
                          @NonNull List<Widget.Choice> choices) {
        super(context, resource, choices);
        this.context = context;
        this.choices = choices;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setPleaseClickListener(OnPleaseClickListener pleaseClickListener) {
        this.pleaseClickListener = pleaseClickListener;
    }

    @Nullable
    @Override
    public Widget.Choice getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    private View getUsualView(int position,
                              @Nullable View convertView,
                              @NonNull ViewGroup parent,
                              @NonNull @LayoutRes int layout) {
        convertView = layoutInflater.inflate(layout, parent, false);
        TextView textView = (TextView) convertView.findViewById(R.id.spinner_item_text);
        textView.setText(choices.get(position).getTitle());

        return convertView;
    }

    private View getUsualView1(int position,
                               @Nullable View convertView,
                               @NonNull ViewGroup parent,
                               @NonNull @LayoutRes int layout) {
        convertView = layoutInflater.inflate(layout, parent, false);
        TextView textView = (TextView) convertView.findViewById(R.id.spinner_item_text);
        textView.setText(choices.get(position).getTitle());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pleaseClickListener.onClick(position);
            }
        });
        return convertView;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getUsualView(position, convertView, parent, R.layout.spinner_child_item);
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                @NonNull ViewGroup parent) {

        return getUsualView1(position, convertView, parent, R.layout.spinner_child_item);
    }

    public interface OnPleaseClickListener {

        void onClick(int pos);

    }


}
