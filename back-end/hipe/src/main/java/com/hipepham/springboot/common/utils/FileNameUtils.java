package com.hipepham.springboot.common.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The type File name utils.
 */
public final class FileNameUtils {

    private FileNameUtils() {
    }

    /**
     * Gets file name in current time.
     *
     * @param fileName the file name
     * @return the file name in current time
     */
    public static String getFileNameInCurrentTime(
            final String fileName) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return fileName + dateFormat.format(date);
    }

    /**
     * Gets file path.
     *
     * @param root      the root
     * @param directory the directory
     * @param fileName  the file name
     * @return the file path
     */
    public static String getFilePath(final String root,
                                     final String directory,
                                     final String fileName) {
        return root + File.pathSeparator + directory + File.pathSeparator
                + fileName;
    }
}
