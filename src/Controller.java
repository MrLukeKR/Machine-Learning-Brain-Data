import javafx.application.Platform;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.lang.Float.NaN;
import static java.lang.System.exit;

public class Controller
{
    static private Stage stage;
    static private boolean setup = false;
    static private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ssa", Locale.UK);

    static void setStage(Stage newStage) { stage = newStage; }
    private ArrayList<XYChart.Series<Float, Float>> rawSeries = new ArrayList<>();
    private ArrayList<XYChart.Series<Float, Float>> avgSeries = new ArrayList<>();

    private static List<GamePerformanceRecord> gamePerformanceRecords = new ArrayList<>();
    private ArrayList<XYChart.Series<Float, Float>> heartRateSeries = new ArrayList<>();

    @FXML private TextArea informationTextArea;
    @FXML private Button importfNIRSDataButton;
    @FXML private Button importfNIRSTimeButton;
    @FXML private Button importEmpaticaHRButton;
    @FXML private Button importSubjectiveDataButton;
    @FXML private Button generateGraphButton;
    @FXML private Button generateAveragedGraphButton;
    private ArrayList<BarChart.Series<String, Integer>> subjectiveSeries = new ArrayList<>();
    @FXML
    private Button generateHeartRateGraphButton;
    @FXML private Button linkEmpaticaToPerformanceButton;
    @FXML
    private ToggleButton togglefNIRSChartButton;
    @FXML
    private ToggleButton toggleHeartRateChartButton;

    @FXML private TableView<fNIRSRecord> fNIRSSpreadsheet;
    @FXML private TableColumn<fNIRSRecord, Float> timeCol;
    @FXML private TableColumn<fNIRSRecord, Float> channel1;
    @FXML private TableColumn<fNIRSRecord, Float> channel2;
    @FXML private TableColumn<fNIRSRecord, Float> channel3;
    @FXML private TableColumn<fNIRSRecord, Float> channel4;
    @FXML private TableColumn<fNIRSRecord, Float> channel5;
    @FXML private TableColumn<fNIRSRecord, Float> channel6;
    @FXML private TableColumn<fNIRSRecord, Float> channel7;
    @FXML private TableColumn<fNIRSRecord, Float> channel8;
    @FXML private TableColumn<fNIRSRecord, Float> channel9;
    @FXML private TableColumn<fNIRSRecord, Float> channel10;
    @FXML private TableColumn<fNIRSRecord, Float> channel11;
    @FXML private TableColumn<fNIRSRecord, Float> channel12;
    @FXML private TableColumn<fNIRSRecord, Float> channel13;
    @FXML private TableColumn<fNIRSRecord, Float> channel14;
    @FXML private TableColumn<fNIRSRecord, Float> channel15;
    @FXML private TableColumn<fNIRSRecord, Float> channel16;
    @FXML private TableColumn<fNIRSRecord, Float> average1;
    @FXML private TableColumn<fNIRSRecord, Float> average2;
    @FXML private TableColumn<fNIRSRecord, Float> average3;
    @FXML private TableColumn<fNIRSRecord, Float> average4;
    @FXML private TableColumn<fNIRSRecord, Float> hrCol;
    @FXML private TableColumn<fNIRSRecord, Long> hrEpochCol;

    @FXML private Label channelCount;
    @FXML private Label recordCount;
    @FXML private Label timeCount;
    @FXML private Label hrCount;

    @FXML private Label fNIRSStartTime;
    @FXML private Label empaticaStartTime;
    @FXML private Label gameplayStartTime;
    private static long gameplayStartTimeEpoch = 0L;
    private static long gameplayEndTimeEpoch = 0L;

    @FXML private LineChart<Float, Float> fNIRSChart;
    @FXML
    private NumberAxis fNIRSxAxis;
    @FXML
    private NumberAxis fNIRSyAxis;

