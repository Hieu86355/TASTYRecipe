package com.h86355.tastyrecipe.adapters;

public interface IMainFragmentListener {
    void onRecipeTodayClick(int position);
    void onFeedCollectionClick(int position);
    void onRefreshClick(String refreshTag);
}
