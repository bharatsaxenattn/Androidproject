package com.example.roomdatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MyDao {

    @Insert
    public void addEmployee(Employee employee);

    @Query("select * from Employee")
    public List<Employee> getEmployee();

    @Delete
    public  void deleteUser(Employee employee);

    @Update
    public  void updateEmployee(Employee employee);

}
