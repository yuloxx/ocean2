create database ocean2;

use ocean2;

create table DataSet(
	setID			int primary key auto_increment,
    setName			varchar(20)
    );
    

create table SeaSite(
	siteID			int primary key auto_increment,
    setID			int,
    siteName		varchar(41) not null,
    lat				float,
    lon				float
    );
    

create table sstObv(
	sstObvID		int primary key auto_increment,
    siteID			int not null,
    `date`			date,
	maxSst			float,
    minSst			float,
	avgSst			float
    );
    
    
