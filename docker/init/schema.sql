CREATE TABLE IF NOT EXISTS member (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS coupon (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(30) NOT NULL,
                        issuer BIGINT NOT NULL,
                        discount_amount DECIMAL(19, 2),
                        minimum_order_amount DECIMAL(19, 2),
                        category VARCHAR(50),
                        issue_at DATETIME(6),
                        expired_at DATETIME(6),
                        created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP,
                        modified_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        CONSTRAINT fk_coupon_issuer FOREIGN KEY (issuer) REFERENCES member(id)
);
