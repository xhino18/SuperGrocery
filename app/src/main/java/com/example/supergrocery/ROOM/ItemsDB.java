package com.example.supergrocery.ROOM;

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
        private static ItemsDB instance;
        public abstract ItemsDao orderItemDao();

        public static synchronized ItemsDB getInstance(Context context) {
                if (instance == null) {
                        instance = Room.databaseBuilder(context.getApplicationContext(), ItemsDB.class, DB_NAME)
                                .fallbackToDestructiveMigration()
                                .allowMainThreadQueries()
                                .build();
                }

                return instance;
        }

}
