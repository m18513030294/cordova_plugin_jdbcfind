安装 ： cordova plugin add 插件安装包绝对路径
卸载 ： cordova plugin remove com.lzsf.jdbc  
环境 ： cordova环境为6.5.0

使用方法：
select() {
    var param = [
      {
          key:"*",
          
          table:"ST_STOCK_ALARM"
      }
    ]
          
    cordova.plugins.jdbcfind.select(param, function (success) {
      alert("本地导出成功-exportPath:" + success);
    }, function (error) {
      alert("本地导出失败-exportPath:" + error);
    });
  }

  添加数据   sql语句：INSERT INTO 表名（属性A，属性b，属性C） VALUES （参数a,参数b，参数C）；
	调用方法 add 参数 [{
						  key:"(CD_STORE,QT_ALARM)",对应（属性A，属性b，属性C）
						  value:"(\'+S001CD07\',1.5)",对应（参数a,参数b，参数C）
						  table:"ST_STOCK_ALARM",对应表名
						  sql:""一般为空，可以直接写sql语句 如INSERT INTO 表名（属性A，属性b，属性C） VALUES （参数a,参数b，参数C）；
						}]
	查询数据   Sql语句：Select 条件 from 表名；
	调用方法 select 参数[{
							key:"*",对应条件
							table:""
						}]
	更新数据   Sql语句：UPDATE 表名 SET  参数  WHERE 条件;
	调用方法  update 参数 [{
							key:"",对应参数 如（value="aa")
							value:"",对应条件
							table:"",
							sql:""
						}]
	删除数据   Sql语句：delete from 表名 where 条件；
	d调用方法 delete 参数 [{
							key:"",
							value:"",对应条件
							table:"",
							sql:""
							}]