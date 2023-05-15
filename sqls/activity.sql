CREATE TABLE IF NOT EXISTS `activity`(
     `activity_id` INT(10) NOT NULL  PRIMARY KEY  COMMENT '登记的活动id',

    `user_id` INT UNSIGNED   COMMENT '用户编号',
    `activity_type` INT UNSIGNED COMMENT '运动类型',
    `activity_city` CHAR(18) NOT NULL COMMENT '活动城市',
--     城市用数字还是用name? 活动名称、
# 活动时间、活动人数、活动性别、已匹配人数、活动状态去（待匹配，已匹配待开始、进行中、已结束）、活动评价及建议
#


);




docker run \
-d \
--name redis \
-p 6379:6379 \
--restart unless-stopped \
-v /mydata/redis/data:/data \
-v /mydata/redis/conf/redis.conf:/etc/redis/redis.conf \
redis-server /etc/redis/redis.conf \
redis:buster
