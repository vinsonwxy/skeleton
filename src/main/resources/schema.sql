DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS receipts;

CREATE TABLE receipts (
  id INT UNSIGNED AUTO_INCREMENT,
  uploaded TIME DEFAULT CURRENT_TIME(),
  merchant VARCHAR(255),
  amount DECIMAL(12,2),
  receipt_type INT UNSIGNED,

  PRIMARY KEY (id)
);

CREATE TABLE tags (
  id INT UNSIGNED AUTO_INCREMENT,
  name VARCHAR(255),

  PRIMARY KEY (id)
);

CREATE TABLE receipts_tags (
  id INT,
  merchant VARCHAR(255),
  amount DECIMAL(12,2),
  name VARCHAR(255)
)