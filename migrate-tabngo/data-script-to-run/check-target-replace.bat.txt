cd C:\Users\Administrator\Desktop\backup

java -jar migrate-tabngo.jar mongo-dsum -show true  -db TabnGo   -out ./out-check4a.txt  

java -jar migrate-tabngo.jar mongo-replace -show true  -db TabnGo   -out $/out-dump.txt  -rfrom http://192.168.100.60:7070/   -rto http://this-is-a-very-long-string-to-replace-and-to-repeat.com:7070/
java -jar migrate-tabngo.jar mongo-dsum -show true  -db TabnGo   -out ./out-check4b.txt  

java -jar migrate-tabngo.jar mongo-replace -show true  -db TabnGo   -out $/out-dump.txt  -rto http://192.168.100.60:7070/   -rfrom http://this-is-a-very-long-string-to-replace-and-to-repeat.com:7070/
java -jar migrate-tabngo.jar mongo-dsum -show true  -db TabnGo   -out ./out-check4c.txt  

rem no old value in 4b meaning we are successful in replacement
rem 4a != 4c meaning we lost something during replacement

	
pause