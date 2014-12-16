REGISTER /home/hduser/Hadoop_learning_path/oozie/pig/dtudf/dtudf.jar;
A = LOAD '/home/hduser/Hadoop_learning_path/oozie/pig/dtudf/dump.dat'  USING PigStorage(',') as ( street_nm,city,zip,st,bed,bath,sqft,rtype,saledt: chararray,pr,lat,ln);  
A1= FOREACH A GENERATE  ln;
A2= FOREACH A GENERATE saledt;
A3= FOREACH A GENERATE street_nm,city,zip,st,bed,sqft,rtype,dtudf.ConvertDateFrmt(saledt),pr,lat,ln;
A4= LIMIT A3 5;
DUMP A4;
