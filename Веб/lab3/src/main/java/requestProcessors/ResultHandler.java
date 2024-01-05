package requestProcessors;

import beans.ResultBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import tools.MessageHandler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Named @SessionScoped
public class ResultHandler implements Serializable {

    private final ResultBean result = new ResultBean();
    private final List<ResultBean> resultList = new ArrayList<>();
    private final List<Double> availableR = List.of(1.0, 1.5, 2.0, 2.5, 3.0);
    @Setter
    private List<Double> selectedR = new ArrayList<>();

    @PostConstruct
    public void init() {
        result.setY(0.0);
    }

    public void execute() {
        for (Double r : selectedR) {
            result.setR(r);
            saveResult(result);
        }
    }

    public void setResultParams() {
        Map<String, String> requestParameters = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String error = requestParameters.get("error");
        if (error != null) {
            MessageHandler.error(error);
            return;
        }
		ResultBean result = new ResultBean();
        try {
            result.setX(Double.parseDouble(requestParameters.get("x")));
            result.setY(Double.parseDouble(requestParameters.get("y")));
            result.setR(Double.parseDouble(requestParameters.get("r")));
        } catch (NumberFormatException | NullPointerException e) {
            MessageHandler.error("Некорректное значение.");
            return;
        }
        saveResult(result);
    }

    private void saveResult(ResultBean shot) {
        ResultBean newResult = DatabaseHandler.create(shot);
        resultList.add(newResult);
    }
}
