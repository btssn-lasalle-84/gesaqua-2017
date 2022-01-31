
CREATE DATABASE IF NOT EXISTS gesaqua;

USE gesaqua;

--------------------------------------------------------------------------

--
-- Structure de la table `appareils`
--

CREATE TABLE IF NOT EXISTS `appareils` (
  `idAppareil` INTEGER PRIMARY KEY NOT NULL ,
  `nom` VARCHAR(45) NULL ,
  `etat`TINYINT(1) NULL ,
  `horodatage` DATETIME ); -- DATETIME : format `YYYY-MM-DD HH:MM:SS`

--
-- Contenu de la table `appareils`
--

INSERT INTO `appareils` VALUES(1,'chauffage',0,'2017-03-24 16:31:10');
INSERT INTO `appareils` VALUES(2,'eclairage',0,'2017-03-24 16:31:20');
INSERT INTO `appareils` VALUES(3,'nourriture',0,'2017-03-24 16:31:20');
INSERT INTO `appareils` VALUES(4,'engrais',0,'2017-03-24 16:31:20');
INSERT INTO `appareils` VALUES(5,'oxygenation',0,'2017-03-24 16:31:20');
INSERT INTO `appareils` VALUES(6,'filtration',0,'2017-03-24 16:31:20');
INSERT INTO `appareils` VALUES(7,'ventilation',0,'2017-03-24 16:31:20');


-----------------------------------------------------------------------------
    

--
-- Structure de la table `capteurs`
--

CREATE TABLE IF NOT EXISTS `capteurs` (
  `idCapteurs` INTEGER PRIMARY KEY NOT NULL ,
  `typeCapteurs`INTEGER NULL ,
  `nom`VARCHAR(45) NULL ,
  `unite` VARCHAR(45) NULL ,
   CONSTRAINT fk_capteurs_1 FOREIGN KEY (typeCapteurs ) REFERENCES typeCapteurs (idtypeCapteurs ) );

--
-- Contenu de la table `capteurs`
--

INSERT INTO `capteurs` VALUES(1,1,'temperature eau','°C');
INSERT INTO `capteurs` VALUES(2,1,'temperature air','°C');
INSERT INTO `capteurs` VALUES(3,2,'ph','');
INSERT INTO `capteurs` VALUES(4,3,'niveau d''eau','cm');


-------------------------------------------------------------------------------


--
-- Structure de la table `parametresVitaux`
--
    
CREATE TABLE IF NOT EXISTS `parametresVitaux` (
  `idParametresVitaux` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,
  `idCapteur` INTEGER NULL ,
  `mesure` DOUBLE NULL ,
  `horodatage` DATETIME, --DATETIME : format `YYYY-MM-DD HH:MM:SS`
 CONSTRAINT fk_parametresVitaux_1 FOREIGN KEY (idCapteur ) REFERENCES capteurs (idCapteurs ));



----------------------------------------------------------------------

   
--
-- Structure de la table `typeCapteurs`
--

CREATE TABLE IF NOT EXISTS `typeCapteurs` (
  `idtypeCapteurs` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,
  `typeCapteurs` VARCHAR(45) NULL );

--
-- Contenu de la table `typeCapteurs`
--

INSERT INTO `typeCapteurs` VALUES(1,'temperature');
INSERT INTO `typeCapteurs` VALUES(2,'ph');
INSERT INTO `typeCapteurs` VALUES(3,'niveau');


----------------------------------------------------------
  
--
-- Structure de la table `consignes`
--

CREATE TABLE IF NOT EXISTS `consignes` (
   `temperatureMin` DOUBLE NULL ,
   `temperatureMax` DOUBLE NULL ,
   `phMin` DOUBLE NULL ,
   `phMax` DOUBLE NULL ,
   `niveauEauMin` DOUBLE NULL );

--
-- Contenu de la table `consignes`
-- 
            

INSERT INTO `consignes` VALUES(24,26,6.8,7.2,34);


-----------------------------------------------------------------
   

--
-- Structure de la table `seuils`
--

CREATE TABLE IF NOT EXISTS `seuils` (
    `temperatureMin` DOUBLE NULL ,
    `temperatureMax` DOUBLE NULL ,
    `phMin` DOUBLE NULL ,
    `phMax` DOUBLE NULL ,
    `niveauEauMin` DOUBLE NULL ,
    `niveauEauMax` DOUBLE NULL );

--
-- Contenu de la table `seuils`
--  
           
INSERT INTO `seuils` VALUES(23,27,5.5,9,20,40);

----------------------------------------------------------------------
 

--
-- Structure de la table `aquariums`
--

CREATE TABLE IF NOT EXISTS `aquariums` (
    `idAquarium` INTEGER PRIMARY KEY NOT NULL ,
    `largeur` DOUBLE NULL ,
    `longueur` DOUBLE NULL ,
    `hauteur` DOUBLE NULL ,
    `volume` DOUBLE NULL ,
    `dateMiseEnEau` DATETIME,  ---DATETIME : format `YYYY-MM-DD HH:MM:SS`
    `type` VARCHAR(50) NOT NULL);

--
-- Contenu de la table `aquariums`
--  
           
                    
INSERT INTO `aquariums` VALUES(1,40,100,50,200,'2017-04-20 14:20:36','amazonien');

--------------------------------------------------------------------------
 

--
-- Structure de la table `alarmes`
--

CREATE TABLE IF NOT EXISTS `alarmes` (
    `idAlarme` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,
    `type` VARCHAR(50) NOT NULL ,
    `mesure` DOUBLE NULL ,
    `horodatage` DATETIME ,   ---DATETIME : format `YYYY-MM-DD HH:MM:SS`
    `seuilMin` DOUBLE NOT NULL ,
    `seuilMax` DOUBLE NOT NULL ,
    `description` VARCHAR(150) );

--
-- Contenu de la table `alarmes`
--                        

INSERT INTO `alarmes` VALUES(1,'pH',6.2,'2017-04-21 14:31:36',6.5,8,'pH trop bas');
INSERT INTO `alarmes` VALUES(2,'niveau',19.8,'2017-04-21 14:31:36',20,40,'niveau : eau trop basse');
INSERT INTO `alarmes` VALUES(3,'temperature',19.3,'2017-04-21 14:31:36',23,27,'température trop basse');


-------------------------------------------------------------------------------


--
-- Structure de la table `modes`
--

CREATE TABLE IF NOT EXISTS `modes` (
 `idMode` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,
 `dateDebut` DATE ,
 `dateFin` DATE ,
 `heureDebut` TIME ,
 `heureFin` TIME ,
 `mode` INTEGER NOT NULL );  --- 0 pour mode manuel et 1 pour mode automatique

--
-- Contenu de la table `modes`
--                        
   
INSERT INTO `modes` VALUES(1,'2017-04-21','2017-04-21','14:31:36','18:43:36',0);
INSERT INTO `modes` VALUES(2,'2017-04-21','2017-04-24','14:31:36','18:43:36',1);

