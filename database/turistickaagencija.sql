/*
SQLyog Community v12.2.4 (64 bit)
MySQL - 5.6.17 : Database - turistickaagencija
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`turistickaagencija` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `turistickaagencija`;

/*Table structure for table `mesto` */

DROP TABLE IF EXISTS `mesto`;

CREATE TABLE `mesto` (
  `Ptt` int(50) NOT NULL,
  `Naziv` varchar(50) NOT NULL,
  PRIMARY KEY (`Ptt`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `mesto` */

insert  into `mesto`(`Ptt`,`Naziv`) values 
(11000,'Beograd'),
(18101,'Niš'),
(21000,'Novi Sad');

/*Table structure for table `putnik` */

DROP TABLE IF EXISTS `putnik`;

CREATE TABLE `putnik` (
  `PutnikID` int(11) NOT NULL AUTO_INCREMENT,
  `Ime` varchar(30) NOT NULL,
  `Prezime` varchar(30) NOT NULL,
  `JMBG` varchar(50) NOT NULL,
  `Kontakt` varchar(30) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `Ulica` varchar(50) NOT NULL,
  `Broj` int(50) NOT NULL,
  `Ptt` int(50) NOT NULL,
  PRIMARY KEY (`PutnikID`),
  KEY `ptt_fk` (`Ptt`),
  CONSTRAINT `ptt_fk` FOREIGN KEY (`Ptt`) REFERENCES `mesto` (`Ptt`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

/*Data for the table `putnik` */

insert  into `putnik`(`PutnikID`,`Ime`,`Prezime`,`JMBG`,`Kontakt`,`Email`,`Ulica`,`Broj`,`Ptt`) values 
(12,'Marko','Markovic','1234567891234','6455555555','marko@gmail.com','Beogradska',45,11000),
(13,'Ivan','Ivanovic','2345678912345','6165648','ivani@gmail.com','Francuska',55,11000),
(15,'Mara','Maric','1234567981234','213665478','masam@gmail.com','Makedonska',4,11000),
(17,'Jovana','Jovanovic','1234567894567','658879654','jovanaj@gmail.com','Vojvode Stepe',148,11000),
(20,'Pera','Peric','7894561233216','615469875','perap@gmail.com','Vitanovacka',33,11000);

/*Table structure for table `putovanje` */

DROP TABLE IF EXISTS `putovanje`;

CREATE TABLE `putovanje` (
  `PutovanjeID` int(50) NOT NULL,
  `Naziv` varchar(50) NOT NULL,
  `Polaziste` varchar(50) NOT NULL,
  `Odrediste` varchar(50) NOT NULL,
  `DatumOD` date NOT NULL,
  `DatumDO` date NOT NULL,
  `Cena` double NOT NULL,
  PRIMARY KEY (`PutovanjeID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `putovanje` */

insert  into `putovanje`(`PutovanjeID`,`Naziv`,`Polaziste`,`Odrediste`,`DatumOD`,`DatumDO`,`Cena`) values 
(1,'Italija-Venecija','Beograd','Venecija','2016-10-01','2016-10-10',150),
(2,'Italija-Rim','Beograd','Rim','2016-09-11','2016-09-18',170),
(3,'Španija-Barselona','Beograd','Barselona','2016-09-15','2016-09-22',300),
(4,'Španija-Madrid','Beograd','Madrid','2016-09-27','2016-10-06',290),
(5,'Francuska-Pariz','Beograd','Pariz','2016-09-23','2016-09-30',270),
(6,'Nemacka-Berlin','Beograd','Berlin','2016-09-30','2016-10-07',270);

/*Table structure for table `rezervacija` */

DROP TABLE IF EXISTS `rezervacija`;

CREATE TABLE `rezervacija` (
  `RezervacijaID` int(50) NOT NULL,
  `PutnikID` int(50) NOT NULL,
  `ZaposleniID` int(50) NOT NULL,
  PRIMARY KEY (`RezervacijaID`),
  KEY `zaposleni_fk` (`ZaposleniID`),
  KEY `putnik_fk` (`PutnikID`),
  CONSTRAINT `putnik_fk` FOREIGN KEY (`PutnikID`) REFERENCES `putnik` (`PutnikID`),
  CONSTRAINT `zaposleni_fk` FOREIGN KEY (`ZaposleniID`) REFERENCES `zaposleni` (`ZaposleniID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `rezervacija` */

insert  into `rezervacija`(`RezervacijaID`,`PutnikID`,`ZaposleniID`) values 
(2,12,1),
(4,13,1),
(6,12,1),
(8,12,1),
(9,13,1),
(10,15,1);

/*Table structure for table `stavkarezervacije` */

DROP TABLE IF EXISTS `stavkarezervacije`;

CREATE TABLE `stavkarezervacije` (
  `RezervacijaID` int(50) NOT NULL,
  `RedniBroj` int(50) NOT NULL,
  `DatumRezervacije` date NOT NULL,
  `DatumVazenjaRezervacije` date NOT NULL,
  `AktivnaRezervacija` varchar(2) NOT NULL DEFAULT 'ne',
  `DatumPlacanja` date NOT NULL,
  `PutovanjeID` int(50) NOT NULL,
  PRIMARY KEY (`RezervacijaID`,`RedniBroj`),
  KEY `RedniBroj` (`RedniBroj`),
  KEY `putovanje_fk` (`PutovanjeID`),
  CONSTRAINT `putovanje_fk` FOREIGN KEY (`PutovanjeID`) REFERENCES `putovanje` (`PutovanjeID`),
  CONSTRAINT `rezervacija_fk` FOREIGN KEY (`RezervacijaID`) REFERENCES `rezervacija` (`RezervacijaID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `stavkarezervacije` */

insert  into `stavkarezervacije`(`RezervacijaID`,`RedniBroj`,`DatumRezervacije`,`DatumVazenjaRezervacije`,`AktivnaRezervacija`,`DatumPlacanja`,`PutovanjeID`) values 
(2,1,'2016-09-09','2016-09-20','da','2016-09-10',4),
(4,1,'2016-09-10','2016-09-20','da','2016-09-20',1),
(6,1,'2016-09-11','2016-09-20','da','2016-09-19',1),
(6,2,'2016-09-11','2016-09-14','da','2016-09-13',3),
(8,1,'2016-09-12','2016-09-28','da','2016-09-28',1),
(9,1,'2016-09-13','2016-09-27','da','2016-09-14',1),
(10,1,'2016-09-15','2016-09-30','da','2016-09-16',1);

/*Table structure for table `zaposleni` */

DROP TABLE IF EXISTS `zaposleni`;

CREATE TABLE `zaposleni` (
  `ZaposleniID` int(50) NOT NULL,
  `KorisnickoIme` varchar(50) NOT NULL,
  `KorisnickaSifra` varchar(50) NOT NULL,
  `Ulogovan` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ZaposleniID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `zaposleni` */

insert  into `zaposleni`(`ZaposleniID`,`KorisnickoIme`,`KorisnickaSifra`,`Ulogovan`) values 
(1,'admin','admin',0),
(2,'jovana','jovana',0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
