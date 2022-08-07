package app.mappers.dto;

import java.util.List;

public class RegisterTestDTO {
    private TestTypeDTO test;
    private List<ParameterDTO> parameterDTOS;
    private String nhsCode;
    private LaboratoryDTO laboratoryDTO;
    private String TIN;

    /**
     * Constructor
     * @param test
     * @param parameterDTOS
     * @param nhsCode
     * @param laboratoryDTO
     * @param TIN
     */
    public RegisterTestDTO(TestTypeDTO test,List<ParameterDTO> parameterDTOS, String nhsCode, LaboratoryDTO laboratoryDTO, String TIN){
        this.test = test;
        this.TIN = TIN;
        this.laboratoryDTO = laboratoryDTO;
        this.parameterDTOS = parameterDTOS;
        this.nhsCode = nhsCode;
    }

    /**
     * Returns a list of parameters of a registered test
     * @return
     */
    public List<ParameterDTO> getParameterDTOS() {
        return parameterDTOS;
    }

    /**
     * returns the laboratory of a registered test
     * @return
     */
    public LaboratoryDTO getLaboratoryDTO() {
        return laboratoryDTO;
    }

    /**
     * returns the tin of a registered test
     * @return
     */
    public String getTIN() {
        return TIN;
    }

    /**
     * returns the NHS Code of a registered test
     * @return
     */
    public String getNhsCode() {
        return nhsCode;
    }

    /**
     * Returns the type of test in a registered test
     * @return
     */
    public TestTypeDTO getTestType() {
        return test;
    }
}

