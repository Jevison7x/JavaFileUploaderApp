/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyneex.uploads.images;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import javax.activation.MimetypesFileTypeMap;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author Jevison7x
 */
public class ImageFileObject implements Serializable
{
    private static final long serialVersionUID = 1L;
    private byte[] byteArray;

    private String fileName;

    private long fileSize;

    private String fileNameExtension;

    public ImageFileObject()
    {
    }

    public ImageFileObject(FileItem fileItem) throws IOException
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
            if(this.getSuffix(this.fileName).equalsIgnoreCase("png"))
                mimetype = "image/png";
            else if(this.getSuffix(this.fileName).equalsIgnoreCase("jpg"))
                mimetype = "image/jpg";
            else if(this.getSuffix(this.fileName).equalsIgnoreCase("jpeg"))
                mimetype = "image/jpeg";
            else if(this.getSuffix(this.fileName).equalsIgnoreCase("gif"))
                mimetype = "image/gif";
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
