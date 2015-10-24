db.configuration.insert(
{
    "cycleInterval" : 10,
    "apiUri" : "http://api-aparcaba.rhcloud.com/rest/admin/sensors/",
    "comunes" : [ 
        {
            "firstSensorId" : 1,
            "lastSensorId" : 84,
            "timeLapses" : [ 
                {
                    "dateFrom" : ISODate("2015-07-28T21:59:34.886Z"),
                    "dateTo" : ISODate("2016-07-28T21:59:34.886Z"),
                    "numberOfSensorsToTake" : 7,
                    "numberOfTriesToTakeSensor" : 4,
                    "parkingStayTimeFrom" : 15,
                    "parkingStayTimeTo" : 30
                }
            ]
        }
    ]
});
/*
{
    "cycleInterval": 10,
    "apiUri": "http://localhost:5050/api-1.0/rest/admin/sensors/",
    "comunes": [
        {
            "firstSensorId": 1,
            "lastSensorId": 99,
            "timeLapses": [
                {
                    "dateFrom": ISODate("2015-07-28T21:59:34.886Z"),
                    "dateTo": ISODate("2015-07-28T21:59:34.886Z"),
                    "numberOfSensorsToTake": 1,
                    "numberOfTriesToTakeSensor": 2,
                    "parkingStayTimeFrom": 3,
                    "parkingStayTimeTo": 4
                },
                {
                    "dateFrom": ISODate("2015-07-28T21:59:34.886Z"),
                    "dateTo": ISODate("2015-07-28T21:59:34.886Z"),
                    "numberOfSensorsToTake": 5,
                    "numberOfTriesToTakeSensor": 6,
                    "parkingStayTimeFrom": 7,
                    "parkingStayTimeTo": 8
                }
            ]
        },
        {
            "firstSensorId": 100,
            "lastSensorId": 199,
            "timeLapses": [
                {
                    "dateFrom": ISODate("2015-07-28T21:59:34.886Z"),
                    "dateTo": ISODate("2015-07-28T21:59:34.886Z"),
                    "numberOfSensorsToTake": 1,
                    "numberOfTriesToTakeSensor": 2,
                    "parkingStayTimeFrom": 3,
                    "parkingStayTimeTo": 4
                },
                {
                    "dateFrom": ISODate("2015-07-28T21:59:34.886Z"),
                    "dateTo": ISODate("2015-07-28T21:59:34.886Z"),
                    "numberOfSensorsToTake": 5,
                    "numberOfTriesToTakeSensor": 6,
                    "parkingStayTimeFrom": 7,
                    "parkingStayTimeTo": 8
                }
            ]
        }
    ]
}
*/