    @FXML
    private LineChart<Float, Float> heartRateChart;
    @FXML
    private NumberAxis heartRatexAxis;
    @FXML
    private NumberAxis heartRateyAxis;

    @FXML private ProgressBar progressBar;
    @FXML
    private Button importGameplayDataButton;
    @FXML
    private Button generateSubjectiveRatingGraphButton;

    private static int channels = 0;
    private static int times = 0;
    private static int heartRates = 0;

    private static long heartRateEpoch = 0L;
    @FXML
    private Label gameplayEndTime;
    @FXML
    private Label gameplayLength;
    private static long sessionEpoch = 0L;

    private static long dateEpoch = 0L;

    private static boolean sessionEpochInitialised = false; //Determines whether a session start date has been captured via the various files

    private static ObservableList<fNIRSRecord> data = FXCollections.observableArrayList();
    private static List<fNIRSRecord> fNIRSRecords = new ArrayList<>();
    private static List<EmpaticaRecord> empaticaRecords = new ArrayList<>();
    @FXML
    private BarChart<String, Integer> subjectiveRatingChart;

    @FXML
    private void handleCloseButtonAction(ActionEvent event)
    {
        exit(1);
    }

    public void initialize() {
        if(!setup) {
            fNIRSSpreadsheet.setItems(data);

            timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
            channel1.setCellValueFactory(new PropertyValueFactory<>("channel1"));
            channel2.setCellValueFactory(new PropertyValueFactory<>("channel2"));
            channel3.setCellValueFactory(new PropertyValueFactory<>("channel3"));
            channel4.setCellValueFactory(new PropertyValueFactory<>("channel4"));
            channel5.setCellValueFactory(new PropertyValueFactory<>("channel5"));
            channel6.setCellValueFactory(new PropertyValueFactory<>("channel6"));
            channel7.setCellValueFactory(new PropertyValueFactory<>("channel7"));
            channel8.setCellValueFactory(new PropertyValueFactory<>("channel8"));
            channel9.setCellValueFactory(new PropertyValueFactory<>("channel9"));
            channel10.setCellValueFactory(new PropertyValueFactory<>("channel10"));
            channel11.setCellValueFactory(new PropertyValueFactory<>("channel11"));
            channel12.setCellValueFactory(new PropertyValueFactory<>("channel12"));
            channel13.setCellValueFactory(new PropertyValueFactory<>("channel13"));
            channel14.setCellValueFactory(new PropertyValueFactory<>("channel14"));
            channel15.setCellValueFactory(new PropertyValueFactory<>("channel15"));
            channel16.setCellValueFactory(new PropertyValueFactory<>("channel16"));

            average1.setCellValueFactory(new PropertyValueFactory<>("average1"));
            average2.setCellValueFactory(new PropertyValueFactory<>("average2"));
            average3.setCellValueFactory(new PropertyValueFactory<>("average3"));
            average4.setCellValueFactory(new PropertyValueFactory<>("average4"));

            hrCol.setCellValueFactory(new PropertyValueFactory<>("heartRate"));
            hrEpochCol.setCellValueFactory(new PropertyValueFactory<>("heartRateEpoch"));

            togglefNIRSChart();
            toggleHeartRateChart();

            setup = true;
        }
    }

    @FXML
    private void importfNIRSData(ActionEvent event) throws IOException, ParseException {

        printToInfoBox("Importing fNIRS Hemoglobin Data");

        List<File> files;

        FileChooser fc = new FileChooser();
        fc.setTitle("Import fNIRS Hemoglobin Data");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("fNIRS Block CSV", "*Block*.csv"));

        files = fc.showOpenMultipleDialog(stage);

        for (File file : files) {
            if (!file.exists() || file == null) {
                printToInfoBox("Error: File not found");
                printEndOfFunction();
                return;
            }
        }

        importfNIRSTimeButton.setDisable(true);
        generateGraphButton.setDisable(true);
        generateAveragedGraphButton.setDisable(true);

