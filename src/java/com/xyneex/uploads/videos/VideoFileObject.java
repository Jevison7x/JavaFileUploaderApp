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

import java.io.IOException;
import java.io.InputStream;
import javax.activation.MimetypesFileTypeMap;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author Jevison7x
 * @since Sep 21, 2021 5:54:18 PM
 */
public class VideoFileObject
{
    private static final long serialVersionUID = 1L;
    private byte[] byteArray;

    private String fileName;

    private long fileSize;

    private String fileNameExtension;

    public VideoFileObject()
    {
    }

    public VideoFileObject(FileItem fileItem) throws IOException
    {
        try(InputStream inputStream = fileItem.getInputStream();)
        {
            this.byteArray = new byte[inputStream.available()];
            inputStream.read(this.byteArray);
            this.fileName = fileItem.getName();
            this.fileSize = fileItem.getSize();
            this.fileNameExtension = this.getSuffix(this.fileName);
        }
    }

    public byte[] getByteArray()
    {
        return byteArray;
    }

    public void setByteArray(byte[] byteArray)
    {
        this.byteArray = byteArray;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public long getFileSize()
    {
        return fileSize;
    }

    public void setFileSize(long fileSize)
    {
        this.fileSize = fileSize;
    }

    public String getFileNameExtension()
    {
        return fileNameExtension;
    }

    public void setFileNameExtension(String fileNameExtension)
    {
        this.fileNameExtension = fileNameExtension;
    }

    public String getMimeType()
    {
        String mimetype = "";
        if(!this.fileName.isEmpty())
            if(this.getSuffix(this.fileName).equalsIgnoreCase("m3u"))
                mimetype = "application/x-mpegurl";
            else if(this.getSuffix(this.fileName).equalsIgnoreCase("m3u8"))
                mimetype = "application/x-mpegurl";
            else if(this.getSuffix(this.fileName).equalsIgnoreCase("3gp"))
                mimetype = "video/3gpp";
            else if(this.getSuffix(this.fileName).equalsIgnoreCase("mp4"))
                mimetype = "video/mp4";
            else if(this.getSuffix(this.fileName).equalsIgnoreCase("m4a"))
                mimetype = "video/mp4";
            else if(this.getSuffix(this.fileName).equalsIgnoreCase("m4p"))
                mimetype = "video/mp4";
            else if(this.getSuffix(this.fileName).equalsIgnoreCase("m4b"))
                mimetype = "video/mp4";
            else if(this.getSuffix(this.fileName).equalsIgnoreCase("m4r"))
                mimetype = "video/mp4";
            else if(this.getSuffix(this.fileName).equalsIgnoreCase("m4v"))
                mimetype = "video/mp4";
            else if(this.getSuffix(this.fileName).equalsIgnoreCase("m1v"))
                mimetype = "video/mpeg";
            else if(this.getSuffix(this.fileName).equalsIgnoreCase("ogg"))
                mimetype = "video/ogg";
            else if(this.getSuffix(this.fileName).equalsIgnoreCase("mov"))
                mimetype = "video/quicktime";
            else if(this.getSuffix(this.fileName).equalsIgnoreCase("qt"))
                mimetype = "video/quicktime";
            else if(this.getSuffix(this.fileName).equalsIgnoreCase("webm"))
                mimetype = "video/webm";
            else if(this.getSuffix(this.fileName).equalsIgnoreCase("m4v"))
                mimetype = "video/x-m4v";
            else if(this.getSuffix(this.fileName).equalsIgnoreCase("asf"))
                mimetype = "video/ms-asf";
            else if(this.getSuffix(this.fileName).equalsIgnoreCase("wma"))
                mimetype = "video/ms-asf";
            else if(this.getSuffix(this.fileName).equalsIgnoreCase("wmv"))
                mimetype = "video/x-ms-wmv";
            else if(this.getSuffix(this.fileName).equalsIgnoreCase("avi"))
                mimetype = "video/x-msvideo";
            else
            {
                MimetypesFileTypeMap mtMap = new MimetypesFileTypeMap();
                mimetype = mtMap.getContentType(fileName);
            }
        return mimetype;
    }

    private String getSuffix(String fileName)
    {
        String suffix = "";
        int pos = fileName.lastIndexOf('.');
        if(pos > 0 && pos < fileName.length() - 1)
            suffix = fileName.substring(pos + 1);
        return suffix;
    }
}
