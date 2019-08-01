package library;

import java.sql.Time;

public class Reading
{
    
    private Time time;
    private int location;
    private int noOfLanes;
    private int totalNoVehicles;
    private int averageNoVehicles;
    private int averageVelocity;
    
    public Time getTime()
    {
        return time;
    }


    public int getLocation()
    {
        return location;
    }


    public int getNoOfLanes()
    {
        return noOfLanes;
    }


    public int getTotalNoVehicles()
    {
        return totalNoVehicles;
    }


    public int getAverageNoVehicles()
    {
        return averageNoVehicles;
    }


    public int getAverageVelocity()
    {
        return averageVelocity;
    }

 
    
    public Reading()
    {
        time=new Time(0,0,0);
        location=0;
        noOfLanes=0;
        totalNoVehicles=0;
        averageNoVehicles=0;
        averageVelocity=0;
    }

    
    public Reading(Time time, int location, int noOfLanes, int totalNoVehicles, 
                int averageNoVehicles, int averageVelocity)
    {
        this.time = time;
        this.location = location;
        this.noOfLanes = noOfLanes;
        this.totalNoVehicles = totalNoVehicles;
        this.averageNoVehicles = averageNoVehicles;
        this.averageVelocity = averageVelocity;
    }
    
    Reading(Reading that)
{
    this.time = that.time;
    this.location = that.location;
    this.noOfLanes = that.noOfLanes;
    this.totalNoVehicles = that.totalNoVehicles;
    this.averageNoVehicles = that.averageNoVehicles;
    this.averageVelocity = that.averageVelocity;
}
    
    void copyFrom(Reading that)
    {
        this.time = that.time;
        this.location = that.location;
        this.noOfLanes = that.noOfLanes;
        this.totalNoVehicles = that.totalNoVehicles;
        this.averageNoVehicles = that.averageNoVehicles;
        this.averageVelocity = that.averageVelocity;
    }
    
    void copyTo(Reading that)
    {
        that.time = this.time;
        that.location = this.location;
        that.noOfLanes = this.noOfLanes;
        that.totalNoVehicles = this.totalNoVehicles;
        that.averageNoVehicles = this.averageNoVehicles;
        that.averageVelocity = this.averageVelocity;
    }
    
    public String toString()
    {
        return time.toString() + " - " + location + " - " + noOfLanes + " - " + 
                     totalNoVehicles + " - " + averageNoVehicles + " - " + averageVelocity;
    }

}
