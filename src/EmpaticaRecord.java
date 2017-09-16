import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleLongProperty;

import java.util.ArrayList;

public class EmpaticaRecord
{
    private int recordID = -1;
    SimpleLongProperty heartRateEpoch = new SimpleLongProperty();
    SimpleFloatProperty heartRate = new SimpleFloatProperty();

    private ArrayList<SimpleFloatProperty> heartRates = new ArrayList<>();

    EmpaticaRecord()
    {
        Init();
    }

    EmpaticaRecord(int id)
    {
        recordID = id;
        Init();
    }

    private void Init()
    {
        heartRateEpoch.add(heartRate);
        heartRates.add(heartRate);
    }

    public int getID() { return recordID; }

    public Long getHeartRateEpoch()  { return heartRateEpoch.get(); }

    public Float getHeartRate()  { return heartRate.get(); }

    public void setID(int ID) {
        this.recordID = ID;
    }
}
