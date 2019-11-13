CREATE TABLE IF NOT EXISTS `articles`
(
    `id`        INT(11)      NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(50)  NOT NULL,
    `slug`  VARCHAR(50) NOT NULL,
    `content`     VARCHAR(255) NOT NULL,
    `published`   TINYINT,
    `createdAt` DATETIME DEFAULT current_timestamp(),
    `updatedAt` DATETIME DEFAULT current_timestamp(),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
