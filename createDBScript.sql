DROP DATABASE IF EXISTS Think_a_bitDB;
CREATE DATABASE Think_a_bitDB;
USE Think_a_bitDB;

CREATE TABLE Users (
  Id INTEGER NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
  UserName VARCHAR(64) NOT NULL,
  Pass TEXT NOT NULL
);

CREATE TABLE Question (
  Id INTEGER NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
  Type ENUM ('CLOSED_ONE', 'CLOSED_MANY', 'OPEN') NOT NULL,
  Title TEXT NOT NULL
);

CREATE TABLE Categories (
  Id INTEGER NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
  Name TEXT NOT NULL
);

CREATE TABLE Answers (
  Id INTEGER NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
  QuestionId INTEGER NOT NULL,
  Payload TEXT NOT NULL,
  IsCorrect BOOLEAN NOT NULL DEFAULT FALSE,

  FOREIGN KEY (QuestionId) REFERENCES Question(Id)
);

CREATE TABLE UserProgress (
  UserId INTEGER NOT NULL,
  CategoryId INTEGER NOT NULL,
  ReachedStage INTEGER NOT NULL DEFAULT 0,

  PRIMARY KEY (UserId, CategoryId),
  FOREIGN KEY (UserId) REFERENCES Users(Id),
  FOREIGN KEY (CategoryId) REFERENCES Categories(Id)
);

CREATE TABLE QuestionCategories (
  QuestionId INTEGER NOT NULL,
  CategoryId INTEGER NOT NULL,

  PRIMARY KEY (QuestionId, CategoryId),
  FOREIGN KEY (QuestionId) REFERENCES Question(Id),
  FOREIGN KEY (CategoryId) REFERENCES Categories(Id)
);

CREATE TABLE Stages (
  Id INTEGER NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
  CategoryId INTEGER NOT NULL,
  Number INTEGER NOT NULL,
  FOREIGN KEY (CategoryId) REFERENCES Categories(Id)
);

CREATE TABLE StageAttempts (
  StageId INTEGER NOT NULL,
  UserId INTEGER NOT NULL,
  CategoryId INTEGER NOT NULL,
  Attempts INTEGER NOT NULL DEFAULT 0,

  PRIMARY KEY (StageId, UserId),
  FOREIGN KEY (UserId) REFERENCES Users(Id),
  FOREIGN KEY (StageId) REFERENCES Stages(Id),
  FOREIGN KEY (CategoryId) REFERENCES Categories(Id)
);

CREATE TABLE QuestionStages (
  QuestionId INTEGER NOT NULL,
  StageId INTEGER NOT NULL,

  PRIMARY KEY (QuestionId, StageId),
  FOREIGN KEY (QuestionId) REFERENCES Question(Id),
  FOREIGN KEY (StageId) REFERENCES Stages(Id)
);

CREATE TABLE Sessions (
  UserId INTEGER NOT NULL,
  SessionId INTEGER NOT NULL,
  CreatedAt TIMESTAMP NULL DEFAULT NULL,
  ExpiresAt TIMESTAMP NULL DEFAULT NULL,

  PRIMARY KEY (UserId, SessionId),
  FOREIGN KEY (UserId) REFERENCES Users(Id)
);