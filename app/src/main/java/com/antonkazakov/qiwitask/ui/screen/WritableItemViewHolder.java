package com.antonkazakov.qiwitask.ui.screen;

import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.antonkazakov.qiwitask.R;

/**
 * @author Anton Kazakov
 * @date 26.07.17.
 */

public class WritableItemViewHolder extends RecyclerView.ViewHolder {

    public EditText editText;
    public TextInputLayout textInputLayout;

    public WritableItemViewHolder(View itemView) {
        super(itemView);
        editText = (EditText) itemView.findViewById(R.id.edit_text);
        textInputLayout = (TextInputLayout) itemView.findViewById(R.id.text_input);
    }

}
