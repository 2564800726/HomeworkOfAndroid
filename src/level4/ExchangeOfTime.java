package level4;

import java.util.Scanner;

public class ExchangeOfTime {
    private long timestamp;
    private String time;
    // 定义一个数组，存储从年初到某月份有多少天
    private int[] dayToMonth = {
            0,
            31,
            31 + 28,
            31 + 28 + 31,
            31 + 28 + 31 + 30,
            31 + 28 + 31 + 30 + 31,
            31 + 28 + 31 + 30 + 31 + 30,
            31 + 28 + 31 + 30 + 31 + 30 + 31 ,
            31 + 28 + 31 + 30 + 31 + 30 + 31 + 31,
            31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30,
            31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31,
            31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31 + 30,
            31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31 + 30 + 31,
    };
    public ExchangeOfTime(String time, long timestamp) {
        this.time = time;
        this.timestamp = timestamp;
    }

    ExchangeOfTime() {
    }

    void startExchange() {
        long timestamp;
        String time;
        String currentType;
        String aimType;
        String command;
        Scanner scan = new Scanner(System.in);
        System.out.println("输入格式(1,2018-10-26 21:02:32,ZH,ZH 或者 2,12345678901,ZH,ZH)\n" +
                "ZH,ZH，前者表示当前地方时间，后者表示希望转换成的地方的时间\n" +
                "ZH：北京时间\n" +
                "U.S：英国时间\n" +
                "U.S.A：美国时间\n" +
                "退出(exit):");
        while (true) {
            command = scan.nextLine();
            // 将输入的命令中的字符串参数截取出来
            if (command.equals("exit")) {
                return;
            } else if ("1".equals(command.split(",")[0])) {
                time = command.split(",")[1];
                currentType = command.split(",")[2];
                aimType = command.split(",")[3];
                timeToTimestamp(time, currentType, aimType);
            } else if ("2".equals(command.split(",")[0])) {
                timestamp = Long.parseLong(command.split(",")[1]);
                currentType = command.split(",")[2];
                aimType = command.split(",")[3];
                timestampToTime(timestamp, currentType, aimType);
            } else {
                System.out.println("非法数据格式");
            }
        }
    }

    // 把时间戳转化为标准时间格式
    private void timestampToTime(long timestamp, String currentType, String aimType) {
        this.timestamp = timestamp;
        long days = this.timestamp / (24 * 60 * 60);  // 时间戳中包含的整天
        int leftHour = (int) this.timestamp % (24 * 60 * 60) / (60 * 60);  // 时间戳中包含的整时
        int leftMinute = (int) this.timestamp % (24 * 60 * 60) % (60 * 60) / 60;  // 时间戳中包含的整分
        int year = 1970;
        int month = 0;
        int second = (int) this.timestamp % (24 * 60 * 60) % (60 * 60) % 60;  // 时间戳中不足一分钟的秒数
        // 从1970年开始，判断改时间戳可以组成多少个整年，同时除去整年占的天数
        while (days > 364) {
            if ((year % 100) != 0) {
                if ((year % 4) == 0) {
                    days -= 366;
                    year++;
                } else {
                    days -= 365;
                    year++;
                }
            } else {
                if ((year % 400) == 0) {
                    days -= 366;
                    year++;
                } else {
                    days -= 365;
                    year++;
                }
            }
        }
        // 判断剩下的天数可以组成多少个整月，并除去组成整月的天数
        while ((days - dayToMonth[month]) > 0) {
            month++;
        }
        days -= dayToMonth[month - 1];
        // 得到换算为标准格式时间的字符串，并换算成期望的地区时间
        this.time = year + "-" + month + "-" + (days + 1) + " " + leftHour + ":" + leftMinute + ":" + second;
        toWhatType("U.S", currentType);
        toWhatType(currentType, aimType);
        days = Integer.parseInt(this.time.split("-")[2].split(" ")[0]);
        leftHour = Integer.parseInt(this.time.split(":")[0].split(" ")[1]);
        // 格式化输出时间，补充上前导零
        System.out.printf("%s %s <--> %s-%02d %02d:%02d:%02d %s\n",
                this.timestamp, currentType,
                this.time.split("-")[0] + "-" + this.time.split("-")[1],
                days, leftHour, leftMinute, second, aimType);
    }