        fNIRSRecords.clear();
        data.clear();
        channels = 0;

        for (File file : files) {

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String currLine;

            boolean start = false;

            while ((currLine = br.readLine()) != null) {
                if (start) {
                    String split[] = currLine.split(",");
                    fNIRSRecord currentRec = new fNIRSRecord(fNIRSRecords.size());

                    for (int i = 0; i < 16; i++)
                        if (split[i].contains("NaN"))
                            currentRec.channels.get(i).set(NaN);
                        else
                            currentRec.channels.get(i).set(Float.parseFloat(split[i]));

                    if (channels == 0)
                        for (String str : split)
                            if (!str.contains("NaN"))
                                channels++;
                    fNIRSRecords.add(currentRec);
                    data.add(currentRec);
                }

                if (currLine.split(",")[0].equals("Date:")) //Don't use 'contains' as the files also contain "ExportDate:"
                {
                    DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.UK);
                    Date date = format.parse(currLine.split(",")[1]);
                    fNIRSStartTime.setText(dateFormat.format(date));
                }

                if (!start && currLine.split(",")[0].contains("Optode"))
                    start = true;
            }

            br.close();
            fr.close();
        }

        channelCount.setText(String.valueOf(channels));
        recordCount.setText(String.valueOf(fNIRSRecords.size()));

        printToInfoBox("Loaded fNIRS data");
        progressBar.setProgress(0);

        printToInfoBox("Calculating 4-Cluster Averages");

        for(fNIRSRecord record : fNIRSRecords) {
            record.average1.set((record.getChannel1() + record.getChannel2() + record.getChannel3() + record.getChannel4()) / 4);
            record.average2.set((record.getChannel5() + record.getChannel6() + record.getChannel7() + record.getChannel8()) / 4);
            record.average3.set((record.getChannel9() + record.getChannel10() + record.getChannel11() + record.getChannel12()) / 4);
            record.average4.set((record.getChannel13() + record.getChannel14() + record.getChannel15() + record.getChannel16()) / 4);
        }

        importfNIRSTimeButton.setDisable(false);
        generateGraphButton.setDisable(!(times == fNIRSRecords.size()));//TODO: Migrate to separate method
        generateAveragedGraphButton.setDisable(!(times == fNIRSRecords.size())); //TODO: Migrate to separate method

        printEndOfFunction();
    }

    @FXML private void importfNIRSTimeData(ActionEvent event) throws IOException {

        List<File> files;

        FileChooser fc = new FileChooser();
        fc.setTitle("Import fNIRS Time Data");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("fNIRS Timestamp CSV", "*Time*.csv"));

        files = fc.showOpenMultipleDialog(stage);


        for (File file : files) {
            if (!file.exists() || file == null) {
                printToInfoBox("Error: File not found");
                printEndOfFunction();
                return;
            }
        }

        times = 0;

        for (File file : files) {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String currLine;

            boolean start = false;

            while ((currLine = br.readLine()) != null) {
                if (start && !currLine.isEmpty())
                    fNIRSRecords.get(times++).setTime(new SimpleFloatProperty(Float.parseFloat(currLine)));
                if (currLine.split(",")[0].contains("Time"))
                    start = true;
            }

            br.close();
            fr.close();
        }

        timeCount.setText(String.valueOf(times));

        fNIRSSpreadsheet.refresh();

        generateGraphButton.setDisable(!(times == fNIRSRecords.size()));
        generateAveragedGraphButton.setDisable(!(times == fNIRSRecords.size()));

        printToInfoBox("Loaded TimeStamp data");
        printEndOfFunction();
    }

    @FXML
    private void generateAveragedfNIRSChart(ActionEvent ehvent)
    {
        printToInfoBox("Generating Averaged fNIRS Graph");

                fNIRSChart.setTitle("Averaged fNIRS Data");

                if(fNIRSChart.getData().size() > 0)
                    fNIRSChart.getData().remove(0,fNIRSChart.getData().size());

        new Thread(() -> {
                if (avgSeries.size() > 0)
                    avgSeries.clear();

                progressBar.setProgress(0);
                printToInfoBox("Adding Data Points");

                for (int i = 0; i < 4; i++)
                    avgSeries.add(new XYChart.Series<>());

                for (fNIRSRecord record : fNIRSRecords)
                    for (int i = 0; i < 4; i++) {
                        if (Float.isFinite(record.averages.get(i).getValue()))
                            avgSeries.get(i).getData().add(new XYChart.Data<>(record.getTime(), record.averages.get(i).getValue()));
                        progressBar.setProgress(progressBar.getProgress() + 0.5 / (fNIRSRecords.size() * 4));
                    }

                Node node;

                int totalSize = 0;

                for (int j = 0; j < 4; j++)
                    totalSize += avgSeries.get(j).getData().size();

                avgSeries.get(0).setName("Channel 1, 2, 3, 4");
                avgSeries.get(1).setName("Channel 5, 6, 7, 8");
                avgSeries.get(2).setName("Channel 9, 10, 11, 12");
                avgSeries.get(3).setName("Channel 13, 14, 15, 16");

                for (int j = 0; j < 4; j++) {
                    for (XYChart.Data data : avgSeries.get(j).getData()) {
                        node = new Rectangle(0, 0);
                        node.setVisible(false);
                        data.setNode(node);

                        progressBar.setProgress(progressBar.getProgress() + 0.5 / totalSize);
                    }
                }

                int j = 0;

            while (j < avgSeries.size()) {
                    if (avgSeries.get(j).getData().size() == 0)
                        avgSeries.remove(j);
                    else
                        j++;
                }


            realignfNIRSChart();

                printToInfoBox("Generating Line Chart");

                Platform.runLater(() ->
                {
                    fNIRSChart.getData().addAll(avgSeries);
                    progressBar.setProgress(0);
                });

            printEndOfFunction();
        }).start();
    }

    @FXML
    private void generatefNIRSChart(ActionEvent event) {
        printToInfoBox("Generating fNIRS Graph");
        fNIRSChart.setTitle("fNIRS Data");

        if(fNIRSChart.getData().size() > 0)
            fNIRSChart.getData().remove(0, fNIRSChart.getData().size());

        new Thread(() ->
        {

        if(rawSeries.size() > 0)
                    rawSeries.clear();

                progressBar.setProgress(0);
                printToInfoBox("Adding Data Points");

                for (int i = 0; i < 16; i++)
                    rawSeries.add(new XYChart.Series<>());

                for (fNIRSRecord record : fNIRSRecords)
                    for (int i = 0; i < 16; i++) {
                        if (Float.isFinite(record.channels.get(i).getValue()))
                            rawSeries.get(i).getData().add(new XYChart.Data<>(record.getTime(), record.channels.get(i).getValue()));
                        progressBar.setProgress(progressBar.getProgress() + 0.5 / (fNIRSRecords.size() * 16));
                    }

                Node node;

                int totalSize = 0;

                for(int j = 0; j < 16; j++)
                    totalSize+= rawSeries.get(j).getData().size();

                for (int j = 0; j < 16; j++) {
                    rawSeries.get(j).setName("Channel " + (j + 1));

                    for (XYChart.Data data : rawSeries.get(j).getData()) {
                        node = new Rectangle(0, 0);
                        node.setVisible(false);
                        data.setNode(node);

                        progressBar.setProgress(progressBar.getProgress() + 0.5 / totalSize);
                    }
                }

                int j = 0;

                printToInfoBox("Removing Null Anomalies");

                while(j < rawSeries.size()){
                    if (rawSeries.get(j).getData().size() == 0)
                        rawSeries.remove(j);
                    else
                        j++;
                }

                printToInfoBox("Generating Line Chart");

            realignfNIRSChart();

                Platform.runLater(() ->
                {
                    fNIRSChart.getData().addAll(rawSeries);
                    progressBar.setProgress(0);
                });
            printEndOfFunction();
            }).start();
    }

    private void realignfNIRSChart() {
        fNIRSxAxis.setLowerBound(fNIRSRecords.get(0).getTime());
        fNIRSxAxis.setUpperBound(fNIRSRecords.get(fNIRSRecords.size() - 1).getTime());
    }

    private void realignHRChart() {
        int currTime = empaticaRecords.get(0).getHeartRateEpoch().intValue();
        int endTime = empaticaRecords.get(empaticaRecords.size() - 1).getHeartRateEpoch().intValue();

        heartRatexAxis.setLowerBound(0);
        heartRatexAxis.setUpperBound(endTime - currTime);

        float lowHR = Float.MAX_VALUE, highHR = Float.MIN_VALUE;

        for (EmpaticaRecord record : empaticaRecords) {
            if (record.getHeartRate() < lowHR) lowHR = record.getHeartRate();
            if (record.getHeartRate() > highHR) highHR = record.getHeartRate();
        }

        heartRateyAxis.setLowerBound(lowHR);
        heartRateyAxis.setUpperBound(highHR);
    }

    private void printToInfoBox(String string)
    {
        Platform.runLater(() ->
        {
            informationTextArea.appendText(string);
            informationTextArea.appendText("\r\n");
        });
    }

    private void printEndOfFunction()
    {
        printToInfoBox("------------------------------------");
    }

    @FXML
    private void stepDetectfNIRS()
    {
        printToInfoBox("Processing Raw fNIRS: Detecting Steps");
        printEndOfFunction();
    }

    @FXML
    private void stepDetectAveragedfNIRS()
    {
        printToInfoBox("Processing Averaged fNIRS: Detecting Steps");
        printEndOfFunction();
    }

    @FXML
    private void importEmpaticaHR() throws IOException, ParseException {
        printToInfoBox("Importing Empatica Heart Rate Data");

        File file;

        FileChooser fc = new FileChooser();
        fc.setTitle("Import Empatica Heart Rate Data");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Empatica HR CSV", "HR.csv"));

        file = fc.showOpenDialog(stage);

        if (file == null || !file.exists()) {
            printToInfoBox("Error: File not found");
            printEndOfFunction();
            return;
        }

        heartRates = 0;
        empaticaRecords.clear();

        FileReader fr       = new FileReader(file);
        BufferedReader br   = new BufferedReader(fr);

        String currLine;

        boolean start = false;
        int count = 0;

        long epoch = 0;

        while ((currLine = br.readLine()) != null) {
            EmpaticaRecord currentRec = new EmpaticaRecord(empaticaRecords.size());

            if (start && !currLine.isEmpty())
            {
                //TODO: Sync with fNIRS (1 HR record for every 2 fNIRS fNIRSRecords)
                currentRec.heartRate = new SimpleFloatProperty(Float.parseFloat(currLine));
                currentRec.heartRateEpoch = new SimpleLongProperty(epoch++);

                empaticaRecords.add(currentRec);
                heartRates++;
            }
            else if (count == 0)
            {
                epoch = Float.valueOf(currLine).longValue();
                long msEpoch = epoch * 1000L;
                Date epochToDate = new Date(msEpoch);
                printToInfoBox("Epoch: " + epoch + " (" + dateFormat.format(epochToDate) + ")");
                empaticaStartTime.setText(dateFormat.format(epochToDate));

                heartRateEpoch = epoch;

                long timeRemainder = epoch % (24 * 60 * 60);

                dateEpoch = epoch - timeRemainder;

                updateSessionTimes();

                printToInfoBox("Date Epoch set to " + dateEpoch);

            }
            else if (count == 1)
                printToInfoBox("Sample rate is: " +  Float.valueOf(currLine).intValue() + "Hz");
            else if(count == 2)
                start = true;

            count++;
        }

        br.close();
        fr.close();

        hrCount.setText(String.valueOf(heartRates));

        fNIRSSpreadsheet.refresh();
        printToInfoBox("Loaded Heart Rate data");
        printEndOfFunction();

        linkEmpaticaToPerformanceButton.setDisable(!sessionEpochInitialised);
        generateHeartRateGraphButton.setDisable(heartRates == 0);
    }

    @FXML
    private void exportBWF()
    {

        printEndOfFunction();
    }

    @FXML
    private void importSubjectiveData() throws IOException {
        printToInfoBox("Importing Subjective Workload Rating Data");

        File file;

        FileChooser fc = new FileChooser();
        fc.setTitle("Import Subjective Workload Rating Data");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Subjective Data CSV", "Subjective_ratings*.csv"));

        file = fc.showOpenDialog(stage);

        if (file == null || !file.exists()) {
            printToInfoBox("Error: File not found");
            printEndOfFunction();
            return;
        }

        FileReader fr       = new FileReader(file);
        BufferedReader br   = new BufferedReader(fr);

        String currLine;

        boolean start = false;
        int count = 0;

        String split[];

        while ((currLine = br.readLine()) != null)
        {
            split = currLine.split(",");

            if (start && !currLine.isEmpty()) {
                GamePerformanceRecord temp = new GamePerformanceRecord(count - 1);
                temp.setLevel(new SimpleIntegerProperty(Integer.parseInt(split[0])));
                temp.setRating(new SimpleIntegerProperty(Integer.parseInt(split[1])));

                gamePerformanceRecords.add(temp);
            } else if (count == 0)
                start = true;

            count++;
        }

        br.close();
        fr.close();

        generateSubjectiveRatingGraphButton.setDisable(gamePerformanceRecords.isEmpty());

        printToInfoBox("Loaded Subjective Workload Rating data");
        printEndOfFunction();
    }

    @FXML
    private void importGameplayData() throws IOException {
        printToInfoBox("Importing Gameplay Performance Data");

        File file;

        FileChooser fc = new FileChooser();
        fc.setTitle("Import Gameplay Performance Data");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Performance Log CSV", "performance_log_Participant_*.csv"));

        file = fc.showOpenDialog(stage);

        if (file == null || !file.exists()) {
            printToInfoBox("Error: File not found");
            printEndOfFunction();
            return;
        }

        //TODO: Increment no. of fNIRSRecords (if count of performance log data is important)

        FileReader fr       = new FileReader(file);
        BufferedReader br   = new BufferedReader(fr);

        String currLine;

        boolean start = false;
        int count = 0;
        String split[];
        String date;
        int level = 0;

        int hour = 0, min = 0, sec = 0;

        while ((currLine = br.readLine()) != null)
        {
            split = currLine.split(",");

            if (start && !currLine.isEmpty())
            {
                int currLevel = Integer.parseInt(split[0]);

                hour = Integer.parseInt(split[1]) * 60 * 60;
                min = Integer.parseInt(split[2]) * 60;
                sec = Integer.parseInt(split[3]);

                if(count == 1)
                {
                    gameplayStartTimeEpoch = (hour + min + sec);
                    printToInfoBox("Gameplay Epoch set to " + gameplayStartTimeEpoch);
                }

                if(currLevel != level)
                {
                    level = currLevel;
                    date = split[1] + ":" + split[2] + ":" + split[3];
                    printToInfoBox("Level " + level + ": " + date);

                    if(level == 13) {
                        printToInfoBox("");
                    }
                }
            }
            else if (count == 0)
                start = true;

            count++;
        }

        br.close();
        fr.close();

        gameplayEndTimeEpoch = (hour + min + sec);
        printToInfoBox("Gameplay End Epoch set to " + gameplayEndTimeEpoch);
        updateSessionTimes();

        fNIRSSpreadsheet.refresh();
        printToInfoBox("Loaded Gameplay Performance data");
        printEndOfFunction();

        linkEmpaticaToPerformanceButton.setDisable(!sessionEpochInitialised);
    }

    private void updateSessionTimes()
    {
        if (!(sessionEpochInitialised = !(dateEpoch == 0 || gameplayStartTimeEpoch == 0)))
            return;

        sessionEpoch = (dateEpoch + gameplayStartTimeEpoch);
        Date sessionTime = new Date(sessionEpoch * 1000L);
        Date endSessionTime = new Date((dateEpoch + gameplayEndTimeEpoch) * 1000L);

        gameplayStartTime.setText(dateFormat.format(sessionTime));
        gameplayEndTime.setText(dateFormat.format(endSessionTime));

        Long length = (endSessionTime.getTime() - sessionTime.getTime());

        gameplayLength.setText("(" + length / 1000L / 60L + " minutes, " +
                length / 1000L % 60L + " seconds)");

        printToInfoBox("Updated session start time to: " + sessionEpoch + " -> " + dateFormat.format(sessionTime));
        printToInfoBox("Updated session end time to: " + endSessionTime.getTime() / 1000L + " -> " + dateFormat.format(endSessionTime));
    }

    @FXML
    private void linkEmpaticaToPerformance()
    {
        printToInfoBox("Syncing Imported Data");

        if (empaticaRecords.get(0).getHeartRateEpoch() != sessionEpoch) {
            EmpaticaRecord initialHR = findHRSyncPoint(sessionEpoch);

            if (initialHR != null) {
                printToInfoBox("Found Heart Rate sync point! (Skipping " + initialHR.getID() + " preamble records)");

                printToInfoBox("\tHR Record " + initialHR.getID() + " Time:\r\n\t\t"
                        + initialHR.getHeartRateEpoch() + " -> " + dateFormat.format(new Date(initialHR.getHeartRateEpoch() * 1000)));
                printToInfoBox("\tSession Start Time:\r\n\t\t"
                        + sessionEpoch + " -> " + dateFormat.format(new Date(sessionEpoch * 1000)));

                syncHRtoSession(initialHR.getID());
            } else
                printToInfoBox("Error: Heart Rate data cannot be synced with session information - " +
                        "Either the Heart Rate sensor's time is incorrect or was not started within the loaded session.");
        } else
            printToInfoBox("Already synchronised!");

        printEndOfFunction();
    }

    /**
    @return The fNIRS / Heart Rate record containing the same epoch
     */
    private EmpaticaRecord findHRSyncPoint(long sessionEpoch)
    {
        long id = sessionEpoch - empaticaRecords.get(0).getHeartRateEpoch();

        return empaticaRecords.get((int)id);
    }

    private void syncHRtoSession(long id)
    {
        empaticaRecords.removeIf(empaticaRecord -> empaticaRecord.getID() < id);

        empaticaStartTime.setText(dateFormat.format(new Date(empaticaRecords.get(0).getHeartRateEpoch() * 1000)));
        hrCount.setText(String.valueOf(empaticaRecords.size()));

        checkSynchronisation(); //TODO: Migrate this to a "OnTextChanged" event

        printToInfoBox("Removed " + id + " out-of-sync Empatica records");

        int newID = 0;

        for (EmpaticaRecord record : empaticaRecords)
            record.setID(newID++);

        int trimEnd = empaticaRecords.size();


        empaticaRecords.removeIf(empaticaRecord -> empaticaRecord.getID() >= 39 * 45); //Trim the end:
        //Total should contain 3 rounds * 13 levels * 45 second intervals

        printToInfoBox("Re-aligned record ID numbers");
    }

    private void checkSynchronisation() {
        if (empaticaRecords.size() > 0 && empaticaRecords.get(0).getHeartRateEpoch() == sessionEpoch)
            empaticaStartTime.setTextFill(Color.GREEN);
        else
            empaticaStartTime.setTextFill(Color.RED);

        if (fNIRSRecords.size() > 0 && fNIRSRecords.get(0).getEpoch() == sessionEpoch)
            fNIRSStartTime.setTextFill(Color.GREEN);
        else
            fNIRSStartTime.setTextFill(Color.RED);
    }

    @FXML
    private void generateHeartRateGraph() {
        printToInfoBox("Generating Heart Rate Graph");

        if (heartRateChart.getData().size() > 0)
            heartRateChart.getData().remove(0, heartRateChart.getData().size());

        new Thread(() ->
        {
            long initTime = empaticaRecords.get(0).getHeartRateEpoch();


            if (heartRateSeries.size() > 0)
                heartRateSeries.clear();

            progressBar.setProgress(0);
            printToInfoBox("Adding Data Points");

            heartRateSeries.add(new XYChart.Series<>());

            for (EmpaticaRecord record : empaticaRecords) {
                if (Float.isFinite(record.getHeartRate())) {
                    float currTime = (int) (record.getHeartRateEpoch() - initTime);
                    heartRateSeries.get(0).getData().add(new XYChart.Data(currTime, record.getHeartRate()));
                }
                progressBar.setProgress(progressBar.getProgress() + 0.5 / empaticaRecords.size());
            }

            Node node;

            heartRateSeries.get(0).setName("Heart Rate");

            for (XYChart.Data data : heartRateSeries.get(0).getData()) {
                node = new Rectangle(0, 0);
                node.setVisible(false);
                data.setNode(node);

                progressBar.setProgress(progressBar.getProgress() + 0.5 / heartRateSeries.size());
            }

            int j = 0;

            printToInfoBox("Removing Null Anomalies");

            while (j < heartRateSeries.size()) {
                if (heartRateSeries.get(j).getData().size() == 0)
                    heartRateSeries.remove(j);
                else
                    j++;
            }

            printToInfoBox("Generating Line Chart");

            realignHRChart();

            Platform.runLater(() ->
            {
                heartRateChart.getData().addAll(heartRateSeries);
                progressBar.setProgress(0);
            });

            printEndOfFunction();
        }).start();
    }

    @FXML
    private void togglefNIRSChart() {
        fNIRSChart.setVisible(togglefNIRSChartButton.isSelected());
        fNIRSChart.setManaged(togglefNIRSChartButton.isSelected());
    }

    @FXML
    private void toggleHeartRateChart() {
        heartRateChart.setVisible(toggleHeartRateChartButton.isSelected());
        heartRateChart.setManaged(toggleHeartRateChartButton.isSelected());
    }

    @FXML
    private void generateSubjectiveRatingGraph() {
        if (subjectiveRatingChart.getData().size() > 0)
            subjectiveRatingChart.getData().remove(0, subjectiveRatingChart.getData().size());

        new Thread(() -> {
            if (subjectiveSeries.size() > 0)
                subjectiveSeries.clear();

            progressBar.setProgress(0);
            printToInfoBox("Adding Data Points");

            for (int i = 0; i < 13; i++)
                subjectiveSeries.add(new XYChart.Series<>());

            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < 13; i++) {
                    GamePerformanceRecord record = gamePerformanceRecords.get(i + j * 13);
                    subjectiveSeries.get(i).getData().add(new XYChart.Data<>(String.valueOf("Round " + j), record.getRating()));
                    progressBar.setProgress(progressBar.getProgress() + 0.5 / (gamePerformanceRecords.size() * 13));
                }
            }

            int j = 0;

            while (j < subjectiveSeries.size()) {
                if (subjectiveSeries.get(j).getData().size() == 0)
                    subjectiveSeries.remove(j);
                else
                    j++;
            }

            printToInfoBox("Generating Bar Chart");

            Platform.runLater(() ->
            {
                subjectiveRatingChart.getData().addAll(subjectiveSeries);
                progressBar.setProgress(0);
            });

            printEndOfFunction();
        }).start();
    }
}