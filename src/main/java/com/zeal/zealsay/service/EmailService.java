package com.zeal.zealsay.service;

import com.zeal.zealsay.common.constant.SystemConstants;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jcajce.provider.symmetric.AES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 邮箱发送服务.
 *
 * @author zhanglei
 * @date 2019-09-25  17:59
 */
@Slf4j
@Service
public class EmailService {

  @Value("${spring.mail.username}")
  private String whoAmI;

  @Autowired
  private JavaMailSender mailSender;
  @Autowired
  SystemConstants systemConstants;

  private static final int TIMELIMIT = 1000 * 60 * 60 * 24; //激活邮件过期时间24小时

  /**
   * 发送简单邮件
   *
   * @param to      发送给谁
   * @param subject 邮件主题
   * @param content 邮件内容
   */
  public void sendSimpleMail(String to, String subject, String content) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject(subject);
    message.setText(content);
    message.setFrom(whoAmI);
    mailSender.send(message);
  }

  /**
   * 发送html邮件
   *
   * @param to      发送给谁
   * @param subject 邮件主题
   * @param content 邮件内容
   */
  public void sendHtmlMail(String to, String subject, String content) {
    MimeMessage message = mailSender.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setTo(to);
      helper.setFrom(whoAmI,systemConstants.getName());
      helper.setSubject(subject);
      helper.setText(content, true);
      mailSender.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

  }

  /**
   * 发送附件邮件
   *
   * @param to       发送给谁
   * @param subject  邮件主题
   * @param content  邮件内容
   * @param filePath 文件路径
   */
  public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = null;
    try {
      helper = new MimeMessageHelper(message, true);
      helper.setTo(to);
      helper.setFrom(whoAmI);
      helper.setSubject(subject);
      helper.setText(content, true);

      FileSystemResource file = new FileSystemResource(new File(filePath));
      String fileName = file.getFilename();
      //此处可以添加多个附件 zjy0910
      helper.addAttachment(fileName, file);

      mailSender.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

  /**
   * 发送图片邮件
   *
   * @param to      发送给谁
   * @param subject 邮件主题
   * @param content 邮件内容
   * @param rscPath 图片路径
   * @param rscId
   */
  public void sendInlinResourceMail(String to, String subject, String content,
                                    String rscPath, String rscId) {
    MimeMessage message = mailSender.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setTo(to);
      helper.setFrom(whoAmI);
      helper.setSubject(subject);
      helper.setText(content, true);

      //可以添加多个图片
      FileSystemResource res = new FileSystemResource(new File(rscPath));
      helper.addInline(rscId, res);

      mailSender.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }


  /**
   * 构造注册邮件内容.
   *
   * @param username 用户名称
   * @param email    邮件
   * @param url      链接
   * @return 邮件内容
   */
  private String buildRegisterEmail(String username, String email, String url) {
    return "<html>\n" +
        "    \n" +
        "<head>\n" +
        "<base target=\"_blank\" />\n" +
        "<style type=\"text/css\">\n" +
        "::-webkit-scrollbar{ display: none; }\n" +
        "</style>\n" +
        "<style id=\"cloudAttachStyle\" type=\"text/css\">\n" +
        "#divNeteaseBigAttach, #divNeteaseBigAttach_bak{display:none;}\n" +
        "</style>\n" +
        "<style id=\"blockquoteStyle\" type=\"text/css\">blockquote{display:none;}</style>\n" +
        "</head>\n" +
        "<body tabindex=\"0\" role=\"listitem\">\n" +
        "\n" +
        "\n" +
        "\n" +
        "<div id=\"content\">\n" +
        "\n" +
        "<table style=\"-webkit-font-smoothing: antialiased;font-family:'microsoft yahei', 'Helvetica Neue', sans-serif, SimHei;padding:30px;margin: 25px auto; background:#fff; border-radius:5px; box-sizing: border-box;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\" align=\"center\">\n" +
        "    <tbody>\n" +
        "    <tr style=\"text-align: center\">\n" +
        "        <td style=\"color:#000\">\n" +
        "            <img width=\"180px\" height=\"140px\" src=\"https://pan.zealsay.com/logo.png\" alt=\"Coding.net\">\n" +
        "                    </td>\n" +
        "    </tr>\n" +
        "    <tr>\n" +
        "        <td style=\"height: 33px;padding: 10px 20px 10px 20px;font-size: 24px;color: #212e40;\">\n" +
        "            Hi " + username + "，\n" +
        "        </td>\n" +
        "    </tr>\n" +
        "    <tr>\n" +
        "        <td style=\"font-size: 20px;  padding: 10px 20px;color: #212e40;font-weight: normal;\">\n" +
        "           您已注册成功。\n" +
        "        </td>\n" +
        "    </tr>\n" +
        "    <tr>\n" +
        "        <td style=\"font-size: 14px;color: #212e40;  padding: 10px 20px;\">\n" +
        "            以下是您的登录信息：\n" +
        "        </td>\n" +
        "    </tr>\n" +
        "    <tr>\n" +
        "        <td valign=\"left\" style=\"font-size: 36px;color: #323A45;\">\n" +
        "            <div style=\"background: #F2F2F7;border-radius: 2px;margin: 10px 20px;\">\n" +
        "                <div style=\"font-size: 14px;line-height: 14px; padding: 20px 20px 10px 20px;\">\n" +
        "                    用户名称：" + username + "\n" +
        "                </div>\n" +
        "                <div style=\"font-size: 14px;line-height: 14px; padding: 10px 20px 20px 20px;\">\n" +
        "                    登录邮箱：<A data-auto-link=1 href=\"mailto:" + email + "\">" + email + "</A>            </div>\n" +
        "            </div>\n" +
        "        </td>\n" +
        "    </tr>\n" +
        "    <tr>\n" +
        "        <td valign=\"middle\" style=\"font-size: 12px;height: 17px;padding: 15px 20px;\">\n" +
        "           欢迎您的加入！这都能找到我们，看来我们很有缘分哟(≥﹏ ≤) 赶紧点击下面链接去激活您的账号，跟我们一起愉快的玩耍吧！\n" +
        "        </td>\n" +
        "    </tr>\n" +
        "    <tr>\n" +
        "        <td style=\"height: 50px;color: white;\" valign=\"middle\">\n" +
        "            <div style=\"padding:10px 20px;border-radius:5px;background: rgb(64, 69, 77);margin-left:20px;margin-right:20px\">\n" +
        "                <a style=\"word-break:break-all;line-height:23px;color:white;font-size:15px;\" href=\"" + url + "\">\n" +
        "                    " + url + "\n" +
        "                </a>\n" +
        "            </div>\n" +
        "        </td>\n" +
        "    </tr>\n" +
        "    <tr>\n" +
        "        <td style=\"padding: 20px 20px 20px 20px;font-size: 12px;\">\n" +
        "            <p style=\"margin: 8px 0;\">如果点击以上链接无效，请尝试将链接复制到浏览器地址栏访问。</p>\n" +
        "            <p style=\"margin: 8px 0;\">假如您没有进行注册操作，请忽略此邮件，不要点击上面链接。</p>\n" +
        "            <p style=\"margin: 8px 0;\">注册链接只在24小时内有效，若您有任何疑问，请随时联系我们：<A data-auto-link=1 href=\"mailto:admin@zealsay.com\">admin@zealsay.com</A></p>\n" +
        "        </td>\n" +
        "    </tr>\n" +
        "    <tr>\n" +
        "        <td style=\"font-size: 14px; color: #323A45; padding: 20px 0;text-align: right;\">\n" +
        "            zeal\n" +
        "        </td>\n" +
        "    </tr>\n" +
        "    <tr>\n" +
        "        <td style=\"padding-top: 30px;\">\n" +
        "            <hr style=\"border:none;border-top:1px solid #ccc;\">\n" +
        "        </td>\n" +
        "    </tr>\n" +
        "    <tr>\n" +
        "        <td style=\"color:#76808E;font-size: 12px;\">\n" +
        "            <div style=\"text-align: center; line-height: 24px;\">\n" +
        "                Copyright © 2019 zealsay\n" +
        "            </div>\n" +
        "                        \n" +
        "                </div>\n" +
        "                    </td>\n" +
        "    </tr>\n" +
        "    </tbody>\n" +
        "</table>\n" +
        " \n" +
        "\n" +
        "</div>\n" +
        "\n" +
        "<script>var _c=document.getElementById('content');_c.innerHTML=(_c.innerHTML||'').replace(/(href|formAction|onclick|javascript)/ig, '__$1').replace(/<\\/?marquee>/ig,'');</script>\n" +
        "<style type=\"text/css\">\n" +
        "body{font-size:14px;font-family:arial,verdana,sans-serif;line-height:1.666;padding:0;margin:0;overflow:auto;white-space:normal;word-wrap:break-word;min-height:100px}\n" +
        "td, input, button, select, body{font-family:Helvetica, 'Microsoft Yahei', verdana}\n" +
        "pre {white-space:pre-wrap;white-space:-moz-pre-wrap;white-space:-pre-wrap;white-space:-o-pre-wrap;word-wrap:break-word;width:95%}\n" +
        "th,td{font-family:arial,verdana,sans-serif;line-height:1.666}\n" +
        "img{ border:0}\n" +
        "header,footer,section,aside,article,nav,hgroup,figure,figcaption{display:block}\n" +
        "blockquote{margin-right:0px}\n" +
        "</style>\n" +
        "\n" +
        "\n" +
        "\n" +
        "<style id=\"netease_mail_footer_style\" type=\"text/css\">#netease_mail_footer{display:none;}</style>\n" +
        "\n" +
        "\n" +
        "<style id=\"ntes_link_color\" type=\"text/css\">a,td a{color:#064977}</style>\n" +
        "\n" +
        "</body>\n" +
        "</html>";
  }


}
