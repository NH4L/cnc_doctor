package doctor.aysst.www.user;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserInfo {
    private String username;
    private String sex;
    private String email;
    private String password;
    private String phone;
    private String signature;		//签名
    private String portraitAddr;	//头像
	private float money;
    public UserInfo(){

    }
    public UserInfo(String username) {
        this.username = username;
    }

    public UserInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserInfo(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public float getMoney() {
        return money;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSignature() {
        return signature;
    }

    public String getPortraitAddr() {
        return portraitAddr;
    }

    public void setPortraitAddr(String portraitAddr) {
        this.portraitAddr = portraitAddr;
    }

    String driver = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/aysst?useUnicode=true&characterEncoding=utf8&useSSL=false";

    public boolean isUserRegister(String username, String email, String password){

        boolean isItRegisted = false;
        try{
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, "root", "1234");
            if(!conn.isClosed()){
                System.out.println("------------------注册------数据库连接成功---------------------");
            }
            Statement stmt1 = conn.createStatement();
            Statement stmt2 = conn.createStatement();
            String check_sql1 = "select * from userinfo where username='" + username + "'";
            String check_sql2 = "select * from userinfo where email='" + email + "'";

            ResultSet rs1 = stmt1.executeQuery(check_sql1);
            ResultSet rs2 = stmt2.executeQuery(check_sql2);
            if (! rs1.next() && !rs2.next()){
                String insert_sql = "insert into userinfo(username, password, email, money) values('" + username + "','" + email+ "','" + password + "','" + 0.00f + "')";
                System.out.println(insert_sql);
                System.out.println("注册信息插入数据库成功!");
                stmt1.execute(insert_sql);               //以插入到数据,注册成功
                isItRegisted = true;
                rs1.close();
                rs2.close();
            }

            stmt1.close();
            stmt2.close();
            conn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        if (isItRegisted)
            System.out.println("注册成功");
        else
            System.out.println("注册失败");
        return isItRegisted;
    }

    public boolean isUserLogin(String account,String password){
        boolean isItLogin = false;

        String name_sql = "select * from userinfo where username='" + account + "' and password='" + password + "'";
        String email_sql = "select * from userinfo where email='" + account + "' and password='" + password + "'";
        //System.out.println(name_sql + "\n" + email_sql);
        try{
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, "root", "1234");
            if(!conn.isClosed()){
                System.out.println("--------------------登录界面---数据库连接成功---------------------");
            }
            Statement stmt1 = conn.createStatement();
            Statement stmt2 = conn.createStatement();
            ResultSet rs1 = stmt1.executeQuery(name_sql);
            ResultSet rs2 = stmt2.executeQuery(email_sql);
            if(rs1.next() || rs2.next()){
                isItLogin = true;
            }
            rs1.close();
            rs2.close();
            stmt1.close();
            stmt2.close();
            conn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return isItLogin;
    }
    public boolean executeSex(String sex) {
        boolean result = false;
        try{
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, "root", "1234");
            if(!conn.isClosed()){
                System.out.println("---------------------更改性别---数据库---------------------");
            }
            Statement stmt1 = conn.createStatement();
            Statement stmt2 = conn.createStatement();

            String checkSQL = "select * from userinfo where sex='" + sex + "' and username='" + username + "'";
            ResultSet rs = stmt1.executeQuery(checkSQL);
            if (!rs.next()){
                String exeSexSQL = "update userinfo set sex='" + sex + "' where username='" + username + "'";
                System.out.println(exeSexSQL);
                stmt2.execute(exeSexSQL);
                result = true;
                System.out.println("性别更改成功!");
            } else {
                result = false;
                System.out.println("性别输入重复");
            }

            stmt1.close();
            stmt2.close();
            conn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public boolean executePhone(String phone) {
        boolean result = false;
        try{
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, "root", "1234");
            if(!conn.isClosed()){
                System.out.println("---------------------更改手机号---数据库---------------------");
            }
            Statement stmt1 = conn.createStatement();
            Statement stmt2 = conn.createStatement();

            String checkSQL = "select * from userinfo where phone='" + phone + "' and username='" + username + "'";
            ResultSet rs = stmt1.executeQuery(checkSQL);
            if (!rs.next()){
                String exePhoneSQL = "update userinfo set phone='" + phone + "' where username='" + username + "'";
                System.out.println(exePhoneSQL);
                stmt2.execute(exePhoneSQL);
                result = true;
                System.out.println("手机号更改成功!");
            } else {
                result = false;
                System.out.println("手机号输入重复");
            }
            stmt1.close();
            stmt2.close();
            conn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    public boolean executeSignature(String signature) {
        boolean result = false;
        try{
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, "root", "1234");
            if(!conn.isClosed()){
                System.out.println("---------------------更改签名---数据库---------------------");
            }
            Statement stmt1 = conn.createStatement();
            Statement stmt2 = conn.createStatement();

            String checkSQL = "select * from userinfo where signature='" + signature + "' and username='" + username + "'";
            ResultSet rs = stmt1.executeQuery(checkSQL);
            if (!rs.next()){
                String exeSignatureSQL = "update userinfo set signature='" + signature + "' where username='" + username + "'";
                System.out.println(exeSignatureSQL);
                stmt2.execute(exeSignatureSQL);
                result = true;
                System.out.println("签名更新成功!");
            } else {
                result = false;
                System.out.println("签名输入重复");
            }
            stmt1.close();
            stmt2.close();
            conn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     *  更改密码
     * @param email 邮箱
     * @param password 需要更改的密码
     * @return 返回bool值，是否更改成功
     */
    public boolean executePassword(String email, String password) {
        boolean result = false;
        try{
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, "root", "1234");
            if(!conn.isClosed()){
                System.out.println("---------------------更改密码---数据库---------------------");
            }
            Statement stmt1 = conn.createStatement();
            Statement stmt2 = conn.createStatement();

            String checkSQL = "select * from userinfo where email='" + email + "' and password='" + password + "'";
            ResultSet rs = stmt1.executeQuery(checkSQL);
            if (!rs.next()){
                String exePwdSQL = "update userinfo set password='" + password + "' where email='" + email + "'";
                System.out.println(exePwdSQL);
                stmt2.execute(exePwdSQL);
                result = true;
                System.out.println(" 密码更新成功!");
            } else {
                result = false;
                System.out.println("和原密码相同");
            }
            stmt1.close();
            stmt2.close();
            conn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     *  更改邮箱
     * @param email 邮箱
     * @param username 用户名
     * @return 返回bool值，是否更改成功
     */
    public boolean executeEmail(String email, String username) {
        boolean result = false;
        try{
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, "root", "1234");
            if(!conn.isClosed()){
                System.out.println("---------------------更改邮箱---数据库---------------------");
            }
            Statement stmt = conn.createStatement();
            String exeEmailSQL = "update userinfo set email='" + email + "' where username='" + username + "'";
            System.out.println(exeEmailSQL);
            stmt.execute(exeEmailSQL);
            result = true;
            System.out.println("邮箱更新成功!");
            stmt.close();
            conn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean addMoneyDB(String money) {
        boolean result = false;
        try{
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, "root", "1234");
            if(!conn.isClosed()){
                System.out.println("---------------------更改金额---数据库---------------------");
            }
            Statement stmt = conn.createStatement();

            String exePwdSQL = "update userinfo set money=money+" + money + " where username='" + username + "'";
            System.out.println(exePwdSQL);
            stmt.execute(exePwdSQL);
            result = true;
            System.out.println("更改赏金成功!");
            stmt.close();
            conn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public String getUserInfo(String account) {
        JSONObject user_json = new JSONObject();
        try{
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, "root", "1234");
            if(!conn.isClosed()){
                System.out.println("----------------个人信息---连接成功-------------");
            }
            Statement stmt1 = conn.createStatement();
            Statement stmt2 = conn.createStatement();

            String sql1 = "select * from userinfo where username='" + account + "'";
            String sql2 = "select * from userinfo where email='" + account + "'";
            System.out.println(sql1 + "\n" + sql2);
            ResultSet rs1 = stmt1.executeQuery(sql1);

            if (rs1.next()){
                username = account;
                email = (String)rs1.getString("email");
                money = (float) rs1.getFloat("money");
                sex = (String) rs1.getString("sex");
                if (sex == null){
                    sex = "无";
                }
                phone = (String)rs1.getString("phone");
                if (phone == null){
                    phone = "无";
                }
                signature = (String)rs1.getString("signature");
                if (signature == null){
                    signature = "个性签名";
                }
                portraitAddr = (String)rs1.getString("portraitAddr");
                if (portraitAddr == null) {
                    portraitAddr = "无";
                }
            }else {
                email = account;
                ResultSet rs2 = stmt1.executeQuery(sql2);
                if (rs2.next()){
                    username = (String)rs2.getString("username");
                    money = (float) rs2.getFloat("money");
                    sex = (String) rs2.getString("sex");
                    if (sex == null){
                        sex = "无";
                    }
                    phone = (String)rs2.getString("phone");
                    if (phone == null){
                        phone = "无";
                    }
                    signature = (String)rs2.getString("signature");
                    if (signature == null){
                        signature = "个性签名";
                    }
                    portraitAddr = (String)rs2.getString("portraitAddr");
                    if (portraitAddr == null) {
                        portraitAddr = "无";
                    }
                }
                rs2.close();
            }
            try {
                user_json.put("name", username);
                user_json.put("email", email);
                user_json.put("money", money);
                user_json.put("sex", sex);
                user_json.put("phone", phone);
                user_json.put("signature", signature);
                user_json.put("portraitAddr", portraitAddr);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            rs1.close();
            stmt1.close();
            stmt2.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(user_json.toString());

        String temp = null;
        try {
            temp = URLDecoder.decode(user_json.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return temp;
    }

    public boolean checkEmail(String email){
        boolean isExistEmail = false;
        String checkEmail_sql = "select * from userinfo where email='" + email + "'";
        try{
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, "root", "1234");
            if(!conn.isClosed()){
                System.out.println("--------------------找回密码界面查找邮箱是否存在---数据库连接成功---------------------");
            }
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(checkEmail_sql);
            if(rs.next()){
                isExistEmail = true;
            }
            rs.close();
            stmt.close();
            conn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return isExistEmail;
    }
    public boolean checkEmail(String username, String email){
        boolean isExistEmail = false;
        String checkEmail_sql = "select * from userinfo where email='" + email + "' and username='" + username +  "'";
        try{
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, "root", "1234");
            if(!conn.isClosed()){
                System.out.println("--------------------修改密码查找邮箱是否存在---数据库连接成功---------------------");
            }
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(checkEmail_sql);
            if(rs.next()){
                isExistEmail = true;
            }
            rs.close();
            stmt.close();
            conn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return isExistEmail;
    }
    public String getDBUsername(String email){
        String name = "";
        String getName_sql = "select * from userinfo where email='" + email + "'";
        try{
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, "root", "1234");
            if(!conn.isClosed()){
                System.out.println("--------------------根据邮箱获取用户名---数据库连接成功---------------------");
            }
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getName_sql);
            if(rs.next()){
                name = (String) rs.getString("username");
            }
            rs.close();
            stmt.close();
            conn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    public boolean executePortrait(String portraitAddr) {
        boolean result = false;
        try{
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, "root", "1234");
            if(!conn.isClosed()){
                System.out.println("---------------------更改头像---数据库---------------------");
            }
            Statement stmt = conn.createStatement();
            String exePortraitSQL = "update userinfo set portraitAddr='" + portraitAddr + "' where username='" + username + "'";
            System.out.println(exePortraitSQL);
            stmt.execute(exePortraitSQL);
            result = true;
            System.out.println("头像更新成功!");

            stmt.close();
            conn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
