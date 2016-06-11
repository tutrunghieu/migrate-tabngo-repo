cd C:\Users\HoangHung\Desktop\backup

"C:\Program Files\MongoDB\Server\3.2\bin\mongodump"   -d  TabnGo  -o .

java -jar migrate-tabngo.jar war-sum  -show true   -war ./migrate-tabngo.jar    -out  ./out-check1.txt 

java -jar migrate-tabngo.jar folder-sum -show true   -in E:\upanhtest   -out  ./out-check2.txt 

java -jar migrate-tabngo.jar mongo-sum -show true  -db TabnGo   -out ./out-check3.txt

	
pause