import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleLongProperty;

import java.util.ArrayList;

public class EmpaticaRecord
{
    private static int currentID = 0;
    private int recordID;
    SimpleLongProperty heartRateEpoch = new SimpleLongProperty();
    SimpleFloatProperty heartRate = new SimpleFloatProperty();

    ArrayList<SimpleFloatProperty> heartRates = new ArrayList<>();

    EmpaticaRecord()
    {
        Init();
    }

    void Init()
    {
        recordID = currentID++;

        heartRateEpoch.add(heartRate);
        heartRates.add(heartRate);
    }

    public int getID() { return recordID; }

    public Long getHeartRateEpoch()  { return heartRateEpoch.get(); }

    public Float getHeartRate()  { return heartRate.get(); }
}
