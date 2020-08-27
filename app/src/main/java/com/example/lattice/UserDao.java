package com.example.lattice;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Flowable;

@Dao
interface UserDao {

    @Query("Select * from user where email like :email")
    User checkEmail(String email);

    @Query("select * from user")
    Flowable<List<User>> getUsersList();

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);

    @Update
    void update(User user);

}