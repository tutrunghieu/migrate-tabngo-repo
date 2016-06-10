cd C:\Users\Administrator\Desktop\backup

"C:\Program Files\MongoDB\Server\3.2\bin\mongorestore"   -d  TabnGo   ./TabnGo

java -jar migrate-tabngo.jar war-sumtar -show true   -war ./migrate-tabngo.jar   -cmp ./out-check1.txt     -out  ./out-done1.txt 

java -jar migrate-tabngo.jar folder-sumtar -show true   -in ./upanhtest   -cmp ./out-check2.txt    -out  ./out-done2.txt 

java -jar migrate-tabngo.jar mongo-sumtar -show true  -db TabnGo   -cmp ./out-check3.txt   -out ./out-done3.txt
	
pause