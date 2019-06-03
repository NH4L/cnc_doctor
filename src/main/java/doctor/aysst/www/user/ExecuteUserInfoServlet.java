package doctor.aysst.www.user;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;


@WebServlet(name = "ExecuteUserInfoServlet", urlPatterns = {"/ExecuteUserInfoServlet"})
public class ExecuteUserInfoServlet extends HttpServlet {

    @Override
    public  void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");
        boolean result = false;
        PrintWriter out = response.getWriter();
        String action = request.getParameter("msg");

        switch (action){
            case "sexAction":
                String sex = request.getParameter("sex");
                String name1 = request.getParameter("name");
                UserInfo user1 = new UserInfo(name1);
                result = user1.executeSex(sex);
                if (result){
                    out.write("success");
                } else {
                    out.write("fail");
                }
                break;

            case "phoneAction":
                String phone = request.getParameter("phone");
                String name2 = request.getParameter("name");
                UserInfo user2 = new UserInfo(name2);
                result = user2.executePhone(phone);
                if (result){
                    out.write("success");
                } else {
                    out.write("fail");
                }
                break;

            case "userInfoAction":
                String account = request.getParameter("account");
                UserInfo user3 = new UserInfo();
                String infoJson = user3.getUserInfo(account);
                System.out.println(infoJson);
                out.write(infoJson);
                break;

            case "signatureAction":
                String signature = request.getParameter("signature");
                String name3 = request.getParameter("name");
                UserInfo user4 = new UserInfo(name3);
                result = user4.executeSignature(signature);
                if (result){
                    out.write("success");
                } else {
                    out.write("fail");
                }
                break;

            case "sendForgetPasswordEmailAction":
                String email = request.getParameter("email");
                UserInfo user5 = new UserInfo();
                result = user5.checkEmail(email);
                System.out.println(result);
                String verificationCode = "";
                if (result){
                    UserInfo user6 = new UserInfo();
                    String userName = user6.getDBUsername(email);
                    verificationCode = (100000 + (int)(Math.random() * 900000)) + "";
                    try {
                        String path = "classpath:templates/getPasswordSendMail.py";
                        String[] strings = new String[] {"python", path, verificationCode, email, userName};
                        Process process = Runtime.getRuntime().exec(strings);

                        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String line = null;
                        while ((line = in.readLine()) != null) {
                            System.out.println(line);
                        }
                        in.close();
                        process.waitFor();
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                        //out.write("fail");
                    }

                    System.out.println(verificationCode);
                    out.write(verificationCode);
                } else {
                    out.write("fail");
                }
                break;

            case "changePwdAction":
                String email2 = request.getParameter("email");
                String password2 = request.getParameter("password");
                UserInfo user6 = new UserInfo();
                result = user6.executePassword(email2, password2);
                if (result){
                    out.write("success");
                } else {
                    out.write("fail");
                }
                break;

            case "changeEmailDBAction":
                String email4 = request.getParameter("email");
                String name9 = request.getParameter("name");
                UserInfo user9 = new UserInfo();
                result = user9.executeEmail(email4, name9);
                if (result){
                    out.write("success");
                } else {
                    out.write("fail");
                }
                break;

            case "exeHeadPortraitReqAction":
                String imgAddr = request.getParameter("imgAddr");
                String name4 = request.getParameter("username");
                UserInfo user7 = new UserInfo(name4);
                result = user7.executePortrait(imgAddr);
                if (result){
                    out.write("success");
                } else {
                    out.write("fail");
                }
                break;

            case "changeEmailAction":
                String name5 = request.getParameter("name");
                String email3 = request.getParameter("email");
                UserInfo user8 = new UserInfo(name5);
                result = user8.checkEmail(name5, email3);
                if (!result) {
                    verificationCode = (100000 + (int)(Math.random() * 900000)) + "";
                    try {
                        String path = "classpath:templates/changeEmail.py";
                        String[] strings = new String[] {"python", path, verificationCode, email3, name5};
                        Process process = Runtime.getRuntime().exec(strings);

                        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String line = null;
                        while ((line = in.readLine()) != null) {
                            System.out.println(line);
                        }
                        in.close();
                        process.waitFor();
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                        //out.write("fail");
                    }

                    System.out.println(verificationCode);
                    out.write(verificationCode);
                } else {
                    out.write("fail");
                }
                break;
            case "addMoney":
                String name6 = request.getParameter("name");
                String money = request.getParameter("money");

                UserInfo user10 = new UserInfo(name6);
                result = user10.addMoneyDB(money);
                if (result) {
                    out.write("success");
                } else {
                    out.write("fail");
                }

                break;
            default:
                out.write("fail");
                break;
        }
        out.flush();
        out.close();
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
        return "ExecuteUserInfoServlet";
    }
}
