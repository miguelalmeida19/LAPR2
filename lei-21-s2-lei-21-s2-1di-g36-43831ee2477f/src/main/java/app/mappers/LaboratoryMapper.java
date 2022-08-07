package app.mappers;

import app.controller.App;
import app.domain.model.Company;
import app.domain.model.Laboratory;
import app.domain.model.ParameterCategory;
import app.domain.store.LaboratoryStore;
import app.domain.store.ParameterCategoryStore;
import app.mappers.dto.LaboratoryDTO;
import app.mappers.dto.ParameterCategoryDTO;

import java.util.ArrayList;
import java.util.List;

public class LaboratoryMapper {

    /**
     * Constructor
     */
    public LaboratoryMapper(){
        //removed
    }

    /**
     * This method converts a list of laboratories to DTO
     * @param laboratoryList
     * @return
     */
    public List<LaboratoryDTO> toDTO(List<Laboratory> laboratoryList){
        List<LaboratoryDTO> listDTOs = new ArrayList<>();
        for(Laboratory l : laboratoryList){
            listDTOs.add(toDTO(l));
        }
        return listDTOs;
    }

    /**
     * This method converts a laboratory to DTO
     * @param l
     * @return
     */
    public LaboratoryDTO toDTO(Laboratory l){
        return new LaboratoryDTO(l.getNameOfLaboratory(), l.getID(), l.getAddress(), l.getPhoneNumber(), l.getTIN(), l.getTestsOfLab());
    }

    public LaboratoryDTO toDTOParam(Laboratory l){
        return new LaboratoryDTO(l.getAddress(), l.getID());
    }

    /**
     * This method converts a laboratoryDTO to a laboratory
     * @return
     */
    public Laboratory toLaboratory(LaboratoryDTO l){
        return  new Laboratory(l.getNameOfLaboratory(), l.getId(), l.getAddress(), l.getPhoneNumber(), l.getTin(), l.getTestsOfLab());
    }
}
