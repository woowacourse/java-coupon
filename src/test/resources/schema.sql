DROP TABLE IF EXISTS MemberCoupon;
DROP TABLE IF EXISTS Coupon;
DROP TABLE IF EXISTS Member;

CREATE TABLE Coupon (
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        amount INTEGER,
                        discountRate INTEGER,
                        end DATE,
                        price INTEGER,
                        start DATE,
                        name VARCHAR(255),
                        category ENUM('FASHION', 'ELECTRONICS', 'FURNITURE', 'FOOD') NOT NULL,
                        PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE Member (
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        name VARCHAR(255) NOT NULL,
                        PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE MemberCoupon (
                              id BIGINT NOT NULL AUTO_INCREMENT,
                              isUsed BIT NOT NULL,
                              issuedAt DATE NOT NULL,
                              coupon_id BIGINT NOT NULL,
                              member_id BIGINT NOT NULL,
                              PRIMARY KEY (id),
                              FOREIGN KEY (coupon_id) REFERENCES Coupon (id),
                              FOREIGN KEY (member_id) REFERENCES Member (id)
) ENGINE=InnoDB;
