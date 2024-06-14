use ocean2;


INSERT INTO DataSet(setName) VALUES
('TOC'),
('MOC'),
('DOC');

INSERT INTO SeaSite (setID,siteName, lat, lon) VALUES  
(1,'上海', 31.2304, 121.4737),  
(1,'大连', 38.9126, 121.6186),  
(1,'宁波', 29.8652, 121.5504);

INSERT INTO ObvData (siteID, `date`, maxSst, minSst, avgSst) VALUES  
(1, '2023-01-01', 25.5, 22.0, 23.8),  
(1, '2023-01-02', 26.0, 22.5, 24.2),  
(2, '2023-01-01', 24.0, 20.5, 22.3),  
(2, '2023-01-03', 24.5, 21.0, 22.8),  
(3, '2023-01-02', 27.0, 23.5, 25.2),  
(3, '2023-01-04', 26.5, 23.0, 24.8);



