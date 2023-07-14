package com.depauw.repairshop.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.depauw.repairshop.RepairWithVehicle;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "repair_shop.db";
    public static final int DB_VERSION  = 1;
    public static DBHelper helper;

    public static final String TABLE_VEHICLE = "Vehicle";
    public static final String COL_VEHICLE_ID = "vid";
    public static final String COL_VEHICLE_YEAR = "year";
    public static final String COL_VEHICLE_MAKE_AND_MODEL= "make_and_model";
    public static final String COL_VEHICLE_PURCHASE_PRICE = "purchase_price";
    public static final String COL_VEHICLE_IS_NEW = "is_new";

    public static final String TABLE_REPAIR = "Repair";
    public static final String COL_REPAIR_ID = "rid";
    public static final String COL_REPAIR_VEHICLE_ID = "vehicle_id";
    public static final String COL_REPAIR_DATE = "date";
    public static final String COL_REPAIR_COST = "cost";
    public static final String COL_REPAIR_DESCRIPTION = "description";

    private DBHelper(@Nullable Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static DBHelper getInstance(Context context){
        if(helper==null){
            helper = new DBHelper(context);
        }
        return helper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_VEHICLE + " (" +
                COL_VEHICLE_ID + " INTEGER," +
                COL_VEHICLE_YEAR + " INTEGER NOT NULL," +
                COL_VEHICLE_MAKE_AND_MODEL + " TEXT NOT NULL," +
                COL_VEHICLE_PURCHASE_PRICE + " REAL NOT NULL," +
                COL_VEHICLE_IS_NEW + " INTEGER NOT NULL," +
                "PRIMARY KEY(" + COL_VEHICLE_ID + " AUTOINCREMENT)" +
                ")";
        db.execSQL(sql);
         sql = "CREATE TABLE " + TABLE_REPAIR + " (" +
                COL_REPAIR_ID	+ " INTEGER," +
                COL_REPAIR_VEHICLE_ID + " INTEGER NOT NULL," +
                COL_REPAIR_DATE	+ " TEXT NOT NULL," +
                COL_REPAIR_COST	+ " REAL NOT NULL," +
                COL_REPAIR_DESCRIPTION + " TEXT NOT NULL," +
                "FOREIGN KEY(" + COL_REPAIR_VEHICLE_ID + ") REFERENCES " + TABLE_VEHICLE + "(" + COL_VEHICLE_ID + ")," +
                "PRIMARY KEY(" + COL_REPAIR_ID + " AUTOINCREMENT)" +
                ")";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { }

    //Vehicle
    public long insertVehicle(Vehicle vehicle){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_VEHICLE_YEAR, vehicle.getYear());
        cv.put(COL_VEHICLE_MAKE_AND_MODEL, vehicle.getMakeAndModel());
        cv.put(COL_VEHICLE_PURCHASE_PRICE, vehicle.getPurchasePrice());
        cv.put(COL_VEHICLE_IS_NEW, vehicle.isNew());

        long result = db.insert(TABLE_VEHICLE, null, cv);
        db.close();
        return result;
    }

    public ArrayList<Vehicle> getAllVehicles(){

        ArrayList<Vehicle> vehicles = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_VEHICLE;

        Cursor myCursor = db.rawQuery(sql, null);

        int idx_id = myCursor.getColumnIndex(COL_VEHICLE_ID);
        int idx_year = myCursor.getColumnIndex(COL_VEHICLE_YEAR);
        int idx_makeAndModel = myCursor.getColumnIndex(COL_VEHICLE_MAKE_AND_MODEL);
        int idx_purchasePrice = myCursor.getColumnIndex(COL_VEHICLE_PURCHASE_PRICE);
        int idx_isNew = myCursor.getColumnIndex(COL_VEHICLE_IS_NEW);

        if(myCursor.moveToFirst()){
            do {
                int id = myCursor.getInt(idx_id);
                int year = myCursor.getInt(idx_year);
                String makeAndMode = myCursor.getString(idx_makeAndModel);
                double purchasePrice = myCursor.getDouble(idx_purchasePrice);
                int isNew = myCursor.getInt(idx_isNew);

                Vehicle v = new Vehicle(id, year, makeAndMode, purchasePrice, isNew);
                vehicles.add(v);
            }   while(myCursor.moveToNext());
        }
        db.close();
        return vehicles;
    }

    //Repair
    public long insertRepair(Repair repair){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_REPAIR_VEHICLE_ID, repair.getVehicleId());
        cv.put(COL_REPAIR_DATE, repair.getDate());
        cv.put(COL_REPAIR_COST, repair.getCost());
        cv.put(COL_REPAIR_DESCRIPTION, repair.getDescription());

        long result = db.insert(TABLE_REPAIR, null, cv);
        db.close();
        return result;
    }

    //Delete Repair
    public int deleteRepair (int id){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = String.format("%s = %s", COL_REPAIR_ID, id);
        int result = db.delete(TABLE_REPAIR, whereClause, null);

        db.close();
        return result;
    }

    //Vehicle and Repair
    public List<RepairWithVehicle> getRepairsWithVehicle(String search){
        String sql = String.format("SELECT * FROM %s INNER JOIN %s ON %s = %s WHERE %s LIKE '%%%s%%'",TABLE_VEHICLE, TABLE_REPAIR, COL_REPAIR_VEHICLE_ID, COL_VEHICLE_ID, COL_REPAIR_DESCRIPTION, search);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        int vid_idx = cursor.getColumnIndex(COL_VEHICLE_ID);
        int vehicle_make_and_model_idx = cursor.getColumnIndex(COL_VEHICLE_MAKE_AND_MODEL);
        int vehicle_purchase_price_idx = cursor.getColumnIndex(COL_VEHICLE_PURCHASE_PRICE);
        int vehicle_year_idx = cursor.getColumnIndex(COL_VEHICLE_YEAR);
        int vehicle_is_new_idx = cursor.getColumnIndex(COL_VEHICLE_IS_NEW);

        int rid_idx = cursor.getColumnIndex(COL_REPAIR_ID);
        int rix_vid_idx = cursor.getColumnIndex(COL_REPAIR_VEHICLE_ID);
        int repair_date_idx = cursor.getColumnIndex(COL_REPAIR_DATE);
        int repair_cost_idx = cursor.getColumnIndex(COL_REPAIR_COST);
        int repair_description_idx = cursor.getColumnIndex(COL_REPAIR_DESCRIPTION);

        List<RepairWithVehicle> repairVehicles = new ArrayList<>();

        if(cursor.moveToFirst()){
            do {
                int vid = cursor.getInt(vid_idx);
                int year = cursor.getInt(vehicle_year_idx);
                String makeAndModel = cursor.getString(vehicle_make_and_model_idx);
                double purchasePrice = cursor.getDouble(vehicle_purchase_price_idx);
                int isNew = cursor.getInt(vehicle_is_new_idx);

                int rid = cursor.getInt(rid_idx);
                int rVid = cursor.getInt(rix_vid_idx);
                String repairDate = cursor.getString(repair_date_idx);
                double repairCost = cursor.getDouble(repair_cost_idx);
                String repairDescription = cursor.getString(repair_description_idx);

                Vehicle vehicle = new Vehicle(vid, year, makeAndModel, purchasePrice, isNew);
                Repair repair = new Repair(rid, rVid, repairDate, repairCost, repairDescription);

                RepairWithVehicle repairVehicle = new RepairWithVehicle(vehicle,repair);

                repairVehicles.add(repairVehicle);
            }   while(cursor.moveToNext());
        }
        db.close();
        return repairVehicles;
    }
}
