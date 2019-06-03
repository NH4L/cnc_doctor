package doctor.aysst.www.user;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    @Override
    public  void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
        request.setCharacterEncoding("utf-8");

        boolean isItExist = false;

        String userAccount = request.getParameter("account");
        String password = request.getParameter("password");
        UserInfo user = new UserInfo(userAccount, password);
        String result = "";
        System.out.println("useraccount:" + userAccount);
        System.out.println("password:" + password);
        isItExist = user.isUserLogin(userAccount, password);
        PrintWriter out = response.getWriter();

        if(isItExist){
            result = "success";
        }
        else {
            result = "fail";
        }
        out.write(result);
        out.flush();
        out.close();
        System.out.println(result);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public String getServletInfo() {
        return "LoginServlet";
    }
}


