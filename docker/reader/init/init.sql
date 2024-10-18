CREATE TABLE Category (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL
);

CREATE TABLE Member (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL
);

CREATE TABLE Coupon (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        minimum_amount BIGINT NOT NULL,
                        discount_amount BIGINT NOT NULL,
                        start_issue_date DATE NOT NULL,
                        end_issue_date DATE NOT NULL,
                        category_id BIGINT NOT NULL,
                        CONSTRAINT fk_coupon_category FOREIGN KEY (category_id) REFERENCES Category(id)
);

CREATE TABLE MemberCoupon (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              coupon_id BIGINT NOT NULL,
                              member_id BIGINT NOT NULL,
                              is_used BOOLEAN NOT NULL DEFAULT FALSE,
                              issue_date DATE NOT NULL,
                              CONSTRAINT fk_membercoupon_coupon FOREIGN KEY (coupon_id) REFERENCES Coupon(id),
                              CONSTRAINT fk_membercoupon_member FOREIGN KEY (member_id) REFERENCES Member(id)
);
