#!/bin/bash
cd `dirname $0`
java -Xmx1000m -Xms100m -Duser.timezone=GMT+8 -Dname=CrawlerMain -cp  crawler.jar com.fast.dev.crawler.CrawlerMain