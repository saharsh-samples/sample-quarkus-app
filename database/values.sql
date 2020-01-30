-- Create the vals database
CREATE DATABASE valsdb;

-- Create the vals user with default password
CREATE USER valsuser IDENTIFIED BY 'password';

-- Make vals user admin of vals database
GRANT ALL PRIVILEGES ON valsdb.* TO 'valsuser';

-- Switch to vals database to create the schema
USE valsdb;

-- vals table

CREATE TABLE vals (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  value VARCHAR(255) NOT NULL,
  date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

