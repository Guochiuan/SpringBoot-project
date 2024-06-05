
### start docker application on your computer

### cd into the root folder DroneDelivery
`cd DroneDelivery`

### make sure you can see the Dockerfile
`ls`

### create a network
`docker network create dronedelivery-mysql`

### pull the mysql image if you are using windows
`docker pull mysql`

#### **if you are using Mac(not recommanded),use the following command to pull mysql instead
`docker pull --platform linux/x86_64 mysql`

### start the mysql container
`docker run --name mysql-standalone --network dronedelivery-mysql -e MYSQL_ALLOW_EMPTY_PASSWORD=true -e MYSQL_DATABASE=drone_delivery -d mysql:latest`

### make sure the container is running
`docker ps`

###build the spring boot app image
`docker build -t dronedelivery-docker-image:latest .`

###cd to the upper folder make sure you can see schema.sql
`cd ..`

###import schema to mysql container, if this fails with error saying no drone_delivery database found, or saying  Can't connect to local MySQL server through socket '/var/run/mysqld/mysqld.sock', just run the same command again.
`docker exec -i mysql-standalone mysql -uroot drone_delivery < schema.sql`

#### **if you meet problem saying '<' operator is reserved, run the following
`cmd /c 'docker exec -i mysql-standalone mysql -uroot drone_delivery < schema.sql'`

### start the drone delivery app with mysql
`docker run --network dronedelivery-mysql --name dronedelivery-docker-container -p 8080:8080 -d dronedelivery-docker-image`

### go to `localhost:8080` to check out our app

### We have preloaded the following users in the database, please feel free to try using these credentials
- Administrator \
Username: admin <br>
Password: admin

- Store Manger \
Username: manager1 <br>
Password: manager1 <br>
\
Username: manager2 <br>
Password: manager2 <br>
<br>
Username: manager3 <br>
Password: manager3 <br>
<br>
Username: manager4 <br>
Password: manager4 <br>

- Customer\
Username: customer1 <br>
Password: customer1<br>
<br>
Username: customer2 <br>
Password: customer2<br>
<br>
Username: customer3 <br>
Password: customer3<br>
<br>

- Pilot \
Username: pilot1 <br>
Password: pilot1 <br>
\
Username: pilot2 <br>
Password: pilot2 <br>
<br>
Username: pilot3 <br>
Password: pilot3 <br>
<br>
Username: pilot4 <br>
Password: pilot4 <br>
