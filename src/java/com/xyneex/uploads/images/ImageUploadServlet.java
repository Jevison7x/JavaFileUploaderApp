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
package com.xyneex.uploads.images;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Jevison7x
 */
public class ImageUploadServlet extends HttpServlet
{
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession(true);
        if(session != null)
            if(!ServletFileUpload.isMultipartContent(request))
                throw new IllegalArgumentException("Request is not multipart, please add 'multipart/form-data' enctype for your form.");
            else
            {
                ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
                PrintWriter out = response.getWriter();
                response.setContentType("application/json");

                JSONArray jsonArray = new JSONArray();
                try
                {
                    List<FileItem> items = uploadHandler.parseRequest(request);
                    for(FileItem item : items)
                        if(!item.isFormField())
                        {
                            ImageFileObject imageFileObject = new ImageFileObject(item);
                            String imageFileName = ImageProcessor.createTempImage(imageFileObject, request);
                            JSONObject jsonObj = new JSONObject();
                            jsonObj.put("name", item.getName());
                            jsonObj.put("size", item.getSize());
                            jsonObj.put("newfilename", imageFileName);
                            jsonArray.put(jsonObj);
                        }
                }
                catch(Exception xcp)
                {
                    xcp.printStackTrace(out);
                }
                finally
                {
                    out.write(jsonArray.toString());
                    out.flush();
                    out.close();
                }
            }
        else
        {
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            JSONArray json = new JSONArray();
            JSONObject jsono = new JSONObject();
            try
            {
                jsono.put("ErrorMgs", "There is no session");
                json.put(jsono);
                //System.out.println(json.toString());
            }
            catch(JSONException xcp)
            {
                xcp.printStackTrace(out);
            }
            finally
            {
                out.write(json.toString());
                out.flush();
                out.close();
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>
}
