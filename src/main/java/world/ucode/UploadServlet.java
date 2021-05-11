package world.ucode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {

    private String saveImagePartToDisk(Part part, String fileName) throws IOException, InterruptedException {
        String fileExt = getExtFromFilename(fileName);

        File uploads = new File(getServletContext().getInitParameter("upload.location"));

        if (!uploads.exists()){
            uploads.mkdir();
        }

        File file = File.createTempFile("img_", "." + fileExt, uploads);

        try (InputStream input = part.getInputStream()) {
            Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        int triesCounter = 0;

        while (!file.exists() && triesCounter < 20) {
            Thread.sleep(300); triesCounter++;
        }

        return file.getName();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("file");
        String fileContentType = filePart.getContentType();
        String fileName = getSubmittedFileName(filePart);
        long fileSize = filePart.getSize();
        String filePath = null;

        try {
            filePath = saveImagePartToDisk(filePart, fileName);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!fileContentType.startsWith("image")) {
            response.sendRedirect(request.getContextPath());
        }

        StoredImageDB db = new StoredImageDB();
        StoredImage image = new StoredImage(fileName, fileContentType, fileSize, filePath);

        try {
            ImageIO.read(new File(getServletContext().getInitParameter("upload.location") + '/' + image.getFilePath()));
            db.insert(image);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print("{\"id\": " + image.getId() + "}");
            out.flush();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static String getSubmittedFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }

    private static String getExtFromFilename(String filename) {
        int lastDotPos = filename.lastIndexOf(".");
        return filename.substring(lastDotPos + 1);
    }
}
