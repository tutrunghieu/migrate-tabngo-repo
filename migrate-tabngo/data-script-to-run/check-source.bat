cd C:\Users\Administrator\Desktop\migrate-tabngo

"C:\Program Files\MongoDB\Server\3.2\bin\mongodump"   -d  data-trenzi105  -o .

java -jar migrate-tabngo.jar war-sum  -show true   -war ./migrate-tabngo.jar    -out  ./out-check1.txt 
rem java -jar migrate-tabngo.jar war-sumtar -show true   -war ./migrate-tabngo.jar   -cmp ./out-check1.txt     -out  ./out-done1.txt 

java -jar migrate-tabngo.jar folder-sum -show true   -in c:/opt/apps   -out  ./out-check2.txt 
rem java -jar migrate-tabngo.jar folder-sumtar -show true   -in c:/opt/apps   -cmp ./out-check2.txt    -out  ./out-done2.txt 

java -jar migrate-tabngo.jar mongo-sum -show true  -db data-trenzi105   -out ./out-check3.txt
rem java -jar migrate-tabngo.jar mongo-sumtar -show true  -db data-trenzi105   -cmp ./out-check3.txt   -out ./out-done3.txt


	
pause