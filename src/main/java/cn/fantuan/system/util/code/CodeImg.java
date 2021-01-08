package cn.fantuan.system.util.code;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

@Service
public class CodeImg {
    @Autowired
    HttpSession session;
    public byte[] getCodeImg() throws IOException {
        int width = 100;
        int height = 38;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 美化图片
        // 填充背景色
        Graphics g = image.getGraphics();// 画笔对象
        g.setColor(Color.GRAY);// 设置画笔颜色
        g.fillRect(0, 0, width, height);
        // 画边框
        g.setColor(Color.YELLOW);
        g.drawRect(0, 0, width - 1, height - 1);
        // 写验证码
        String str = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz23456789";
        // 生成一个随机角标
        Random ran = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 4; i++) {
            // 设置角标范围
            int index = ran.nextInt(str.length());
            // 获取字符
            char ch = str.charAt(index);
            // 设置字体
            Font font = new Font("黑体", Font.BOLD, 24);
            g.setFont(font);
            sb.append(ch);
            // 写验证码
            g.drawString(ch + "", width / 5 * i - 5, height / 2 + 8);
        }
        //画干扰线
//        g.setColor(Color.red);
//        for (int i = 0; i < 10; i++) {
//            // 随机坐标点生成
//            int x1 = ran.nextInt(width);
//            int x2 = ran.nextInt(width);
//            int y1 = ran.nextInt(height);
//            int y2 = ran.nextInt(height);
//            g.drawLine(x1, y1, x2, y2);
//        }
        session.setAttribute("session_validatecode",sb.toString());
        //定义一个二进制数组输出流
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        //将图片写入输出流
        ImageIO.write(image, "jpg", os);
        //返回结果
        return os.toByteArray();
    }
}
