
# AnalyzeLog 在后台线程 将log写入文件中

## Installation
* apk 的路径为 AndroidLogDemo/app/build/outputs/apk/debug/app-debug.apk
```sh
 adb install -r app-debug.apk
```
## API
```
  * write log : AnalyzeLog.getInstance(context).d("xxx");
  * switch log on : AnalyzeLog.getInstance(context).logOn();
  * switch log off : AnalyzeLog.getInstance(context).logOff();
```
## 提交历史
* 增加广播发送权限和接收权限