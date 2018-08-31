/*==============================================================*/
/* Table: 报销单                                                 */
/*==============================================================*/
create table claim_voucher
(
   id int not null auto_increment COMMENT "报销单ID",
   cause varchar(100) NOT NULL COMMENT "事由",
   create_sn char(5) NOT NULL COMMENT "创建人编号",
   create_time datetime COMMENT "创建时间",
   next_deal_sn char(5) COMMENT "待处理人编号",
   total_amount double COMMENT "总金额",
   status varchar(20) COMMENT "状态",
   primary key (id)
);

/*==============================================================*/
/* Table: 报销单内容明细                                          */
/*==============================================================*/
create table claim_voucher_item
(
   id int not null auto_increment COMMENT "编号",
   claim_voucher_id int NOT NULL COMMENT "报销单编号",
   item varchar(20) COMMENT "费用类型",
   amount double COMMENT "金额",
   comment varchar(100) COMMENT "描述",
   primary key (id)
);

/*==============================================================*/
/* Table: 报销单处理记录                                          */
/*==============================================================*/
create table claim_deal_record
(
   id int not null auto_increment COMMENT "编号",
   claim_voucher_id int NOT NULL COMMENT "报销单编号",
   deal_sn char(5) not NULL COMMENT "处理人编号",
   deal_time datetime COMMENT "处理时间",
   deal_way varchar(20) COMMENT "处理类型",
   deal_result varchar(20) COMMENT "处理结果",
   comment varchar(100) COMMENT "描述",
   primary key (id)
);

/*==============================================================*/
/* Table: 部门                                                   */
/*==============================================================*/
create table department
(
   sn char(5) not null COMMENT "编号",
   name varchar(20) NOT NULL COMMENT "名称",
   address varchar(100) COMMENT "位置",
   primary key (sn)
);

/*==============================================================*/
/* Table: 员工                                                   */
/*==============================================================*/
create table employee
(
   sn char(5) not null COMMENT "编号",
   password varchar(20) NOT NULL COMMENT "密码",
   name varchar(20) COMMENT "姓名",
   department_sn char(5) COMMENT "所属部门编号",
   post varchar(20) COMMENT "职位",
	 auth_sn SMALLINT COMMENT "权限编号",
   primary key (sn)
);

/*==============================================================*/
/* Table: 员工权限                                               */
/*==============================================================*/
CREATE TABLE authority
(
	id SMALLINT NOT NULL auto_increment COMMENT "编号",
	level SMALLINT NOT NULL COMMENT "权限等级",
	comment VARCHAR(100) COMMENT "描述",
	PRIMARY KEY (id)
);

/*==============================================================*/
/* Table: 请假                                                   */
/*==============================================================*/
CREATE TABLE leave_voucher
(
   id int not null auto_increment COMMENT "请假单ID",
   cause varchar(100) NOT NULL COMMENT "事由",
   create_sn char(5) NOT NULL COMMENT "创建人编号",
   create_time datetime COMMENT "创建时间",
   next_deal_sn char(5) COMMENT "待处理人编号",
   total_days double COMMENT "请假天数",
   status varchar(20) COMMENT "状态",
   primary key (id)
);

/*==============================================================*/
/* Table: 请假单处理记录                                          */
/*==============================================================*/
create table leave_deal_record
(
   id int not null auto_increment COMMENT "编号",
   leave_voucher_id int NOT NULL COMMENT "请假单编号",
   deal_sn char(5) not NULL COMMENT "处理人编号",
   deal_time datetime COMMENT "处理时间",
   deal_way varchar(20) COMMENT "处理类型",
   deal_result varchar(20) COMMENT "处理结果",
   comment varchar(100) COMMENT "描述",
   primary key (id)
);

alter table claim_voucher_item add constraint FK_item_voucher foreign key (claim_voucher_id)
      references claim_voucher (id) on delete restrict on update restrict;

alter table employee add constraint FK_employee_dept foreign key (department_sn)
      references department (sn) on delete restrict on update restrict;

alter table employee add constraint FK_employee_auth foreign key (auth_sn)
      references authority (id) on delete restrict on update restrict;

insert into department values('10001','总经理办公室','A大厦C座1201');
insert into department values('10002','财务部','A大厦C座1202');
insert into department values('10003','事业部','A大厦C座1101');
insert into department values('10004','人事部','A大厦C座1102');

insert into authority(level, comment) values(10, '管理员');
insert into authority(level, comment) values(100, '普通');

insert into employee values('10000','000000','管理员','10004','管理员', 1);
insert into employee values('10001','000000','李明','10001','总经理', 2);
insert into employee values('10002','000000','张红','10002','财务', 2);
insert into employee values('10003','000000','孙剑','10003','部门经理', 2);
insert into employee values('10004','000000','周周','10003','员工', 2);
insert into employee values('10005','000000','王淼','10004','部门经理', 2);
