package app.domain.store;

import app.Persistence;
import app.domain.model.Laboratory;
import app.domain.model.Parameter;
import app.domain.model.ParameterCategory;
import app.domain.model.TestType;

import java.util.ArrayList;
import java.util.List;

public class TestTypeStore {
    private List<TestType> TestTypeList = new ArrayList<>();

    /**
     * Constructor
     */
    public TestTypeStore() {
        List<ParameterCategory> parameterCategoryListBlood = new ArrayList<>();
        TestTypeList.add(new TestType("12346", "Blood", "Analysis", parameterCategoryListBlood));

        List<ParameterCategory> parameterCategoryListCovid = new ArrayList<>();
        TestTypeList.add(new TestType("12345", "Covid", "Analysis", parameterCategoryListCovid));
        try{
            TestTypeList = (List<TestType>) Persistence.readObjectFromFile("testTypes.bin");
        }catch (Exception e){
            //System.out.println("The TestTypeStore was not loaded :)");
        }
    }

    /**
     * This method creates a new test type
     * @param code
     * @param description
     * @param collectingMethod
     * @param parameterCategoryList
     * @return
     */
    public TestType createTestType(String code, String description, String collectingMethod, List<ParameterCategory> parameterCategoryList) {
        return new TestType(code, description, collectingMethod, parameterCategoryList);
    }

    /**
     * This method checks if a test type already exists ou if it's null
     * @param testType
     */
    public void validateTest(TestType testType) {
        if(TestTypeList.contains(testType)){
            throw new IllegalArgumentException("invalid arguments for test creation.");
        }

    }

    /**
     * This method saves a new test type
     * @param testType
     */
    public void saveTest(TestType testType) {
        validateTest(testType);
        addTestType(testType);
    }

    /**
     * This method adds a new test type to the list of test types
     * @param testType
     */
    public void addTestType(TestType testType) {

        this.TestTypeList.add(testType);
    }

    /**
     * This method returns an array with all codes of all test types. this is used to show all tests on interface.
     * @return list with all codes
     */
    public List<String> getTestTypes(){
        List<String> codesOfTestTypes = new ArrayList<>();

        if(this.TestTypeList == null){
            return null;
        }
        for(TestType t : this.TestTypeList){
            codesOfTestTypes.add(t.getCode() + " - "+t.getDescription());
        }

        return codesOfTestTypes;
    }

    /**
     * This method converts a string with test type codes to testTypes objects.
     * @param testTypeCodes list of codes.
     * @return list with all tests.
     */
    public List<TestType> getTestTypeByCodes(List<String> testTypeCodes){
        List<TestType> testTypes = new ArrayList<>();
        for(String s: testTypeCodes){
            for(TestType t: TestTypeList){
                if((t.getCode() + " - "+t.getDescription()).equals(s)){
                    testTypes.add(t);
                }
            }
        }
        return testTypes;
    }

    public TestType getTestTypeByName(String name){
        for (TestType testType : TestTypeList){
            if (testType.getDescription().equals(name)){
                return testType;
            }
        }
        throw new IllegalArgumentException("Given TestType Name does not exist");
    }

    public List<TestType> getListOfTestType(){
        return TestTypeList;
    }

}