# -*- coding:utf-8 -*-
import smtplib
from email.mime.text import MIMEText
import datetime
import sys
import random

pause = 100
mailto_list = ['nh4l@qq.com', ]
mail_host = 'smtp.qq.com'
mail_user = 'nh4l'
mail_pass = 'mbdzkhrrlebpeadd'
mail_postfix = 'qq.com'


def send_email(to_list, sub, content):
    me = mail_user + '<' + mail_user + '@' + mail_postfix + '>'
    msg = MIMEText(content, 'html', 'utf-8')
    msg['Subject'] = sub
    msg['From'] = me
    msg['To'] = ';'.join(to_list)  # 将收件人列表以‘；’分隔
    try:
        server = smtplib.SMTP_SSL(mail_host, 465)
        server.connect(mail_host)  # 连接服务器
        server.login(mail_user, mail_pass)  # 登录操作
        print('登录成功')
        server.sendmail(me, to_list, msg.as_string())
        print('发送成功')
        server.close()

    except smtplib.SMTPException as e:
        print(e)


def main(verificationCode, user_email, username) :
   try:
        nowTime = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
        content = '<pre style="background-color: rgb(248, 248, 255);"><pre style="font-family: arial;">亲爱的 ' + username + ' ，你好！</pre><pre style="font-family: arial;">您的账号 ' + username + '在申请修改邮箱。</pre><pre style="font-family: arial;">请输入以下六位数验证码：<b><font color="#0000ff">' + str(verificationCode) + '</font></b></pre><pre style="font-family: arial;">此验证码仅做单次使用，请尽快输入验证码修改邮箱！</pre><pre style="font-family: arial;"><font face="Lucida Sans Typewriter">                          ----------------数控小医生App by Aysst Team</font></pre></pre>'
        print('邮件内容:', content)
        mailto_list.append(user_email)
        send_email(mailto_list, '智集-修改邮箱', content)
        print('send over')

   except Exception as e:
       pass

if __name__ == '__main__':
    verificationCode = sys.argv[1]
    user_mail = sys.argv[2]
    username = sys.argv[3]
    # verificationCode = 123456
    # user_mail = 'nh4ly@outlook.com'
    # username = 'lcy'
    main(verificationCode, user_mail, username)

