package tmp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Author zhoujun002
 * @Date 2020/9/25
 **/
public class MainProcess {

    public static String getHtmlConentByUrl(String ssourl) {
        try {
            URL url = new URL(ssourl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setInstanceFollowRedirects(false);
            con.setUseCaches(false);
            con.setAllowUserInteraction(false);
            con.connect();
            StringBuffer sb = new StringBuffer();
            String line = "";
            BufferedReader URLinput = new BufferedReader(new InputStreamReader(con.getInputStream(), "GBK"));
            while ((line = URLinput.readLine()) != null) {
                sb.append(line);
            }
            con.disconnect();
            return sb.toString().toLowerCase();
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        String[] ind = {"股票名称", "今日开盘价", "昨日收盘价", "当前价格",
                        "今日最高价", "今日最低价",
                        "买一报价", "卖一报价", "成交股数", "成交金额",
                        "买一股数", "买一报价", "买二股数", "买二报价", "买三股数", "买三报价", "买四股数", "买四报价", "买五股数", "买五报价",
                        "卖一股数", "卖一报价", "卖二股数", "卖二报价", "卖三股数", "卖三报价", "卖四股数", "卖四报价", "卖五股数", "卖五报价",
                        "当前日期", "当前时间"};
        Look look = new Look(ind, 82.6);
        Thread t = new Thread(look);
        t.start();
    }


    static class Look implements Runnable {

        double desPri;
        String[] ind;

        public Look(String[] ind, double pri) {
            this.ind = ind;
            this.desPri = pri;
        }

        @Override
        public void run() {
            int count = 0;
            while (true) {
                String ss = getHtmlConentByUrl("http://hq.sinajs.cn/list=sh600536");
                String[] fcs = ss.split("\"");
                String scs = fcs[1];
                // 返回的数据
                String[] tcs = scs.split(",");
                // 获取当前价格
                Double price = Double.parseDouble(tcs[3]);

                if (price < desPri) {
                    System.out.println(ind[3] + ":  " + tcs[3]);
                }
                Runtime run = Runtime.getRuntime();
                Process pro = null;
                // 400亿 / 49.5万股 = 80.80元
                if (price < (desPri * 0.99)) {
                    try {
                        pro = run.exec("notepad.exe");
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    pro.destroy();
                    desPri = desPri * 0.995;
                }
                if (price > desPri * 1.01) {
                    desPri = desPri * 1.005;
                }
                // 每分钟
                if (count ++ % 30 == 0) {
                    System.out.println("===========================");
                    System.out.println(ind[3] + ":  " + tcs[3]);
                    System.out.println("===========================");
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

