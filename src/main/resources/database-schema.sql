create table base(
id INTEGER PRIMARY KEY AUTO_INCREMENT,
name VARCHAR
);

create table zone(
id INTEGER PRIMARY KEY AUTO_INCREMENT,
name VARCHAR,
idBase INTEGER ,
  FOREIGN KEY (idBase) REFERENCES base(id)
);

 create table lubrifiant(
id INTEGER PRIMARY KEY AUTO_INCREMENT,
designation VARCHAR
);

create table engine(
id INTEGER PRIMARY KEY  AUTO_INCREMENT,
type VARCHAR ,
designation VARCHAR ,
startSetupDate TIMESTAMP ,
workingTimeType VARCHAR ,
Frequincy INTEGER,
lubrifiantConsomation VARCHAR ,
idZone INTEGER ,
idLubrifiant INTEGER,

FOREIGN KEY (idZone) REFERENCES zone(id),
FOREIGN KEY (idLubrifiant) REFERENCES lubrifiant(id)
);



CREATE table maintance(
id INTEGER PRIMARY KEY  AUTO_INCREMENT,
maintDate TIMESTAMP,
idEngine INTEGER ,
FOREIGN KEY (idEngine) REFERENCES engine(id)
);


CREATE table workingHours(
id INTEGER PRIMARY KEY  AUTO_INCREMENT,
workingDate TIMESTAMP ,
workingTimeHour INTEGER ,
workingTimeMinute INTEGER ,
workingTimeSecond INTEGER ,
idEngine INTEGER ,
FOREIGN KEY (idEngine) REFERENCES engine(id)
);