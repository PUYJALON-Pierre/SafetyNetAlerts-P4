# SafetyNet Alerts :

Rest API develop in Java. 

The aim is to manage informations about inhabitants and send them to emergency services when needed, in order to prevent emergencies situations.

-------------------------------------------------------------------------------------------------------------------------------------

## API Configuration :

- Java 17 
- Maven 3.8.7 
- Spring Boot 3.0.2
- Maven dependencies : Jsoniter (0.9.19) / Lombok (1.18.20) / Surefire(3.0.0) / Jacoco(0.8.8) / log4j(1.2.17) /

- Properties in : src/main/resources/application.properties
- Server port 8080 (http://localhost:8080)

- No database required, data informations are extract from a JSON file located in: src/main/resources/data/data.json, and then stock into objects.

-------------------------------------------------------------------------------------------------------------------------------------

## Getting Started :

-> Copy project from Github on your local machine

-> Go to the root of the application and execute mvn spring-boot:run

-> The server port is 8080

-> Endpoints can be use with Postman

-> Tests can be run with Maven

-------------------------------------------------------------------------------------------------------------------------------------

## CRUD Endpoints :

  *-> (For person)*

- __POST__ http://localhost:8080/person (add a person)

- __PUT__ http://localhost:8080/person (update a person by firstname and lastname)

- __DELETE__ http://localhost:8080/person (delete a person by firstname and lastname)

- __GET__ http://localhost:8080/persons (get all persons)

  *-> (For fireStation)*

- __POST__ http://localhost:8080/firestation (add a firestation)

- __PUT__ http://localhost:8080/firestation (update a firestation number)

- __DELETE__ http://localhost:8080/firestation (delete a firestation)

- __GET__ http://localhost:8080/firestations (get all firestations)

  *->(For medicalRecord)*

- __POST__ http://localhost:8080/medicalRecord (add a medicalRecord)

- __PUT__ http://localhost:8080/medicalRecord (update a medicalRecord by firstname and lastname)

- __DELETE__ http://localhost:8080/medicalRecord (delete a medicalRecord by firstname and lastname)

- __GET__ http://localhost:8080/medicalRecords (get all medicalRecords)

-------------------------------------------------------------------------------------------------------------------------------------

## Specifics Endpoints :

- __GET__ http://localhost:8080/firestation?stationNumber={station_number} (return list of person covered by a fireStation with count of adult and children)

- __GET__ http://localhost:8080/childAlert?address={address} (return list of children living at an address with persons living at same address)

- __GET__ http://localhost:8080/phoneAlert?firestation={firestation_number} (return list of person's phonenumbers covered by a firestation)

- __GET__ http://localhost:8080/fire?address={address} (return list of persons living at an address with station number desserving this address)

- __GET__ http://localhost:8080/flood/stations?stations={list_of_station_numbers} (return list of addresses deserved by a list of firestation)

- __GET__ http://localhost:8080/personInfo?firstName={firstName}&lastName={lastName} (return specifics informations of all inhabitants)

- __GET__ http://localhost:8080/communityEmail?city={city} (return list of emails of all the habitants by city)

-------------------------------------------------------------------------------------------------------------------------------------

## Actuator (GET) :

__-INFO__ : http://localhost:8080/actuator/info

__-HEALTH__ : http://localhost:8080/actuator/health

__-TRACE__ : http://localhost:8080/actuator/httpexchanges

__-METRICS__ : http://localhost:8080/actuator/metrics

-------------------------------------------------------------------------------------------------------------------------------------

## JSON Body :

 *-> (For person)*

 { "firstName":"John", 
"lastName":"Boyd", 
"address":"1509 Culver St",
"city":"Culver", 
"zip":"97451", 
"phone":"841-874-6512", 
"email":"jaboyd@email.com" }


  *-> (For fireStation)*

{ "address":"1509 Culver St", "station":"3" }


 *->(For medicalRecord)*

 { "firstName":"John", 
"lastName":"Boyd", 
"birthdate":"03/06/1984", 
"medications":["aznol:350mg", 
"hydrapermazol:100mg"], 
"allergies":["nillacilan"] }


-------------------------------------------------------------------------------------------------------------------------------------

