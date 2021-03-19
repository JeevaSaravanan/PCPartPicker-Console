CREATE TABLE `cabinets` (
  `PID` varchar(45) COLLATE utf8_bin NOT NULL,
  `BRAND` int NOT NULL,
  `MODEL` varchar(45) COLLATE utf8_bin NOT NULL,
  `RATE` float DEFAULT NULL,
  `QTY` int DEFAULT NULL,
  `schemaID` int NOT NULL,
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PID_UNIQUE` (`PID`),
  KEY `CabBrand_idx` (`BRAND`),
  KEY `schemaId_idx` (`schemaID`),
  CONSTRAINT `CabBrand` FOREIGN KEY (`BRAND`) REFERENCES `vendor_table` (`ID`)
);

CREATE TABLE `compatible_board` (
  `compatibleID` int NOT NULL AUTO_INCREMENT,
  `GID` int NOT NULL,
  `SeriesID` int NOT NULL,
  PRIMARY KEY (`compatibleID`),
  UNIQUE KEY `compatibleID_UNIQUE` (`compatibleID`),
  KEY `Gen_ID_idx` (`GID`),
  KEY `SeriesID_idx` (`SeriesID`),
  CONSTRAINT `Gen_ID` FOREIGN KEY (`GID`) REFERENCES `gen_type` (`GID`),
  CONSTRAINT `SeriesID` FOREIGN KEY (`SeriesID`) REFERENCES `motherboard_series` (`SeriesID`)
);

CREATE TABLE `cpu` (
  `PID` varchar(45) COLLATE utf8_bin NOT NULL,
  `MODEL` varchar(45) COLLATE utf8_bin NOT NULL,
  `DETAILS` varchar(100) COLLATE utf8_bin NOT NULL,
  `RATE` float DEFAULT NULL,
  `QTY` int DEFAULT NULL,
  `GC` tinyint NOT NULL,
  `GID` int NOT NULL,
  `schemaID` int NOT NULL,
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PID_UNIQUE` (`PID`),
  KEY `GGID_idx` (`GID`),
  KEY `schemaid_idx` (`schemaID`),
  CONSTRAINT `GGID` FOREIGN KEY (`GID`) REFERENCES `gen_type` (`GID`)
) ;

CREATE TABLE `gen_type` (
  `GID` int NOT NULL AUTO_INCREMENT,
  `GEN/SERIES` varchar(45) COLLATE utf8_bin NOT NULL,
  `CID` int NOT NULL,
  PRIMARY KEY (`GID`),
  UNIQUE KEY `ID_UNIQUE` (`GID`),
  UNIQUE KEY `GEN/SERIES_UNIQUE` (`GEN/SERIES`),
  KEY `CCGID_idx` (`CID`),
  CONSTRAINT `GCID` FOREIGN KEY (`CID`) REFERENCES `vendor_table` (`ID`)
);

CREATE TABLE `graphic_card` (
  `PID` varchar(45) COLLATE utf8_bin NOT NULL,
  `BRAND` int NOT NULL,
  `GPU_CHIP` varchar(45) COLLATE utf8_bin NOT NULL,
  `MODEL` varchar(45) COLLATE utf8_bin NOT NULL,
  `RATE` float DEFAULT NULL,
  `QTY` int DEFAULT NULL,
  `schemaID` int DEFAULT NULL,
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PID_UNIQUE` (`PID`),
  KEY `GBRAND_idx` (`BRAND`),
  KEY `schemaidd_idx` (`schemaID`),
  CONSTRAINT `GBRAND` FOREIGN KEY (`BRAND`) REFERENCES `vendor_table` (`ID`),
  CONSTRAINT `schemaidd` FOREIGN KEY (`schemaID`) REFERENCES `schema_details` (`ID`)
) ;

CREATE TABLE `memory_module` (
  `PID` varchar(45) COLLATE utf8_bin NOT NULL,
  `BRAND` int NOT NULL,
  `MODEL` varchar(45) COLLATE utf8_bin NOT NULL,
  `RATE` float DEFAULT NULL,
  `QTY` int DEFAULT NULL,
  `schemaID` int DEFAULT NULL,
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PID_UNIQUE` (`PID`),
  KEY `MEMORYBRAND_idx` (`BRAND`),
  KEY `schemaD_idx` (`schemaID`),
  CONSTRAINT `MEMORYBRAND` FOREIGN KEY (`BRAND`) REFERENCES `vendor_table` (`ID`),
  CONSTRAINT `schemaD` FOREIGN KEY (`schemaID`) REFERENCES `schema_details` (`ID`)
);

