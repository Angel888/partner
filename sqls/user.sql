CREATE TABLE IF NOT EXISTS `user`(
    `id` INT UNSIGNED AUTO_INCREMENT KEY COMMENT '用户编号',
    `username` VARCHAR(20) NOT NULL UNIQUE COMMENT '用户名',
    `password` CHAR(32) NOT NULL COMMENT '密码',
    `email` VARCHAR(50) NOT NULL UNIQUE COMMENT '邮箱',
    `age` TINYINT UNSIGNED NOT NULL DEFAULT 18 COMMENT '年龄',
    `gender` ENUM('man','woman','baomi') NOT NULL DEFAULT 'baomi' COMMENT '性别',
    `tel` CHAR(11) NOT NULL UNIQUE COMMENT '电话',
    )ENGINE=INNODB DEFAULT CHARSET=UTF8;