/*
 * Abstract Factory for creating different types of File Readers
 */
package DAL;

public abstract class FileReaderFactory {

    /*
     * Abstract method to create a Text File Reader
     * @return IDALFileReader An instance implementing IDALFileReader for reading text files
     */
    public abstract IDALFileReader createTextFileReader();

    /*
     * Abstract method to create a JSON File Reader
     * @return IDALFileReader An instance implementing IDALFileReader for reading JSON files
     */
    public abstract IDALFileReader createJsonFileReader();
}