CREATE TABLE `mother_board` (
  `PID` varchar(45) COLLATE utf8_bin NOT NULL,
  `BRAND` int NOT NULL,
  `MODEL` varchar(45) COLLATE utf8_bin NOT NULL,
  `SeriesID` int NOT NULL,
  `RATE` float DEFAULT NULL,
  `QTY` int DEFAULT NULL,
  `schemaID` int NOT NULL,
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PID_UNIQUE` (`PID`),
  KEY `MSID_idx` (`SeriesID`),
  KEY `MBRAND_idx` (`BRAND`),
  KEY `schemaaId_idx` (`schemaID`),
  CONSTRAINT `MBRAND` FOREIGN KEY (`BRAND`) REFERENCES `vendor_table` (`ID`),
  CONSTRAINT `MSID` FOREIGN KEY (`SeriesID`) REFERENCES `motherboard_series` (`SeriesID`)
);

CREATE TABLE `motherboard_series` (
  `SeriesID` int NOT NULL AUTO_INCREMENT,
  `Series` varchar(45) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`SeriesID`),
  UNIQUE KEY `ID_UNIQUE` (`SeriesID`),
  UNIQUE KEY `Series_UNIQUE` (`Series`)
);

CREATE TABLE `order` (
  `OrderID` varchar(45) COLLATE utf8_bin NOT NULL,
  `userID` varchar(45) COLLATE utf8_bin NOT NULL,
  `Total` int NOT NULL,
  PRIMARY KEY (`OrderID`),
  UNIQUE KEY `OrderID_UNIQUE` (`OrderID`),
  KEY `UserID_IDX` (`userID`),
  CONSTRAINT `UserID` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`)
);
CREATE TABLE `order_details` (
  `OrderDetailsID` varchar(45) COLLATE utf8_bin NOT NULL,
  `OrderID` varchar(45) COLLATE utf8_bin NOT NULL,
  `PID` varchar(45) COLLATE utf8_bin NOT NULL,
  `ProductName` varchar(45) COLLATE utf8_bin NOT NULL,
  `ProductModel` varchar(45) COLLATE utf8_bin NOT NULL,
  `RATE` float NOT NULL,
  `QTY` int NOT NULL,
  PRIMARY KEY (`OrderDetailsID`),
  UNIQUE KEY `OrderDetailsID_UNIQUE` (`OrderDetailsID`),
  KEY `OrderID_IDX` (`OrderID`),
  CONSTRAINT `OrderID` FOREIGN KEY (`OrderID`) REFERENCES `order` (`OrderID`)
);

