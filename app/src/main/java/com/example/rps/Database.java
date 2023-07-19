package com.example.rps;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Rock Paper Scissor";
    private static int DATABASE_VERSION = 24;
    private Context context;
    private static final String SHARED_PREF_USER = "User Details";
    //    review table
    private static final String REVIEW = "reviews";
    private static final String REVIEW_ID = "review_id";
    private static final String REVIEW_TITLE = "review_title";
    private static final String REVIEW_DESCRIPTION = "review_description";
    private static final String REVIEW_LIKE_COUNT = "review_like";
    private static final String REVIEW_DISLIKE_COUNT = "review_dislike";
    private static final String REVIEW_DATE = "review_date";
    private static final String REVIEW_ER = "reviewer";
    private static final String REVIEW_RATING = "rating";
    private static final String create_review_table = "CREATE TABLE "
            + REVIEW + "(" + REVIEW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + REVIEW_ER + " VARCHAR(100), "
            + REVIEW_TITLE + " TEXT, "
            + REVIEW_DATE + " VARCHAR(20), "
            + REVIEW_DESCRIPTION + " TEXT,"
            + REVIEW_RATING + " INTEGER DEFAULT 0, "
            + REVIEW_LIKE_COUNT + " INTEGER DEFAULT 0, "
            + REVIEW_DISLIKE_COUNT + " INTEGER DEFAULT 0);";

    //    user table
    private static final String USER = "users";
    private static final String USER_NAME = "name";
    private static final String USER_USER_NAME = "user_name";
    private static final String USER_LOCATION = "location";
    private static final String USER_REGISTRATION_DATE = "registration";
    private static final String USER_WIN = "win_count";
    private static final String USER_LOSE = "lose_count";
    private static final String USER_COIN = "COIN";
    private static final String USER_DoB = "user_DoB";
    private static final String USER_PHONE = "user_phone";
    private static final String USER_EMAIL = "user_email";
    private static final String USER_PASSWORD = "user_password";
    private static final String USER_PIC = "user_pic";
    private static final String create_user_table = "CREATE TABLE "
            + USER + "(" + USER_USER_NAME + " VARCHAR(100) PRIMARY KEY,"
            + USER_NAME + " VARCHAR(100), "
            + USER_LOCATION + " VARCHAR(200), "
            + USER_EMAIL + " VARCHAR(200), "
            + USER_PASSWORD + " VARCHAR(50) NOT NULL, "
            + USER_REGISTRATION_DATE + " VARCHAR(20) default CURRENT_DATE, "
            + USER_WIN + " INTEGER DEFAULT 0, "
            + USER_PIC + " BLOB DEFAULT null, "
            + USER_LOSE + " INTEGER DEFAULT 0, "
            + USER_PHONE + " VARCHAR(20) DEFAULT null, "
            + USER_COIN + " INTEGER DEFAULT 0, "
            + USER_DoB + " VARCHAR(20));";
    // comment table: postID, userName, comment, date
    private static final String COMMENT_TABLE = "comments";
    private static final String POSTID = "post_id";
    private static final String CMNT_USER = "username";
    private static final String COMMENT = "comment";
    private static final String CMNT_DATE = "date";
    private static final String CMNT_ID = "id";
    private static final String create_comment_table = "CREATE TABLE " +
            COMMENT_TABLE + "("
            + CMNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + POSTID + " INTEGER, "
            + CMNT_USER + " VARCHAR(100), "
            + CMNT_DATE + " VARCHAR(20) default CURRENT_DATE, "
            + COMMENT + " TEXT DEFAULT NULL)";
    //    react table : postID, userName, react (1, -1)
    private static final String REACT_TABLE = "reacts";
    private static final String REACT_POSTID = "post_id";
    private static final String REACT_USERNAME = "username";
    private static final String REACT = "react";
    private static final String create_react_table = "CREATE TABLE " +
            REACT_TABLE + "("
            + REACT_POSTID + " INTEGER, "
            + REACT_USERNAME + " VARCHAR(100), "
            + REACT + " INTEGER DEFAULT 0, "
            + "PRIMARY KEY ( " + REACT_POSTID + ", " + REACT_USERNAME + "))";

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
//        Toast.makeText(context, "Database Connected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        Toast.makeText(context, "onCreate is called", Toast.LENGTH_SHORT).show();
        try {
            db.execSQL(create_review_table);
            db.execSQL(create_user_table);
            db.execSQL(create_comment_table);
            db.execSQL(create_react_table);
            Toast.makeText(context, "Database is created.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Error: " + e, Toast.LENGTH_SHORT).show();
            Log.i("babel", "onCreate: " + e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            db.execSQL("DROP TABLE IF EXISTS " + USER);
            db.execSQL("DROP TABLE IF EXISTS " + REVIEW);
            db.execSQL("DROP TABLE IF EXISTS " + REACT_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + COMMENT_TABLE);
            Toast.makeText(context, "Table Dropped", Toast.LENGTH_SHORT).show();
            onCreate(db);
        } catch (Exception e) {
            Toast.makeText(context, "Failed to delete table.", Toast.LENGTH_SHORT).show();
        }
    }

    public double avgRating() {
        String query = "select avg(rating) from reviews";
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor c = db.rawQuery(query, null);
            c.moveToFirst();
//            Log.i("babel", "avgRating: " + c.getDouble(0));
            return c.getDouble(0);

        } catch (Exception e) {
            Log.i("babel", "avgRating: " + e);
            return -1.0f;
        }
    }

    //    inserting new review
    public void addNewReview(String reviewer, String review_date, String review_title, String review_description, int rating) {
        ContentValues container = new ContentValues();
//        initializing values
        container.put(REVIEW_ER, reviewer);
        container.put(REVIEW_DATE, review_date);
        container.put(REVIEW_TITLE, review_title);
        container.put(REVIEW_DESCRIPTION, review_description);
        container.put(REVIEW_RATING, rating);
//        making query ready
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            long a = db.insert(REVIEW, null, container);
            Toast.makeText(context, "Review Added", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    //    add like and dislike

    public boolean addReact(int postID, String username, int react) {
        String query = "INSERT OR REPLACE INTO " + REACT_TABLE + "( " + REACT_POSTID + ", " + REACT_USERNAME + ", " + REACT + ") " +
                "VALUES (" +
                postID + ", \"" + username + "\", " + react + ");";
        Log.i("babel", "addReact: " + username + query);
        final SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL(query);
            return true;
        } catch (Exception e) {
            Log.i("babel", "addReact: " + e);
            return false;
        }
    }

    public Cursor getReact(int postId, String username) {
        String query = "" +
                "select sum(react) react, (select react from reacts" +
                " where username = \"" + username + "\" and post_id = " + postId + ") user" +
                " from reacts\n" +
                " where post_id = " + postId + ";";
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            Cursor result = db.rawQuery(query, null);
            if (result.moveToFirst())
                return result;
            return null;
        } catch (Exception e) {
            Log.i("babel", "getReact: " + e);
            return null;
        }

    }

    // add & get comment
    public boolean addComment(String username, int post_id, String comment) {
        ContentValues container = new ContentValues();
//        initializing values
        container.put(CMNT_USER, username);
        container.put(POSTID, post_id);
        container.put(COMMENT, comment);
//        making query ready
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            long a = db.insert(COMMENT_TABLE, null, container);
            Log.i("babel", "addComment: " + a);
            return true;
        } catch (Exception e) {
            Log.i("babel", "addComment: " + e);
            return false;
        }
    }

    public ArrayList<Comments> getComments(int post_id) {
        ArrayList<Comments> comments = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + COMMENT_TABLE + " WHERE "
                + POSTID + " = " + post_id + " ORDER BY " + CMNT_ID + " DESC ";
        try {
            Cursor result = db.rawQuery(query, null);
            if (result.moveToFirst()) {
                do {
                    comments.add(new Comments(result.getInt(0), result.getInt(1), result.getString(2), result.getString(3), result.getString(4)));
                } while (result.moveToNext());
            }
        } catch (Exception e) {
            Log.i("babel", "getComments: " + e);
        }
        return comments;
    }

    // getting all reviews
    public ArrayList<Reviews> getAllReviews(int page_no) {
        ArrayList<Reviews> reviews = new ArrayList<>();
//        Toast.makeText(context, "Loading all reviews", Toast.LENGTH_SHORT).show();
        SQLiteDatabase db = this.getWritableDatabase();
        int skip = (page_no - 1) * 10;

//        Toast.makeText(context, reviewCursor.getCount() + " reviews found", Toast.LENGTH_SHORT).show();
        try {
            Cursor reviewCursor = db.rawQuery("SELECT * FROM " + REVIEW + " ORDER BY " + REVIEW_ID + " DESC"

                    +" LIMIT 10 " +
                    "OFFSET " + skip
                    , null);
            if (reviewCursor.moveToFirst()) {
                do {
                    String rating_temp, like_temp, dislike_temp;
                    if (reviewCursor.getString(5) == null) {
                        rating_temp = "0";
                    } else {
                        rating_temp = reviewCursor.getString(5);
                    }

                    if (reviewCursor.getString(6) == null) {
                        like_temp = "0";
                    } else {
                        like_temp = reviewCursor.getString(6);
                    }
                    if (reviewCursor.getString(7) == null) {
                        dislike_temp = "0";
                    } else {
                        dislike_temp = reviewCursor.getString(7);
                    }
//                Log.i("babel", "getAllReviews: "+reviewCursor.getColumnName(0) + reviewCursor.getInt(0));
                    reviews.add(new Reviews(
                            reviewCursor.getInt(0),
                            reviewCursor.getString(1),
                            reviewCursor.getString(2),
                            reviewCursor.getString(3),
                            reviewCursor.getString(4),
                            Integer.parseInt(rating_temp),
                            Integer.parseInt(like_temp),
                            Integer.parseInt(dislike_temp)
                    ));
//                Toast.makeText(context, reviewCursor.getColumnName(7) , Toast.LENGTH_SHORT).show();
                } while (reviewCursor.moveToNext());
            }
        } catch (Exception e) {
            Log.i("babel", "getAllReviews: " + e);
        }
        return reviews;
    }

    public int reviewCount() {
        String query = "SELECT COUNT(*) FROM " + REVIEW;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor result = db.rawQuery(query, null);
            if (result.moveToFirst()) {
                return result.getInt(0);
            }
        } catch (Exception e) {
            Log.i("babel", "reviewCount: " + e);
        }
        return 0;
    }

    public boolean UploadImage(byte[] bmp, String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            byte[] imageData = bmp;
//            String query = "UPDATE " + USER + " SET " + USER_PIC + " = ? WHERE user_name = ?";
            ContentValues values = new ContentValues();
            values.put("user_pic", imageData);
            int rowsAffected = db.update(USER, values, "user_name = ?", new String[]{userName});
            if (rowsAffected != 1) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            Log.i("babel", "UploadImageError: " + e);
            return false;
        }
    }

    public byte[] GetImage(String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String query = "SELECT user_pic FROM " + USER + " WHERE user_name = " + userName;
            Cursor cursor = db.query(USER, new String[]{"user_pic"}, "user_name= '" + userName + "'", null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex("user_pic");
                if (columnIndex != -1) {
                    byte[] blobData = cursor.getBlob(columnIndex);
                    cursor.close();
                    return blobData;
                }

                // Use the blobData byte array as needed
            }

        } catch (Exception e) {
            Log.i("babel", "UploadImageError: " + e);
            return null;
        }
        return null;
    }

    public boolean userNameVerification(String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + USER + " WHERE " + USER_USER_NAME + " = \"" + userName + "\"", null);
