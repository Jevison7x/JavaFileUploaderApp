/*
 * Copyright (c) 2018, Xyneex Technologies. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * You are not meant to edit or modify this source code unless you are
 * authorized to do so.
 *
 * Please contact Xyneex Technologies, #1 Orok Orok Street, Calabar, Nigeria.
 * or visit www.xyneex.com if you need additional information or have any
 * questions.
 */
package com.xyneex.uploads.videos;

import com.xyneex.util.RandomNumberGenerator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Jevison7x
 * @since Sep 21, 2021 5:55:08 PM
 */
public class VideoProcessor
{

    private static final String TEMP_DIRECTORY = "/temp";

    private static final String PERM_DIRECTORY = "/videos";

    public static String createTempVideo(VideoFileObject videoFileObject, HttpServletRequest request) throws FileNotFoundException, IOException, SQLException, IllegalArgumentException, ClassNotFoundException
    {
        String fileName = generateVideoFileName(videoFileObject.getFileNameExtension());
        byte[] videoBytes = videoFileObject.getByteArray();
        //Where to save the video part
        ServletContext servletContext = request.getServletContext();
        String absolutePath = servletContext.getRealPath("");
        File tempDir = new File(absolutePath + File.separator + TEMP_DIRECTORY);
        if(!tempDir.exists())
            tempDir.mkdir();
        try(FileOutputStream fos = new FileOutputStream(absolutePath + File.separator + TEMP_DIRECTORY + File.separator + fileName))
        {
            fos.write(videoBytes);
        }
        return fileName;
    }

    public static void moveFileToPermanentDirectory(String fileName, HttpServletRequest request) throws IOException
    {
        ServletContext servletContext = request.getServletContext();
        String absolutePath = servletContext.getRealPath("");
        File permanentDirectoryFile = new File(absolutePath + File.separator + PERM_DIRECTORY);
        if(!permanentDirectoryFile.exists())
            permanentDirectoryFile.mkdir();
        String fromPath = absolutePath + File.separator + TEMP_DIRECTORY + File.separator + fileName;
        String toPath = absolutePath + File.separator + PERM_DIRECTORY + File.separator + fileName;
        Files.move(Paths.get(fromPath), Paths.get(toPath), StandardCopyOption.REPLACE_EXISTING);
    }

    private static String generateVideoFileName(String fileNameExtension) throws SQLException, IOException, IllegalArgumentException, ClassNotFoundException
    {
        return RandomNumberGenerator.generateRandomAlphanumericCharacters(7, true) + "." + fileNameExtension;
    }
}
