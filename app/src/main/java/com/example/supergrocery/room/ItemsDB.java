package com.example.supergrocery.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(
        entities = {
                OrderItem.class,
        },
        exportSchema = false,
        version = 3
)

public abstract class ItemsDB extends RoomDatabase {
        private static final String DB_NAME = "SuperGrocery_DB";
        public abstract ItemsDao orderItemDao();

}
