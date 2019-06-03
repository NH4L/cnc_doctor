package doctor.aysst.www.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static doctor.aysst.www.utils.IdGenertor.FILE;
import static doctor.aysst.www.utils.IdGenertor.genericPath;

@WebServlet(name = "UploadImageServlet", urlPatterns = {"/UploadImageServlet"})
public class UploadImageServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");
        String message = "";
        String fieldName = "";
        String taskId = "";
        PrintWriter out = response.getWriter();
        try{
            DiskFileItemFactory dff = new DiskFileItemFactory();
            ServletFileUpload sfu = new ServletFileUpload(dff);
            List<FileItem> items = sfu.parseRequest(request);
            for(FileItem item:items){
                if(item.isFormField()){
                    //普通表单
                    fieldName = item.getFieldName();
                    taskId = item.getString();
                    System.out.println("name="+fieldName + ", value="+ taskId);
                } else {// 获取上传字段
                    // 更改文件名为唯一的
                    String filename = item.getName();
                    if (filename != null) {
                        filename = IdGenertor.generateGUID() + "." + FilenameUtils.getExtension(filename);
                    }
                    System.out.println(filename);
                    // 生成存储路径
                    //String storeDirectory = getServletContext().getRealPath("/files/images/" + taskId);
                    String storeDirectory = FILE + "/images/" + taskId;
                    File file = new File(storeDirectory);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    String path = genericPath(filename, storeDirectory);
                    System.out.println(path);
                    // 处理文件的上传
                    try {
                        item.write(new File(storeDirectory + path, filename));
                        String filePath = "/files/images/" + taskId + path + "/" + filename;
                        //System.out.println("filePath=" + " http://10.11.12.10:8080" + filePath);
                        message = filePath;
                    } catch (Exception e) {
                        message = "上传图片失败";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "上传图片失败";
        } finally {
            out.write(message);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "UploadImageServlet";
    }

}
