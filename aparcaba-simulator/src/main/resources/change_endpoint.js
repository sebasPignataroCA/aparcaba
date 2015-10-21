db.getCollection('sensor').update(
    // query 
    {
        "covered" : true
    },
    
    // update 
    {
        $set:{
            "covered" : false
        }
    },
    
    // options 
    {
        "multi" : true,  // update only one document 
        "upsert" : false  // insert a new document, if no existing document match the query 
    }
);