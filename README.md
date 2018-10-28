时间戳转换
=========
ExchangeOfTime类中的主要方法
--------------
public void startExchange()<br>
// 开始等待输入的状态，根据输入的命令来调用timeToTimestamp方法和timestampToTime方法<br>
<br>
private void timeToTimestamp(String time, String currentType, String aimType)<br>
// 将标准格式时间转换为时间戳<br>
// time是标准格式时间，currentType是当前是哪一个国家的标准时间，aimType是想要转换成的那个国家的时间<br>
<br>
private void timestampToTime(String stamp, currentType, aimType)<br>
// 将时间戳转换成标准时间格式<br>
// timestamp是时间戳，currentType是当前是哪一个国家的时间戳，aimType是想要转换成的那个国家的标准格式时间<br>
<br>
/*<br>
ZH 表示北京时间<br>
U.S 表示格林威治时间<br>
U.S.A 表示纽约时间<br>
*/<br>
