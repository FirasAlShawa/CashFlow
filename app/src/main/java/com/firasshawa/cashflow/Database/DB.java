package com.firasshawa.cashflow.Database;

import androidx.annotation.NonNull;

import com.firasshawa.cashflow.Callback;
import com.firasshawa.cashflow.DataModels.Category;
import com.firasshawa.cashflow.DataModels.Main;
import com.firasshawa.cashflow.DataModels.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DB {
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    private DatabaseReference mainRef = database.getReference("Main");
    private DatabaseReference userRef = database.getReference("Users");
    private DatabaseReference logRef = database.getReference("Log");
    private DatabaseReference CategoriesRef = database.getReference("Categories");
    private User CurrentUser = new User();
    private ArrayList<Category> categories = new ArrayList<>();

    public DatabaseReference MainRef() {
        return mainRef;
    }

    public DatabaseReference UserRef() {
        return userRef;
    }

    public DatabaseReference LogRef() {
        return logRef;
    }

    public void initMain() {
        mainRef.orderByKey().limitToFirst(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    if (dataSnapshot.getChildrenCount() == 1) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Main main = snapshot.getValue(Main.class);
                            System.out.println("initMain() => " + main.getTotal());
                        }
                    } else {
                        System.out.println("initMain() => No Children !");
                        Main main = new Main(mainRef.push().getKey(), 0);
                        mainRef.child(main.getKey()).setValue(main);
                        System.out.println("new Childrn has been added !");
                    }
                } else {
                    System.out.println("initMain() => dataSnapshot equals null !");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //User Functions
    public String NewUser(String name) {
        User user = new User(userRef.push().getKey(), name, 0, 0, "");
        userRef.child(user.getKey()).setValue(user);
        return user.getKey();
    }
    public void GetUser(String key, final Callback mCallback) {
        userRef.orderByKey().equalTo(key).limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {
            User user = null;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        user = snapshot.getValue(User.class);
                        System.out.println(user);
                    }
                }

                if (user != null) {
                    mCallback.onCallbackUser(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public User UpdateUser(String field,User user){
        UserRef().child(user.getKey()).setValue(user);
        CurrentUser = user;
        return this.CurrentUser;
    }
    //Categories Functions
    public void AddCategory(String name) {
        Category category = new Category(name, CategoriesRef.push().getKey());
        CategoriesRef.child(category.getKey()).setValue(category);
    }
    public void GetCategories(final Callback myCallback) {
        final ArrayList<Category> categoryArrayList = new ArrayList<>();
        CategoriesRef.addListenerForSingleValueEvent(new ValueEventListener() {

            ArrayList<Category> arrayList = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        arrayList.add(snapshot.getValue(Category.class));
                    }
                }

                myCallback.onCallbackCategories(arrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
}

