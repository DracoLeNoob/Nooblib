package fr.ideria.nooblib.data.mysql;

import fr.ideria.nooblib.data.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * An object that simplify the use of a ResultSet from SQL
 */
public class DataSet {
    /**
     * The length of each row of the data set
     */
    private int rowLength;
    /**
     * The List that represent the ResultSet
     */
    private final List<Data<?>[]> dataSet;

    /**
     * Constructor
     * @param set the result set that will be copied to fill dataSet
     */
    public DataSet(ResultSet set){
        this.dataSet = new ArrayList<>();

        try{
            // Loop all the elements of the dataset to fill dataSet list
            while(set.next()){
                // Count the length of a row
                if(set.isFirst()){
                    int rowLength = 0;
                    boolean error = false;

                    while(!error){
                        try{
                            set.getObject(rowLength + 1);
                            rowLength++;
                        }catch(IndexOutOfBoundsException | SQLException e){
                            error = true;
                        }
                    }

                    this.rowLength = rowLength;
                }

                // Fill the dataSet list
                Data<?>[] row = new Data[rowLength];

                for(int i = 0; i < row.length; i++)
                    row[i] = Data.fromObject(set.getObject(i + 1));

                dataSet.add(row);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * To get a specified row in the dataSet
     * @param columnIndex the index of the row
     * @return A data array of the selected row
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public Data<?>[] getRow(int columnIndex) throws IndexOutOfBoundsException{
        if(columnIndex < 0 || columnIndex >= dataSet.size())
            throw new IndexOutOfBoundsException(columnIndex);

        return dataSet.get(columnIndex);
    }

    /**
     * To get a data from a column index and a row index
     * @param columnIndex the column index of the data
     * @param rowIndex the row index of the data
     * @return the specified data
     */
    public Data<?> getData(int columnIndex, int rowIndex){
        if(rowIndex < 0 || rowIndex >= rowLength)
            throw new IndexOutOfBoundsException(rowIndex);

        return this.getRow(columnIndex)[rowIndex];
    }

    /**
     * To loop all the elements
     * @param consumer the action to do for all the rows
     */
    public void forEach(Consumer<Data<?>[]> consumer){
        Objects.requireNonNull(consumer);
        for(Data<?>[] row : dataSet) consumer.accept(row);
    }

    public int getHeight(){ return dataSet.size(); }
}