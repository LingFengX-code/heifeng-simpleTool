package com.heifeng.generator.entity;



//数据库实体类
public class DataBase {
                                     //             127.0.0.1：3306/ihrm
    private static String mysqlUrl = "jdbc:mysql://[ip]:[port]/[db]?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    private static String oracleUrl = "jdbc:oracle:thin:@[ip]:[port]:[db]";

    private String dbType;//数据库类型
    private String userName;
    private String passWord;
    private String dbName;  //数据库名

    private String driver;
    private String url;

    public DataBase() {}

    public DataBase(String dbType) {
        this(dbType,"127.0.0.1","3306","");
    }

    public DataBase(String dbType,String dbName) {
        this(dbType,"127.0.0.1","3306",dbName);
    }


    /**
     *
     * @param dbType        数据库类型
     * @param ip            ip
     * @param port          3306
     * @param dbName         db_permission_manage(数据库名)
     */
    public DataBase(String dbType,String ip,String port,String dbName) {
        this.dbType = dbType;
        this.dbName = dbName;
        if("MYSQL".endsWith(dbType.toUpperCase())) {
            this.driver="com.mysql.jdbc.Driver";
            this.url=mysqlUrl.replace("[ip]",ip).replace("[port]",port).replace("[db]",dbName);
        }else{
            this.driver="oracle.jdbc.driver.OracleDriver";
            this.url=oracleUrl.replace("[ip]",ip).replace("[port]",port).replace("[db]",dbName);
        }
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
