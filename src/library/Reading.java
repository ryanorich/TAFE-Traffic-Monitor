package library;

import java.sql.Time;

/**
 * Class containing the Reading data from traffic monitoring stations.
 * 
 * @author ryan
 *
 */
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
    
    /**
     * Creates a blank reading object, with all parameters set to 0
     */
    @SuppressWarnings("deprecation")
    public Reading()
    {
        // TODO - Re-Implement the depreciated time constructor
        time=new Time(0,0,0);
        location=0;
        noOfLanes=0;
        totalNoVehicles=0;
        averageNoVehicles=0;
        averageVelocity=0;
    }

    /**
     * Cretes a new Reading object using provided data
     * 
     * @param time              The time of the readings
     * @param location          The number of the reading station
     * @param noOfLanes         The number of lanes
     * @param totalNoVehicles   The total number of recored vehicles
     * @param averageNoVehicles The average number of vehicles per lane (Typically (totalNoVehicles) / (noOfLanes)
     * @param averageVelocity   The average velocity of all vehicles
     */
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
    
    @SuppressWarnings("deprecation")
    public Reading(String reading)
    {
        String parts[] = reading.split(",");
        if (parts.length != 6)
            return;
        String timeParts[] = parts[0].split(":");
        if (timeParts.length !=3)
            return;
        
        time = new Time(Integer.parseInt(timeParts[0]), 
                        Integer.parseInt(timeParts[1]), 
                        Integer.parseInt(timeParts[2]));
        location = Integer.parseInt(parts[1]);
        noOfLanes = Integer.parseInt(parts[2]);
        totalNoVehicles = Integer.parseInt(parts[3]);
        averageNoVehicles = Integer.parseInt(parts[4]);
        averageVelocity = Integer.parseInt(parts[5]);
    }
    
    /**
     * Creates a new reading that is a copy of the one passed.
     * 
     * @param that  The reading to copy data from.
     */
    Reading(Reading that)
{
    this.time = that.time;
    this.location = that.location;
    this.noOfLanes = that.noOfLanes;
    this.totalNoVehicles = that.totalNoVehicles;
    this.averageNoVehicles = that.averageNoVehicles;
    this.averageVelocity = that.averageVelocity;
}
    
    /**
     * Sets the reading data be a copy of the one passed.
     * 
     * @param that The reading to copy data from.
     */
    void copyFrom(Reading that)
    {
        this.time = that.time;
        this.location = that.location;
        this.noOfLanes = that.noOfLanes;
        this.totalNoVehicles = that.totalNoVehicles;
        this.averageNoVehicles = that.averageNoVehicles;
        this.averageVelocity = that.averageVelocity;
    }
    
    /**
     * Copies the reading data to another reading.
     * 
     * @param that The reading to copy data to.
     */
    void copyTo(Reading that)
    {
        that.time = this.time;
        that.location = this.location;
        that.noOfLanes = this.noOfLanes;
        that.totalNoVehicles = this.totalNoVehicles;
        that.averageNoVehicles = this.averageNoVehicles;
        that.averageVelocity = this.averageVelocity;
    }
    
    /**
     * Prints out the  data as a string
     */
    public String toString()
    {
        String r = time.toString() + "," + location + "," + noOfLanes + "," + 
                totalNoVehicles + "," + averageNoVehicles + "," + averageVelocity;
        return r;
    }

}
