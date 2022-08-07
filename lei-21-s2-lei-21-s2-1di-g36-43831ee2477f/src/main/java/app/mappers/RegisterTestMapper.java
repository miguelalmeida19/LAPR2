package app.mappers;

import app.mappers.dto.LaboratoryDTO;
import app.mappers.dto.ParameterDTO;
import app.mappers.dto.RegisterTestDTO;
import app.mappers.dto.TestTypeDTO;

import java.util.List;

public class RegisterTestMapper {

    /**
     * This method converts a test registration to test DTO registration
     * @param test
     * @param parameters
     * @param nhsCode
     * @param laboratoryDTO
     * @param TIN
     * @return
     */
    public RegisterTestDTO toDTO(TestTypeDTO test, List<ParameterDTO> parameters, String nhsCode, LaboratoryDTO laboratoryDTO, String TIN) {
        return new RegisterTestDTO(test, parameters, nhsCode, laboratoryDTO, TIN);
    }
}


