
#1. Creat training:
curl -l -H "Content-type: application/json" -X POST -d '{"name":"test training A name","description":"test training A desc"}' http://localhost:7170/training

#2. Update training
curl -l -H "Content-type: application/json" -X PUT -d '{"name":"test training A name changed","description":"test training A desc"}' http://localhost:7170/training/1

#3. Get one training
curl -i http://localhost:7170/training/1

#4. Get training list
curl -i http://localhost:7170/training/list

#5. Create training group
curl -l -H "Content-type: application/json" -X POST -d '{"name":"test training A group level 1", "subGroups": [{"name":"test training A group level 2"}, {"name":"test training C group level 2"}]}' http://localhost:7170/training/1/group

#5. Update training group
curl -l -H "Content-type: application/json" -X PUT -d '{"name":"test training A group level 1 changed", "subGroups": [{"id":2,"name":"test training A group level 2 changed"}]}' http://localhost:7170/training/1/group/1

#6. Get training group list
curl -i http://localhost:7170/training/1/group/list

#7. Create training class
curl -l -H "Content-type: application/json" -X POST -d '{"name":"test training A class 1"}' http://localhost:7170/training/1/group/2/class

#8. Update training group
curl -l -H "Content-type: application/json" -X PUT -d '{"name":"test training A class 1 changed", "price": 100, "capacity": 50}' http://localhost:7170/training/1/group/2/class/1

#9. Get training class list
curl -i http://localhost:7170/training/1/group/2/class/list

#10. Order request
curl -l -H "Content-type: application/json" -X POST -d '{"items":[{"itemId":1, "itemType":"training"}, {"itemId":1, "itemType":"coupon"}]}' http://localhost:7170/customer/13572169731/order
