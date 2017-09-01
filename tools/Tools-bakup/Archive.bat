@echo off
cd /d %~dp0\..\..\
@echo 开始备份文件
set fileName=backup_%date:~0,4%%date:~5,2%%date:~8,2%.zip
echo 压缩：%fileName%
if exist %fileName% del %fileName%
rar a %fileName% -r -x*\.settings -x*\.classpath -x*\.gitignore -x*\.project -x*\target  .\components .\examples .\PCore .\PParent .\templates .\tools 
rar a %fileName% -r -x*\target .\PServer
rar a %fileName% -r .\README.md
echo 处理完成 , 5秒自动退出.
ping 127.1 -n 5 >nul