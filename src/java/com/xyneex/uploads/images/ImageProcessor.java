/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyneex.uploads.images;

import com.xyneex.util.RandomNumberGenerator;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Jevison7x
 */
public class ImageProcessor
{

    private static final String TEMP_DIRECTORY = "/temp";

    private static final String PERM_DIRECTORY = "/images";

    /**
     * Compresses a given image in bytes
     *
     * @param original the original image in bytes
     * @return the compressed image in bytes
     * @throws IOException
     */
    private static byte[] compressImage(byte[] original) throws IOException
    {
        try( ByteArrayInputStream bais = new ByteArrayInputStream(original))
        {
            BufferedImage bImg = ImageIO.read(bais);
            //Scale the image down to 250 pixels in with
            Image scaledImage = bImg.getScaledInstance(1500, -1, Image.SCALE_FAST);
            //convert image to buffered image
            BufferedImage scaledBimg = toBufferedImage(scaledImage);
            //Convert buffered image to byte array
            try( ByteArrayOutputStream baos = new ByteArrayOutputStream();)
            {
                ImageIO.write(scaledBimg, "jpg", baos);
                byte[] imageInByte = baos.toByteArray();
                return imageInByte;
            }
            finally
            {
                scaledBimg.flush();
            }
        }
    }

    /**
     * Converts a given Image into a BufferedImage
     *
     * @param img The Image to be converted
     * @return The converted BufferedImage
     */
    private static BufferedImage toBufferedImage(Image img)
    {
        if(img instanceof BufferedImage)
            return (BufferedImage)img;

        // Create a buffered image without transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.OPAQUE);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    public static String createTempImage(ImageFileObject imageFileObject, HttpServletRequest request) throws FileNotFoundException, IOException, SQLException, IllegalArgumentException, ClassNotFoundException
    {
        String fileName = generateImageFileName(imageFileObject.getFileNameExtension());
        byte[] imageBytes = imageFileObject.getByteArray();
        byte[] thumbnailBytes = compressImage(imageBytes);
        //Where to save the image part
        ServletContext servletContext = request.getServletContext();
        String absolutePath = servletContext.getRealPath("");
        File tempDir = new File(absolutePath + File.separator + TEMP_DIRECTORY);
        if(!tempDir.exists())
            tempDir.mkdir();
        try( FileOutputStream fos = new FileOutputStream(absolutePath + File.separator + TEMP_DIRECTORY + File.separator + fileName))
        {
            fos.write(thumbnailBytes);
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

    private static String generateImageFileName(String fileNameExtension) throws SQLException, IOException, IllegalArgumentException, ClassNotFoundException
    {
        return RandomNumberGenerator.generateRandomAlphanumericCharacters(7, true) + "." + fileNameExtension;
    }
}
