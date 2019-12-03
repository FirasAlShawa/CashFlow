package com.firasshawa.cashflow;

import com.firasshawa.cashflow.DataModels.Category;
import com.firasshawa.cashflow.DataModels.Log;
import com.firasshawa.cashflow.DataModels.User;

import java.util.ArrayList;

public interface Callback {
    void onCallbackCategories(ArrayList<Category> categories);

    void onCallbackLogs(ArrayList<Log> logs);

    void onCallbackUser(User user);
}
