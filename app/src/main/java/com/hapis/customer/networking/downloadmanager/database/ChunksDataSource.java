package com.hapis.customer.networking.downloadmanager.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hapis.customer.networking.downloadmanager.Utils.helper.SqlString;
import com.hapis.customer.networking.downloadmanager.database.constants.CHUNKS;
import com.hapis.customer.networking.downloadmanager.database.constants.TABLES;
import com.hapis.customer.networking.downloadmanager.database.elements.Chunk;
import com.hapis.customer.networking.downloadmanager.database.elements.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JKHAN
 */
public class ChunksDataSource {

    private SQLiteDatabase database;

    public void openDatabase(DatabaseHelper databaseHelper){
        database = databaseHelper.getWritableDatabase();
    }

    public int insertChunks(Task task){
        long lastChunkInserted = 0;

        if (task.size == 0) { // not resumable

        } else {

            long chunkSize = task.size / task.chunks;

            for (int i = 0; i < task.chunks; i++) {
                Chunk chunk = new Chunk(task.id);

                if (i == 0) {
                    chunk.begin = 0;
                } else {
                    chunk.begin = (chunkSize * i)+1;
                }


                if (i == task.chunks - 1) {
                    chunk.end = task.size;
                } else {
                    chunk.end = chunkSize * (i + 1);
                }

                lastChunkInserted = database.insert(TABLES.CHUNKS, null, chunk.converterToContentValues());
            }
        }

        return (int) lastChunkInserted - task.chunks + 1;
    }

    public List<Chunk> chunksRelatedTask(int taskID){
        List<Chunk> chunks = new ArrayList<Chunk>();
        String query = "SELECT * FROM "+ TABLES.CHUNKS+" WHERE "+ CHUNKS.COLUMN_TASK_ID+" == "+taskID;
        Cursor cr = database.rawQuery(query, null);

        if (cr.moveToFirst()){
            do{
                Chunk chunk = new Chunk(taskID);
                chunk.cursorToChunk(cr);
                chunks.add(chunk);
            } while (cr.moveToNext());
        }

        cr.close();
        return chunks;
    }

    public boolean delete(int chunkID){
        int affectedRow = database.delete(TABLES.CHUNKS, CHUNKS.COLUMN_ID+"="+ SqlString.Int(chunkID), null);

        return affectedRow != 0;

    }

    public void close(){
        database.close();
    }
}
