# Kalagato Assignment - Toll Application

The entire project has been built as a Springboot Application. 
The application has an embedded H2 Database. 
As mentioned in the problem statement, registration of the 
Toll & Toll Booths have been done through a Runner and we can add any number of Toll's and Toll Booth's to the system without it affecting the Application 

The Toll's have the following ID & description: 
1	Bangalore Toll
2	Hyderabad Toll
3	Mangalore Toll

Each Toll has 3 Individual Toll Booths/ Gates. And their Id's are shown below. These ID's will be necessary to fire/test API's
i.e 

For Toll 1 - Bangalore
9	
10	
11	

For Toll 2 - Hyderbad
15	
16	
17	

For Toll 3 - Mangalore
12	
13	
14	

Based on the Type of Passes: 
SINGLE: 
A Vehicle Will be Allowed to Pass through Only when he Pays at the Toll Booth(1 Visit).

RETURN: 
A Vehicle will be allowed to pass through when he pays and when he returns(2 Visits). These passes are also valid for only 1 day and will be deemed unusable if used after 24 hours

SEVEN_DAY_PASSES:
A Vehicle will be allowed unlimited visits for the next 7 days from purchase of pass.

There is a Cron Job Configured within the Application that will regularly check if a Pass Is invalid and mark it as INACTIVE, after the time frame

## Getting Started
For ease of assessment the project can run as a docker container. 

### For this you will have to run the following steps:
1. Make sure docker Engine is installed in your machine
2. Clone the Repo - ```git clone https://github.com/avinpereira/kala-gato-assignment.git```
3. CD into the directory and build the docker image using - ```docker build -t assignment . ```
4. Run the Docker Container - ``` docker run -p 8080:8080 assignment```

Application should now be running on Port 8080


# API:

### There are two main API Controllers
1. Movement Controller - API's to Check Vehicle Eligibility or Create New Toll Subscription
2. LeaderBoard Controller - Gives Data on Max Charges per Toll Booth & Max Visits per Toll Booth


### Movement Controller 
1. POST- API to Register a New Vehicle of a Particular Type with a Particular Pass
```
curl --location 'localhost:8080/api/movements' \
--header 'Content-Type: application/json' \
--data '{
  "vehicleRegistration": "KA01MV1234",
  "tollBoothId": 9,
  "vehicleType": "FOUR_WHEELER",
  "passType": "RETURN"
}'
```

Here Vehicle Types are: 
1. FOUR_WHEELER
2. TWO_WHEELER

Here are the PassType's :

1. SINGLE
2. RETURN
3. SEVEN_DAY


Toll Booth Id's preregistered are mentioned above. 


2. POST- API to Check If a Vehicle Can Pass through this Toll
```
curl --location 'localhost:8080/api/movements/status' \
--header 'Content-Type: application/json' \
--data '{
  "vehicleRegistration": "KA01MV1234",
  "tollBoothId": 9,
  "vehicleType": "FOUR_WHEELER"
}'
```

#####  Response: 
```
{
    "passExists": true,
    "passValidTill": "2023-07-21T17:40:31.748194",
    "purchasablePasses": null
}
```

##### Response If Vehicle cannot pass through
```
{
    "passExists": false,
    "passValidTill": null,
    "purchasablePasses": {
        "SEVEN_DAY": 2000.0,
        "RETURN": 200.0,
        "SINGLE": 100.0
    }
}
```


### Leaderboard Controller

1. API to Get Toll Booth Leader By Most Revenue
```
curl --location 'localhost:8080/api/boards/toll_booths/charges'
```

##### Response: 
```
[
  {
    "tollBoothId": 9,
    "totalChargeBilled": 200.0
  }
]
```


1. API to Get Toll Booth Leader By Most Visits
```
curl --location 'localhost:8080/api/boards/toll_booths/visits'
```

##### Response: 
```
[
  {
    "tollBoothId": 9,
    "totalVisits": 2
  }
]
```

