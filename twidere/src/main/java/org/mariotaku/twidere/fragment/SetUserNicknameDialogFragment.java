/*
 * 				Twidere - Twitter client for Android
 * 
 *  Copyright (C) 2012-2014 Mariotaku Lee <mariotaku.lee@gmail.com>
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mariotaku.twidere.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.FrameLayout;

import org.mariotaku.twidere.R;
import org.mariotaku.twidere.model.UserKey;
import org.mariotaku.twidere.util.ParseUtils;

public class SetUserNicknameDialogFragment extends BaseDialogFragment implements OnClickListener {

    private static final String FRAGMENT_TAG_SET_USER_NICKNAME = "set_user_nickname";
    private EditText mEditText;

    @Override
    public void onClick(final DialogInterface dialog, final int which) {
        final Bundle args = getArguments();
        assert args != null;
        final String text = ParseUtils.parseString(mEditText.getText());
        final UserKey userId = args.getParcelable(EXTRA_USER_KEY);
        assert userId != null;
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE: {
                if (TextUtils.isEmpty(text)) {
                    mUserColorNameManager.clearUserNickname(userId);
                } else {
                    mUserColorNameManager.setUserNickname(userId, text);
                }
                break;
            }
            case DialogInterface.BUTTON_NEUTRAL: {
                mUserColorNameManager.clearUserNickname(userId);
                break;
            }
        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final Bundle args = getArguments();
        final String nick = args.getString(EXTRA_NAME);
        final Context context = getActivity();
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.set_nickname);
        builder.setPositiveButton(android.R.string.ok, this);
        if (!TextUtils.isEmpty(nick)) {
            builder.setNeutralButton(R.string.clear, this);
        }
        builder.setNegativeButton(android.R.string.cancel, null);
        final FrameLayout view = new FrameLayout(context);
        mEditText = new AppCompatEditText(context);
        final FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = lp.topMargin = lp.bottomMargin = lp.rightMargin = getResources().getDimensionPixelSize(
                R.dimen.element_spacing_normal);
        view.addView(mEditText, lp);
        builder.setView(view);
        mEditText.setText(nick);
        return builder.create();
    }

    public static SetUserNicknameDialogFragment show(final FragmentManager fm, final UserKey userKey, final String nickname) {
        final SetUserNicknameDialogFragment f = new SetUserNicknameDialogFragment();
        final Bundle args = new Bundle();
        args.putParcelable(EXTRA_USER_KEY, userKey);
        args.putString(EXTRA_NAME, nickname);
        f.setArguments(args);
        f.show(fm, FRAGMENT_TAG_SET_USER_NICKNAME);
        return f;
    }

}