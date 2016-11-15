create table JNG_SYS_TRANS_LIST_STAT
(
  guid                 VARCHAR2(32) not null, --guid
  trans_type           VARCHAR2(20) not null, --交易类型
  trans_stat_count     NUMBER(10) not null,   --统计交易量
  trans_stat_interval  NUMBER(5) not null,    --统计间隔时间（分钟）
  trans_stat_time      DATE                   --统计时间
) ;
-- Add comments to the table
comment on table JNG_SYS_TRANS_LIST_STAT
  is '交易流水统计表';
-- Add comments to the columns
comment on column JNG_SYS_TRANS_LIST_STAT.trans_type
  is '交易类型';
comment on column JNG_SYS_TRANS_LIST_STAT.trans_stat_count
  is '统计交易量';
comment on column JNG_SYS_TRANS_LIST_STAT.trans_stat_interval
  is '统计间隔时间（分钟）';
comment on column JNG_SYS_TRANS_LIST_STAT.trans_stat_time
  is '统计时间';
--Ad的 the index
create index JNG_SYS_TRANS_LIST_STAT_I0 on JNG_SYS_TRANS_LIST_STAT (trans_stat_time,trans_type, trans_stat_interval);

-- ip段归属地区表

create table JNG_SYS_IP_EARE
(
  guid                 VARCHAR2(32) default sys_guid() not null, --guid
  ip_adr               VARCHAR2(10) not null, --ip地址段
  ip_bank              VARCHAR2(20) not null, --ip地址段归属行
  ip_bank_cn           VARCHAR2(20) not null, --ip地址段归属行中文
  ip_eare              VARCHAR2(20) not null, --ip地址段归属地缩写
  ip_eare_cn           VARCHAR2(20) not null  --ip地址段归属地中文

) ;
create index JNG_SYS_IP_EARE_I0 on JNG_SYS_IP_EARE
(ip_adr);
 -- Add comments to the table
comment on table JNG_SYS_IP_EARE
  is 'ip段归属地区表';
-- Add comments to the columns
comment on column JNG_SYS_IP_EARE.guid
  is 'guid';
comment on column JNG_SYS_IP_EARE.ip_adr
  is 'ip地址段';
comment on column JNG_SYS_IP_EARE.ip_bank
  is 'ip地址段归属行';
comment on column JNG_SYS_IP_EARE.ip_bank_cn
  is 'ip地址段归属行中文';
comment on column JNG_SYS_IP_EARE.ip_eare
  is 'ip地址段归属地缩写';
comment on column JNG_SYS_IP_EARE.ip_eare_cn
  is 'ip地址段归属地中文';

-- insert data

insert into JNG_SYS_IP_EARE (IP_ADR, IP_BANK, IP_BANK_CN, IP_EARE, IP_EARE_CN)
values ('178.80', 'bj_bank', '北京分行', 'bj', '北京');

insert into JNG_SYS_IP_EARE (IP_ADR, IP_BANK, IP_BANK_CN, IP_EARE, IP_EARE_CN)
values ('178.79', 'sh_bank', '上海分行', 'sh', '上海');

insert into JNG_SYS_IP_EARE (IP_ADR, IP_BANK, IP_BANK_CN, IP_EARE, IP_EARE_CN)
values ('178.81', 'sz_bank', '深圳分行', 'sz', '深圳');

insert into JNG_SYS_IP_EARE (IP_ADR, IP_BANK, IP_BANK_CN, IP_EARE, IP_EARE_CN)
values ('178.83', 'nj_bank', '南京分行', 'nj', '南京');

insert into JNG_SYS_IP_EARE (IP_ADR, IP_BANK, IP_BANK_CN, IP_EARE, IP_EARE_CN)
values ('178.87', 'hf_bank', '合肥分行', 'hf', '合肥');

insert into JNG_SYS_IP_EARE (IP_ADR, IP_BANK, IP_BANK_CN, IP_EARE, IP_EARE_CN)
values ('178.82', 'nb_bank', '宁波分行', 'ningbo', '宁波');

insert into JNG_SYS_IP_EARE (IP_ADR, IP_BANK, IP_BANK_CN, IP_EARE, IP_EARE_CN)
values ('178.85', 'sx_bank', '绍兴分行', 'shaoxing', '绍兴');

insert into JNG_SYS_IP_EARE (IP_ADR, IP_BANK, IP_BANK_CN, IP_EARE, IP_EARE_CN)
values ('178.89', 'zs_bank', '舟山分行', 'zhoushan', '舟山');

insert into JNG_SYS_IP_EARE (IP_ADR, IP_BANK, IP_BANK_CN, IP_EARE, IP_EARE_CN)
values ('178.88', 'wz_bank', '温州分行', 'wenzhou', '温州');

insert into JNG_SYS_IP_EARE (IP_ADR, IP_BANK, IP_BANK_CN, IP_EARE, IP_EARE_CN)
values ('178.90', 'qz_bank', '衢州分行', 'quzhou', '衢州');

insert into JNG_SYS_IP_EARE (IP_ADR, IP_BANK, IP_BANK_CN, IP_EARE, IP_EARE_CN)
values ('178.91', 'jh_bank', '金华分行', 'jinhua', '金华');

insert into JNG_SYS_IP_EARE (IP_ADR, IP_BANK, IP_BANK_CN, IP_EARE, IP_EARE_CN)
values ('178.86', 'dq_zhi', '德清', 'hz', '杭州');

insert into JNG_SYS_IP_EARE (IP_ADR, IP_BANK, IP_BANK_CN, IP_EARE, IP_EARE_CN)
values ('178.128', 'ls_zhi', '丽水', 'hz', '杭州');

insert into JNG_SYS_IP_EARE (IP_ADR, IP_BANK, IP_BANK_CN, IP_EARE, IP_EARE_CN)
values ('178.84', 'jx_zhi', '嘉兴', 'hz', '杭州');

insert into JNG_SYS_IP_EARE (IP_ADR, IP_BANK, IP_BANK_CN, IP_EARE, IP_EARE_CN)
values ('178.78', 'hz_bank', '杭州', 'hz', '杭州');


