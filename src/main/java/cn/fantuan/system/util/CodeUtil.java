package cn.fantuan.system.util;

import java.util.Random;
import java.util.UUID;

public class CodeUtil {
    /**
     * 生成n位随机验证码，包括数字和大小写字母
     *
     * @param n
     * @return
     */
    public static String Code(int n) {
        StringBuilder strB = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            //生成一个随机的 int 值，该值介于 [0,n)，包含 0 而不包含 n。
            int r1 = rand.nextInt(3);
            int r2 = 0;
            switch (r1) {  // r2为ascii码值ֵ
                case 0: // 数字
                    r2 = rand.nextInt(10) + 48;  // 数字：48-57的随机数
                    break;
                case 1:
                    r2 = rand.nextInt(26) + 65;  // 大写字母：65-90的随机数
                    break;
                case 2:
                    r2 = rand.nextInt(26) + 97;  // 小写字母：97-122的随机数
                    break;
                default:
                    break;
            }
            strB.append((char) r2);
        }
        return strB.toString();
    }

    /**
     * 生成6为数的随机验证码
     */
    public static String getRandom() {
        String[] letters = new String[]{"q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String code = "";
        for (int i = 0; i < 6; i++) {
            code = code + letters[(int) Math.floor(Math.random() * letters.length)];
        }
        return code;
    }

    /**
     * 获取UUID
     *
     * @return
     */
    public static String getUUID() {
        String UUIDCode = UUID.randomUUID().toString().replaceAll("-", "");
        return UUIDCode;
    }
}
