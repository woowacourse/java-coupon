create DATABASE IF NOT EXISTS coupon;

use coupon;

CREATE TABLE Coupon
(
    id                 BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name               VARCHAR(30) NOT NULL,
    minimumOrderAmount INT         NOT NULL,
    discountAmount     INT         NOT NULL,
    discountRate       INT         NOT NULL,
    category           VARCHAR(30),
    issueStartDate     DATETIME,
    issueEndDate       DATETIME
);

