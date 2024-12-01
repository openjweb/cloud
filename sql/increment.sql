/*本文件记录2024/11/30号以后在OpenJWeb平台中新增的表结构及数据*/

create table comm_sso_client_app(row_id varchar(40) NOT NULL   COMMENT '唯一行号'
    ,obj_name varchar(40) NULL   COMMENT '名称'
    ,master_row_id varchar(40) NULL   COMMENT '主表RowId'
    ,flow_trans_id varchar(40) NULL   COMMENT '流程事务ID'
    ,sort_no bigint  NULL
    ,create_dt varchar(23) NULL   COMMENT '创建日期'
    ,update_dt varchar(23) NULL   COMMENT '最后修改日期'
    ,create_uid varchar(32) NULL   COMMENT '创建人'
    ,update_uid varchar(32) NULL   COMMENT '修改人'
    ,data_flg varchar(6) NULL   COMMENT '是否启用'
    ,record_version bigint  NOT NULL
    ,flow_status varchar(16) NULL   COMMENT '流程状态'
    ,refresh_token_overdue_seconds bigint  NULL
    ,account varchar(80) NULL   COMMENT '企业账号'
    ,password varchar(200) NULL   COMMENT '登录密码'
    ,client_name varchar(255) NULL   COMMENT '客户端名称'
    ,access_token_overdue_seconds bigint  NULL
    ,client_id bigint  NULL
    ,call_back_url varchar(255) NULL   COMMENT '回调地址'
);
alter table comm_sso_client_app add primary key (row_id);
