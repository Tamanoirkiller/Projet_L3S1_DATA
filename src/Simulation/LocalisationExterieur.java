package Simulation;

/**
 * Created by msi on 23/11/2016.
 */
public class LocalisationExterieur extends Localisation {
    public double latitude;
    public double longitude;

    public LocalisationExterieur (String type, double latitude, double longitude) throws Exception{
        super(type);
        this.latitude = latitude;
        this.longitude = longitude;
    }
}