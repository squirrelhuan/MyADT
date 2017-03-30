package com.huan.myadt.utils;

import android.content.Context;

import com.huan.myadt.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Administrator on 2017/3/30 0030.
 */

public class DBUtils {

    /**
     * 此处是拷贝操作的关键代码，需要我们传入raw资源ID，数据库的拷贝路径，数据库文件名，是否覆盖已经存在的db文件，@return 拷贝是否成功
     * @param context
     * @param copyRawDbResId
     * @param apkDbPath
     * @param dbName
     * @param refresh
     * @return
     * @throws IOException
     */
    public static boolean copyRawDBToApkDb(Context context, int copyRawDbResId, String apkDbPath, String dbName, boolean refresh)
            throws IOException
    {
        boolean b = false;

        File f = new File(apkDbPath);
        if (!f.exists())
        {
            f.mkdirs();
        }

        File dbFile = new File(apkDbPath + dbName);
        //b = isDbFileExists(dbFile,refresh);
        if (!b)
        {
            InputStream is = context.getResources().openRawResource(copyRawDbResId);

            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is));
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null)
            {
                int size;
                byte[] buffer = new byte[1024 * 2];

                OutputStream fos = new FileOutputStream(apkDbPath + entry.getName());
                BufferedOutputStream bos = new BufferedOutputStream(fos, buffer.length);

                while ((size = zis.read(buffer, 0, buffer.length)) != -1)
                {
                    bos.write(buffer, 0, size);
                }
                bos.flush();
                bos.close();
            }
            zis.close();
            is.close();
        }
        return !b;
    }


   /* private void selDBData()
    {
        StudentDao student = DBController.getDaoSession(DBController.DATABASE_SCHOOL_NAME).getStudentDao();
        List<student> students = student.queryBuilder().list();
        builder = new StringBuilder();
        for (int i = 0; i < students.size(); i++)
        {
            builder.append(students.get(i).getName() + "---");
        }
        Toast.makeText(MainActivity.this, builder.toString(), Toast.LENGTH_SHORT).show();
    }*/

    private String APK_DB_PATH;
    private String ECMC_DB_NAME;
    private void copyRawDB(Context context)
    {
        try
        {
            // 拷贝res/raw/xxxxdb.zip 到
            // /data/data/com.xinhang.mobileclient/databases/ 目录下面
            boolean isSuccess = DBUtils.copyRawDBToApkDb(context, R.raw.myadt, APK_DB_PATH, ECMC_DB_NAME, false);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
