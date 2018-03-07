@echo off
cd /d %~dp0
java -Dfile.encoding=UTF-8 -Xmx1000m -Xms100m -Duser.timezone=GMT+8 -Dname=CrawlerMain -cp  crawler.jar com.fast.dev.crawler.CrawlerMain