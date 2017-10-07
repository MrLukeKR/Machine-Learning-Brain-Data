import javafx.beans.property.SimpleIntegerProperty;

public class GamePerformanceRecord {

    private int recordID;

    private SimpleIntegerProperty rating = new SimpleIntegerProperty();
    private SimpleIntegerProperty level = new SimpleIntegerProperty();

    GamePerformanceRecord(int id) {
        Init();
        recordID = id;
    }

    private void Init() {

    }

    public Integer getRating() {
        return rating.get();
    }

    public void setRating(SimpleIntegerProperty rating) {
        this.rating = rating;
    }

    public Integer getLevel() {
        return level.get();
    }

    public void setLevel(SimpleIntegerProperty level) {
        this.level = level;
    }

    public int getID() {
        return recordID;
    }
}