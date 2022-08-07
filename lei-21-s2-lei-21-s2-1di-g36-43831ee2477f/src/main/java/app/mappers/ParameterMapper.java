package app.mappers;

import app.controller.App;
import app.domain.model.Parameter;
import app.domain.store.ParameterStore;
import app.mappers.dto.ParameterDTO;

import java.util.ArrayList;
import java.util.List;

public class ParameterMapper {

    private final ParameterStore parameterStore;

    /**
     * Constructor
     */
    public ParameterMapper(){
        parameterStore = App.getInstance().getCompany().getParameterStore();
    }

    /**
     * Method to create a list of DTOs with
     * @param parameterList list of parameters
     * @return list of Parameter DTOs
     */
    public List<ParameterDTO> toDTO(List<Parameter> parameterList){
        List<ParameterDTO> listDTOs = new ArrayList<>();
        for(Parameter p : parameterList){
            listDTOs.add(toDTO(p));
        }
        return listDTOs;
    }

    /**
     * This method converts a Parameter to ParameterDTO
     * @param parameter
     * @return
     */
    public ParameterDTO toDTO(Parameter parameter){
        return new ParameterDTO(parameter);
    }

    /**
     * This method converts a list of ParameterDTOs to a list of Parameters
     * @param parameters
     * @return
     */
    public List<Parameter> toParameterList(List<ParameterDTO> parameters){
        List<Parameter> parametersList = new ArrayList<>();
        for(ParameterDTO p : parameters){
            for(Parameter t : parameterStore.getListOfParameters()){
                if(t.getCode().equals(p.getCode())){
                    parametersList.add(t);
                }
            }
        }
        return parametersList;
    }
}
