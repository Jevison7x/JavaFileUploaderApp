package com.xyneex.uploads.images;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class CropperImageUploaderServlet extends HttpServlet
{
    private final String CROPPED_DIRECTORY = "cropped-images";

    @Override
    // assuming you are using a Servlet
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Part imagePart = request.getPart("image"); // Retrieves <input type="file" name="image">
        String userName = request.getParameter("username");
        String mimeType = request.getParameter("mimeType"); // Retrieves <input type="text" name="mimeType">
        String extension = request.getParameter("fileExtension"); // Retrieves <input type="text" name="fileExtension">
        String imageType = request.getParameter("imageType");
        String fileName = imageType + "." + extension;

        // Get absolute server path and the location where the file will be stored
        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + File.separator + CROPPED_DIRECTORY + File.separator + userName;

        // Create directory if it doesn't exist
        File fileSaveDir = new File(uploadFilePath);
        if(!fileSaveDir.exists())
            fileSaveDir.mkdirs();

        String filePath = uploadFilePath + File.separator + fileName;
        imagePart.write(filePath); // save the file to server

        // Send back a response
        response.getWriter().write("{ \"status\": \"success\", \"path\": \"" + fileName + "\" }");
    }

}
