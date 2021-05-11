package world.ucode;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/pixelate")
public class PixelizateServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String range = request.getParameter("range");
        String format = request.getParameter("format") != null ? request.getParameter("format") : "jpg";

        StoredImage image = null;

        if(id == null && range == null) {
            response.sendError(412);
            response.sendRedirect(request.getContextPath());
        }

        if(id == null) { response.sendRedirect(request.getContextPath()); }

        StoredImageDB db = new StoredImageDB();
        image = db.selectById(Integer.parseInt(id));

        if(image == null) { response.sendRedirect(request.getContextPath()); }

        BufferedImage imgBuf = ImageIO.read(new File(getServletContext().getInitParameter("upload.location") + '/' + image.getFilePath()));
        BufferedImage pixelatedImage = pixelate(imgBuf, Integer.parseInt(range));
        try {
            File directory = new File(getServletContext().getInitParameter("pixelated.location") );
            if (!directory.exists()){
                directory.mkdir();
            }
            ImageIO.write(pixelatedImage, format, new File(getServletContext().getInitParameter("pixelated.location")  + '/' + image.getFilename() + range + "." + format));

            try {
                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print("{\"path\": \"" + "http://localhost:8080/pxlztr_war/images/pixelated/"  + image.getFilename() + range +  '.' + format + "\"," +
                        "\"fileName\": " + "\"" + image.getOriginalName() + "\"," +
                        "\"format\": " + "\"" + image.getType() + "\"," +
                        "\"size\": " + image.getSize() +
                        "}");

                out.flush();
            } catch (IOException e) {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private BufferedImage pixelate(BufferedImage source, int range) {
        BufferedImage result = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());

        for (int y = 0; y < source.getHeight(); y += range) {
            for (int x = 0; x < source.getWidth(); x += range) {
                int color = source.getRGB(x, y);

                for (int yd = y; yd < y + range && yd < result.getHeight(); yd++) {
                    for (int xd = x; xd < x + range && xd < result.getWidth(); xd++) {
                        result.setRGB(xd, yd, color);
                    }
                }
            }
        }
        return result;
    }
}
