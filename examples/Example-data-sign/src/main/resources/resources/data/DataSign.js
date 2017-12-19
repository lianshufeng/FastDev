//Crc32算法
var Crc32=function(){var signed_crc_table=function(){var c=0,table=new Array(256);for(var n=0;n!=256;++n){c=n;c=((c&1)?(-306674912^(c>>>1)):(c>>>1));c=((c&1)?(-306674912^(c>>>1)):(c>>>1));c=((c&1)?(-306674912^(c>>>1)):(c>>>1));c=((c&1)?(-306674912^(c>>>1)):(c>>>1));c=((c&1)?(-306674912^(c>>>1)):(c>>>1));c=((c&1)?(-306674912^(c>>>1)):(c>>>1));c=((c&1)?(-306674912^(c>>>1)):(c>>>1));c=((c&1)?(-306674912^(c>>>1)):(c>>>1));table[n]=c}return typeof Int32Array!=="undefined"?new Int32Array(table):table};var T=signed_crc_table();this.crc32_str=function(str,seed){var C=seed^-1;for(var i=0,L=str.length,c,d;i<L;){c=str.charCodeAt(i++);if(c<128){C=(C>>>8)^T[(C^c)&255]}else{if(c<2048){C=(C>>>8)^T[(C^(192|((c>>6)&31)))&255];C=(C>>>8)^T[(C^(128|(c&63)))&255]}else{if(c>=55296&&c<57344){c=(c&1023)+64;d=str.charCodeAt(i++)&1023;C=(C>>>8)^T[(C^(240|((c>>8)&7)))&255];C=(C>>>8)^T[(C^(128|((c>>2)&63)))&255];C=(C>>>8)^T[(C^(128|((d>>6)&15)|((c&3)<<4)))&255];C=(C>>>8)^T[(C^(128|(d&63)))&255]}else{C=(C>>>8)^T[(C^(224|((c>>12)&15)))&255];C=(C>>>8)^T[(C^(128|((c>>6)&63)))&255];C=(C>>>8)^T[(C^(128|(c&63)))&255]}}}}var result=C^-1;return result<0?4294967296+result:result}};


/**
* 数据签名
*/
var DataSign = function (token,option){
    
    var _config = {
        'option' : {
            'hash':'_hash',
            'time':'_time'
        },
        'token':null
    }
    
    /**
    *初始化
    */
    var _init_ = function() {
        if (option){
            _config.option = option;
        }
        _config.token = token;
    }
    
    //字符串转到对象
    var _dataStrToObject = function(postInfo){
        var object = {};
        var kvs = postInfo.split('&');
        for (var k in kvs){
            if ( kvs[k] == '' ){
                continue;
            }
            var kv = kvs[k].split('=');
            if ( kv.length > 1 ){
            	if (object[kv[0]]){
            		object[kv[0]].push( kv[1] );
            	}else{
            		object[kv[0]] = [ kv[1] ];
            	}
            }else if ( kv.length > 0 ){
                object[kv[0]] = null;
            }
        }
        return object;
    }
    
    //通过URL取参数,并追加到对象里
    var _urlGetInfo = function(url,obj){    
        var at = url.toString().indexOf('?');
        var info = null;
        if ( at > 0 ){
            var info = url.substring(at+1,url.length);
            at = info.indexOf('#');
            if (at>0){
                info = info.substring(0,at);
            }
        }
        //如果取出参数则追加到对象里
        if (info != null ){
            var data = _dataStrToObject(info);
            for (var key in data){
                obj[key] = data[key];
            }
            
        }
    }
    
    /**
     * 给原始的数据解码
     */
    var decodeUrlData = function(data){
    	return data + '';
    }
    
    /**
    * 数据签名
    */
    this.sign = function( time , url , data ){
        //数据
        var obj = null;
        //字符串数据转换为对象
        if ( typeof data == 'string' ){
            obj = _dataStrToObject(data);
        }else{
            obj = data;
        }
        //url转换为数据对象
        _urlGetInfo(url,obj);
        //排序key
        var keys = new Array();
        for (var i in obj){
    		keys.push(i);
        }
        //key 排序
        keys.sort();
        //组合数据
        var datas = new Array();
        //增加令牌
        datas.push(_config.token);
        //增加数据
        for (var i in keys){
            var val = obj[keys[i]];
            //数组内容排序
            if ( val instanceof Array ){
            	//解码
            	val.forEach(function(v,i, arr){
            		arr[i] = decodeUrlData(v);
            	});
                //排序
                val.sort();
                datas = datas.concat(val);
            }else{
            	//解码并添加到数组中
                datas.push( decodeUrlData(val) );
            }
        }
        //增加时间
        datas.push(time+'');
        //进行数据签名
        var hash = new Crc32().crc32_str(datas.join(''));
        var result = null;
        //判断数据类型进行返回
        if (typeof data == 'string'){
            result = data+'&'+_config.option.hash+'='+hash+'&'+_config.option.time+'='+time
        }else{
            result = JSON.parse(JSON.stringify(data));
            result[_config.option.hash] = hash;
            result[_config.option.time] = time;
        }
        return result;
    }
    
    //初始化
    _init_();
};