//        Log.i("babel", "userNameVerification: " + result.moveToFirst());
        return result.moveToFirst();
    }

    public boolean emailVerification(String email) {
        Log.i("listener", "emailVerification: " + Patterns.EMAIL_ADDRESS.matcher(email).matches());


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + USER + " WHERE " + USER_EMAIL + " = \"" + email + "\"", null);
        return result.moveToFirst();
    }

    public boolean loginValidation(String key, String password, boolean checked) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String getValidate = "SELECT * FROM " + USER
                    + " WHERE (" + USER_USER_NAME + " = \"" + key + "\") AND " + USER_PASSWORD + " = \"" + password + "\";";
            Cursor userCursor = db.rawQuery(getValidate, null);
            if (userCursor.moveToFirst()) {
                SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                for (int i = 0; i < userCursor.getColumnCount(); i++) {
                    if (i == 7)
                        continue;
                    String name = userCursor.getColumnName(i);
                    String value = userCursor.getString(i);
                    editor.putString(name, value);
                }
                Log.i("babel", "loginValidation: " + checked);
                editor.putString("remember", checked + "");
                editor.apply();
            }
            return userCursor.moveToFirst();
        } catch (Exception e) {
            Toast.makeText(context, "exception" + e, Toast.LENGTH_SHORT).show();
            Log.i("babel", "loginValidation: " + e);
            return false;
        }
    }

    public void updateUser(String columnName, String columnValue, String key) {
        String query = "UPDATE " + USER + " SET \""
                + columnName + "\" = \"" + columnValue + "\" WHERE \"" + USER_USER_NAME + "\" = \"" + key + "\" ;";
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            Log.i("babel", "updateUser: " + query);
            db.execSQL(query);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Failed to update.", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean addNewUser(String userName, String name, String email, String country, String dob, String password, String phone) {
        ContentValues container = new ContentValues();
//        initializing values
        container.put(USER_NAME, name);
        container.put(USER_USER_NAME, userName);
        container.put(USER_EMAIL, email);
        container.put(USER_LOCATION, country);
        container.put(USER_DoB, dob);
        container.put(USER_PASSWORD, password);
        container.put(USER_PHONE, phone);
//        making query ready
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            long a = db.insert(USER, null, container);
            SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(USER_NAME, name);
            editor.putString(USER_USER_NAME, userName);
            editor.putString(USER_EMAIL, email);
            editor.putString(USER_LOCATION, country);
            editor.putString(USER_DoB, dob);
            editor.putString(USER_PASSWORD, password);
            editor.putString(USER_PHONE, phone);
            editor.commit();
            Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show();
            return true;

        } catch (Exception e) {
            Toast.makeText(context, "Error: " + e, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
