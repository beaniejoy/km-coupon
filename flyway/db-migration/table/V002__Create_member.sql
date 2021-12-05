CREATE TABLE `member` (
    `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '사용자 ID',
    `email` varchar(20) NOT NULL COMMENT '사용자 이메일',
    `password` varchar(500) NOT NULL COMMENT '사용자 비밀번호',
    `role` varchar(20) NOT NULL COMMENT '사용자 권한(ADMIN, USER)',
    `created_at` datetime NOT NULL COMMENT '사용자 계정 생성일시',
    `created_by` varchar(20) NOT NULL COMMENT '사용자 계정 생성주체',
    `updated_at` datetime COMMENT '사용자 계정 변경일시',
    `updated_by` varchar(20) COMMENT '사용자 계정 변경주체',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX `idx_email_id` ON `member`(`email`);