# ActiveMQ 
-> make sure that old subscribers are removed by setting timeout for offline durable subscriber:
```xml
    <broker xmlns="http://activemq.apache.org/schema/core" 
            brokerName="localhost" 
            dataDirectory="${activemq.data}" 
            offlineDurableSubscriberTimeout="600000" 
            offlineDurableSubscriberTaskSchedule="300000"
>
    </broker>
```