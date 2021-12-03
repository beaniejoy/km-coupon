CREATE TABLE `coupon` (
    `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '쿠폰 ID',
    `use_min_amount` decimal(10,2) NOT NULL COMMENT '최소 사용가능 상품 금액',
    `discount_amount` decimal(10,2) NOT NULL COMMENT '할인 금액                               ',
    `usable_from` datetime NOT NULL COMMENT '쿠폰 사용 가능일시',
    `usable_until` datetime NOT NULL COMMENT '쿠폰 사용 만료일시',
    `status` varchar(20) NOT NULL COMMENT '쿠폰 상태(NORMAL:사용가능, USED:사용됨)',
    `created_at` datetime NOT NULL COMMENT '쿠폰 생성일시',
    `created_by` varchar(20) NOT NULL COMMENT '쿠폰 생성주체',
    `updated_at` datetime COMMENT '쿠폰 내역 변경일시',
    `updated_by` varchar(20) COMMENT '쿠폰 내역 변경주체',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;