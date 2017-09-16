import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleLongProperty;

import java.util.ArrayList;

public class fNIRSRecord
{
    private int recordID;

    private SimpleFloatProperty time = new SimpleFloatProperty();
    private SimpleFloatProperty channel1 = new SimpleFloatProperty();
    private SimpleFloatProperty channel2 = new SimpleFloatProperty();
    private SimpleFloatProperty channel3 = new SimpleFloatProperty();
    private SimpleFloatProperty channel4 = new SimpleFloatProperty();
    private SimpleFloatProperty channel5 = new SimpleFloatProperty();
    private SimpleFloatProperty channel6 = new SimpleFloatProperty();
    private SimpleFloatProperty channel7 = new SimpleFloatProperty();
    private SimpleFloatProperty channel8 = new SimpleFloatProperty();
    private SimpleFloatProperty channel9 = new SimpleFloatProperty();
    private SimpleFloatProperty channel10 = new SimpleFloatProperty();
    private SimpleFloatProperty channel11 = new SimpleFloatProperty();
    private SimpleFloatProperty channel12 = new SimpleFloatProperty();
    private SimpleFloatProperty channel13 = new SimpleFloatProperty();
    private SimpleFloatProperty channel14 = new SimpleFloatProperty();
    private SimpleFloatProperty channel15 = new SimpleFloatProperty();
    private SimpleFloatProperty channel16 = new SimpleFloatProperty();

    SimpleFloatProperty average1 = new SimpleFloatProperty();
    SimpleFloatProperty average2 = new SimpleFloatProperty();
    SimpleFloatProperty average3 = new SimpleFloatProperty();
    SimpleFloatProperty average4 = new SimpleFloatProperty();

    ArrayList<SimpleFloatProperty> channels = new ArrayList<>();
    ArrayList<SimpleFloatProperty> averages = new ArrayList<>();

    private EmpaticaRecord empaticaRecord = new EmpaticaRecord();

    fNIRSRecord(int id)
    {
        Init();
        recordID = id;
    }

    private void Init()
    {
        channels.add(channel1);
        channels.add(channel2);
        channels.add(channel3);
        channels.add(channel4);
        channels.add(channel5);
        channels.add(channel6);
        channels.add(channel7);
        channels.add(channel8);
        channels.add(channel9);
        channels.add(channel10);
        channels.add(channel11);
        channels.add(channel12);
        channels.add(channel13);
        channels.add(channel14);
        channels.add(channel15);
        channels.add(channel16);

        averages.add(average1);
        averages.add(average2);
        averages.add(average3);
        averages.add(average4);

    }

    public Float getTime() { return time.get(); }

    public void setTime(SimpleFloatProperty time) { this.time = time; }

    public void linkEmpaticaRecord(EmpaticaRecord record){ empaticaRecord = record; }

    public int getID() { return recordID; }

    public Float getChannel1() { return channel1.get(); }

    public Float getChannel2() { return channel2.get(); }

    public Float getChannel3()
    {
        return channel3.get();
    }

    public Float getChannel4()
    {
        return channel4.get();
    }

    public Float getChannel5()
    {
        return channel5.get();
    }

    public Float getChannel6()
    {
        return channel6.get();
    }

    public Float getChannel7()
    {
        return channel7.get();
    }

    public Float getChannel8()
    {
        return channel8.get();
    }

    public Float getChannel9()
    {
        return channel9.get();
    }

    public Float getChannel10()
    {
        return channel10.get();
    }

    public Float getChannel11()
    {
        return channel11.get();
    }

    public Float getChannel12()
    {
        return channel12.get();
    }

    public Float getChannel13()
    {
        return channel13.get();
    }

    public Float getChannel14()
    {
        return channel14.get();
    }

    public Float getChannel15()
    {
        return channel15.get();
    }

    public Float getChannel16() { return channel16.get(); }

    public Float getAverage1()  { return average1.get(); }

    public Float getAverage2()  { return average2.get(); }

    public Float getAverage3()  { return average3.get(); }

    public Float getAverage4()  { return average4.get(); }

    public Float getHeartRate() { return empaticaRecord.getHeartRate(); }

    public Long getHeartRateEpoch() { return empaticaRecord.getHeartRateEpoch(); }
}
