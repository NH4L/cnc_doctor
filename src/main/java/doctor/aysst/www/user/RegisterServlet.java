package doctor.aysst.www.user;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {

    public  void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
        request.setCharacterEncoding("utf-8");

        boolean isItExist = false;
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password=request.getParameter("password");
        UserInfo user = new UserInfo(username, email, password);
        String result = "";

        isItExist = user.isUserRegister(username, email, password);
        PrintWriter out = response.getWriter();

        if(isItExist){
            result = "success";
        }
        else{
            result = "fail";
        }
        out.write(result);
        out.flush();
        out.close();
        System.out.println(result);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public String getServletInfo() {
        return "RegisterServlet";
    }
}

