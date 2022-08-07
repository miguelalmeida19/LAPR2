package app.domain.store;

import app.Persistence;
import app.domain.model.NHSReport;
import app.domain.model.ParameterCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ParameterCategoryStore {
    private List<ParameterCategory> parameterCategoryList;

    /**
     * Constructor
     */
    public ParameterCategoryStore() {
        parameterCategoryList = new ArrayList<>();
        //parameterCategoryList.add(new ParameterCategory("he001", "Hemogram"));
        try{
            parameterCategoryList = (List<ParameterCategory>) Persistence.readObjectFromFile("parameterCategories.bin");
        }catch (Exception e){
            //System.out.println("The ParameterCategoryStore was not loaded :)");
        }
    }

    /**
     * This method creates a Parameter Category
     *
     * @param code
     * @param description
     * @return
     */
    public ParameterCategory createParametercategory(String code, String description) {
        return new ParameterCategory(code, description);
    }

    /**
     * This method checks if a Parameter Category is valid or not
     *
     * @param pc
     * @return
     */
    public boolean validateParameterCategory(ParameterCategory pc) {
        if (pc == null)
            throw new IllegalArgumentException("Parameter Category cannot be null");
        else if (this.parameterCategoryList.contains(pc)) {
            throw new IllegalArgumentException("Parameter Category already exists");
        }
        return !this.parameterCategoryList.contains(pc);
    }

    /**
     * This method saves a Parameter Category
     *
     * @param pc
     * @return
     */
    public boolean saveParameterCategory(ParameterCategory pc) {
        if (!validateParameterCategory(pc))
            return false;
        return this.parameterCategoryList.add(pc);
    }

    /**
     * This method returns the Parameter Category matching a specific description
     *
     * @param description
     * @return
     */
    public ParameterCategory getParameterCategoryByDescription(String description) {
        for (ParameterCategory pc : getParameterCategoryList()) {
            if (pc.getName().equals(description)) {
                return pc;
            }
        }
        return null;
    }

    /**
     * This method adds a Parameter Category to the list of parameter categories
     *
     * @param category
     * @return
     */
    public boolean add(ParameterCategory category) {
        if (category != null) {
            if (!parameterCategoryList.contains(category)) {
                parameterCategoryList.add(category);
                return true;
            }
        }
        return false;

    }

    /**
     * This method returns a list with all the Parameter Categories
     *
     * @return
     */
    public List<ParameterCategory> getParameterCategoryList() {
        return parameterCategoryList;
    }

    public String createParameterCategoryCode(String description) {
        char[] descriptionArray = description.toCharArray();
        String name = (String.valueOf(descriptionArray[0]) + String.valueOf(descriptionArray[1])).toLowerCase(Locale.ROOT);
        String number = "1";
        for (ParameterCategory p : parameterCategoryList) {
            if (p.getCode().contains(name)) {
                number = String.valueOf(Integer.parseInt(number) + 1);
            }
        }
        while (number.length() < 3) {
            number = "0" + number;
        }
        name = name + number;
        return name;
    }
}
