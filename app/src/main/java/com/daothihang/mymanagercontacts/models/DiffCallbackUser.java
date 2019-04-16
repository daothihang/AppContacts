package com.daothihang.mymanagercontacts.models;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

public class DiffCallbackUser extends DiffUtil.Callback {
    private final List<User> mOldUserList;
    private final List<User> mNewUserList;


    public DiffCallbackUser(List<User> mOldEmployeeList, List<User> mNewEmployeeList) {
        this.mOldUserList = mOldEmployeeList;
        this.mNewUserList = mNewEmployeeList;
    }

    @Override
    public int getOldListSize() {
        return mOldUserList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewUserList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldUserList.get(oldItemPosition).getId_user() == mNewUserList.get(
                newItemPosition).getId_user();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final User oldEmployee = mOldUserList.get(oldItemPosition);
        final User newEmployee = mNewUserList.get(newItemPosition);

        return oldEmployee.getName().equals(newEmployee.getName());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