    // 把标准格式时间转换为时间戳
    private void timeToTimestamp(String time, String currentType, String aimType) {
        this.time = time;
        toWhatType(currentType, "U.S");
        toWhatType(currentType, aimType);
        // 将标准格式时间的字符串中的日期相关信息截取出来
        int[] dates = new int[6];
        for (int i = 0; i < dates.length; i++) {
            if (i < 2) {
                dates[i] = Integer.parseInt(this.time.split("-")[i]);
            } else if (i == 2) {
                dates[i] = Integer.parseInt(this.time.split("-")[2].split(" ")[0]);
            } else {
                dates[i] = Integer.parseInt(this.time.split(" ")[1].split(":")[i - 3]);
            }
        }
        long timestamp = 0;
        // 计算出从1970年到现在日期的整年份所占的秒数
        for (int i = 1970; i < dates[0]; i++) {
            if ((i % 100) != 0) {
                if ((i % 4) == 0) {
                    timestamp += (366 * 24 * 60 * 60);
                } else {
                    timestamp += (365 * 24 * 60 * 60);
                }
            } else {
                if ((i % 400) == 0) {
                    timestamp += (366 * 24 * 60 * 60);
                } else {
                    timestamp += (365 * 24 * 60 * 60);
                }
            }
        }
        // 加上不足一年的整月份所占的秒数，同时加上剩余不足一个月以及一天和一分钟的秒数
        if ((dates[0] % 100) != 0) {
            if (dates[0] % 4 == 0) {
                if (dates[1] > 2) {
                    timestamp = timestamp + (getDays(dates[1]) + 1 + dates[2] - 1) * 24 * 60 * 60 + (dates[3] * 60 + dates[4]) * 60 + dates[5];
                } else {
                    timestamp = timestamp + (getDays(dates[1]) + dates[2] - 1) * 24 * 60 * 60 + (dates[3] * 60 + dates[4]) * 60 + dates[5];
                }
            } else {
                timestamp = timestamp + (getDays(dates[1]) + dates[2] - 1) * 24 * 60 * 60 + (dates[3] * 60 + dates[4]) * 60 + dates[5];
            }
        } else {
            if (dates[0] % 400 == 0) {
                if (dates[1] > 2) {
                    timestamp = timestamp + (getDays(dates[1]) + 1 + dates[2] - 1) * 24 * 60 * 60 + (dates[3] * 60 + dates[4]) * 60 + dates[5];
                } else {
                    timestamp = timestamp + (getDays(dates[1]) + dates[2] - 1) * 24 * 60 * 60 + (dates[3] * 60 + dates[4]) * 60 + dates[5];
                }
            } else {
                timestamp = timestamp + (getDays(dates[1]) + dates[2] - 1) * 24 * 60 * 60 + (dates[3] * 60 + dates[4]) * 60 + dates[5];
            }
        }
        // 得到最终的时间戳
        System.out.println(timestamp + " " + aimType + " <--> " + time + " " + currentType);
    }


    // 时差的转换

    // 北京时间和其他时间的互换
    private void toWhatType(String currentType, String aimType) {
        if (currentType.equals("ZH")) {
            switch (aimType) {
                case "U.S.A":
                    toLow(12);
                    break;
                case "U.S":
                    toLow(8);
                    break;
                default:
                    toLow(0);
                    break;
            }
        }
        if (aimType.equals("ZH")) {
            switch (currentType) {
                case "U.S.A":
                    toHigh(12);
                    break;
                case "U.S":
                    toHigh(8);
                    break;
                default:
                    toHigh(0);
                    break;
            }
        }
    }

    // 将字符串中的北京时间转换为其他地区时间
    private void toLow(int lag) {
        if (Integer.parseInt(this.time.split(":")[0].split(" ")[1]) >= 8) {
            this.time = this.time.split(" ")[0] + " "
                    + (Integer.parseInt(this.time.split(":")[0].split(" ")[1]) - lag) + ":"
                    + this.time.split(":")[1] + ":"
                    + this.time.split(":")[2];
        } else {
            this.time = this.time.split("-")[0] + "-"
                    + this.time.split("-")[1] + "-"
                    + (Integer.parseInt(this.time.split(" ")[0].split("-")[2]) - 1) + " "
                    + (24 + Integer.parseInt(this.time.split(" ")[1].split(":")[0]) - lag) + ":"
                    + this.time.split(":")[1] + ":"
                    + this.time.split(":")[2];
        }
    }

    // 将字符串中的其他地区时间转换为北京时间
    private void toHigh(int lag) {
        if ((Integer.parseInt(this.time.split(":")[0].split(" ")[1]) + lag) < 24) {
            this.time = this.time.split(" ")[0] + " "
                    + (Integer.parseInt(this.time.split(":")[0].split(" ")[1]) + lag) + ":"
                    + this.time.split(":")[1] + ":"
                    + this.time.split(":")[2];
        } else {
            this.time = this.time.split("-")[0] + "-"
                    + this.time.split("-")[1] + "-"
                    + (Integer.parseInt(this.time.split(" ")[0].split("-")[2]) + 1) + " "
                    + (24 + Integer.parseInt(this.time.split(" ")[1].split(":")[0]) + lag - 24) + ":"
                    + this.time.split(":")[1] + ":"
                    + this.time.split(":")[2];
        }
    }
    // 在换算为时间戳是将整月份的天数选择出来
    private int getDays(int month) {
        int days = 0;
        switch (month) {
            case 1:
                days += dayToMonth[0];
                break;
            case 2:
                days += dayToMonth[1];
                break;
            case 3:
                days += dayToMonth[2];
                break;
            case 4:
                days += dayToMonth[3];
                break;
            case 5:
                days += dayToMonth[4];
                break;
            case 6:
                days += dayToMonth[5];
                break;
            case 7:
                days += dayToMonth[6];
                break;
            case 8:
                days += dayToMonth[7];
                break;
            case 9:
                days += dayToMonth[8];
                break;
            case 10:
                days += dayToMonth[9];
                break;
            case 11:
                days += dayToMonth[10];
                break;
            case 12:
                days += dayToMonth[11];
                break;
        }
        return days;
    }
}
