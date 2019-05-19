package doctor.aysst.www.entitys;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Document(collection = "fanuc")
public class CNCProblem implements Serializable {

    @Id
    private Long id;

    private String problemEquipmentBrand;
    private String problemEquipmentSystem;
    private String problemFaultType;
    private String problemFaultId;
    private String problemFaultDescription;
    private String problemFaultResponse;
    private String problemFaultSolution;
    private String problemFault;

    public CNCProblem(String problemFault, String problemEquipmentBrand, String problemEquipmentSystem, String problemFaultType, String problemFaultId, String problemFaultDescription, String problemFaultResponse, String problemFaultSolution) {
        this.problemFault = problemFault;
        this.problemEquipmentBrand = problemEquipmentBrand;
        this.problemEquipmentSystem = problemEquipmentSystem;
        this.problemFaultType = problemFaultType;
        this.problemFaultId = problemFaultId;
        this.problemFaultDescription = problemFaultDescription;
        this.problemFaultResponse = problemFaultResponse;
        this.problemFaultSolution = problemFaultSolution;
    }
    public CNCProblem(String problemEquipmentBrand, String problemEquipmentSystem, String problemFaultType, String problemFaultId, String problemFaultDescription, String problemFaultResponse, String problemFaultSolution) {
        this.problemEquipmentBrand = problemEquipmentBrand;
        this.problemEquipmentSystem = problemEquipmentSystem;
        this.problemFaultType = problemFaultType;
        this.problemFaultId = problemFaultId;
        this.problemFaultDescription = problemFaultDescription;
        this.problemFaultResponse = problemFaultResponse;
        this.problemFaultSolution = problemFaultSolution;
    }
    public CNCProblem(String problemEquipmentBrand, String problemEquipmentSystem, String problemFaultType, String problemFaultId, String problemFaultDescription, String problemFaultSolution) {
        this.problemEquipmentBrand = problemEquipmentBrand;
        this.problemEquipmentSystem = problemEquipmentSystem;
        this.problemFaultType = problemFaultType;
        this.problemFaultId = problemFaultId;
        this.problemFaultDescription = problemFaultDescription;
        this.problemFaultSolution = problemFaultSolution;
    }
    public CNCProblem(String problemEquipmentBrand, String problemEquipmentSystem, String problemFaultType, String problemFaultDescription, String problemFaultSolution) {
        this.problemEquipmentBrand = problemEquipmentBrand;
        this.problemEquipmentSystem = problemEquipmentSystem;
        this.problemFaultType = problemFaultType;
        this.problemFaultDescription = problemFaultDescription;
        this.problemFaultSolution = problemFaultSolution;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public String getProblemEquipmentBrand() {
        return problemEquipmentBrand;
    }
    public void setProblemEquipmentBrand(String problemEquipmentBrand) {
        this.problemEquipmentBrand = problemEquipmentBrand;
    }

    public String getProblemEquipmentSystem() {
        return problemEquipmentSystem;
    }
    public void setProblemEquipmentSystem(String problemEquipmentSystem) {
        this.problemEquipmentSystem = problemEquipmentSystem;
    }

    public String getProblemFault() {
        return problemFault;
    }
    public void setProblemFault(String problemFault) {
        this.problemFault = problemFault;
    }

    public String getProblemFaultDescription() {
        return problemFaultDescription;
    }
    public void setProblemFaultDescription(String problemFaultDescription) {
        this.problemFaultDescription = problemFaultDescription;
    }

    public String getProblemFaultId() {
        return problemFaultId;
    }
    public void setProblemFaultId(String problemFaultId) {
        this.problemFaultId = problemFaultId;
    }

    public String getProblemFaultResponse() {
        return problemFaultResponse;
    }
    public void setProblemFaultResponse(String problemFaultResponse) {
        this.problemFaultResponse = problemFaultResponse;
    }

    public String getProblemFaultSolution() {
        return problemFaultSolution;
    }
    public void setProblemFaultSolution(String problemFaultSolution) {
        this.problemFaultSolution = problemFaultSolution;
    }

    public String getProblemFaultType() {
        return problemFaultType;
    }
    public void setProblemFaultType(String problemFaultType) {
        this.problemFaultType = problemFaultType;
    }

    public String toString() {
        String str = "problem_fault:" + getProblemFault() +
                "\nproblem_equipment_system:" + getProblemEquipmentSystem() +
                "\nproblem_equipment_brand:" + getProblemEquipmentBrand() +
                "\nproblem_fault_type:" + getProblemFaultType() +
                "\nproblem_fault_description:" + getProblemFaultDescription() +
                "\nproblem_fault_response:" + getProblemFaultResponse() +
                "\nproblem_fault_id:" + getProblemFaultId() +
                "\nproblem_fault_solution:" + getProblemFaultSolution();

        return str;
    }
}
