package app.mappers;

import app.controller.App;
import app.domain.model.Company;
import app.domain.model.Parameter;
import app.domain.model.ParameterCategory;
import app.domain.store.ParameterCategoryStore;
import app.mappers.dto.ParameterCategoryDTO;
import app.mappers.dto.ParameterDTO;

import java.util.ArrayList;
import java.util.List;

public class ParameterCategoryMapper {
    private Company company;
    private ParameterCategoryStore parameterCategoryStore;

    /**
     * Constructor
     */
    public ParameterCategoryMapper(){
        company = App.getInstance().getCompany();
        parameterCategoryStore = company.getParameterCategoryStore();
    }

    /**
     * This method converts a List of ParameterCategoryDTO to a list of ParameterCategory
     * @param parameterCategoryList
     * @return
     */
    public List<ParameterCategoryDTO> toDTO(List<ParameterCategory> parameterCategoryList){
        List<ParameterCategoryDTO> listDTOs = new ArrayList<>();
        for(ParameterCategory p : parameterCategoryList){
            listDTOs.add(toDTO(p));
        }
        return listDTOs;
    }

    /**
     * This method converts a Parameter Category to a Parameter Category DTO
     * @param p
     * @return
     */
    public ParameterCategoryDTO toDTO(ParameterCategory p){
        return new ParameterCategoryDTO(p);
    }

    /**
     * This method converts a Parameter Category DTO List to a Parameter Category List
     * @param parameterCategoryDTOList
     * @return
     */
    public List<ParameterCategory> toParameterCategoryList(List<ParameterCategoryDTO> parameterCategoryDTOList){
        List<ParameterCategory> parameterCategoryList = new ArrayList<>();
        for(ParameterCategoryDTO p : parameterCategoryDTOList){
            for(ParameterCategory t : parameterCategoryStore.getParameterCategoryList()){
                if(t.getCode().equals(p.getCode())){
                    parameterCategoryList.add(t);
                }
            }
        }
        return parameterCategoryList;
    }
}
