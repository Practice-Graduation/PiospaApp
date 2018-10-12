package com.ptit.baobang.piospaapp.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileUtil {

    private static String FOLDER = "/piospa";
    private static String LOG = "/log";
    private static String LOGFILE = "logcat";
    private static String EXTERNAL = ".txt";

    public static void writeLog(String message) {

        try {
            File logFile = createFileLog();
            FileOutputStream fOut = new FileOutputStream(logFile, true);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);

            // Write the string to the file
            osw.append(message + AppConstants.BREAK_LINE);
            osw.flush();
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static File createFileLog() throws IOException {
        File appDirectory = new File(Environment.getExternalStorageDirectory() + FOLDER);
        File logDirectory = new File(appDirectory + LOG);
        File logFile = new File(logDirectory, LOGFILE + DateTimeUtils.getCurrentDate() + EXTERNAL);

        // create app folder
        if (!appDirectory.exists()) {
            appDirectory.mkdir();
        }

        // create log folder
        if (!logDirectory.exists()) {
            logDirectory.mkdir();
        }

            if (!logFile.exists()) {
            logFile.createNewFile();
        }

        return logFile;
    }

}
