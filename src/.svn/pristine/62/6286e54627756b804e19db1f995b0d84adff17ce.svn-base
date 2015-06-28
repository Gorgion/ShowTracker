-------------------------------------------------------------------------------------------------------------------------------
CREATE TABLE  `show` (
`id` BIGINT(20) AUTO_INCREMENT PRIMARY KEY ,
`user_id` BIGINT(20),
`name` VARCHAR( 100 ) NOT NULL ,
`series` SMALLINT,
`episode` SMALLINT,
`note` VARCHAR( 255 ) ,
CONSTRAINT  `user_id_fkey` FOREIGN KEY ( `user_id` ) REFERENCES `users`( `id` ) ON DELETE CASCADE
)

CREATE TABLE `persistent_logins` (
    `username` VARCHAR(64) NOT NULL,
    `series` VARCHAR(64) NOT NULL,
    `token` VARCHAR(64) NOT NULL,
    `last_used` TIMESTAMP NOT NULL,
    PRIMARY KEY (`series`)
);



----------------

CREATE TABLE `users` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(50) NOT NULL,
    `email` VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY (`id`)
);

CREATE TABLE `users_auth` (
    `user_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `password` VARCHAR(80) NOT NULL ,
    `enabled` TINYINT NOT NULL DEFAULT 1,
    PRIMARY KEY (`user_id`),
    CONSTRAINT `FK_USERS` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
);

CREATE TABLE `login_details` (
    `user_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `login_time` TIMESTAMP NOT NULL ,
    PRIMARY KEY (`user_id`, `login_time`),
    CONSTRAINT `FK_USERS_LOGIN_DETAILS` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;


CREATE DEFINER=`adminECgYBVT`@`127.10.186.130` EVENT `login_details_delete_event` ON SCHEDULE EVERY 2 WEEK STARTS '2014-08-30 00:00:00' ON COMPLETION PRESERVE ENABLE DO delete ld1.* FROM `login_details` ld1 JOIN
 (
    select `user_id` from `login_details`
    group by `user_id`
    having count(`user_id`) > 10
) ld2 
ON ld1.`user_id` = ld2.`user_id`
where ld1.`login_time` < CURRENT_TIMESTAMP - INTERVAL 2 month

CREATE TABLE `verification_tokens` (
    `token` VARCHAR(40),
    `email` VARCHAR(50) NOT NULL,
    `expiration_time` TIMESTAMP NOT NULL,
    `token_type` VARCHAR(30) NOT NULL,
    PRIMARY KEY (`token`)
);

CREATE DEFINER=`adminPHYqPlU`@`127.10.186.130` EVENT `verification_tokens_expiration` ON SCHEDULE EVERY 1 WEEK STARTS '2014-09-01 00:00:00' ON COMPLETION PRESERVE ENABLE DO delete from `verification_tokens` where `expiration_time` < CURRENT_TIMESTAMP
