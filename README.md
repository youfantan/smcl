# shandiankulishe's Minecraft Launcher



## 本项目包含启动器核心、启动器示例以及使用文档(Javadoc)

### 如何使用

进入目录smclc，输入命令`mvn package`以构建核心，将核心（target\smclc-0.1-dev0807.jar）导入你的IDE即可使用。

进入目录smcl-demo，输入命令`mvn package`以构建demo，运行jar文件（`java -jar target/smcl-demo-0.1dev0808-jar-with-dependencies.jar`）即可使用。

将smcl目录导入IDE，将lib目录加入libraries，然后下载OpenCV3.2.0（或者更高版本，需要在GaussianBlur.java将jna加载目录改为你的目录，注意不加后缀名），将 解压路径\build\java目录复制到项目根目录，重命名为opencv即可开始继续开发  
**注意，启动器处于开发阶段，并未完成**

如果您对这个项目有任何建议或意见，请发送Github Issues至本项目仓库，如果有改进意见请提交Github Pull Request至本项目仓库。

**感谢您的使用！**


## This Project include LauncherCore,LauncherDemo and Javadoc.

### How to Use?

Enter the directory `smclc` and run the command `mvn package` to build the core,then import the core to your IDE.Enjoy Use!:D

Enter the directory `smcl-demo` and run the command `mvn package` to build the demo,then run the command `java -jar target/smcl-demo-0.1dev0808-jar-with-dependencies.jar`.

Put the directory `smcl` to your IDE and add the directory `lib` to libraries.Then download OpenCV3.2.0 (or later.But your should change the JNA libraries load path in GaussianBlur.java to your directory).Copy the ZippedPath\build\java to the Project Root Path and then rename it to opencv.Have a good time in Developing! :D  
**Attention,the launcher is in develop,NOT FINISH!**

If you have any thinks of this project,please send Github Issues to this repository.If you have any suggestions for improvement please send Github Pull Request to this repository.

**Thanks for using this project!**

### 更新日志

2020年8月7日 v0.1-dev0708 首个版本  
2020年8月8日 v0.1-dev0808 增加了smcl-demo  
2020年8月18日 v0.1.1-dev0818  
+ 修复了xyz.shandiankulishe.smcl.core.authProfile中的状态码获取Bug，增加了几个新的方法，可以更方便的验证Mojang Account了
+ 增加了启动器主版本项目，目前已经完成了加载动画、主页面和设置


javadoc注释已经（gu）在（gu）写（gu）了（确信