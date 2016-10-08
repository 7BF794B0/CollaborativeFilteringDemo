import java.util.ArrayList;

class Person
{
    private int mId;
    public int getId () { return mId; }
    public void setId (int value) { mId = value; }

    private String mName;
    public String getName () { return mName; }
    public void setName (String value) { mName = value; }

    /**
     * The dataset which stores cosine measure of the user relative to all other users. Array index = id user - 1
     */
    private double[] mCosineMeasure;
    public double[] getCosineMeasure () { return mCosineMeasure; }
    public void setCosineMeasure (int index, double value) { mCosineMeasure[index] = value; }

    public ArrayList<String> Films = new ArrayList<>();
    public ArrayList<Integer> Ratings = new ArrayList<>();

    public void setCosineMeasureArraySize (int n)
    {
        mCosineMeasure = new double[n];
    }
}
