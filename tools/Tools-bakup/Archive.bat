@echo off
cd /d %~dp0\..\..\
@echo ��ʼ�����ļ�
set fileName=backup_%date:~0,4%%date:~5,2%%date:~8,2%.zip
echo ѹ����%fileName%
if exist %fileName% del %fileName%
rar a %fileName% -r -x*\.settings -x*\.classpath -x*\.gitignore -x*\.project -x*\target  .\components .\examples .\PCore .\PParent .\templates .\tools 
rar a %fileName% -r -x*\target .\PServer
rar a %fileName% -r .\README.md
echo ������� , 5���Զ��˳�.
ping 127.1 -n 5 >nul