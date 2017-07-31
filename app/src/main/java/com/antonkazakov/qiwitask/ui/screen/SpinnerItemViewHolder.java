package com.antonkazakov.qiwitask.ui.screen;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Spinner;

import com.antonkazakov.qiwitask.R;

/**
 * @author Anton Kazakov
 * @date 26.07.17.
 */

public class SpinnerItemViewHolder extends RecyclerView.ViewHolder {

    public Spinner spinner;

    public SpinnerItemViewHolder(View itemView) {
        super(itemView);
        spinner = (Spinner) itemView.findViewById(R.id.spinner);
    }

}
