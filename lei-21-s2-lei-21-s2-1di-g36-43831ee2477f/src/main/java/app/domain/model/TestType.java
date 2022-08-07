package app.domain.model;

import app.externalModule.ReferenceValuesExternalModule;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class TestType  implements Serializable {
    private final String code;
    private final String description;
    private final String collectingMethod;
    private final List<ParameterCategory> categoriesList;

    /**
     * Constructor
     * @param code
     * @param description
     * @param collectingMethod
     * @param categoriesList
     */
    public TestType(String code, String description, String collectingMethod, List<ParameterCategory> categoriesList){
        checkCode(code);
        checkDescription(description);
        checkCollectingMethod(collectingMethod);
        this.code = code;
        this.description = description;
        this.collectingMethod = collectingMethod;
        this.categoriesList = categoriesList;
    }

    public List<ParameterCategory> getCategoriesList() {
        return categoriesList;
    }

    public ReferenceValue getReferenceValues(TestParameter testParameter) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Properties properties = new Properties();
        InputStream inputStream = new FileInputStream("configuration.conf");
        properties.load(inputStream);
        Class<?> c= Class.forName(properties.getProperty("Company.API."+description+".Class"));
        ReferenceValuesExternalModule refValueAPI  = (ReferenceValuesExternalModule) c.newInstance();
        ReferenceValue refValue = refValueAPI.getReferenceValues(testParameter);
        inputStream.close();
        return  refValue;
    }

    /**
     * This method returns the code of a test
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     *  This method returns the description of a test
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *  This method returns the collecting method of a test
     * @return
     */
    public String getCollectingMethod() {
        return collectingMethod;
    }

    /**
     * This method checks if the code of a test is valid or not
     * @param code
     */
    public void checkCode(String code) {
        if(!(code.length()==5)){
            throw new IllegalArgumentException("Code of test must have 5 characters.");
        }
    }

    /**
     * This method checks if the description of a test is valid or not
     * @param description
     */
    public void checkDescription(String description) {
        if(description.isEmpty()) {

            throw new IllegalArgumentException("Description cannot be empty!");
        }
    }

    /**
     * This method checks if the collecting method of a test is valid or not
     * @param collectingMethod
     */
    public void checkCollectingMethod(String collectingMethod) {
        if(collectingMethod.isEmpty()){
            throw new IllegalArgumentException("Collecting method cannot be empty.");
        }
    }

    public Object getExternalModule() throws  ClassNotFoundException,InstantiationException,IllegalAccessException{
        Class<?> oClass = null;
        oClass = Class.forName("esoft.adapters.ExternalModuleAdapter1");
        return oClass;


    }
    /**
     * This method returns a string with all the parameters of a Type of Test
     * @return
     */
    @Override
    public String toString() {
        String categoryMessage = "Category/categories selected: ";
        int i=0;
        for (ParameterCategory parameterCategory: categoriesList){
            if (i==0){
                categoryMessage = categoryMessage + categoriesList.get(i).getName();
            }
            else {
                categoryMessage = categoryMessage + ", " + categoriesList.get(i).getName();
            }
            i++;
        }
        return "TestType{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", collectingMethod='" + collectingMethod + '\'' +
                ", " + categoryMessage +
                '}';
    }

    /**
     * This method checks if a Test is equal to another one
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestType testType = (TestType) o;
        return Objects.equals(code, testType.code) && Objects.equals(description, testType.description) && Objects.equals(collectingMethod, testType.collectingMethod) && Objects.equals(categoriesList, testType.categoriesList);
    }

    /**
     * This method returns the hashcode of a Test
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(code, description, collectingMethod, categoriesList);
    }
}
