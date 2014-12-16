REGISTER /home/hduser/Hadoop_learning_path/oozie/pig/dtudf/dtudf.jar;
A = LOAD '$INPUT/part-m-00000'  USING PigStorage(',') as ( street_nm,city,zip,st,bed,bath,sqft,rtype,saledt: chararray,pr,lat,ln);  
A1= FOREACH A GENERATE  ln;
A2= FOREACH A GENERATE saledt;
A3= FOREACH A GENERATE street_nm,city,zip,st,bed,sqft,rtype,dtudf.ConvertDateFrmt(saledt),pr,lat,ln;
STORE A3 INTO '$OUTPUT';