CREATE TABLE `power_supply` (
  `PID` varchar(45) COLLATE utf8_bin NOT NULL,
  `BRAND` int NOT NULL,
  `MODEL` varchar(45) COLLATE utf8_bin NOT NULL,
  `DETAILS` varchar(45) COLLATE utf8_bin NOT NULL,
  `RATE` float DEFAULT NULL,
  `QTY` int DEFAULT NULL,
  `schemaID` int DEFAULT NULL,
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PID_UNIQUE` (`PID`),
  KEY `PBRAND_idx` (`BRAND`),
  KEY `schemaIDd_idx` (`schemaID`),
  CONSTRAINT `PBRAND` FOREIGN KEY (`BRAND`) REFERENCES `vendor_table` (`ID`),
  CONSTRAINT `schemaIDw` FOREIGN KEY (`schemaID`) REFERENCES `schema_details` (`ID`)
);

CREATE TABLE `schema_details` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `schemaName` varchar(45) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `schemaName_UNIQUE` (`schemaName`)
);

CREATE TABLE `storage` (
  `PID` varchar(45) COLLATE utf8_bin NOT NULL,
  `BRAND` int NOT NULL,
  `MODEL` varchar(45) COLLATE utf8_bin NOT NULL,
  `CAPACITY` varchar(45) COLLATE utf8_bin NOT NULL,
  `RATE` float DEFAULT NULL,
  `QTY` int DEFAULT NULL,
  `SID` int NOT NULL,
  `schemaID` int DEFAULT NULL,
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PID_UNIQUE` (`PID`),
  KEY `SID_idx` (`SID`),
  KEY `SBRAND_idx` (`BRAND`),
  KEY `schemadi_idx` (`schemaID`),
  CONSTRAINT `SBRAND` FOREIGN KEY (`BRAND`) REFERENCES `vendor_table` (`ID`),
  CONSTRAINT `schemadi` FOREIGN KEY (`schemaID`) REFERENCES `schema_details` (`ID`),
  CONSTRAINT `SID` FOREIGN KEY (`SID`) REFERENCES `storage_type` (`SID`)
);

CREATE TABLE `storage_type` (
  `SID` int NOT NULL AUTO_INCREMENT,
  `TYPE` varchar(45) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`SID`),
  UNIQUE KEY `SID_UNIQUE` (`SID`),
  UNIQUE KEY `TYPE_UNIQUE` (`TYPE`)
);

CREATE TABLE `ups` (
  `PID` varchar(45) COLLATE utf8_bin NOT NULL,
  `BRAND` int NOT NULL,
  `MODEL` varchar(45) COLLATE utf8_bin NOT NULL,
  `RATE` float DEFAULT NULL,
  `QTY` int DEFAULT NULL,
  `schemaID` int DEFAULT NULL,
  PRIMARY KEY (`PID`),
  UNIQUE KEY `PID_UNIQUE` (`PID`),
  KEY `UBRAND_idx` (`BRAND`),
  KEY `schemaiddd_idx` (`schemaID`),
  CONSTRAINT `schemaiddd` FOREIGN KEY (`schemaID`) REFERENCES `schema_details` (`ID`),
  CONSTRAINT `UBRAND` FOREIGN KEY (`BRAND`) REFERENCES `vendor_table` (`ID`)
);

CREATE TABLE `user` (
  `userID` varchar(45) COLLATE utf8_bin NOT NULL,
  `FirstName` varchar(45) COLLATE utf8_bin NOT NULL,
  `LastName` varchar(45) COLLATE utf8_bin NOT NULL,
  `Email` varchar(45) COLLATE utf8_bin NOT NULL,
  `psw` varchar(45) COLLATE utf8_bin NOT NULL,
  `phone` varchar(45) COLLATE utf8_bin NOT NULL,
  `Address` varchar(200) COLLATE utf8_bin NOT NULL,
  `State` varchar(45) COLLATE utf8_bin NOT NULL,
  `City` varchar(45) COLLATE utf8_bin NOT NULL,
  `Zip` varchar(45) COLLATE utf8_bin NOT NULL,
  `Country` varchar(45) COLLATE utf8_bin NOT NULL,
  `EmailVerification` tinyint NOT NULL DEFAULT '1',
  `PhoneVerification` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`userID`),
  UNIQUE KEY `userID_UNIQUE` (`userID`),
  UNIQUE KEY `Email_UNIQUE` (`Email`),
  UNIQUE KEY `phone_UNIQUE` (`phone`)
);

CREATE TABLE `vendor_table` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `VendorName` varchar(45) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  UNIQUE KEY `TYPE_UNIQUE` (`VendorName`)
);